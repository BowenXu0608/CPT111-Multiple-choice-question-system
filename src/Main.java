import xjtlu.cpt111.assignment.quiz.ReadQuestions;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Scanner;
import java.io.IOException;

public class Main {

    public static int getLinecount(File file) {
        int lineCount = 0;
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                input.nextLine();
                lineCount++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lineCount;
    }
    //Aim to get the line number.


    public static Score[] getHistory(int time, String[] values){
        Score[] scores = new Score[3];
        for (int k = 0; k < 3; k++) {
            int c = Integer.parseInt(values[time + 1]);
            Score score = new Score(values[time], c);
            scores[k] = score;
            time += 2;
        }
        return scores;
    }
    //Aim to get the history of four subject.
    //Pay attention to adjust the time.


    public static Information[] getInformation(File file, int linecount) {
        Information[] userInformation = new Information[linecount];
        try {
            Scanner input = new Scanner(file);
            int i = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] values = line.split(",");
                Score[] history_CS;
                Score[] history_EE;
                Score[] history_EG;
                Score[] history_MT;
                Score[] highestScore = new Score[4];
                String Id = values[0];
                String password = values[1];
                String name = values[2];
                int time = 3;
                history_CS=getHistory(time,values);
                   time=9;
                history_EE=getHistory(time,values);
                time=15;
                history_EG=getHistory(time,values);
                time=21;
                history_MT=getHistory(time,values);
                time=27;

                for (int j = 0; j < 4; j++) {
                    int c = Integer.parseInt(values[time + 1]);
                    Score score = new Score(values[time], c);
                    highestScore[j] = score;
                    time += 2;
                }
                Information information = new Information(Id, password, name, history_CS,history_EE,history_EG,history_MT, highestScore);
                userInformation[i] = information;
                i++;
            }
            input.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return userInformation;
    }
    //Aim to read and store user data in an array of class "information".
    //Information in document is "userid, password, username, history(CS, EE, EG, MT), highest record(CS, EE,EG,MT)"



    public static boolean isExistId(String userId,int linecount,Information[] userInformation) {
        for (int i = 0; i < linecount; i++) {
            String checkId = userInformation[i].getUserId();
            if(Objects.equals(checkId, userId)){
                return true;
            }
        }
        return false;
    }
    //Aim to check if the id has been used.


    public static int getPlace(String userId,int linecount,Information[] userInformation) {
        int place = 0;
        for (int i = 0; i < linecount; i++) {
            if (Objects.equals(userInformation[i].getUserId(), userId)) {
                place = i;
            }
        }
        return place;
    }
    //Aim to make sure whose data is used.



    public static boolean isRightPassword(String password,int place,Information[] userInformation) {
        String checkPassword = userInformation[place].getPassword();
        return Objects.equals(checkPassword, password);
    }
    //Aim to check if the password is right.


      public static int findBestPerson(Information[] newUserInformation,int linecount,int index) {
          int tmp = -2;
          int kingPlace = 0;
          for (int i = 0; i < linecount; i++) {
              int c = newUserInformation[i].getHighestScore()[index].getScore();
              if (c > tmp) {
                  tmp = c;
                  kingPlace = i;
              }


          }
          return kingPlace;
      }
      //Aim to get the best score and who did it.

      public static int[] recordRepeatBest(Information[] newUserInformation,int linecount,int index,int kingplace) {
        int man = 0;
        for (int i = 0; i < linecount; i++) {
            int c = newUserInformation[i].getHighestScore()[index].getScore();
            int d =newUserInformation[kingplace].getHighestScore()[index].getScore();
            if(c==d){
                man++;
            }

        }
        int[] repeatPlace = new int[man];
        int time = 0;
          for (int i = 0; i < linecount; i++) {
              int c = newUserInformation[i].getHighestScore()[index].getScore();
              int d =newUserInformation[kingplace].getHighestScore()[index].getScore();
              if(c==d){
                  repeatPlace[time] = i;
                  time++;
              }

          }
        return repeatPlace;
      }
      //Aim to check if people have the same score.










    public static void main(String[] args) {
        String CS_PATH = "question\\questions_cs.xml";
        String EE_PATH = "question\\questions_ee.xml";
        String EG_PATH = "question\\questions_english.xml";
        String MT_PATH = "question\\questions_mathematics.xml";
        //data path
        int finalScore;
        //the score person get this time.
        Score[] newbest;
        //use to find the highest score.
        File file = new File("userdata\\userdata.txt");
        //user data document
        int lineCount = getLinecount(file);
        Information[] userInformation;
        userInformation = getInformation(file, lineCount);
        //read document
        Scanner input = new Scanner(System.in);
        int place = -1;
        //set initial place (not in array)
        Information[] newUserInformation;
        while (true) {
            System.out.println("Please register / login (1:register, 2:login):");
            String enter = input.nextLine();
            //check is the new user or not
            if (!enter.equals("1") && !enter.equals("2")) {
                System.out.println("Please enter correct number:");//check input is 1/2 or not.
            } else if (enter.equals("2")) {
                System.out.println("Please enter your userid:");
                String userid = input.nextLine();
                System.out.println("Please enter your password:");
                String password = input.nextLine();
                if (!isExistId(userid, lineCount, userInformation)) {
                    System.out.println("ID don't exist!");//Check if Id exists.
                } else {
                    place = getPlace(userid, lineCount, userInformation);//get the place in array
                    if (!isRightPassword(password, place, userInformation)) {
                        System.out.println("Wrong password!");//check password
                    } else {
                        System.out.println("Welcome back!");
                        newUserInformation=userInformation;//update the information (nothing is changed)
                        break;
                    }
                }
            }
            //login part
            else {
                System.out.println("Please enter your firstname (It will be used as your userid):");
                String firstname = input.nextLine();
                System.out.println("Please enter your lastname:");
                String lastname = input.nextLine();
                String username = firstname + "_" + lastname;
                if (isExistId(firstname, lineCount, userInformation)) {
                    System.out.println("ID have been used!\nPlease change username or use XXX(2) form.");//Not allow people use same ID
                } else {
                    System.out.println("Please enter your password:");
                    String password = input.nextLine();
                    newUserInformation = Information.addUserData(lineCount, userInformation, firstname, password, username);//update the information( add a new user)
                    System.out.println("Welcome!");
                    lineCount++;//because add one user
                    //register part

                    try {
                        FileWriter filewriter = new FileWriter("userdata\\userdata.txt");
                        BufferedWriter buffer = new BufferedWriter(filewriter);
                        for (int i = 0; i < lineCount; i++) {
                            buffer.write(Information.dataDisplay(newUserInformation[i]));
                            buffer.newLine();
                            buffer.flush();
                        }
                        buffer.close();
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }//update the txt document
                    place = lineCount - 1;//get the place in array.
                    break;
                }
                //new user write in document

            }


        }
        while (true) {
        System.out.println("Please choose your action(1:Answer the questions, 2:Watch the user dashboard, 3:Watch the Leaderboard, 4:Over):");
        String action = input.nextLine();
            if (!action.equals("1") && !action.equals("2") && !action.equals("3")&& !action.equals("4")) {
                System.out.println("Please enter correct number:");

            }// illegal input
            if (action.equals("1")) {
                while(true) {
                    System.out.println("Please enter the subject(1:cs, 2:ee, 3:english, 4:mathematics, 5:Over):");
                    String subject = input.nextLine();
                    if(!subject.equals("1")&&!subject.equals("2")&&!subject.equals("3")&&!subject.equals("4")&&!subject.equals("5")) {
                        System.out.println("Please enter correct number:");
                    } else if (subject.equals("5")) {
                        break;// jump out the loop
                    } else if (subject.equals("1")) {
                        System.out.println("You will answer the cs question");
                        finalScore=ReadQuestions.readQuestions(CS_PATH);
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println("the score is "+finalScore+"!");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        if(finalScore>newUserInformation[place].getHighestScore()[0].getScore()) {
                           newbest=Score.updata2(newUserInformation[place].getHighestScore(),finalScore,0);
                        }
                        else {
                            newbest=newUserInformation[place].getHighestScore();
                        }
                        Score[] newHistory;
                        newHistory=Score.updata(newUserInformation[place].getHistory_CS(),finalScore,"CS");
                        newUserInformation[place]=new Information(newUserInformation[place].getUserId(),newUserInformation[place].getPassword(),newUserInformation[place].getUserName(),newHistory,newUserInformation[place].getHistory_EE(),newUserInformation[place].getHistory_EG(),newUserInformation[place].getHistory_MT(),newbest);
                        //update the information
                        try {
                            FileWriter filewriter = new FileWriter("userdata\\userdata.txt");
                            BufferedWriter buffer = new BufferedWriter(filewriter);
                            for (int i = 0; i < lineCount; i++) {
                                buffer.write(Information.dataDisplay(newUserInformation[i]));
                                buffer.newLine();
                                buffer.flush();
                            }
                            buffer.close();
                        } catch (IOException ioe) {
                            System.out.println(ioe.getMessage());
                        }//update the txt document
                        break;
                    }
                    //Answering CS qusetions part



                    else if (subject.equals("2")) {
                        System.out.println("You will answer the ee question");
                        finalScore=ReadQuestions.readQuestions(EE_PATH);
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println("the score is "+finalScore+"!");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        if(finalScore>newUserInformation[place].getHighestScore()[1].getScore()) {
                            newbest=Score.updata2(newUserInformation[place].getHighestScore(),finalScore,1);
                        }
                        else {
                            newbest=newUserInformation[place].getHighestScore();
                        }
                        Score[] newHistory;
                        newHistory=Score.updata(newUserInformation[place].getHistory_EE(),finalScore,"EE");
                        newUserInformation[place]=new Information(newUserInformation[place].getUserId(),newUserInformation[place].getPassword(),newUserInformation[place].getUserName(),newUserInformation[place].getHistory_CS(),newHistory,newUserInformation[place].getHistory_EG(),newUserInformation[place].getHistory_MT(),newbest);
                        //update the information
                        try {
                            FileWriter filewriter = new FileWriter("userdata\\userdata.txt");
                            BufferedWriter buffer = new BufferedWriter(filewriter);
                            for (int i = 0; i < lineCount; i++) {
                                buffer.write(Information.dataDisplay(newUserInformation[i]));
                                buffer.newLine();
                                buffer.flush();
                            }
                            buffer.close();
                        } catch (IOException ioe) {
                            System.out.println(ioe.getMessage());
                        }//update the txt document
                        break;
                    }
                    //Answer EE questions part



                    else if (subject.equals("3")) {
                        System.out.println("You will answer the english question");
                        finalScore=ReadQuestions.readQuestions(EG_PATH);
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println("the score is "+finalScore+"!");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        if(finalScore>newUserInformation[place].getHighestScore()[2].getScore()) {
                            newbest=Score.updata2(newUserInformation[place].getHighestScore(),finalScore,2);
                        }
                        else {
                            newbest=newUserInformation[place].getHighestScore();
                        }
                        Score[] newHistory;
                        newHistory=Score.updata(newUserInformation[place].getHistory_EG(),finalScore,"EG");
                        newUserInformation[place]=new Information(newUserInformation[place].getUserId(),newUserInformation[place].getPassword(),newUserInformation[place].getUserName(),newUserInformation[place].getHistory_CS(),newUserInformation[place].getHistory_EE(),newHistory,newUserInformation[place].getHistory_MT(),newbest);
                        //update the information
                        try {
                            FileWriter filewriter = new FileWriter("userdata\\userdata.txt");
                            BufferedWriter buffer = new BufferedWriter(filewriter);
                            for (int i = 0; i < lineCount; i++) {
                                buffer.write(Information.dataDisplay(newUserInformation[i]));
                                buffer.newLine();
                                buffer.flush();
                            }
                            buffer.close();
                        } catch (IOException ioe) {
                            System.out.println(ioe.getMessage());
                        }//update the txt document
                        break;
                    }
                    //Answer EG questions part



                    else {
                        System.out.println("You will answer the mathematics question");
                        finalScore=ReadQuestions.readQuestions(MT_PATH);
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println("the score is "+finalScore+"!");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        if(finalScore>newUserInformation[place].getHighestScore()[3].getScore()) {
                            newbest=Score.updata2(newUserInformation[place].getHighestScore(),finalScore,3);
                        }
                        else {
                            newbest=newUserInformation[place].getHighestScore();
                        }
                        Score[] newHistory;
                        newHistory=Score.updata(newUserInformation[place].getHistory_MT(),finalScore,"MT");
                        newUserInformation[place]=new Information(newUserInformation[place].getUserId(),newUserInformation[place].getPassword(),newUserInformation[place].getUserName(),newUserInformation[place].getHistory_CS(),newUserInformation[place].getHistory_EE(),newUserInformation[place].getHistory_EG(),newHistory,newbest);
                        //update the information
                        try {
                            FileWriter filewriter = new FileWriter("userdata\\userdata.txt");
                            BufferedWriter buffer = new BufferedWriter(filewriter);
                            for (int i = 0; i < lineCount; i++) {
                                buffer.write(Information.dataDisplay(newUserInformation[i]));
                                buffer.newLine();
                                buffer.flush();
                            }
                            buffer.close();
                        } catch (IOException ioe) {
                            System.out.println(ioe.getMessage());
                        }//update the txt document
                        break;
                    }
                    //Answer MT questions part
                }


            }
            if (action.equals("2")) {
                while(true) {
                    System.out.println("Please enter the subject(1:cs, 2:ee, 3:english, 4:mathematics):");
                    String subject = input.nextLine();
                    if(!action.equals("1") && !action.equals("2") && !action.equals("3")&& !action.equals("4")){
                        System.out.println("Please enter correct number!");
                    } else if (subject.equals("1")){
                        for (int i = 0; i < 3; i++) {
                            System.out.println("("+(i+1)+")"+Score.scoreDisplay2(newUserInformation[place].getHistory_CS()[i]));
                        }
                        break;//CS part
                    }else if (subject.equals("2")){
                        for (int i = 0; i < 3; i++) {
                            System.out.println("("+(i+1)+")"+Score.scoreDisplay2(newUserInformation[place].getHistory_EE()[i]));
                        }
                        break;//EE part
                    }else if (subject.equals("3")){
                        for (int i = 0; i < 3; i++) {
                            System.out.println("("+(i+1)+")"+Score.scoreDisplay2(newUserInformation[place].getHistory_EG()[i]));
                        }
                        break;//EG part
                    }else if (subject.equals("4")){
                        for (int i = 0; i < 3; i++) {
                            System.out.println("("+(i+1)+")"+Score.scoreDisplay2(newUserInformation[place].getHistory_MT()[i]));
                        }
                        break;//MT part
                    }
                }

            }
            //Check person's history
            else if (action.equals("3")) {
                int bestCS = findBestPerson(newUserInformation, lineCount,0);
                int[] repeatCS=recordRepeatBest(newUserInformation, lineCount,0,bestCS);
                int bestEE = findBestPerson(newUserInformation, lineCount,1);
                int[] repeatEE=recordRepeatBest(newUserInformation, lineCount,1,bestEE);
                int bestEG = findBestPerson(newUserInformation, lineCount,2);
                int[] repeatEG=recordRepeatBest(newUserInformation, lineCount,2,bestEG);
                int bestMT = findBestPerson(newUserInformation, lineCount,3);
                int[] repeatMT=recordRepeatBest(newUserInformation, lineCount,3,bestMT);
                //find the best place in array
                System.out.print("Best CS: ");
                if(newUserInformation[bestCS].getHighestScore()[0].getScore()<0){
                    System.out.println("null");//if no record
                }else {
                    for (int repeatC : repeatCS) {
                        System.out.print(newUserInformation[repeatC].getUserName() + ", ");
                    }// more than one get best score
                    System.out.println(newUserInformation[bestCS].getHighestScore()[0].getScore());
                }
                System.out.print("Best EE: ");
                if(newUserInformation[bestEE].getHighestScore()[1].getScore()<0) {
                    System.out.println("null");//if no record
                }
                else {
                    for (int value : repeatEE) {
                        System.out.print(newUserInformation[value].getUserName() + ", ");
                    }// more than one get best score
                    System.out.println(newUserInformation[bestEE].getHighestScore()[1].getScore());
                }
                System.out.print("Best EG: ");
                if(newUserInformation[bestEG].getHighestScore()[2].getScore()<0) {
                    System.out.println("null");//if no record
                }
                else {
                    for (int k : repeatEG) {
                        System.out.print(newUserInformation[k].getUserName() + ", ");
                    }// more than one get best score
                    System.out.println(newUserInformation[bestEG].getHighestScore()[2].getScore());
                }
                System.out.print("Best MT: ");
                if(newUserInformation[bestMT].getHighestScore()[3].getScore()<0) {
                    System.out.println("null");//if no record
                }
                else {
                    for (int j : repeatMT) {
                        System.out.print(newUserInformation[j].getUserName() + ", ");
                    }// more than one get best score
                    System.out.println(newUserInformation[bestMT].getHighestScore()[3].getScore());
                }
            }else if (action.equals("4")) {
                System.out.println("Goodbye!");
                break;
            }
            //Check the best score of all people


        }
    }
}




