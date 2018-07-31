/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.statement;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.app.find.statement.export.DataExport;
import nts.uk.ctx.at.function.app.find.statement.export.StatementList;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository;
import nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingGenerator;
import nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeOutputConditionOfEmbossingGenerator.
 */
@Stateless
public class AsposeOutputConditionOfEmbossingGenerator extends AsposeCellsReportGenerator implements OutputConditionOfEmbossingGenerator{

	/** The stamping output item set repository. */
	@Inject
	private StampingOutputItemSetRepository stampingOutputItemSetRepository;
	
	@Inject
	private DataExport dataExport;
	
	/** The Constant filename. */
	private static final String filename = "report/KDP003.xlsx";
	
	/** The Constant yyyyMMdd. */
	private static final String yyyyMMdd = "yyyy/MM/dd";
	
	/** The Constant yyyyMd. */
	private static final String yyyyMd = "yyyy/M/d";
	
	private static final String[] ATTANDANCE_CLASSIFICATION_COLUMN = new String[]{"AM3", "AQ3"}; // 出退勤区分
	private static final String[] WORKING_HOURS_COLUMN = new String[]{"AR3", "AV3"}; // 就業時間帯
	private static final String[] INSTALL_LOCATION_COLUMN =  new String[]{"AW3", "AZ3"}; // 設置場所
	private static final String[] LOCATION_INFORM_COLUMN =  new String[]{"BA3", "BD3"}; // 位置情報
	private static final String[] OT_HOURS_COLUMN =  new String[]{"BE3", "BH3"}; // 残業時間
	private static final String[] LATE_NIGHT_TIME_COLUMN =  new String[]{"BI3", "BL3"}; // 深夜時間
	private static final String[] SUPPORT_CARD_COLUMN =  new String[]{"BM3", "BQ3"}; // 応援カード
	
	/* (non-Javadoc)
	 * @see nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingGenerator#generate(nts.arc.layer.infra.file.export.FileGeneratorContext, nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingQuery)
	 */
	@Override
	public void generate(FileGeneratorContext fileGeneratorContext, OutputConditionOfEmbossingQuery query) {
		String companyId = AppContexts.user().companyId();
//		List<String> lstEmployeeId = query.getLstEmployee();
		
		// ドメインモデル「打刻一覧出力項目設定」を取得する(get domain model 「打刻一覧出力項目設定」)
		StampingOutputItemSet stampingOutputItemSet = stampingOutputItemSetRepository.getByCidAndCode(companyId, query.getOutputSetCode()).get();
		
		List<StatementList> dataPreExport = dataExport.getTargetData(query.getLstEmployee(), 
																	 convertToDate(query.getStartDate(), yyyyMMdd), 
																	 convertToDate(query.getEndDate(), yyyyMMdd), 
																	 query.isCardNumNotRegister());
		exportExcel(fileGeneratorContext, dataPreExport, stampingOutputItemSet);
	}
	
