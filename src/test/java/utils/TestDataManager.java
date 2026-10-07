package utils;

import java.io.IOException;
import java.util.Map;

public class TestDataManager {

    private static final String FILE_PATH = "src/test/resources/testData/OrangeHRM_TestData.xlsx";

    private static final String CANDIDATE_SHEET_NAME = "CandidatesData";

    private static final String EMPLOYEE_SHEET_NAME = "EmployeeData";

    private static final int DATA_ROW = 1;


    public static Map<String, String> getCandidateData() throws IOException {
        return getSheetData(CANDIDATE_SHEET_NAME);
    }


    public static Map<String, String> getEmployeeData() throws IOException {
        return getSheetData(EMPLOYEE_SHEET_NAME);
    }


    private static Map<String, String> getSheetData(String sheetName) throws IOException {

        ExcelUtility excel = new ExcelUtility(FILE_PATH, sheetName);
        Map<String, String> data = excel.getRowData(DATA_ROW);
        excel.closeWorkbook();

        return data;
    }
}