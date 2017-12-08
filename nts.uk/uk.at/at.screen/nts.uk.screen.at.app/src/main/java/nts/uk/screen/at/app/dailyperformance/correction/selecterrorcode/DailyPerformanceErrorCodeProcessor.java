package nts.uk.screen.at.app.dailyperformance.correction.selecterrorcode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ActualLockDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFomatDailyDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFormatInitialDisplayDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFormatSheetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ClosureDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.CorrectionOfDailyPerformance;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItemControl;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPBusinessTypeControl;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCellDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPControlDisplayItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorSettingDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPHeaderDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPSheetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkFixedDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkshowbutton.DailyPerformanceAuthorityDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyPerformanceErrorCodeProcessor {
	@Inject
	private DailyPerformanceScreenRepo repo;
	public DailyPerformanceCorrectionDto generateData(DateRange dateRange, List<DailyPerformanceEmployeeDto> lstEmployee, int displayFormat, CorrectionOfDailyPerformance correct, List<String> errorCodes, List<String> formatCodes) {
		String sId = AppContexts.user().employeeId();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
		
		/** システム日付を基準に1ヵ月前の期間を設定する | Set date range one month before system date */
		screenDto.setDateRange(dateRange);
		
		/** 画面制御に関する情報を取得する | Acquire information on screen control */
		// アルゴリズム「社員の日別実績の権限をすべて取得する」を実行する | Execute "Acquire all permissions of employee's daily performance"--
		// roleId = AppContexts.user().roles().forPersonalInfo()  fixed
		List<DailyPerformanceAuthorityDto> dailyPerformans = repo.findDailyAuthority("001");
		if(dailyPerformans.isEmpty()){
		   throw new BusinessException("Msg_671");
		}
		else{
			// NO.15
			screenDto.setAuthorityDto(dailyPerformans);
		}
		//アルゴリズム「社員に対応する処理締めを取得する」を実行する | Execute "Acquire Process Tightening Corresponding to Employees"--
		ClosureDto closureDto = repo.getClosureId(sId, dateRange.getEndDate());
	    
		// アルゴリズム「休暇の管理状況をチェックする」を実行する | Get holiday setting data
		getHolidaySettingData(screenDto);
		
		
		/** アルゴリズム「表示形式に従って情報を取得する」を実行する | Execute "Get information according to display format" */
		// アルゴリズム「対象者を抽出する」を実行する  | Execute "Extract subject"
		
		List<String> listEmployeeId = lstEmployee.stream().map(e -> e.getId()).collect(Collectors.toList());
		List<DPErrorDto> lstError = this.repo.getListDPError(screenDto.getDateRange(), listEmployeeId, errorCodes);
		Map<String, String> mapIdError =  new HashMap<>();
		for(DPErrorDto dto : lstError){
			mapIdError.put(dto.getEmployeeId(), "");
		}
		lstEmployee = lstEmployee.stream().filter(x -> mapIdError.get(x.getId())!= null).collect(Collectors.toList());
		if(lstEmployee.isEmpty()){
			   throw new BusinessException("Msg_672");
		}
		screenDto.setLstEmployee(lstEmployee);
		// 表示形式をチェックする | Check display format => UI
		// Create lstData: Get by listEmployee & listDate
		// 日付別の情報を取得する + 個人別の情報を取得する + エラーアラームの情報を取得する | Acquire information by date + Acquire personalized information + Acquire error alarm information
		screenDto.setLstData(getListData(screenDto.getLstEmployee(), dateRange));
		/// 対応する「日別実績」をすべて取得する | Acquire all corresponding "daily performance"
		///対応する「日別実績」をすべて取得する-- lay tat ca thanh tich theo ngay tuong ung 
		//// 日別実績の勤務情報
//		 List<WorkInfoOfDailyPerformanceDetailDto> workInfoOfDailyPerformanceDetailDtos = repo.find(listEmployeeId, dateRange);
		/// アルゴリズム「対象日に対応する社員の実績の編集状態を取得する」を実行する | Execute "Acquire edit status of employee's record corresponding to target date"| lay ve trang thai sua cua thanh tich nhan vien tuong ung 
		//--List<DailyRecEditSetDto> dailyRecEditSets = repo.getDailyRecEditSet(listEmployeeId, dateRange);
		/// アルゴリズム「実績エラーをすべて取得する」を実行する | Execute "Acquire all actual errors"
		if (screenDto.getLstEmployee().size() > 0) {
			/// ドメインモデル「社員の日別実績エラー一覧」をすべて取得する  + 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
			/// Acquire all domain model "employee's daily performance error list" + "work error error alarm" | lay loi thanh tich trong khoang thoi gian 
			if (lstError.size() > 0) {
				// Get list error setting
				List<DPErrorSettingDto> lstErrorSetting = this.repo.getErrorSetting(lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
				// Seperate Error and Alarm
				screenDto.addErrorToResponseData(lstError, lstErrorSetting);
			}
		}
		
		/// TODO : アルゴリズム「対象日に対応する承認者確認情報を取得する」を実行する | Execute "Acquire Approver Confirmation Information Corresponding to Target Date"
		
		//アルゴリズム「就業確定情報を取得する」を実行する
		/// アルゴリズム「日別実績のロックを取得する」を実行する (Tiến hành xử lý "Lấy về lock của thành tích theo ngày")
		 Optional<ActualLockDto> actualLockDto =  repo.findAutualLockById(AppContexts.user().companyId(), closureDto.getClosureId());
		// アルゴリズム「表示項目を制御する」を実行する | Execute "control display items"
		 Optional<WorkFixedDto> workFixedOp = repo.findWorkFixed( closureDto.getClosureId(), closureDto.getClosureMonth());
		 
		DPControlDisplayItem dPControlDisplayItem = getControlDisplayItems(listEmployeeId, screenDto.getDateRange(), correct, formatCodes);
		screenDto.setLstControlDisplayItem(dPControlDisplayItem);
		//// 11. Excel: 未計算のアラームがある場合は日付又は名前に表示する
		//Map<Integer, Integer> typeControl =  lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem:: getId, DPAttendanceItem::getAttendanceAtr));
		List<WorkInfoOfDailyPerformanceDto> workInfoOfDaily = repo.getListWorkInfoOfDailyPerformance(listEmployeeId,
				dateRange);
		List<DPDataDto> lstData = new ArrayList<DPDataDto>();
		Map<Integer,DPAttendanceItem>  mapDP = dPControlDisplayItem.getLstAttendanceItem() != null ? dPControlDisplayItem.getLstAttendanceItem().stream().collect(Collectors.toMap(DPAttendanceItem :: getId, x-> x)): new HashMap<>();
		for (DPDataDto data : screenDto.getLstData()) {
			List<DPCellDataDto> cellDatas = new ArrayList<>();
			if (dPControlDisplayItem.getLstAttendanceItem() != null) {
				dPControlDisplayItem.getLstAttendanceItem().forEach(item -> {
					Random rn = new Random();
					int a = rn.nextInt(100);
					int attendanceAtr = mapDP.get(item.getId()).getAttendanceAtr();
					if (attendanceAtr == DailyAttendanceAtr.Code.value || attendanceAtr == DailyAttendanceAtr.Classification.value) {
						cellDatas.add(new DPCellDataDto("Code" + String.valueOf(item.getId()), String.valueOf(a),
								String.valueOf(item.getAttendanceAtr()), "label"));
						cellDatas.add(new DPCellDataDto("Name" + String.valueOf(item.getId()), "Link Name"+ item.getId(),
								String.valueOf(item.getAttendanceAtr()), "Link2"));
					} else {
						cellDatas.add(new DPCellDataDto("_"+String.valueOf(item.getId()), String.valueOf(a),
								String.valueOf(item.getAttendanceAtr()), "label"));
					}
				});
			}
			data.setCellDatas(cellDatas);
			lstData.add(data);
			//DPCellDataDto bPCellDataDto = new DPCellDataDto(columnKey, value, dataType, type);
			Optional<WorkInfoOfDailyPerformanceDto> optWorkInfoOfDailyPerformanceDto = workInfoOfDaily.stream()
					.filter(w -> w.getEmployeeId().equals(data.getEmployeeId()) && w.getYmd().equals(data.getDate()))
					.findFirst();
			if (optWorkInfoOfDailyPerformanceDto.isPresent()
					&& optWorkInfoOfDailyPerformanceDto.get().getState() == CalculationState.No_Calculated)
				screenDto.setAlarmCellForFixedColumn(data.getId());
		}
		//screenDto.setLstData(lstData);
		screenDto.markLoginUser();
		screenDto.createAccessModifierCellState(mapDP);
		return screenDto;
	}
	
	/** アルゴリズム「休暇の管理状況をチェックする」を実行する */
	private void getHolidaySettingData(DailyPerformanceCorrectionDto dailyPerformanceCorrectionDto) {
		// アルゴリズム「年休設定を取得する」を実行する
		dailyPerformanceCorrectionDto.setYearHolidaySettingDto(this.repo.getYearHolidaySetting());
		// アルゴリズム「振休管理設定を取得する」を実行する
		dailyPerformanceCorrectionDto.setSubstVacationDto(this.repo.getSubstVacationDto());
		// アルゴリズム「代休管理設定を取得する」を実行する
		dailyPerformanceCorrectionDto.setCompensLeaveComDto(this.repo.getCompensLeaveComDto());
		// アルゴリズム「60H超休管理設定を取得する」を実行する
		dailyPerformanceCorrectionDto.setCom60HVacationDto(this.repo.getCom60HVacationDto());
	}
	    
	/** 
	 * Get List Data include:<br/>
	 * Employee and Date
	 **/
	private List<DPDataDto> getListData(List<DailyPerformanceEmployeeDto> listEmployee, DateRange dateRange) {
		List<DPDataDto> result = new ArrayList<>();
		if (listEmployee.size() > 0) {
			List<GeneralDate> lstDate = dateRange.toListDate();
			int dataId = 0;			
			for (int j = 0; j < listEmployee.size(); j++) {
				DailyPerformanceEmployeeDto employee = listEmployee.get(j);
				for (int i = 0; i < lstDate.size(); i++) {
					GeneralDate filterDate = lstDate.get(i);					
					result.add(new DPDataDto(dataId, "1", "", filterDate, false, employee.getId(), employee.getCode(), employee.getBusinessName()));
					dataId++;
				}
			}
		}
		return result;
	}
	
	/** アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items" */
	private DPControlDisplayItem getControlDisplayItems(List<String> lstEmployeeId, DateRange dateRange, CorrectionOfDailyPerformance correct, List<String> formatCodeSelects) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		String companyId = AppContexts.user().companyId();
		if (lstEmployeeId.size() > 0) {
			//対応するドメインモデル「日別実績の運用」を取得する | Acquire corresponding domain model "Operation of daily performance"
			OperationOfDailyPerformanceDto dailyPerformanceDto = repo.findOperationOfDailyPerformance();
			//取得したドメインモデル「日別実績の運用」をチェックする | Check the acquired domain model "Operation of daily performance"
			List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
			List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
			List<Integer> lstAtdItem  = new ArrayList<>();
			List<Integer> lstAtdItemUnique = new ArrayList<>();
			List<DPAttendanceItem> lstAttendanceItem = new ArrayList<>();
            if(dailyPerformanceDto != null && dailyPerformanceDto.getSettingUnit() == SettingUnit.AUTHORITY){
            	List<AuthorityFomatDailyDto> authorityFomatDailys = new ArrayList<>();
            	List<AuthorityFormatSheetDto> authorityFormatSheets = new ArrayList<>();
            	//アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
            	// kiem tra thong tin rieng biet user
				if (correct == null) {
					if (formatCodeSelects.isEmpty()) {
						List<AuthorityFormatInitialDisplayDto> initialDisplayDtos = repo
								.findAuthorityFormatInitialDisplay(companyId);
						if (!initialDisplayDtos.isEmpty()) {
							List<String> formatCodes = initialDisplayDtos.stream()
									.map(x -> x.getDailyPerformanceFormatCode()).collect(Collectors.toList());
							// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
							authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodes);
							List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
									.collect(Collectors.toList());
							authorityFormatSheets = repo.findAuthorityFormatSheet(companyId, formatCodes, sheetNos);
						}
					} else {
						authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodeSelects);
						List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
								.collect(Collectors.toList());
						authorityFormatSheets = repo.findAuthorityFormatSheet(companyId, formatCodeSelects, sheetNos);
					}
                }else{
                	//Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
                	    List<String> formatCodes = Arrays.asList(correct.getPreviousDisplayItem());
                	    authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodes);
                	    List<BigDecimal>sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo()).collect(Collectors.toList());
                	    authorityFormatSheets = repo.findAuthorityFormatSheet(companyId, formatCodes, sheetNos);
                }
                if(!authorityFomatDailys.isEmpty()){
            		lstFormat = new ArrayList<FormatDPCorrectionDto>();
            		lstSheet = new ArrayList<DPSheetDto>();
            		Map<Integer,DPAttendanceItem>  mapDP = new HashMap<>();
            		// set FormatCode for button A2_4
            		result.setFormatCode(authorityFomatDailys.stream().map( x-> x.getDailyPerformanceFormatCode()).collect(Collectors.toSet()));
                	lstSheet = authorityFormatSheets.stream().map(x -> new DPSheetDto(x.getSheetNo().toString(),x.getSheetName().toString())).collect(Collectors.toList());
                	lstFormat = authorityFomatDailys.stream().map(x -> new FormatDPCorrectionDto(companyId, x.getDailyPerformanceFormatCode(), x.getAttendanceItemId(), x.getSheetNo().toString(), x.getDisplayOrder(), x.getColumnWidth().intValue())).collect(Collectors.toList());
                	lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId())
    						.collect(Collectors.toList());
    				lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());
    				lstAttendanceItem = this.repo.getListAttendanceItem(lstAtdItemUnique);
    				mapDP = lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem :: getId, x-> x));
    				List<DPHeaderDto> lstHeader = new ArrayList<>();
    				for(FormatDPCorrectionDto dto : lstFormat){
    					// chia cot con code name cua AttendanceItemId chinh va set 
    					lstHeader.add(DPHeaderDto.createSimpleHeader("_"+String.valueOf(dto.getAttendanceItemId()),
    							String.valueOf(dto.getColumnWidth()) + "px", mapDP));
    				}
    				result.setLstHeader(lstHeader);
    				//result.setLstSheet(lstSheet);
    				result.createSheets(lstSheet);
                	result.addColumnsToSheet(lstFormat, mapDP);
                }
            }else{
        			// アルゴリズム「社員の勤務種別に対応する表示項目を取得する」を実行する
        			/// アルゴリズム「社員の勤務種別をすべて取得する」を実行する
        			List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployeeId, dateRange);
        			
        			// Create header & sheet
        			if (lstBusinessTypeCode.size() > 0) {
        				
        				lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
        				/// 対応するドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する
        				lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode);
        				Map<Integer,DPAttendanceItem>  mapDP = new HashMap<>();
        				lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId())
        						.collect(Collectors.toList());
        				lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());
        				lstAttendanceItem = this.repo.getListAttendanceItem(lstAtdItemUnique);
        				result.createSheets(lstSheet);
        				mapDP = lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem :: getId, x-> x));
        				result.addColumnsToSheet(lstFormat, mapDP);
        				List<DPHeaderDto> lstHeader = new ArrayList<>();
        				for(FormatDPCorrectionDto dto : lstFormat){
        					lstHeader.add(DPHeaderDto.createSimpleHeader("_"+String.valueOf(dto.getAttendanceItemId()),
        							String.valueOf(dto.getColumnWidth()) + "px", mapDP));
        				}
        				result.setLstHeader(lstHeader);
        			}
        			
        			List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
        			if (lstFormat.size() > 0) {
        				lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(lstBusinessTypeCode, lstAtdItemUnique);
        			}
        			if (lstDPBusinessTypeControl.size() > 0) {
        				// set header access modifier
        				// only user are login can edit or others can edit
        				result.setColumnsAccessModifier(lstDPBusinessTypeControl);
        			}
        		}
						// set text to header
						result.setHeaderText(lstAttendanceItem);
						// set color to header
						List<DPAttendanceItemControl> lstAttendanceItemControl = this.repo
								.getListAttendanceItemControl(lstAtdItemUnique);
						result.setLstAttendanceItem(lstAttendanceItem);
						result.setHeaderColor(lstAttendanceItemControl);
		}
		return result;
	}
}
