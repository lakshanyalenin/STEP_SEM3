import java.util.*;

abstract class Question {
    protected int questionNumber;
    protected String questionText;
    protected String correctAnswer;

    public Question(int questionNumber, String questionText, String correctAnswer) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {

    public MultipleChoiceQuestion(int number, String text, String correctAnswer) {
        super(number, text, correctAnswer);
    }

    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Student {
    String name;

    public Student(String name) {
        this.name = name;
    }
}

class Examination {
    String title;
    ArrayList<Question> questions = new ArrayList<>();

    public Examination(String title) {
        this.title = title;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }
}

class Attempt {
    Student student;
    Examination examination;
    HashMap<Integer, String> answers = new HashMap<>();
    boolean submitted = false;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void start() {
        System.out.println("Examination '" + examination.title +
                "' started by " + student.name + ".");
    }

    public void answer(int questionNumber, String answer) {
        if (submitted) {
            System.out.println("Cannot change answer after submission.");
            return;
        }

        answers.put(questionNumber, answer);
        System.out.println("Question " + questionNumber +
                " answered with '" + answer + "'.");
    }

    public void submit() {
        submitted = true;
        System.out.println("Examination '" + examination.title +
                "' submitted successfully.");

        int correct = 0;

        for (Question q : examination.questions) {
            if (q.evaluate(answers.get(q.questionNumber))) {
                correct++;
            }
        }

        System.out.println("Result for '" + examination.title +
                "' attempt: " + correct + "/" +
                examination.questions.size() + " correct.");
    }
}

public class Q1_OnlineExaminationSystem {
    public static void main(String[] args) {

        Student student = new Student("Student");

        Examination exam = new Examination("Math Quiz");

        exam.addQuestion(new MultipleChoiceQuestion(
                1, "2 + 2 = ?", "A"));

        exam.addQuestion(new MultipleChoiceQuestion(
                2, "Capital of India?", "B"));

        Attempt attempt = new Attempt(student, exam);

        attempt.start();

        attempt.answer(1, "A");
        attempt.answer(2, "C");

        attempt.submit();

        attempt.answer(1, "B");
    }
}