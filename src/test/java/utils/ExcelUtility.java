package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtility {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelUtility(String filePath, String sheetName)
            throws IOException {

        FileInputStream fis = new FileInputStream(filePath);

        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
    }

    public Map<String, String> getRowData(int rowNumber) {

        Map<String, String> rowData = new HashMap<>();

        XSSFRow headerRow = sheet.getRow(0);
        XSSFRow dataRow = sheet.getRow(rowNumber);

        DataFormatter formatter = new DataFormatter();

        int columnCount = headerRow.getLastCellNum();

        for (int i = 0; i < columnCount; i++) {

            String header = formatter.formatCellValue(headerRow.getCell(i));
            String value = formatter.formatCellValue(dataRow.getCell(i));
            rowData.put(header, value);
        }

        return rowData;
    }

    public void closeWorkbook() throws IOException {
        workbook.close();
    }
}