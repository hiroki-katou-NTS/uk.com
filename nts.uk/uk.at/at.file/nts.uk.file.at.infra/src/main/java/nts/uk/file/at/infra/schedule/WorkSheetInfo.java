package nts.uk.file.at.infra.schedule;

import com.aspose.cells.Worksheet;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WorkSheetInfo {
    /**
     * Work sheet
     */
    @Setter
    private Worksheet sheet;
    /**
     * Index of start data
     */
    private int startDataIndex;
    /**
     * Index of template sheet
     */
    private int originSheetIndex;

    /**
     * Number of new sheet
     */
    private int newSheetIndex;
    
    /**
     *	Sheet Name
     */
    private String sheetName;

    public WorkSheetInfo(Worksheet sheet, int startDataIndex, int originSheetIndex) {
        this.sheet = sheet;
        this.startDataIndex = startDataIndex;
        this.originSheetIndex = originSheetIndex;
        this.newSheetIndex = 0;
    }
    
    public WorkSheetInfo(Worksheet sheet, int startDataIndex, int originSheetIndex, String sheetName) {
        this.sheet = sheet;
        this.startDataIndex = startDataIndex;
        this.originSheetIndex = originSheetIndex;
        this.newSheetIndex = 0;
        this.sheetName = sheetName;
    }
    

    public void plusNewSheetIndex() {
        this.newSheetIndex++;
    }
}
