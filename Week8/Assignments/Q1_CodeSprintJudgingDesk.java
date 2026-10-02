import java.util.*;

interface ScoringRule {
    double calculate(Score score);
}

class InnovationScoring implements ScoringRule {
    public double calculate(Score s) {
        return s.idea * 0.5 + s.execution * 0.3 + s.presentation * 0.2;
    }
}

class OpenScoring implements ScoringRule {
    public double calculate(Score s) {
        return (s.idea + s.execution + s.presentation) / 3.0;
    }
}

class Score {
    int idea, execution, presentation;

    Score(int idea, int execution, int presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }
}

class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}

class Team {
    String name;
    ArrayList<Student> members = new ArrayList<>();
    Project project;
    Score score;

    Team(String name) {
        this.name = name;
    }

    void addMember(Student student) {
        if (members.size() < 4)
            members.add(student);
    }

    void submitProject(Project project) {
        this.project = project;
    }
}

class Project {
    String name;

    Project(String name) {
        this.name = name;
    }
}

class Judge {
    String name;

    Judge(String name) {
        this.name = name;
    }

    void giveScore(Team team, Score score) {
        team.score = score;
    }
}

class Hackathon {
    String name;
    String track;
    String state = "OPEN";
    ScoringRule scoringRule;
    ArrayList<Team> teams = new ArrayList<>();

    Hackathon(String name, String track) {
        this.name = name;
        this.track = track;

        if (track.equalsIgnoreCase("Innovation"))
            scoringRule = new InnovationScoring();
        else
            scoringRule = new OpenScoring();
    }

    void registerTeam(Team team) {
        if (team.members.size() >= 2 && team.members.size() <= 4)
            teams.add(team);
        else
            System.out.println("Team registration failed: team size must be 2-4");
    }

    void startJudging() {
        state = "JUDGING";
        System.out.println("Hackathon state: " + state);
    }

    void publishResults() {
        state = "PUBLISHED";
        System.out.println("Hackathon state: " + state);
    }

    void showResults() {
        for (Team team : teams) {
            if (team.score != null) {
                double result = scoringRule.calculate(team.score);
                System.out.printf("%s - Score: %.2f%n", team.name, result);
            }
        }
    }

    void rescore(Team team, Score score) {
        if (state.equals("PUBLISHED")) {
            System.out.println("Rescore rejected: results already published");
        } else {
            team.score = score;
        }
    }
}

public class Q1_CodeSprintJudgingDesk {

    public static void main(String[] args) {

        Hackathon hackathon = new Hackathon("Code Sprint", "Innovation");

        Team byteBusters = new Team("ByteBusters");

        byteBusters.addMember(new Student("Asha"));
        byteBusters.addMember(new Student("Ravi"));
        byteBusters.addMember(new Student("Kiran"));

        byteBusters.submitProject(new Project("SmartAttend"));

        hackathon.registerTeam(byteBusters);

        // Invalid team
        Team soloCoder = new Team("SoloCoder");
        soloCoder.addMember(new Student("Arun"));
        hackathon.registerTeam(soloCoder);

        hackathon.startJudging();

        Judge judge = new Judge("Judge 1");
        judge.giveScore(
                byteBusters,
                new Score(8, 7, 9));

        hackathon.showResults();

        hackathon.publishResults();

        // Try to change score after publishing
        hackathon.rescore(
                byteBusters,
                new Score(10, 10, 10));
    }
}