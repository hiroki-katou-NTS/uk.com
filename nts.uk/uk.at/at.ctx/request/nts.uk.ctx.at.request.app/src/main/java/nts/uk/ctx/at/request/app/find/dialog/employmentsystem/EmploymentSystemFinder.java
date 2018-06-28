package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecGenerationDigestionHis;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentManaQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.RecAbsHistoryOutputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffManagementQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffOutputHisData;
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
		BreakDayOffOutputHisData data = breakDayOffManagementQuery.getBreakDayOffData(companyId, employeeId, GeneralDate.fromString(baseDate, "yyyy/MM/dd"));
		
		DetailConfirmDto result = new DetailConfirmDto(closingPeriod, data);
		
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
		AbsRecGenerationDigestionHis data = absenceReruitmentManaQuery
				.generationDigestionHis(companyId, employeeId, GeneralDate.fromString(baseDate, "yyyyMMdd"));

		List<RecAbsHistoryOutputDto> recAbsHistoryOutput = new ArrayList<>();
		
		if(data.getGreneraGigesHis().size() > 0) {
			for (RecAbsHistoryOutputPara item : data.getGreneraGigesHis()) {
				CompensatoryDayoffDateDto ymdData = new CompensatoryDayoffDateDto(item.getYmdData().isUnknownDate(), item.getYmdData().getDayoffDate().get());
				
				Double useDays = item.getUseDays() != null ? item.getUseDays().get() : 0.0;
				
				CompensatoryDayoffDateDto absDate = new CompensatoryDayoffDateDto(item.getAbsHisData() != null ? item.getAbsHisData().get().getAbsDate().isUnknownDate() : true, 
						item.getAbsHisData() != null ? item.getAbsHisData().get().getAbsDate().getDayoffDate().get() : null);
				
				AbsenceHistoryOutputParaDto absHisData = new AbsenceHistoryOutputParaDto(item.getAbsHisData() != null ? item.getAbsHisData().get().getCreateAtr().value : 0, 
						item.getAbsHisData() != null ? item.getAbsHisData().get().getAbsId() : null,
						absDate, item.getAbsHisData() != null ? item.getAbsHisData().get().getRequeiredDays() : null, 
						item.getAbsHisData() != null ? item.getAbsHisData().get().getUnOffsetDays() : null);
				
				CompensatoryDayoffDateDto recDate = new CompensatoryDayoffDateDto(item.getRecHisData() != null ? item.getRecHisData().get().getRecDate().isUnknownDate() : true, 
						item.getRecHisData() != null ? item.getRecHisData().get().getRecDate().getDayoffDate().get() : null);
				
				RecruitmentHistoryOutParaDto recHisData = new RecruitmentHistoryOutParaDto(item.getRecHisData() != null ? item.getRecHisData().get().getExpirationDate() : null, 
						item.getRecHisData() != null ? item.getRecHisData().get().isChkDisappeared() : true,
						item.getRecHisData() != null ? item.getRecHisData().get().getDataAtr().value : 0, 
						item.getRecHisData() != null ? item.getRecHisData().get().getRecId() : null, recDate, 
						item.getRecHisData() != null ? item.getRecHisData().get().getOccurrenceDays() : 0.0,
						item.getRecHisData() != null ? item.getRecHisData().get().getHolidayAtr() : 0, 
						item.getRecHisData() != null ? item.getRecHisData().get().getUnUseDays() : null);
				
				RecAbsHistoryOutputDto outputDto = new RecAbsHistoryOutputDto(ymdData, useDays, absHisData, recHisData);
				recAbsHistoryOutput.add(outputDto);
			}
		}

		NumberRestDaysDto result = new NumberRestDaysDto(closingPeriod, recAbsHistoryOutput, data.getAbsRemainInfor() != null ? data.getAbsRemainInfor() : null);
		
		return result;
	}
}
