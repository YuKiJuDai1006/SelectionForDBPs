import java.io.FileNotFoundException;
import java.io.PrintStream;
import static java.lang.Math.abs;

public class one2one {
    // Standards
    /*
        TO CHANGE HERE
     */
    private static int len = 6750;

    private static double limMZ = 0.01;
    private static double limTIC = 7; // 10%
    private static double limRT = 0.15;

    private static double mzDiff = 2;
    private static double TICDiff = 63.9; // 63.9%

    private static PrintStream ps1;
    static {
        try {
            ps1 = new PrintStream("2_Cl.txt"); // output name
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // read in data
    public static entry[] readFromTxt (String st){
        In in = new In(st);
        entry[] res = new entry[len+1];
        for (int i = 1; i <= len; i++){
            res[i] = new entry(in.readInt(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),
                    in.readInt(), in.readInt());
        }
        return res;
    }

    public static void main(String[] args) throws FileNotFoundException {
        entry[] entries = readFromTxt("data.txt");
        System.setOut(ps1);

        // lLim & rLim
        double lMZ = mzDiff-limMZ;
        double rMZ = mzDiff+limMZ;
        double lTIC = TICDiff-limTIC;
        double rTIC = TICDiff+limTIC;

        // print standards
        System.out.println("1.参数设置: ");
        System.out.println("   样本数量: " + len);
        System.out.println("   m/z偏差: " + limMZ);
        System.out.println("   TIC偏差: " + limTIC + "%");
        System.out.println("   RT偏差: " + limRT);
        System.out.println();
        System.out.println("2.ChemDraw标准: ");
        System.out.println("   m/z差: " + mzDiff);
        System.out.println("   TIC: 100.0% , " + String.format("%.1f", TICDiff) + "%");
        System.out.println();

        System.out.println("3.筛选标准: ");
        System.out.println("   质荷比:  M : (M+" + lMZ + ") ~ (M+" + rMZ +") ");
        System.out.println("   丰度比:  100.0% , " + String.format("%.1f", lTIC) + "%" +" ~ "
                + String.format("%.1f", rTIC) + "%");
        System.out.println("   保留时间差:  |RT_1 - RT_2| < " + limRT);
        System.out.println();

        System.out.println("4.可能的含氯样本: ");

        // #sample
        int counts = 1;

        // two iteration
        for (int i = 1; i < len; i++){
            double st1, st2, st3;
            for(int k = 1; k < i; k++){

                st1 = abs(entries[i].mz - entries[k].mz - mzDiff);
                st2 = abs(entries[i].TIC / entries[k].TIC * 100 - TICDiff);
                st3 = abs(entries[i].RT - entries[k].RT);

                // standards to meet
                if (st1 <= limMZ && st2 <= limTIC && st3 <= limRT){
                    System.out.println("   样本号: " + counts++);
                    System.out.println("   索引号: " + "#" + entries[k].index + " 和 " + "#" +  entries[i].index);

                    // all features
                    System.out.println("   质荷比差: " + String.format("%.4f", st1+mzDiff) +
                            "   丰度比: 100.0% , " + String.format("%.1f", entries[i].TIC/entries[k].TIC * 100.0) + "%" +
                            "   保留时间差: " + String.format("%.4f", st3));

                    // per feature
                    System.out.println("   #"+ entries[k].index + "  质荷比: " + String.format("%.4f", entries[k].mz) +
                            "   峰强度: " + entries[k].TIC +
                            "   保留时间: " + String.format("%.4f", entries[k].RT));
                    System.out.println("   #"+ entries[i].index + "  质荷比: " + String.format("%.4f", entries[i].mz) +
                            "   峰强度: " + entries[i].TIC +
                            "   保留时间: " + String.format("%.4f", entries[i].RT));

                    System.out.println();
                }
            }
        }
    }
}
