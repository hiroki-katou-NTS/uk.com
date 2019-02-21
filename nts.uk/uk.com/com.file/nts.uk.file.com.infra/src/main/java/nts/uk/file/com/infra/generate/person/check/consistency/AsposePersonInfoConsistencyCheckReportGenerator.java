package nts.uk.file.com.infra.generate.person.check.consistency;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.Column;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.file.com.app.person.check.consistency.PersonInfoConsistencyCheckGenerator;
import nts.uk.file.com.app.person.check.consistency.datasource.EmployeInfoErrorDataSource;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposePersonInfoConsistencyCheckReportGenerator extends AsposeCellsReportGenerator
		implements PersonInfoConsistencyCheckGenerator {
	private static final String REPORT_ID = "PersonInfoConsistencyCheck";
	/** The Constant EXTENSION_FILE. */
	private static final String EXTENSION_FILE = ".xlsx";
	// số cột của template
	private final static int NUMBEROFCOL = 5;

	@Override
	public void generate(FileGeneratorContext generatorContext, List<EmployeInfoErrorDataSource> dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);
		String fileName = "個人情報整合性チェックエラーリスト" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + EXTENSION_FILE;
		Workbook workbook = reportContext.getWorkbook();
		Worksheet worksheet = workbook.getWorksheets().get(0);
		setWidthColum(worksheet);
		printHeader(worksheet);
		printEachEmployeeInfo(worksheet, dataSource);
		reportContext.processDesigner();
		reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName));

	}

	/**
	 * printHeader
	 * 
	 * @param worksheet
	 */
	private void printHeader(Worksheet worksheet) {
		int row = 0;
		for (int i = 0; i < NUMBEROFCOL; i++) {
			Cell cell = worksheet.getCells().get(row, i);
			setHeaderStyle(cell);
			switch (i) {
			case 0:
				cell.setValue(TextResource.localize("CPS013_26"));
				break;
			case 1:
				cell.setValue(TextResource.localize("CPS013_27"));
				break;
			case 2:
				cell.setValue(TextResource.localize("CPS013_28"));
				break;
			case 3:
				cell.setValue(TextResource.localize("CPS013_29"));
				break;
			case 4:
				cell.setValue(TextResource.localize("CPS013_30"));
				break;
			default:
				break;
			}
		}

	}

	/**
	 * printEachEmployeeInfo
	 * 
	 * @param worksheet
	 * @param dataSource
	 */
	private void printEachEmployeeInfo(Worksheet worksheet, List<EmployeInfoErrorDataSource> dataSource) {
		int row = 1;
		for (EmployeInfoErrorDataSource employee : dataSource) {
			for (int i = 0; i < NUMBEROFCOL; i++) {
				Cell cell = worksheet.getCells().get(row, i);
				setBodyStyle(cell);
				switch (i) {
				case 0:
					cell.setValue(employee.getEmployeeCode());
					break;
				case 1:
					cell.setValue(employee.getEmployeeName());
					break;
				case 2:
					cell.setValue(employee.getCheckAtr());
					break;
				case 3:
					cell.setValue(employee.getCategoryName());
					break;
				case 4:
					cell.setValue(employee.getContentError());
					break;
				default:
					break;
				}
			}
			row++;
		}
	}

	/**
	 * setWidthColum
	 * 
	 * @param worksheet
	 */
	private void setWidthColum(Worksheet worksheet) {
		// 社員コード
		Column empCode = worksheet.getCells().getColumns().get(0);
		empCode.setWidth(15);
		// 氏名
		Column empName = worksheet.getCells().getColumns().get(1);
		empName.setWidth(20);
		// チェック区分
		Column checkAtr = worksheet.getCells().getColumns().get(2);
		checkAtr.setWidth(30);
		// カテゴリ区分
		Column categoryName = worksheet.getCells().getColumns().get(3);
		categoryName.setWidth(30);
		// エラー内容
		Column contentError = worksheet.getCells().getColumns().get(4);
		contentError.setWidth(75);
	}
	
	/**
	 * Sets the title style.
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setHeaderStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setForegroundColor(Color.fromArgb(207, 241, 165));
		
		style.setTextWrapped(true);
		Font font = style.getFont();
		font.setName("MS ゴシック");
		cell.setStyle(style);
	}

	/**
	 * Sets the title style.
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setBodyStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setTextWrapped(true);
		Font font = style.getFont();
		font.setName("MS ゴシック");
		cell.setStyle(style);
	}

}
