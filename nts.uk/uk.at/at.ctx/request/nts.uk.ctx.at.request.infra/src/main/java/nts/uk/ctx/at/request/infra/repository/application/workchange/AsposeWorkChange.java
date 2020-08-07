package nts.uk.ctx.at.request.infra.repository.application.workchange;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AsposeWorkChange {
	
	public void printWorkChangeContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		Cells cells = worksheet.getCells();
		Cell cellB8 = cells.get("B8");
		cellB8.setValue(I18NText.getText("KAF007_32"));
		Cell cellB9 = cells.get("B9");
		cellB9.setValue(I18NText.getText("KAF007_33"));
		Cell cellB10 = cells.get("B10");
		cellB10.setValue(I18NText.getText("KAF007_34"));
		Cell cellB11 = cells.get("B11");
		cellB11.setValue(I18NText.getText("KAF007_35"));
		Cell cellB12 = cells.get("B12");
		cellB12.setValue(I18NText.getText("KAF007_80"));
		Cell cellB13 = cells.get("B13");
		cellB13.setValue(I18NText.getText("KAF007_81"));
	}
	
}
