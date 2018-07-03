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
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentManaQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.RecAbsHistoryOutputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffManagementQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffOutputHisData;
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
				ComDayoffDateDto hisDate = new ComDayoffDateDto(item.getHisDate().isUnknownDate(), item.getHisDate().getDayoffDate().get());
				
				ComDayoffDateDto breakDate = new ComDayoffDateDto(item.getBreakHis() != null ? item.getBreakHis().get().getBreakDate().isUnknownDate() : true, 
						item.getBreakHis() != null ? item.getBreakHis().get().getBreakDate().getDayoffDate().get() : null);
				
				BreakHistoryDataDto breakHis = new BreakHistoryDataDto(item.getBreakHis() != null ? item.getBreakHis().get().getBreakMngId() : null, breakDate,
						item.getBreakHis() != null ? item.getBreakHis().get().getExpirationDate() : null,
						item.getBreakHis() != null ? item.getBreakHis().get().isChkDisappeared() : true,
						item.getBreakHis() != null ? item.getBreakHis().get().getMngAtr().value : 0,
						item.getBreakHis() != null ? item.getBreakHis().get().getOccurrenceDays() : 0.0,
						item.getBreakHis() != null ? item.getBreakHis().get().getUnUseDays() : 0.0);
				
				ComDayoffDateDto dayOffDate = new ComDayoffDateDto(item.getDayOffHis() != null ? item.getDayOffHis().get().getDayOffDate().isUnknownDate() : true, 
						item.getDayOffHis() != null ? item.getDayOffHis().get().getDayOffDate().getDayoffDate().get() : null);
				
				DayOffHistoryDataDto dayOffHis = new DayOffHistoryDataDto(item.getDayOffHis() != null ? item.getDayOffHis().get().getCreateAtr().value : 0,
						item.getDayOffHis() != null ? item.getDayOffHis().get().getDayOffId() : null, dayOffDate,
						item.getDayOffHis() != null ? item.getDayOffHis().get().getRequeiredDays() : 0.0,
						item.getDayOffHis() != null ? item.getDayOffHis().get().getUnOffsetDays() : 0.0);
				
				Double useDays  = item.getUseDays() != null ? item.getUseDays() : 0.0;
				
				Optional<BsEmploymentHistoryImport> empHistImport = employeeAdaptor.findEmploymentHistory(companyId, employeeId, GeneralDate.fromString(baseDate, "yyyy/MM/dd"));
				if(!empHistImport.isPresent() || empHistImport.get().getEmploymentCode()==null){
					throw new BusinessException("khong co employeeCode");
				}
				
				CompensatoryLeaveEmSetting compensatoryLeaveEmSet = this.compensLeaveEmSetRepository.find(companyId, empHistImport.get().getEmploymentCode());
				
				BreakDayOffHistoryDto outputDto = new BreakDayOffHistoryDto(hisDate, breakHis, dayOffHis, useDays, compensatoryLeaveEmSet.getIsManaged().value);
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
				CompensatoryDayoffDateDto ymdData = new CompensatoryDayoffDateDto(item.getYmdData().isUnknownDate(), item.getYmdData().getDayoffDate().get());
				
				Double useDays = item.getUseDays() != null ? item.getUseDays().get() : 0.0;
				
				CompensatoryDayoffDateDto absDate = new CompensatoryDayoffDateDto(item.getAbsHisData() != null ? item.getAbsHisData().get().getAbsDate().isUnknownDate() : true, 
						item.getAbsHisData() != null ? item.getAbsHisData().get().getAbsDate().getDayoffDate().get() : null);
				
				AbsenceHistoryOutputParaDto absHisData = new AbsenceHistoryOutputParaDto(item.getAbsHisData() != null ? item.getAbsHisData().get().getCreateAtr().value : 0, 
						item.getAbsHisData() != null ? item.getAbsHisData().get().getAbsId() : null,
						absDate, item.getAbsHisData() != null ? item.getAbsHisData().get().getRequeiredDays() : 0.0, 
						item.getAbsHisData() != null ? item.getAbsHisData().get().getUnOffsetDays() : 0.0);
				
				CompensatoryDayoffDateDto recDate = new CompensatoryDayoffDateDto(item.getRecHisData() != null ? item.getRecHisData().get().getRecDate().isUnknownDate() : true, 
						item.getRecHisData() != null ? item.getRecHisData().get().getRecDate().getDayoffDate().get() : null);
				
				RecruitmentHistoryOutParaDto recHisData = new RecruitmentHistoryOutParaDto(item.getRecHisData() != null ? item.getRecHisData().get().getExpirationDate() : null, 
						item.getRecHisData() != null ? item.getRecHisData().get().isChkDisappeared() : true,
						item.getRecHisData() != null ? item.getRecHisData().get().getDataAtr().value : 0, 
						item.getRecHisData() != null ? item.getRecHisData().get().getRecId() : null, recDate, 
						item.getRecHisData() != null ? item.getRecHisData().get().getOccurrenceDays() : 0.0,
						item.getRecHisData() != null ? item.getRecHisData().get().getHolidayAtr() : 0, 
						item.getRecHisData() != null ? item.getRecHisData().get().getUnUseDays() : 0.0);
				
				RecAbsHistoryOutputDto outputDto = new RecAbsHistoryOutputDto(ymdData, useDays, absHisData, recHisData);
				recAbsHistoryOutput.add(outputDto);
			}
		}

		NumberRestDaysDto result = new NumberRestDaysDto(closingPeriod, recAbsHistoryOutput, data.isPresent() ? data.get().getAbsRemainInfor() : null);
		
		return result;
	}
}
