/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository;
import nts.uk.ctx.at.record.dom.stamp.StampItem;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.file.at.app.export.statement.EmployeeGeneralInfoAdapter;
import nts.uk.file.at.app.export.statement.EmployeeGeneralInfoDto;
import nts.uk.file.at.app.export.statement.EmployeeInfor;
import nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingGenerator;
import nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingQuery;
import nts.uk.file.at.app.export.statement.requestlist422.WkpHistWithPeriodAdapter;
import nts.uk.file.at.app.export.statement.requestlist422.WkpHistWithPeriodExport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeOutputConditionOfEmbossingGenerator.
 */
@Stateless
public class AsposeOutputConditionOfEmbossingGenerator extends AsposeCellsReportGenerator implements OutputConditionOfEmbossingGenerator{

	/** The stamping output item set repository. */
	@Inject
	private StampingOutputItemSetRepository stampingOutputItemSetRepository;
	
	/** The stamp card repository. */
	@Inject 
	private StampCardRepository stampCardRepository;
	
	@Inject
	private WorkLocationRepository workLocationRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	/** The Constant EXISTS. */
	private static final String EXISTS = "EXISTS";
	
	/** The Constant GET_EMPLOYMENT. */
	// 雇用を取得する
	private static final boolean GET_EMPLOYMENT = false;
	
	/** The Constant GET_CLASSIFICATION. */
	// 分類を取得する
	private static final boolean GET_CLASSIFICATION = false;
	
	/** The Constant GET_POSITION. */
	// 職位を取得する
	private static final boolean GET_POSITION = false;
	
	/** The Constant GET_WORKPLACE. */
	// 職場を取得する
	private static final boolean GET_WORKPLACE = true;
	
	/** The filename. */
	private final String filename = "report/KDP003.xlsx";
	
	/* (non-Javadoc)
	 * @see nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingGenerator#generate(nts.arc.layer.infra.file.export.FileGeneratorContext, nts.uk.file.at.app.export.statement.OutputConditionOfEmbossingQuery)
	 */
	@Override
	public void generate(FileGeneratorContext fileGeneratorContext, OutputConditionOfEmbossingQuery query) {
		String companyId = AppContexts.user().companyId();
		
		// ドメインモデル「打刻一覧出力項目設定」を取得する(get domain model 「打刻一覧出力項目設定」)
		StampingOutputItemSet stampingOutputItemSet = stampingOutputItemSetRepository.getByCidAndCode(companyId, query.getOutputSetCode()).get();
		
		exportExcel(fileGeneratorContext, query);
		
//		getTargetData(query.getLstEmployee(), query.getStartDate(), query.getEndDate(), query.isCardNumNotRegister());
	}
	
