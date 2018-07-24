package nts.uk.screen.at.app.dailyperformance.correction.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.GetExcessTimesYearAdapter;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.screen.at.app.dailyperformance.correction.finddata.IFindData;

@Stateless
public class DisplayAgreementInfo {
	
	private static String ERROR = "state-error";
	
	private static String ALARM = "state-alarm";
	
	private static String EXCEPTION = "state-exception";
	

	@Inject
	private IFindData findata;
	
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepository;
	
	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;
	
	@Inject
	private GetExcessTimesYearAdapter getExcessTimesYearAdapter;
	
	public AgreementInfomationDto displayAgreementInfo(String companyId, String employeeId, int year, int month){
		
		Optional<DaiPerformanceFun> funOpt = findata.getDailyPerformFun(companyId);
		if(!funOpt.isPresent() || funOpt.get().getDisp36Atr() == 0) return AgreementInfomationDto.builder().showAgreement(false).build();
		
		
		AgreementInfomationDto result = new AgreementInfomationDto();
		
		result.setShowAgreement(true);
		
		Optional<AgreementTimeOfManagePeriod> agreeTimeOpt = agreementTimeOfManagePeriodRepository.find(employeeId, YearMonth.of(year, month));
		
		if(agreeTimeOpt.isPresent()) result.setCssAgree(convertStateCell(agreeTimeOpt.get().getAgreementTime().getStatus().value));
		
		Optional<AgreementOperationSetting> agreeOperationOpt = agreementOperationSettingRepository.find(companyId);
		
		result.setMaxNumber(agreeOperationOpt.isPresent() ? String.valueOf(agreeOperationOpt.get().getNumberTimesOverLimitType().value) : "0");
		result.setExcessFrequency(String.valueOf(getExcessTimesYearAdapter.algorithm(employeeId, new Year(year))));
		if(Integer.parseInt(result.getExcessFrequency()) >= Integer.parseInt(result.getMaxNumber())){
			result.setCssFrequency(ERROR);
		}
		
		return result;
	}
	
	private String convertStateCell(int value) {
		if (value == AgreementTimeStatusOfMonthly.NORMAL_SPECIAL.value
				|| value == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP.value
				|| value == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP.value) {
			return EXCEPTION;
		} else if (value == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR.value
				|| value == AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR.value) {
			return ERROR;
		} else if (value == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM.value
				|| value == AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM.value) {
			return ALARM;
		} else {
			return "";
		}
	}
}
