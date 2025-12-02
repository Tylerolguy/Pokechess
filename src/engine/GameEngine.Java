package src;





public class GameEngine implements Runnable  {



    new Thread(this).start();
    public void run() {
        while (true) {
            repaint();
            try { Thread.sleep(16); } catch (Exception ignored) {}
        }
    }



}