	/**
	 * Export excel.
	 *
	 * @param fileGeneratorContext the file generator context
	 * @param dataPreExport the data pre export
	 */
	private void exportExcel(FileGeneratorContext fileGeneratorContext, List<StatementList> dataPreExport, StampingOutputItemSet stampingOutputItemSet) {
		
		val reportContext = this.createContext(filename);
		
		// Instantiating a Workbook object
		Workbook workbook = reportContext.getWorkbook();

		// Accessing the added worksheet in the Excel file
		Worksheet worksheet = workbook.getWorksheets().get("帳票レイアウト");
		Worksheet worksheetCopy = workbook.getWorksheets().get("copy");
		Cells cells = worksheet.getCells();

//		// copy page template 1 -> 2
		Range range1 = worksheetCopy.getCells().createRange("A3", "BQ34");
		int countLinePage = 3;
		while (countLinePage <= dataPreExport.size() && dataPreExport.size() > 34) {
			countLinePage += 31;
			Range range2 = worksheet.getCells().createRange("A" + (countLinePage+1), "BQ" + (countLinePage + 34));
			try {
				range2.copy(range1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			countLinePage += 1;
		}
		
		Integer count = 3;
		// process export with data
		for (int i = 0; i < dataPreExport.size(); i++) {
			StatementList dto = dataPreExport.get(i);
			if (i == 0) {
				cells.get("X"+count).setValue(dto.getCardNo());
			} else if (i != 0 && dto.getCardNo().compareTo(dataPreExport.get(i-1).getCardNo()) != 0) {
				cells.get("X"+count).setValue(dto.getCardNo());
			}
			String date = dto.getDate().toString(yyyyMd);
			if (i == 0) {
				cells.get("AE"+count).setValue(date);
			} else if (i != 0 && date.compareTo(dataPreExport.get(i-1).getDate().toString(yyyyMd)) != 0) {
				cells.get("AE"+count).setValue(date);
			}
			cells.get("AJ"+count).setValue(dto.getTime());
			cells.get("AM"+count).setValue(dto.getAtdType());
			cells.get("AR"+count).setValue(dto.getWorkTimeZone());
			count++;
		}
		
		// delete row and column 
		int col1Start = cells.get(ATTANDANCE_CLASSIFICATION_COLUMN[0]).getColumn();
		int col1End = cells.get(ATTANDANCE_CLASSIFICATION_COLUMN[1]).getColumn();
		int col2Start = cells.get(WORKING_HOURS_COLUMN[0]).getColumn();
		int col2End = cells.get(WORKING_HOURS_COLUMN[1]).getColumn();
		int col3Start = cells.get(INSTALL_LOCATION_COLUMN[0]).getColumn();
		int col3End = cells.get(INSTALL_LOCATION_COLUMN[1]).getColumn();
		int col4Start = cells.get(LOCATION_INFORM_COLUMN[0]).getColumn();
		int col4End = cells.get(LOCATION_INFORM_COLUMN[1]).getColumn();
		int col5Start = cells.get(OT_HOURS_COLUMN[0]).getColumn();
		int col5End = cells.get(OT_HOURS_COLUMN[1]).getColumn();
		int col6Start = cells.get(LATE_NIGHT_TIME_COLUMN[0]).getColumn();
		int col6End = cells.get(LATE_NIGHT_TIME_COLUMN[1]).getColumn();
		int col7Start = cells.get(SUPPORT_CARD_COLUMN[0]).getColumn();
		int col7End = cells.get(SUPPORT_CARD_COLUMN[1]).getColumn();
		
		if (!stampingOutputItemSet.isOutputSupportCard()) {
			deleteCell(worksheet, col7Start, col7End);
		}
		
		if (!stampingOutputItemSet.isOutputNightTime()) {
			deleteCell(worksheet, col6Start, col6End);
		}
		
		if (!stampingOutputItemSet.isOutputOT()) {
			deleteCell(worksheet, col5Start, col5End);
		}
		
		if (!stampingOutputItemSet.isOutputPosInfor()) {
			deleteCell(worksheet, col4Start, col4End);
		}
		
		if (!stampingOutputItemSet.isOutputSetLocation()) {
			deleteCell(worksheet, col3Start, col3End);
		}
		
		if (!stampingOutputItemSet.isOutputWorkHours()) {
			deleteCell(worksheet, col2Start, col2End);
		}
		
		if (!stampingOutputItemSet.isOutputEmbossMethod()) {
			deleteCell(worksheet, col1Start, col1End);
		}
		
		worksheet.getCells().deleteRows(count-1, worksheet.getCells().getMaxRow(), true);
		
		// Saving the Excel file
		workbook.getWorksheets().removeAt("copy");
		reportContext.saveAsExcel(this.createNewFile(fileGeneratorContext, "KDP003.xlsx"));
	}
	
	/**
	 * Delete cell.
	 *
	 * @param worksheet the worksheet
	 * @param colStart the col start
	 * @param colEnd the col end
	 */
	private void deleteCell(Worksheet worksheet, int colStart, int colEnd) {
		worksheet.getCells().deleteColumns(colStart, colEnd - colStart + 1, true);
	}
	
	/**
	 * Convert to date.
	 *
	 * @param date the date
	 * @param format the format
	 * @return the general date
	 */
	private GeneralDate convertToDate(String date, String format) {
		return GeneralDate.fromString(date, format);
	}
}
