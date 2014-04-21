import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

/*
*
* Helper Class to read an excel file using JExcelAPI
* */
public class ReadFromExcel {

    private String _inputFile;

    public ReadFromExcel(String inputFile) {
        this._inputFile = inputFile;
    }

    /*
    *
    * Function to get the entire excel workbook from a given file path
    *
    * */
    public Workbook getWorkbook() {
        File inputWorkbook = new File(_inputFile);
        Workbook w = null;
        try {

            w = Workbook.getWorkbook(inputWorkbook);

        }
        catch (BiffException e) {

            e.printStackTrace();
        }
        catch (IOException e){

            e.printStackTrace();
        }
        return w;

    }

}