	private void exportExcel(FileGeneratorContext fileGeneratorContext, OutputConditionOfEmbossingQuery query) {
		val reportContext = this.createContext(filename);
		
		// The path to the documents directory.
//		String dataDir = Utils.getSharedDataDir(AddingDataToCells.class) + "Data/";
		String dataDir = "E:\\Project Doc\\Task\\KDP003_打刻一覧表\\Template";
		String filePath = "E:\\Project Doc\\Task\\KDP003_打刻一覧表\\Template\\帳票設計書-KDP003-打刻一覧表.xlsx";
		// Instantiating a Workbook object
//		Workbook workbook = new Workbook(filePath);
		Workbook workbook = reportContext.getWorkbook();

		// Accessing the added worksheet in the Excel file
		Worksheet worksheet = workbook.getWorksheets().get("帳票レイアウト");
		Worksheet worksheetCopy = workbook.getWorksheets().get("copy");
		Cells cells = worksheet.getCells();

		// Adding value to the cell
		Cell cell = cells.get("A40");
		cell.setValue("A40");

		// Setting the display format of the date
		Style style = cell.getStyle();
		style.setNumber(15);
		cell.setStyle(style);

//		// copy page template 1 -> 2
		Range range1 = worksheetCopy.getCells().createRange("A3", "BQ34");
		Range range2 = worksheet.getCells().createRange("A35", "BQ66");
		try {
			range2.copy(range1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Range rangeBorderRight = worksheet.getCells().createRange("BQ35", "BQ66");
		Cell cellEven = cells.get("BQ18");
		Style styleBorderEven = cellEven.getStyle();
		Cell cellOdd = cells.get("BQ17");
		Style styleBorderOdd = cellOdd.getStyle();

//		styleBorder.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getGray());
//		styleBorder.setBackgroundColor(Color.getBlack());
//		cell.setStyle(styleBorder);
		
		Iterator cellArray = rangeBorderRight.iterator();
		while (cellArray.hasNext()) {
			Cell temp = (Cell) cellArray.next();
			if (temp.getRow() % 2 == 0) {
				temp.setStyle(styleBorderOdd);
			} else {
				temp.setStyle(styleBorderEven);
			}
			// Saving the modified style to the cell.
		}
		
		// delete row
		int col7Start = cells.get("BM3").getColumn();
		int col7End = cells.get("BQ3").getColumn();
		int col5Start = cells.get("BE3").getColumn();
		int col5End = cells.get("BH3").getColumn();
		
		worksheet.getCells().deleteColumns(col7Start, col7End - col7Start + 1, true);
		worksheet.getCells().deleteColumns(col5Start, col5End - col5Start + 1, true);
		worksheet.getCells().deleteRows(40, cells.getMaxRow(), true);
		
		// Saving the Excel file
		workbook.getWorksheets().removeAt("copy");
//		workbook.save(dataDir + "\\AddingDataToCells_out.xls");
		reportContext.saveAsExcel(this.createNewFile(fileGeneratorContext, "KDP003.xlsx"));

		// Print message
		System.out.println("Data Added Successfully");
	}
	
	/**
	 * Gets the target data.
	 *
	 * @param lstEmployee the lst employee
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param cardNumNotRegister the card num not register
	 * @return the target data
	 */
	// 対象データを取得する(get data đối tường)
	private void getTargetData(List<EmployeeInfor> lstEmployee, GeneralDate startDate, GeneralDate endDate, boolean cardNumNotRegister) {
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		List<String> lstEmployeeId = lstEmployee.stream().map(data -> data.getEmployeeID()).collect(Collectors.toList());
		DatePeriod datePeriod = new DatePeriod(startDate, endDate);
		
		// 「打刻」
		List<StampItem> lstStampItem = new ArrayList<>();
		
		// Input.カードNO未登録を判定する(phan đoan Input.card NO chưa đăng ky)
		if (cardNumNotRegister) {
			// ドメインモデル「打刻カード」を取得する(get domain model 「打刻カード」)
			List<StampCard> lstStampCard = stampCardRepository.getLstStampCardByContractCode(contractCode);
			
			// TODO: hoangdd - can phai xem lai, hinh nhu buoc nay chua co
			// TODO: hoangdd - request repo de lay toan bo data cua domain StampItem roi tu filter bang java
			// ドメインモデル「打刻」を取得する(get domain model 「打刻」)
			if (lstStampCard.size() > 1000) {
				lstStampCard.stream().forEach(domain -> {
					domain.setStampNumber(new StampNumber(EXISTS));
				});
			}
		} else {
			//TODO: hoangdd - dang fake app cho request list 401, QA lai KH de sua EA
			//		xem lai xem co can dung het cac du lieu trong 401 ko? neu ko thi chi chon nhung cai can. 
			// Refer Request List 401, if have problem, please edit code to same code of 401
			// Imported「社員の履歴情報」 を取得する(get Imported「社員の履歴情報」)
			EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter = new EmployeeGeneralInfoAdapter();
			EmployeeGeneralInfoDto employeeGeneralInfoDto = employeeGeneralInfoAdapter.getPerEmpInfo(lstEmployeeId, datePeriod, GET_EMPLOYMENT, GET_CLASSIFICATION, GET_POSITION, GET_WORKPLACE, false);
			
			// 取得した「社員の履歴情報」から使用している職場IDを抽出する(extract using workplace ID from get 「社員の履歴情報」- history information of employee)
			List<String> lstWkpID = employeeGeneralInfoDto.getWorkplaceDto().stream().map(
																	dto -> dto.getWorkplaceItems()
																			.stream().map(dtoChild -> dtoChild.getWorkplaceId())
																			.collect(Collectors.toList()))
														.collect(Collectors.toList())
													// convert List<List> to List
													.stream().flatMap(List::stream)
													.distinct()
													.collect(Collectors.toList());
			
			WkpHistWithPeriodAdapter wkpHistWithPeriodAdapter = new WkpHistWithPeriodAdapter();
			
			//TODO: hoangdd - dang fake app cho request list 422, QA lai KH de sua EA
			//		xem lai xem co can dung het cac du lieu trong 422 ko? neu ko thi chi chon nhung cai can. 
			// Refer Request List 422, if have problem, please edit code to same code of 422
			// Imported「職場履歴情報」を取得する(get Imported「職場履歴情報」)		
			List<WkpHistWithPeriodExport> lstWkpHistWithPeriodExport = wkpHistWithPeriodAdapter.getLstHistByWkpsAndPeriod(lstWkpID, datePeriod);
			
			// ドメインモデル「打刻カード」を取得する(get domain model「打刻カード」)
			List<StampCard> lstStampCard = stampCardRepository.getLstStampCardByLstSid(lstEmployee.stream()
																								  	.map(dto -> dto.getEmployeeID())
																								  	.collect(Collectors.toList()));
			
			// ドメインモデル「打刻」を取得する(get domain model 「打刻」)
			// TODO: hoangdd - request repo de lay toan bo data cua domain StampItem roi tu filter bang java
			
		}
		
		List<WorkLocation> lstWorkLocation = new ArrayList<>();
		// ドメインモデル「勤務場所」を取得する(get domain model 「勤務場所」- workplace)
		lstStampItem.stream().forEach(domain -> {
			Optional<WorkLocation> optWorkLocation = workLocationRepository.findByCode(companyId, domain.getWorkLocationCd().v());
			if (optWorkLocation.isPresent()) {
				lstWorkLocation.add(optWorkLocation.get());
			}
		});
		
		List<String> lstWorktimeCode = lstStampItem.stream().map(domain -> domain.getSiftCd().v()).collect(Collectors.toList());
		// ドメインモデル「就業時間帯の設定」を取得する(get domain model 「就業時間帯の設定」- setting time zone lam việc)
		List<WorkTimeSetting> lstWorkTimeSetting = workTimeSettingRepository.getListWorkTimeSetByListCode(companyId, lstWorktimeCode);
		
	}
}
