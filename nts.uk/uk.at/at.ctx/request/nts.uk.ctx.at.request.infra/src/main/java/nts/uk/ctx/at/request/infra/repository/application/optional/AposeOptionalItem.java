package nts.uk.ctx.at.request.infra.repository.application.optional;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;

import javax.ejb.Stateless;

@Stateless
public class AposeOptionalItem {


    public void printOptionalItem(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
        Cells cells = worksheet.getCells();



    }
}
