package xjtlu.cpt111.assignment.quiz;

import xjtlu.cpt111.assignment.quiz.model.Option;
import xjtlu.cpt111.assignment.quiz.model.Question;
import xjtlu.cpt111.assignment.quiz.util.IOUtilities;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
public class ReadQuestions {





    public static int readQuestions(String path) {

        int score = 0;
        //initial score

        try {
            System.out.println("===\n=== read questions - started\n===");
            Question[] questions = IOUtilities.readQuestions(path);
            if (null == questions || questions.length == 0) {
                System.out.println("Questions is empty!");
            }
            //check qusetions exist or not
            else {
                Scanner scanner = new Scanner(System.in);
                Random rand = new Random();
                for (int i = 0; i < questions.length; i++) {
                    int randomIndex = rand.nextInt(questions.length);
                    Question question = questions[i];
                    questions[i] = questions[randomIndex];
                    questions[randomIndex] = question;
                }
                //disrupt the order of qusetions
                int numQuestions = 0;

                for (int i = 0; i <10; i++) {
                    Option[] options=questions[i].getOptions();
                    String correctAnswer = String.valueOf(options[0]);
                    for (int j = 0; j < options.length; j++) {
                        int randomIndex = rand.nextInt(options.length);
                        Option option = options[j];
                        options[j] = options[randomIndex];
                        options[randomIndex] = option;
                    }
                    //disrupt the order of options



                    questions[i].setOptions(List.of(options));
                    System.out.println("Question #" + (++numQuestions) + " " + questions[i].toString());
                    while(true){
                        System.out.println("Please enter your answer:");
                        String answer = scanner.nextLine();
                        if(!Objects.equals(answer, "1")&&!Objects.equals(answer, "2")&&!Objects.equals(answer, "3")&&!Objects.equals(answer, "4")) {
                            System.out.println("illegal answer");
                        } else {
                            int tip = Integer.parseInt(answer);
                            if (Objects.equals(String.valueOf(options[tip - 1]), correctAnswer)) {
                                score += 10;
                                break;
                            } else {
                                break;
                            }
                        }
                        //print qusetions and check the answer
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;

    }

}


