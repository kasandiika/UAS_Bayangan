package id.ac.uinjkt.springaiuas;

public class AnswerNotRelevantException extends RuntimeException {

    public AnswerNotRelevantException(String question, String answer) {
        super("Jawaban '" + answer + "' tidak relevan dengan pertanyaan '" + question + "'.");
    }
}
