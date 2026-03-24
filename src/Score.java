public class Score {
    private String project;
    private int score;


    public Score(String project, int score) {
        this.project = project;
        this.score = score;
    }


    public String getProject() {
        return project;
    }

    public int getScore() {
        return score;
    }


@Override
    public String toString() {
        return this.project+","+this.score;
    }



    public static String scoreDisplay(Score score) {
        return score.getProject()+","+score.getScore();
    }
    //using for write data in document


    public static String scoreDisplay2(Score score) {
        if(score.getScore()<0){
            return score.getProject()+","+"null";
        }else {
            return score.getProject() + "," + score.getScore();
        }
    }
    //using for history


    public static Score[] updata(Score[] scores,int finalScore,String project) {
        int tmp1= scores[1].getScore();
        int tmp2= scores[0].getScore();
        scores[2]= new Score(project,tmp1);
        scores[1]= new Score(project,tmp2);
        scores[0]= new Score(project,finalScore);
        return scores;
    }
    //using for update the history


    public static Score[] updata2(Score[] scores,int finalScore,int index) {
        scores[index]=new Score(scores[index].getProject(),finalScore);
        return scores;
    }
    //using for update the best record
}
