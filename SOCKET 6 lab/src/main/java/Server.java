import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {
    private final static int THREAD_POOL_SIZE = 5;

    public static void main(String[] ar)    {
        int port = 07;
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept();
            System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            System.out.println();

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();


            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);


            String line = null;
            while(true) {
                line = in.readUTF();
                System.out.println("The dumb client just sent me this line : " + line);
                System.out.println("I'm sending it back...");
                ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
                    final int count = Integer.parseInt(line);
                    final double[] massive = new double[count];

                    for (int i = 0; i < count; i++){
                        massive[i] = Math.random() * 1000;
                        System.out.println(i + " el: " + massive[i]);
                    }


                double[] sumArray = new double[THREAD_POOL_SIZE];


                for (int i = 0; i < THREAD_POOL_SIZE; i++) {
                    final int finalJ = i;

                    executorService.execute (() -> {
                        int thread_part = finalJ;
                        for (int j = thread_part * (count / THREAD_POOL_SIZE); j < (thread_part + 1) * (count / THREAD_POOL_SIZE); j++) {
                            sumArray[thread_part] += massive[j];
                        }
                    });

                }

                executorService.shutdown();
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

                double sum = 0;
                for (double el : sumArray){
                    sum += el;
                }

                out.writeUTF(Double.toString(sum));
                out.flush();
                System.out.println("Waiting for the next line...");
                System.out.println();
            }
        } catch(Exception x) { x.printStackTrace(); }
    }
}
