package nts.uk.file.com.infra.groupcommonmaster;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMaster;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMasterItem;
import nts.uk.file.com.app.groupcommonmaster.GroupCommonMasterExportGenerator;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeGroupCommonMasterExportGenerator extends AsposeCellsReportGenerator
		implements GroupCommonMasterExportGenerator {

	private static final String TEMPLATE_FILE = "report/マスタリスト.xlsx";

	private static final String REPORT_FILE_NAME = "CMM0022グループ会社共通マスタの登録_";

	private static final String REPORT_FILE_EXTENSION = ".xlsx";

	private static final int COMMONMASTERCODE = 0;
	private static final int COMMONMASTERNAME = 1;
	private static final int ITEMCODE = 2;
	private static final int ITEMNAME = 3;
	private static final int STARTDATE = 4;
	private static final int ENDDATE = 5;
	private static final int DISPLAYORDER = 6;

	private static final int FIRST_ROW_FILL = 10;
	
	private static final int NORMAL_FONT_SIZE = 10;

	@Override
	public void generate(FileGeneratorContext generatorContext, Optional<CompanyInfor> comInfo,
			List<GroupCommonMaster> data) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {
			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			// set up page prepare print
			this.writeFileExcel(worksheets, comInfo.get(), data);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(
					this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME + REPORT_FILE_EXTENSION)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void writeFileExcel(WorksheetCollection worksheets, CompanyInfor companyInfor,
			List<GroupCommonMaster> data) {
		Worksheet ws = worksheets.get(0);
		int rowIndex = FIRST_ROW_FILL;

		ws.setName(TextResource.localize("CMM022_P2_8"));
		try {

			for (int i = 0; i < data.size(); i++) {
				GroupCommonMaster commonMaster = data.get(i);

				ws.getCells().get(rowIndex, COMMONMASTERCODE).putValue(commonMaster.getCommonMasterCode());
				ws.getCells().get(rowIndex, COMMONMASTERNAME).putValue(commonMaster.getCommonMasterName());

				for (int j = 0; j < commonMaster.getCommonMasterItems().size(); j++) {

					GroupCommonMasterItem item = commonMaster.getCommonMasterItems().get(j);

					ws.getCells().get(rowIndex, ITEMCODE).putValue(item.getCommonMasterItemCode().v());
					ws.getCells().get(rowIndex, ITEMNAME).putValue(item.getCommonMasterItemName().v());
					ws.getCells().get(rowIndex, STARTDATE).putValue(item.getUsageStartDate());
					ws.getCells().get(rowIndex, ENDDATE).putValue(item.getUsageEndDate());
					ws.getCells().get(rowIndex, DISPLAYORDER).putValue(item.getDisplayNumber());
					setBorder(ws, rowIndex);
					rowIndex++;
				}
				
				//nếu master này không có item thì thêm thêm dòng và set border cho nó
				if (commonMaster.getCommonMasterItems().size() == 0) {
					setBorder(ws, rowIndex);
					rowIndex++;
				}
			}
			this.settingTableHeader(ws);
			this.settingHeader(ws, companyInfor);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	private void setBorder(Worksheet worksheet, int rowIndex) {
		int totalColumn = 7;
		int columnStart = 0;

		Style style = getDefaultStyle(worksheet);
		for (int column = columnStart; column < totalColumn; column++) {
			Cell cell = worksheet.getCells().get(rowIndex, column);

			cell.setStyle(style);
		}
	}

	private Style getDefaultStyle(Worksheet worksheet) {
		Style style = worksheet.getCells().get(9, 0).getStyle();
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		Font font = style.getFont();
		font.setDoubleSize(NORMAL_FONT_SIZE);
		font.setName("ＭＳ ゴシック");
		return style;
	}

	private void settingHeader(Worksheet ws, CompanyInfor companyInfor) {

		DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
		
		ws.getCells().get(0, 0).putValue(TextResource.localize("CMM022_P2_1"));
		ws.getCells().get(0, 1).putValue(companyInfor.getCompanyCode() + "　" + companyInfor.getCompanyName());
		
		ws.getCells().get(1, 0).putValue(TextResource.localize("CMM022_P2_3"));
		ws.getCells().get(1, 1).putValue("CMM0022　グループ会社共通マスタの登録");

		ws.getCells().get(2, 0).putValue(TextResource.localize("CMM022_P2_5"));
		ws.getCells().get(2, 1).putValue(LocalDateTime.now().format(fullDateTimeFormatter));

		ws.getCells().get(3, 0).putValue(TextResource.localize("CMM022_P2_7"));
		ws.getCells().get(3, 1).putValue(TextResource.localize("CMM022_P2_8"));
	}

	private void settingTableHeader(Worksheet ws) {
		int rowIndex = FIRST_ROW_FILL - 1;

		ws.getCells().get(rowIndex, COMMONMASTERCODE).putValue(TextResource.localize("CMM022_P4_1"));
		ws.getCells().get(rowIndex, COMMONMASTERNAME).putValue(TextResource.localize("CMM022_P4_2"));
		ws.getCells().get(rowIndex, ITEMCODE).putValue(TextResource.localize("CMM022_P4_3"));
		ws.getCells().get(rowIndex, ITEMNAME).putValue(TextResource.localize("CMM022_P4_4"));
		ws.getCells().get(rowIndex, STARTDATE).putValue(TextResource.localize("CMM022_P4_5"));
		ws.getCells().get(rowIndex, ENDDATE).putValue(TextResource.localize("CMM022_P4_6"));
		ws.getCells().get(rowIndex, DISPLAYORDER).putValue(TextResource.localize("CMM022_P4_7"));
	}

}
