package nts.uk.pr.file.infra.wageledger;

import java.util.ArrayList;
import java.util.List;

import com.aspose.cells.Cell;
import com.aspose.cells.Color;
import com.aspose.cells.FindOptions;
import com.aspose.cells.LookInType;
import com.aspose.cells.Worksheet;

import nts.uk.file.pr.app.export.wageledger.data.share.HeaderReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

public class WageLedgerBaseGenerator extends AsposeCellsReportGenerator{
	
	/** The Constant BLUE_COLOR. */
	protected static final Color BLUE_COLOR = Color.fromArgb(197, 241, 247);
	
	/** The Constant GREEN_COLOR. */
	protected static final Color GREEN_COLOR = Color.fromArgb(199, 243, 145);
	
	/**
	 * Safe sub list.
	 *
	 * @param <T> the generic type
	 * @param list the list
	 * @param fromIndex the from index
	 * @param toIndex the to index
	 * @return the list
	 */
	protected <T> List<T> safeSubList(List<T> list, int fromIndex, int toIndex) {
		int size = list.size();
		if (fromIndex >= size || toIndex <= 0 || fromIndex >= toIndex) {
			return new ArrayList<>();
		}

		fromIndex = Math.max(0, fromIndex);
		toIndex = Math.min(size, toIndex);
		return list.subList(fromIndex, toIndex);
	}
	
	/**
	 * Find cell with content.
	 *
	 * @param ws the ws
	 * @param content the content
	 * @return the cell
	 */
	protected Cell findCellWithContent(Worksheet ws, String content) {
		// Cell find option.
		FindOptions findOptions = new FindOptions();
		findOptions.setLookInType(LookInType.VALUES);
		findOptions.setCaseSensitive(false);
		
		Cell cell = ws.getCells().find(content, null, findOptions);
		
		return cell;
	}
	
	
	/**
	 * Fill header data.
	 *
	 * @param reportContext the report context
	 * @param headerData the header data
	 */
	protected void fillHeaderData(AsposeCellsReportContext reportContext, HeaderReportData headerData) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		String infoPadding = "        ";
		
		// Fill Department Label.
		Cell depLabelCell = this.findCellWithContent(ws, "DepartmentLabel");
		depLabelCell.setValue("【部門】");
		
		// Fill Department info.
		Cell depInfo = this.findCellWithContent(ws, "DepartmentInfo");
		depInfo.setValue(headerData.departmentCode + infoPadding + headerData.departmentName);
		
		// Fill Employee label.
		Cell empLabel = this.findCellWithContent(ws, "EmployeeLabel");
		empLabel.setValue("【社員】");
		
		// Fill Employee info.
		Cell empInfo = this.findCellWithContent(ws, "EmployeeInfo");
		empInfo.setValue(headerData.employeeCode + infoPadding + headerData.departmentName);
		
		// Fill Sex label.
		Cell sexLabelCell = this.findCellWithContent(ws, "SexLabel");
		sexLabelCell.setValue("【性別】");
		
		// Fill Sex Info.
		Cell sexInfoCell = this.findCellWithContent(ws, "SexInfo");
		sexInfoCell.setValue(headerData.sex);
	}
}
