package sample.Tools;

import com.sun.javafx.beans.event.AbstractNotifyListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Commander {


    static String CORTEXM="7";



    //proccesor defines
    static String CPU_DEFINES= " -DSTM32F7 -DSTM3232F767xx";
    static String PROCESSOR= " stm32f767zi";


    //semihosing or no (semihosting alows target to use host facilities like keyboard and screen ex. printf() )
    static String USE_SEMIHOST =" --specs=rdimon.specs";
    static String USE_NOHOST= " --specs=nosys.specs";


    //Options for specific architecture
    static String ARCH_FLAGS = " -mthumb -march=armv7e-m" ;


    //wall flag
    static String WL_FLAG = " -Wall";


    //some optimization Flags
    static String OPT_FLAGS = " -ffunction-sections -fdata-sections -fno-builtin";

    // Don't link flag (it could be -c -S -E)
    static String NOLINK = " -c";

    // output flag

    static String OUT_FLAG = " -o";


    //with this flag u can use a costume link script
    static String LSCRIP_FLAG = " -T";

    //linker Script path
    static String DefaultLinkScrptDir = "";


    //compiler Directorie
    static String compilerDir = "";
    static String linkerDir = "";
    static String converterDir ="arm-none-eabi-objcopy";



    //Temp out dir

    static String outTempDir = "";


    //Input Files Directories
    static String wifiModuleDir = "";
    static String GSMModuleDir = "";
    static String LoRaModuleDir = "";
    static String GpioModuleDir = "";



    static public void runCommand(String command){
        //this func is used to to run a command on command prompt


        System.out.println(command);

        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", command);

            builder.redirectErrorStream(true);


            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));


            String Response;
            while (true) {
                Response = r.readLine();
                if (Response == null) { break; }
                System.out.println(Response);

            }


        }catch (Exception i){
            System.out.println("error in command \n" +i);
        }


    }



    public static void compileWifi(){

    }


    static void compileLoRa(){

    }


    static void compileGSM(){

    }

    static void compileGpio(){

    }






    public static void compOrAs(String path){

        String inputFile = path.split(Pattern.quote(File.separator))[path.split(Pattern.quote(File.separator)).length -1];

        String fileName = inputFile.split(Pattern.quote("."))[0];

        String outFile = fileName +".o";

        String command =   compilerDir + ARCH_FLAGS + CPU_DEFINES + WL_FLAG +OPT_FLAGS + NOLINK + OUT_FLAG +
                outTempDir + outFile + inputFile ;

        runCommand(command);

    }



    public static void linker(String filesPath , String includes[] , String linkerScriptPath){

       linkerScriptPath = linkerScriptPath == null ? DefaultLinkScrptDir : linkerScriptPath;

        ArrayList<String> inputFiles = new ArrayList<String>();

        File file = new File(filesPath);

        for (File e: file.listFiles()
             ) {
            String s = e.getAbsolutePath();
            if(s.endsWith(".o")){
                inputFiles.add(s);
            }
        }

        String inputFilesString = "";
        for(String n : inputFiles){
            inputFilesString += " " +"\"" + n +"\"";
        }




        System.out.println(inputFilesString);

        String command = compilerDir+USE_NOHOST +LSCRIP_FLAG+" "+linkerScriptPath + inputFilesString
                + ARCH_FLAGS + CPU_DEFINES + WL_FLAG
                +OPT_FLAGS + OUT_FLAG + " Temp.elf" ;

        runCommand(command);




    }


    public static void convertToHex(String path){

        File file = new File(path);

        for (File file1 : file.listFiles()) {
//            System.out.println(file1.getName());
            if(file1.getAbsolutePath().endsWith(".elf")){
                String commmand = converterDir + " -O"+ " ihex " +
                        "\""+path+"\\"+file1.getName() +"\""+" " +"\""+ path+"\\"+file1.getName().split(Pattern.quote("."))[0]+".hex"+"\"";
                System.out.println(commmand);
                runCommand(commmand);
            }
        }
    }




}
