public class Information {
    private String userId;
    private String password;
    private String userName;
    private Score[] history_CS;
    private Score[] history_EE;
    private Score[] history_EG;
    private Score[] history_MT;
    private Score[] highestScore;




    public Information(String userId, String password, String userName, Score[] history_CS,Score[] history_EE, Score[] history_EG,Score[] history_MT, Score[] highestScore) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.history_CS = history_CS;
        this.history_EE = history_EE;
        this.history_EG = history_EG;
        this.history_MT = history_MT;
        this.highestScore = highestScore;
    }


    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public Score[] getHistory_CS() {
        return history_CS;
    }

    public Score[] getHistory_EE() {
        return history_EE;
    }

    public Score[] getHistory_EG() {
        return history_EG;
    }

    public Score[] getHistory_MT() {
        return history_MT;
    }

    public Score[] getHighestScore() {
        return highestScore;
    }




    public static Information[] addUserData(int linecount, Information[] userInformation,String userId,String password,String userName) {
        Information[] newUserInformation = new Information[linecount + 1];
        Score block_CS = new Score("CS", -1);
        Score block_EE = new Score("EE", -1);
        Score block_EG = new Score("EG", -1);
        Score block_MT = new Score("MT", -1);
        Score[] blockHistory_CS = new Score[]{block_CS, block_CS, block_CS};
        Score[] blockHistory_EE = new Score[]{block_EE, block_EE, block_EE};
        Score[] blockHistory_EG = new Score[]{block_EG, block_EG, block_EG};
        Score[] blockHistory_MT = new Score[]{block_MT, block_MT, block_MT};
        Score[] blockHighestScore = new Score[]{block_CS, block_EE, block_EG, block_MT};
        Information blockInformation = new Information(userId, password, userName, blockHistory_CS, blockHistory_EE, blockHistory_EG, blockHistory_MT, blockHighestScore);
        for (int i = 0; i < linecount; i++) {
            newUserInformation[i] = userInformation[i];
        }
        newUserInformation[linecount] = blockInformation;
        return newUserInformation;
    }
    //Using for add the new user


    @Override
    public String toString() {
        return(userId+","+password+","+userName);
    }

    public static String dataDisplay(Information information) {
        String id=information.getUserId();
        String password=information.getPassword();
        String userName=information.getUserName();
        String history_CS=Score.scoreDisplay(information.history_CS[0])+","+Score.scoreDisplay(information.history_CS[1])+","+Score.scoreDisplay(information.history_CS[2]);
        String history_EE=Score.scoreDisplay(information.history_EE[0])+","+Score.scoreDisplay(information.history_EE[1])+","+Score.scoreDisplay(information.history_EE[2]);
        String history_EG=Score.scoreDisplay(information.history_EG[0])+","+Score.scoreDisplay(information.history_EG[1])+","+Score.scoreDisplay(information.history_EG[2]);
        String history_MT=Score.scoreDisplay(information.history_MT[0])+","+Score.scoreDisplay(information.history_MT[1])+","+Score.scoreDisplay(information.history_MT[2]);
        String highestScore=Score.scoreDisplay(information.highestScore[0])+","+Score.scoreDisplay(information.highestScore[1])+","+Score.scoreDisplay(information.highestScore[2])+","+Score.scoreDisplay(information.highestScore[3]);
        return id+","+password+","+userName+","+history_CS+","+history_EE+","+history_EG+","+history_MT+","+highestScore;
    }
    //Using for write the document



}
