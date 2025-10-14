public class Flashcard {

    


    private String front;
    private String back;
    private boolean leatrned;

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public boolean isLeatrned() {
        return leatrned;
    }

    public void setLeatrned(boolean leatrned) {
        this.leatrned = leatrned;
    }

    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
        this.leatrned = false;
    }

}
