import java.io.*;
import java.net.*;
import java.util.*;

public class QuizServer {
    private static final int PORT = 1234;

    // 질문(정의), 정답, 힌트 리스트
    private static final List<String[]> QUESTIONS = Arrays.asList(
            new String[]{"A place where you can borrow books.", "Library", "It starts with 'L'."},
            new String[]{"A large body of salt water.", "Ocean", "It starts with 'O'."},
            new String[]{"The process of moving air in and out of the lungs.", "Breathing", "It starts with 'B'."},
            new String[]{"A piece of furniture used for writing or working on.", "Desk", "It starts with 'D'."},
            new String[]{"The opposite of 'remember'.", "Forget", "It starts with 'F'."}
    );

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Quiz Server is running...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                int score = 0; // 클라이언트의 점수 초기화

                for (String[] qa : QUESTIONS) {
                    boolean correct = false; // 현재 문제의 정답 여부

                    while (!correct) {
                        out.println(qa[0]); // 정의 전송
                        String answer = in.readLine(); // 클라이언트 답변 수신

                        if (qa[1].equalsIgnoreCase(answer.trim())) {
                            out.println("Correct!"); // 정답
                            score++;
                            correct = true; // 다음 문제로 넘어가기
                        } else {
                            out.println("Incorrect! Hint: " + qa[2]); // 오답 시 힌트 제공
                        }
                    }
                }

                // 최종 점수 전송
                out.println("Quiz Over! Your score: " + score + "/" + QUESTIONS.size());
            }
            System.out.println("Client disconnected."); // 클라이언트 연결 종료
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
