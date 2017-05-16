package nts.uk.pr.file.infra.banktransfer;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.CellArea;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.FormatCondition;
import com.aspose.cells.FormatConditionCollection;
import com.aspose.cells.FormatConditionType;
import com.aspose.cells.Worksheet;

import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

public class BankTranferReportUtil {
	
	private final static int START_ROW_INDEX = 9;
	
	/**
	 * Set color for row
	 * @param reportContext
	 * @param rpData
	 */
	public static void rowColor(AsposeCellsReportContext reportContext, int rpDataSize) {
		Worksheet worksheet = reportContext.getWorkbook().getWorksheets().get(0);
		
		int startRowIdx = START_ROW_INDEX;
		int rowColorIdex = 0;
		
		for (int i=0; i< rpDataSize; i ++) {
			// Add FormatConditions to the instance of Worksheet
			int index = worksheet.getConditionalFormattings().add();
			
			// Access the newly added FormatConditions via its index
			FormatConditionCollection conditionCollection = worksheet.getConditionalFormattings().get(index);
			
			// Define a CellsArea on which conditional formatting will be applicable
			CellArea area = CellArea.createCellArea("A" +  startRowIdx, "O" + startRowIdx);

			// Add area to the instance of FormatConditions
			conditionCollection.addArea(area);

			// Add a condition to the instance of FormatConditions. For this case, the condition type is expression, which is based on
			// some formula
			index = conditionCollection.addCondition(FormatConditionType.EXPRESSION);

			// Access the newly added FormatCondition via its index
			FormatCondition formatCondirion = conditionCollection.get(index);

			// Set the formula for the FormatCondition. Formula uses the Excel's built-in functions as discussed earlier in this
			// article
			formatCondirion.setFormula1("=MOD(ROW(),1)=0");
			
			// Set the background color and patter for the FormatCondition's Style
			formatCondirion.getStyle().setPattern(BackgroundType.SOLID);
			
			if (i < rpDataSize) {
				if (rowColorIdex%2==0) {
					formatCondirion.getStyle().setBackgroundColor(Color.getWhite());
				} else {
					Color color = Color.fromArgb(204,244,145);
					formatCondirion.getStyle().setBackgroundArgbColor(color.toArgb());
				}
				rowColorIdex ++;
			}
			
			if (i == rpDataSize - 1) {
				rowColorIdex = 0;
				Color color = Color.fromArgb(197,241,247);
				formatCondirion.getStyle().setBackgroundArgbColor(color.toArgb());
				formatCondirion.getStyle().setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
			    formatCondirion.getStyle().setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			}  
				
			startRowIdx++;
		}
	}
	
	/**
	 * Get file name with current date time
	 * @return file name
	 */
	public static String getFileName(String reportFileName) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddmmss");
		return MessageFormat.format(reportFileName, format1.format(cal.getTime()));
	}
}
