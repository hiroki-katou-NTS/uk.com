package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.print;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;

public class AsposeHolidayShipment {
    private final String EMPTY = "";
    private final String HALF_WIDTH_SPACE = " ";
    private final String fULL_WIDTH_SPACE = "　";
    
    public int printHolidayShipmentContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
        int deleteCnt = 0;
        
        Cells cells = worksheet.getCells();
        
        // B6
        Cell cellB6 = cells.get("B6");
         cellB6.setValue(I18NText.getText("KAF011_83"));
        
        return deleteCnt;
    }
}
