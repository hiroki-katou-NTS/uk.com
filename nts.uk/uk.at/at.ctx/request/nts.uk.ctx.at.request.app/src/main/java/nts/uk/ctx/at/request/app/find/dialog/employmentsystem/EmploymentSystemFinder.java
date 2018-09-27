package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecGenerationDigestionHis;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceHistoryOutputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentManaQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.RecAbsHistoryOutputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.RecruitmentHistoryOutPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffManagementQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffOutputHisData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffHistoryData;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class EmploymentSystemFinder {	
	@Inject
	BreakDayOffManagementQuery breakDayOffManagementQuery;
	
	@Inject
	EmployeeRequestAdapter employeeRequestAdapter;
	
	@Inject
	ClosureService closureService;
	
	@Inject
	AbsenceReruitmentManaQuery absenceReruitmentManaQuery;
	
	@Inject
	CompensLeaveEmSetRepository compensLeaveEmSetRepository;
	
	@Inject
	ShareEmploymentAdapter employeeAdaptor;
	
	/** 
	 * KDL005
	 * アルゴリズム「代休確認ダイア起動」を実行する
	 * 
	 * @param employeeIds
	 * @param baseDate
	 * @return
	 */
	public List<EmployeeBasicInfoDto> getEmployeeData(List<String> employeeIds, String baseDate) {
		String companyId = AppContexts.user().companyId();
		
		// 社員情報リストを取得する
		List<EmployeeBasicInfoDto> data = employeeRequestAdapter.getPerEmpBasicInfo(companyId, employeeIds)
				.stream().map(c -> EmployeeBasicInfoDto.fromDomain(c)).collect(Collectors.toList());
		
		if(data.isEmpty()) {
			return Collections.emptyList();
		}
		
		return data;
	}
	
	/**
	 * KDL005
	 * アルゴリズム「代休確認ダイア詳細取得」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public DetailConfirmDto getDetailsConfirm(String employeeId, String baseDate) {
		String companyId = AppContexts.user().companyId();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		
		// 基準日（指定がない場合はシステム日付）
		if(baseDate.isEmpty()) {
			baseDate = dtf.format(localDate);
		} else {
			baseDate = GeneralDate.fromString(baseDate, "yyyyMMdd").toString();
		}
		
		// アルゴリズム「社員に対応する締め期間を取得する」を実行する		
		DatePeriod closingPeriod = closureService.findClosurePeriod(employeeId, GeneralDate.fromString(baseDate, "yyyy/MM/dd"));
		
		// アルゴリズム「休出代休発生消化履歴の取得」を実行する
		Optional<BreakDayOffOutputHisData> data = breakDayOffManagementQuery.getBreakDayOffData(companyId, employeeId, GeneralDate.fromString(baseDate, "yyyy/MM/dd"));
		
		List<BreakDayOffHistoryDto> lstHistory = new ArrayList<>();
		
		if(data.isPresent() && data.get().getLstHistory().size() > 0) {
			for (BreakDayOffHistory item : data.get().getLstHistory()) {
				if(item == null) {
					continue;
				}
				
				ComDayoffDateDto hisDate = new ComDayoffDateDto(item.getHisDate().isUnknownDate(), 
						item.getHisDate().getDayoffDate().isPresent() ? item.getHisDate().getDayoffDate().get() : null);
				if(item.getBreakHis() == null) {
					continue;
				}
				BreakHistoryData breakHist = item.getBreakHis().isPresent() ? item.getBreakHis().get() : null;
				
				ComDayoffDateDto breakDate = new ComDayoffDateDto(breakHist != null ? breakHist.getBreakDate().isUnknownDate() : true, 
						breakHist != null ? (breakHist.getBreakDate().getDayoffDate().isPresent() ? breakHist.getBreakDate().getDayoffDate().get() : null) : null);
				
				BreakHistoryDataDto breakHis = new BreakHistoryDataDto(breakHist != null ? breakHist.getBreakMngId() : null, breakDate,
						breakHist != null ? breakHist.getExpirationDate() : null,
						breakHist != null ? breakHist.isChkDisappeared() : true,
						breakHist != null ? breakHist.getMngAtr().value : 0,
						breakHist != null ? breakHist.getOccurrenceDays() : 0.0,
						breakHist != null ? breakHist.getUnUseDays() : 0.0);
				
				DayOffHistoryData dayOffHist = item.getDayOffHis() != null ? item.getDayOffHis().get() : null;
				
				ComDayoffDateDto dayOffDate = new ComDayoffDateDto(dayOffHist != null ? dayOffHist.getDayOffDate().isUnknownDate() : true, 
						dayOffHist != null ? (dayOffHist.getDayOffDate().getDayoffDate().isPresent() ? dayOffHist.getDayOffDate().getDayoffDate().get(): null) : null);
				
				DayOffHistoryDataDto dayOffHis = new DayOffHistoryDataDto(dayOffHist != null ? dayOffHist.getCreateAtr().value : 0,
						dayOffHist != null ? dayOffHist.getDayOffId() : null, dayOffDate,
						dayOffHist != null ? dayOffHist.getRequeiredDays() : 0.0,
						dayOffHist != null ? dayOffHist.getUnOffsetDays() : 0.0);
				
				Double useDays  = item.getUseDays() != null ? item.getUseDays() : 0.0;
				
				Optional<BsEmploymentHistoryImport> empHistImport = employeeAdaptor.findEmploymentHistory(companyId, employeeId, GeneralDate.fromString(baseDate, "yyyy/MM/dd"));
				if(!empHistImport.isPresent() || empHistImport.get().getEmploymentCode()==null){
					throw new BusinessException("khong co employeeCode");
				}
				
				CompensatoryLeaveEmSetting compensatoryLeaveEmSet = this.compensLeaveEmSetRepository.find(companyId, empHistImport.get().getEmploymentCode());
				int isManaged = compensatoryLeaveEmSet != null ? compensatoryLeaveEmSet.getIsManaged().value : ManageDistinct.YES.value;
				
				BreakDayOffHistoryDto outputDto = new BreakDayOffHistoryDto(hisDate, breakHis, dayOffHis, useDays, isManaged);
				lstHistory.add(outputDto);
			}
		}
		
		DetailConfirmDto result = new DetailConfirmDto(closingPeriod, lstHistory, data.isPresent() ? data.get().getTotalInfor() : null);
		
		return result;
	}
	
	/**
	 * KDL009
	 * アルゴリズム「振休確認ダイアログ開始」を実行する
	 * 
	 * @param employeeIds
	 * @param baseDate
	 * @return
	 */
	public List<EmployeeBasicInfoDto> getEmployee(List<String> employeeIds, String baseDate) {
		String companyId = AppContexts.user().companyId();
		
		// 対象社員の件数をチェックする
		if(employeeIds.size() == 0) {
			// エラーメッセージ(#Msg_918#)を表示する
			throw new BusinessException("Msg_918");
		}
		
		// 社員情報リストを取得する
		List<EmployeeBasicInfoDto> data = employeeRequestAdapter.getPerEmpBasicInfo(companyId, employeeIds)
				.stream().map(c -> EmployeeBasicInfoDto.fromDomain(c)).collect(Collectors.toList());
		
		if(data.isEmpty()) {
			return Collections.emptyList();
		}
		
		return data;
	}

	/**
	 * KDL009
	 * アルゴリズム「振休残数情報の取得」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public NumberRestDaysDto getAcquisitionNumberRestDays(String employeeId, String baseDate) {
		String companyId = AppContexts.user().companyId();
		
		// アルゴリズム「社員に対応する締め期間を取得する」を実行する		
		DatePeriod closingPeriod = closureService.findClosurePeriod(employeeId, GeneralDate.fromString(baseDate, "yyyyMMdd"));
		
		// アルゴリズム「振出振休発生消化履歴の取得」を実行する
		Optional<AbsRecGenerationDigestionHis> data = absenceReruitmentManaQuery
				.generationDigestionHis(companyId, employeeId, GeneralDate.fromString(baseDate, "yyyyMMdd"));

		List<RecAbsHistoryOutputDto> recAbsHistoryOutput = new ArrayList<>();
		
		if(data.isPresent() && data.get().getGreneraGigesHis().size() > 0) {
			for (RecAbsHistoryOutputPara item : data.get().getGreneraGigesHis()) {
				if(item == null) {
					continue;
				}
				
				CompensatoryDayoffDateDto ymdData = new CompensatoryDayoffDateDto(item.getYmdData().isUnknownDate(), 
						item.getYmdData().getDayoffDate().isPresent() ? item.getYmdData().getDayoffDate().get() : null);
				
				Double useDays = item.getUseDays() != null ? item.getUseDays().get() : 0.0;
				
				AbsenceHistoryOutputPara absHisDatas = item.getAbsHisData() != null ? item.getAbsHisData().get() : null;
				
				CompensatoryDayoffDateDto absDate = new CompensatoryDayoffDateDto(absHisDatas != null ? absHisDatas.getAbsDate().isUnknownDate() : true, 
						absHisDatas != null ? (absHisDatas.getAbsDate().getDayoffDate().isPresent() ? absHisDatas.getAbsDate().getDayoffDate().get() : null) : null);
				
				AbsenceHistoryOutputParaDto absHisData = new AbsenceHistoryOutputParaDto(absHisDatas != null ? absHisDatas.getCreateAtr().value : 0, 
						absHisDatas != null ? absHisDatas.getAbsId() : null,
						absDate, absHisDatas != null ? absHisDatas.getRequeiredDays() : 0.0, 
						absHisDatas != null ? absHisDatas.getUnOffsetDays() : 0.0);
				
				RecruitmentHistoryOutPara recHisDatas = item.getRecHisData() != null ? item.getRecHisData().get() : null;
				
				CompensatoryDayoffDateDto recDate = new CompensatoryDayoffDateDto(
						recHisDatas != null ? recHisDatas.getRecDate().isUnknownDate() : true, 
						recHisDatas != null 
								? (recHisDatas.getRecDate().getDayoffDate().isPresent() ? recHisDatas.getRecDate().getDayoffDate().get() : null) 
								: null);
				
				RecruitmentHistoryOutParaDto recHisData = new RecruitmentHistoryOutParaDto(recHisDatas != null ? recHisDatas.getExpirationDate() : null, 
						recHisDatas != null ? recHisDatas.isChkDisappeared() : true,
						recHisDatas != null ? recHisDatas.getDataAtr().value : 0, 
						recHisDatas != null ? recHisDatas.getRecId() : null, recDate, 
						recHisDatas != null ? recHisDatas.getOccurrenceDays() : 0.0,
						recHisDatas != null ? recHisDatas.getHolidayAtr() : 0, 
						recHisDatas != null ? recHisDatas.getUnUseDays() : 0.0);
				
				RecAbsHistoryOutputDto outputDto = new RecAbsHistoryOutputDto(ymdData, useDays, absHisData, recHisData);
				recAbsHistoryOutput.add(outputDto);
			}
		}

		NumberRestDaysDto result = new NumberRestDaysDto(closingPeriod, recAbsHistoryOutput, data.isPresent() ? data.get().getAbsRemainInfor() : null);
		
		return result;
	}
}
