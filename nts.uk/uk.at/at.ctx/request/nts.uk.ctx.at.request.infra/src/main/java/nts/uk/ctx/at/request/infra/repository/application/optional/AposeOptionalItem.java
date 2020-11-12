package nts.uk.ctx.at.request.infra.repository.application.optional;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemContent;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemPrintContent;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class AposeOptionalItem {


    public void printOptionalItem(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
        Cells cells = worksheet.getCells();
        Cell cellB8 = cells.get("B8");
        cellB8.setValue(I18NText.getText("KAF020_55"));
        Cell cellB9 = cells.get("B9");
        cellB9.setValue(I18NText.getText("KAF020_56"));
        OptionalItemPrintContent optionalItemPrintContent = printContentOfApp.getOptionalItemPrintContent().get();
        optionalItemPrintContent.getOptionalItemContents();
        List<OptionalItemContent> printContent = optionalItemPrintContent.getOptionalItemContents();
        for (int i = 0; i < printContent.size(); i++) {
            Cell cellD = cells.get("D" + (9 + i));
            cellD.setValue(printContent.get(i).getOptionalItemName());
            Cell cellH = cells.get("H" + (9 + i));
            if (printContent.get(i).getOptionalItemAtr() == 0) {
                cellH.setValue(timeToString(printContent.get(i).getTime()));
            } else if (printContent.get(i).getOptionalItemAtr() == 1) {
                cellH.setValue(printContent.get(i).getTimes());
            } else if (printContent.get(i).getOptionalItemAtr() == 2) {
                cellH.setValue(printContent.get(i).getAmount());
            }
            Cell cellI = cells.get("I" + (9 + i));
            cellI.setValue(printContent.get(i).getUnit());
        }
    }

    public void deleteEmptyRow(Worksheet worksheet) {
        Cells cells = worksheet.getCells();
        for (int i = 0; i < 10; i++) {
            Cell cell = cells.get("D" + (9 + i));
            if (cell.getValue() == null || cell.getValue().toString().isEmpty()) {
                worksheet.getCells().deleteRow(8 + i);
            }
        }
    }

    private String timeToString(int value) {
        if (value % 60 < 10) {
            return String.valueOf(value / 60) + ":0" + String.valueOf(value % 60);
        }
        return String.valueOf(value / 60) + ":" + String.valueOf(value % 60);
    }
}
