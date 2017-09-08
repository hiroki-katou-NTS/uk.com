package nts.uk.file.at.infra.worktype;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.CellArea;
import com.aspose.cells.FormatCondition;
import com.aspose.cells.FormatConditionCollection;
import com.aspose.cells.FormatConditionType;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Worksheet;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.worktype.WorkTypeGenerator;
import nts.uk.file.at.app.export.worktype.data.WorkTypeReport;
import nts.uk.file.at.app.export.worktype.data.WorkTypeReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;

@Stateless
public class WorkTypeReportGenerator extends AsposeCellsReportGenerator implements WorkTypeGenerator {

	private static final String TEMPLATE_FILE = "report/kmk007.xlsx";

	protected static final String REPORT_FILE_NAME = "マスタリスト_勤務種類の登録.xlsx";

	@Override
	public void generate(FileGeneratorContext fileContext, WorkTypeReport reportData) {

		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);

			reportContext.setDataSource("list", reportData.getData());

			// process data binginds in template
			reportContext.getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			Worksheet worksheet = reportContext.getWorkbook().getWorksheets().get(0);

			int startRowIdx = 6;
			for (WorkTypeReportData item : reportData.getData()) {
				// Add FormatConditions to the instance of Worksheet
				int index = worksheet.getConditionalFormattings().add();

				// Access the newly added FormatConditions via its index
				FormatConditionCollection conditionCollection = worksheet.getConditionalFormattings().get(index);

				// Define a CellsArea on which conditional formatting will be
				// applicable
				CellArea area = CellArea.createCellArea("A" + startRowIdx, "F" + startRowIdx);

				// Add area to the instance of FormatConditions
				conditionCollection.addArea(area);

				// Add a condition to the instance of FormatConditions. For this
				// case, the condition type is expression, which is based on
				// some formula
				index = conditionCollection.addCondition(FormatConditionType.EXPRESSION);

				// Access the newly added FormatCondition via its index
				FormatCondition formatCondirion = conditionCollection.get(index);

				// Set the formula for the FormatCondition. Formula uses the
				// Excel's built-in functions as discussed earlier in this
				// article
				formatCondirion.setFormula1("=MOD(ROW(),1)=0");

				// Set the background color and patter for the FormatCondition's
				// Style
				formatCondirion.getStyle().setPattern(BackgroundType.SOLID);

				startRowIdx++;
			}

			// save as PDF file
			PdfSaveOptions option = new PdfSaveOptions(SaveFormat.XLSX);
			option.setAllColumnsInOnePagePerSheet(true);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddmmss");
			String message = MessageFormat.format(REPORT_FILE_NAME, format1.format(cal.getTime()));

			reportContext.getWorkbook().save(this.createNewFile(fileContext, message), option);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
