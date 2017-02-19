import javax.servlet.http.Part;
import java.io.*;
import java.util.Arrays;


public class Damage {

    public static String[] getItem(Part file, String item) throws IOException {

        String[] bolts = null;
        String[] nuts = null;
        InputStream buff = null;
        DataInputStream data = null;
        try {
            buff = new BufferedInputStream(file.getInputStream());
            data = new DataInputStream(buff);
            bolts = data.readLine().split(" ");
            nuts = data.readLine().split(" ");
        }finally {
            if(data != null) data.close();
            if(buff != null) buff.close();
        }

        if(item.equals("bolts")) return bolts;
        if(item.equals("nuts")) return nuts;
        return null;
    }
    public static int damageSum(String[] bolts, String[] nuts) throws IOError{

        int damage;
        int[] arr1 = Arrays.stream(bolts).mapToInt(Integer::parseInt).toArray();
        int[] arr2 = Arrays.stream(nuts).mapToInt(Integer::parseInt).toArray();
        int a = arr1[0]-(arr1[0]*arr1[1]/100);
        int b = arr2[0]-(arr2[0]*arr2[1]/100);
        if(a < b){
            damage = (arr1[0]-a)*arr1[2] + (arr2[0]-a)*arr2[2];
        }else{
            damage = (arr1[0]-b)*arr1[2] + (arr2[0]-b)*arr2[2];}
        return damage;
    }
}


