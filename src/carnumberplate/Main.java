/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package carnumberplate;

import Controller.configurator.Configurator;
import Controller.gui.ReportGenerator;
import View.Home.Home;
import View.Home.CarEntry;
import View.Home.Loading;
import Model.imageanalysis.CarSnapshot;
import Model.imageanalysis.Char;
import Controller.intelligence.Intelligence;
import Controller.recognizer.NeuralPatternClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author shahr
 */
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Main class which initializes the project, taking input parameters from command line and then running the project
 * accordingly.
 * <p>
 * Allowed parameters are: <ul>
 * <li>-help   Displays the help messsage.</li>
 * <li>-gui   Run GUI viewer (default choice).</li>
 * <li>-recognize -i "snapshot"   Recognize single snapshot.</li>
 * <li>-recognize -i "snapshot" -o "dstdir"   Recognize single snapshot and save report html into specified
 * directory.</li>
 * <li>-newconfig -o "file"   Generate default configuration file.</li>
 * <li>-newnetwork -o "file"   Train neural network according to specified feature extraction method and learning
 * parameters (in config. file) and saves it into output file.</li>
 * <li>-newalphabet -i "srcdir" -o "dstdir"   Normalize all images in "srcdir" and save it to "dstdir".</li>
 * </ul>
 */
public final class Main {

    private static final transient Logger logger = LoggerFactory.getLogger(Main.class);
    /**
     * The report generator.
     */
    public static ReportGenerator rg;
    /**
     * The intelligence.
     */
    public static Intelligence systemLogic;
    /**
     * The help message.
     */
    public static final String helpText = "-----------------------------------------------------------\n"
            + "Automatic number plate recognition system\n" + "Copyright 2013 JavaANPR contributors\n"
            + "Copyright 2006 Ondrej Martinsky\n" + "\n"
            + "Licensed under the Educational Community License (ECL-2.0),\n" + "\n"
            + "Usage : java -jar anpr.jar [-options]\n" + "\n" + "Where options include:\n" + "\n"
            + "    -help         Displays this help\n" + "    -gui          Run GUI viewer (default choice)\n"
            + "    -recognize -i <snapshot>\n" + "                  Recognize single snapshot\n"
            + "    -recognize -i <snapshot> -o <dstdir>\n" + "                  Recognize single snapshot and\n"
            + "                  save report html into specified\n" + "                  directory\n"
            + "    -newconfig -o <file>\n" + "                  Generate default configuration file\n"
            + "    -newnetwork -o <file>\n" + "                  Train neural network according to\n"
            + "                  specified feature extraction method and\n"
            + "                  learning parameters (in config. file)\n"
            + "                  and saves it into output file\n" + "    -newalphabet -i <srcdir> -o <dstdir>\n"
            + "                  Normalize all images in <srcdir> and save\n" + "                  it to <dstdir>.";

    static {
        try {
            rg = new ReportGenerator("report");
        } catch (IOException e) {
            throw new IllegalStateException("Error during report generator initialization.", e);
        }
    }

    private Main() {
        // intentionally empty
    }

    /**
     * Normalizes the alphabet in the source directory and writes the result to the target directory.
     *
     * @param srcdir the source directory
     * @param dstdir the destination directory
     * @throws IOException an IOException
     * @deprecated not used
     */
    public static void newAlphabet(String srcdir, String dstdir) throws IOException {
        int x = Configurator.getConfigurator().getIntProperty("char_normalizeddimensions_x");
        int y = Configurator.getConfigurator().getIntProperty("char_normalizeddimensions_y");
        logger.info("\nCreating new alphabet (" + x + " x " + y + " px)... \n");
        for (String fileName : Char.getAlphabetList(srcdir)) {
            Char c = new Char(fileName);
            c.normalize();
            c.saveImage(dstdir + File.separator + fileName);
            logger.info(fileName + " done");
            c.close();
        }
    }

    /**
     * Train neural network according to specified feature extraction method and learning parameters (in config file)
     * and saves it into output file.
     *
     * @param destinationFile the destination file
     * @throws Exception an Exception
     */
    public static void learnAlphabet(String destinationFile) throws Exception {
        File f = new File(destinationFile);
        try {
            if (!f.createNewFile()) {
                throw new IOException("File already exists.");
            }
        } catch (Exception e) {
            throw new IOException("Can't find the path specified.", e);
        }
        NeuralPatternClassifier npc = new NeuralPatternClassifier(true);
        npc.getNetwork().saveToXml(destinationFile);
    }

    /**
     * Main method which parses the input parameters and then runs the project accordingly.
     *
     * @param args the input parameters
     * @throws Exception an Exception
     */
    public static void main(String[] args) throws Exception { // TODO refactor
        if ((args.length == 0) || ((args.length == 1) && args[0].equals("-gui"))) { // gui
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Loading frameComponentInit = new Loading(); // show wait
            Main.systemLogic = new Intelligence();
            frameComponentInit.dispose(); // hide wait
            new Home();
        } else if ((args.length == 3) && args[0].equals("-recognize") && args[1].equals("-i")) {
            // load snapshot args[2] and recognize it
            Main.systemLogic = new Intelligence();
            System.out.println(Main.systemLogic.recognize(new CarSnapshot(args[2]), false));
        } else if ((args.length == 5) && args[0].equals("-recognize") && args[1].equals("-i") && args[3].equals("-o")) {
            // load snapshot arg[2] and generate report into arg[4]
            Main.rg = new ReportGenerator(args[4]);
            Main.systemLogic = new Intelligence();
            Main.systemLogic.recognize(new CarSnapshot(args[2]), true);
        } else if ((args.length == 3) && args[0].equals("-newconfig") && args[1].equals("-o")) {
            // save default config into args[2]
            Configurator.getConfigurator().saveConfiguration(args[2]);
        } else if ((args.length == 3) && args[0].equals("-newnetwork") && args[1].equals("-o")) {
            // learn new neural network and save it into into args[2]
            Main.learnAlphabet(args[2]);
        } else if ((args.length == 5) && args[0].equals("-newalphabet") && args[1].equals("-i") && args[3]
                .equals("-o")) { // transform alphabets from args[2] -> args[4]
            Main.newAlphabet(args[2], args[4]);
        } else { // display help
            System.out.println(Main.helpText);
        }
    }
}
