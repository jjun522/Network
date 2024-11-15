import java.io.*;
import java.net.*;

public class QuizClient {
    private static final String SERVER_IP = "localhost"; // 서버 IP 주소
    private static final int PORT = 1234; // 서버 포트 번호

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_IP, PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to Quiz Server.");

            String question;

            while ((question = in.readLine()) != null) {
                System.out.println("Definition: " + question); // 정의 출력
                String answer = userInput.readLine(); // 사용자 입력
                out.println(answer); // 서버로 답변 전송

                String feedback = in.readLine(); // 피드백 수신
                System.out.println(feedback);

                if (feedback.startsWith("Quiz Over!")) {
                    break; // 퀴즈 종료 시 루프 종료
                } else if (feedback.equals("Correct!")) {
                    continue; // 정답을 맞췄을 때 다음 질문으로 넘어감
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }
}
