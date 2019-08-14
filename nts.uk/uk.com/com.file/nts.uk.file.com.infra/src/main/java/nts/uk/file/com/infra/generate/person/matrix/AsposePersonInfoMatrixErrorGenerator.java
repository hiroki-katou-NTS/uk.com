package nts.uk.file.com.infra.generate.person.matrix;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Column;
import com.aspose.cells.Font;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.file.com.app.person.matrix.PersonInfoMatrixErrorGenerator;
import nts.uk.file.com.app.person.matrix.datasource.ErrorWarningEmployeeInfoDataSource;
import nts.uk.file.com.app.person.matrix.datasource.ErrorWarningInfoOfRowOrderDataSource;
import nts.uk.file.com.app.person.matrix.datasource.PersonMatrixErrorDataSource;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposePersonInfoMatrixErrorGenerator extends AsposeCellsReportGenerator
		implements PersonInfoMatrixErrorGenerator {
	/** The Constant EXTENSION_FILE. */
	private static final String EXTENSION_FILE = ".xlsx";
	// số cột của template
	private static int NUMBEROFCOL;
	private static final String REPORT_ID = "PersonMatrixError";

	@Override
	public void generate(FileGeneratorContext generatorContext, PersonMatrixErrorDataSource dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);
		String fileName = TextResource.localize("CPS003_129") + TextResource.localize("CPS003_115")
				+ GeneralDateTime.now().toString("yyyyMMddHHmmss") + EXTENSION_FILE;
		Workbook workbook = reportContext.getWorkbook();
		Worksheet worksheet = workbook.getWorksheets().get(0);
		worksheet.setName("エラー・警告一覧");
		setWidthColum(worksheet, dataSource);
		printTilte(worksheet);
		// tính ra số cột của template
		if (dataSource.isDisplayE1_006()) {
			NUMBEROFCOL = 7;
		} else {
			NUMBEROFCOL = 6;
		}

		// in header từ dòng thứ 4
		printHeader(worksheet, dataSource);
		int row = 5;
		printEmployeeInfo(worksheet, dataSource, row);

		reportContext.processDesigner();
		reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName));

	}

	/**
	 * printTilte
	 * 
	 * @param worksheet
	 */
	private void printTilte(Worksheet worksheet) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		Cell title = worksheet.getCells().get("A1");
		title.putValue(TextResource.localize("CPS003_129"));
		Style titleStyle = title.getStyle();
		Font font = titleStyle.getFont();
		font.setName("MS ゴシック");
		title.setStyle(titleStyle);

		Cell date = worksheet.getCells().get("G2");
		date.putValue(GeneralDateTime.now().toString());
		Style dateStyle = date.getStyle();
		Font dateFont = dateStyle.getFont();
		dateFont.setName("MS ゴシック");
		date.setStyle(dateStyle);
	}

	private void printHeader(Worksheet worksheet, PersonMatrixErrorDataSource dataSource) {
		int row = 4;
		for (int i = 0; i < NUMBEROFCOL; i++) {
			Cell cell = worksheet.getCells().get(row, i);
			setBodyStyle(cell);
			switch (i) {
			case 0:
				cell.setValue(TextResource.localize("CPS003_100"));
				break;
			case 1:
				cell.setValue(TextResource.localize("CPS003_101"));
				break;
			case 2:
				cell.setValue(TextResource.localize("CPS003_102"));
				break;
			case 3:
				if (dataSource.isDisplayE1_006()) {
					cell.setValue(TextResource.localize("CPS003_103"));
				} else {
					cell.setValue(TextResource.localize("CPS003_104"));
				}

				break;
			case 4:
				if (dataSource.isDisplayE1_006()) {
					cell.setValue(TextResource.localize("CPS003_104"));
				} else {
					cell.setValue(TextResource.localize("CPS003_105"));
				}
				break;
			case 5:
				if (dataSource.isDisplayE1_006()) {
					cell.setValue(TextResource.localize("CPS003_105"));
				} else {
					cell.setValue(TextResource.localize("CPS003_106"));
				}
				break;
			case 6:
				if (dataSource.isDisplayE1_006()) {
					cell.setValue(TextResource.localize("CPS003_106"));
				}
				break;
			default:
				break;
			}
		}
	}

	private void printEmployeeInfo(Worksheet worksheet, PersonMatrixErrorDataSource dataSource, int row) {
		Cells cells = worksheet.getCells();
		List<ErrorWarningEmployeeInfoDataSource> errorEmployeeInfoLst = dataSource.getErrorEmployeeInfoLst();
		int colFix = 0;
		if (dataSource.isDisplayE1_006()) {
			colFix = 4;
		} else {
			colFix = 3;
		}
		for (int i = 0; i < errorEmployeeInfoLst.size(); i++) {
			ErrorWarningEmployeeInfoDataSource employee = errorEmployeeInfoLst.get(i);
			// in ra những cột cố định
			for (int col = 0; col < colFix; col++) {
				Cell cell = cells.get(row, col);
				switch (col) {
				case 0:
					cell.setValue(employee.getEmployeeCd());
					break;
				case 1:
					cell.setValue(employee.getEmployeeName());
					break;
				case 2:
					cell.setValue(String.valueOf(employee.getOrder()));
					break;
				case 3:
					cell.setValue("X");
					break;
				default:
					break;
				}
			}

			// in ra những item lỗi
			int sizeError = employee.getErrorLst().size();
			for (int j = 0; j < sizeError; j++) {
				ErrorWarningInfoOfRowOrderDataSource errorItem = employee.getErrorLst().get(j);

				for (int col = colFix; col < NUMBEROFCOL; col++) {
					Cell cell = cells.get(row, col);
					Style style = cell.getStyle();
					style.setTextWrapped(true);
					if (j == (sizeError - 1)) {
						setBodyStyle(cell);
					}
					switch (col) {
					case 3:
						cell.setValue(errorItem.getErrorType() == 0?"エラー": "確認");
						break;
					case 4:
						if (dataSource.isDisplayE1_006()) {
							cell.setValue(errorItem.getErrorType() == 0?"エラー": "確認");
						} else {
							cell.setValue(errorItem.getItemName());
						}

						break;
					case 5:
						if (dataSource.isDisplayE1_006()) {
							cell.setValue(errorItem.getItemName());
						} else {
							cell.setValue(errorItem.getMessage());
						}
						break;
					case 6:
						if (dataSource.isDisplayE1_006()) {
							cell.setValue(errorItem.getMessage());
						}
						break;
					default:
						break;
					}
				}
				row = row + 1;
			}

			for (int j = 0; j < colFix; j++) {
				Cell cell = cells.get((row - 1), j);
				setBodyStyle(cell);
			}
		}

	}

	/**
	 * Sets the title style.
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setBodyStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getDarkGray());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.NONE, Color.getDarkGray());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.DOTTED, Color.getDarkGray());
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.NONE, Color.getDarkGray());
		style.setTextWrapped(true);

		Font font = style.getFont();
		font.setName("MS ゴシック");
		cell.setStyle(style);
	}

	private void setWidthColum(Worksheet worksheet, PersonMatrixErrorDataSource dataSource) {
		// 社員コード
		Column columnCode = worksheet.getCells().getColumns().get(0);
		columnCode.setWidth(15);
		// 氏名
		Column columnName = worksheet.getCells().getColumns().get(1);
		columnName.setWidth(20);
		// 行番号
		Column columnStt = worksheet.getCells().getColumns().get(2);
		columnStt.setWidth(10);
		// 登録結果
		if (dataSource.isDisplayE1_006()) {
			Column columnResult = worksheet.getCells().getColumns().get(3);
			columnResult.setWidth(10);
			Column columnClass = worksheet.getCells().getColumns().get(4);
			columnClass.setWidth(10);
			Column columnItemName = worksheet.getCells().getColumns().get(5);
			columnItemName.setWidth(50);
			Column columnMess = worksheet.getCells().getColumns().get(6);
			columnMess.setWidth(75);
		} else {
			Column columnClass = worksheet.getCells().getColumns().get(3);
			columnClass.setWidth(10);
			Column columnItemName = worksheet.getCells().getColumns().get(4);
			columnItemName.setWidth(50);
			Column columnMess = worksheet.getCells().getColumns().get(5);
			columnMess.setWidth(75);
		}

	}

}
