package nts.uk.ctx.hr.shared.dom.dateTerm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.ctx.hr.shared.dom.dateTerm.service.dto.EmployeeDateDto;
import nts.uk.ctx.hr.shared.dom.enumeration.DateRule;

@Stateless
public class DateCaculationTermService {

	//算出日の取得
	public List<EmployeeDateDto> getDateBySidList(List<EmployeeDateDto> date, DateCaculationTerm dateCaculationTerm) {
		if(dateCaculationTerm.getCalculationTerm() == DateRule.UNSPECIFIED) {
			return date.stream().map(c-> new EmployeeDateDto(c.getEmployeeId(), null)).collect(Collectors.toList());
		}else if(dateCaculationTerm.getCalculationTerm() == DateRule.SAME_AS_TARGET_DATE) {
			return date;
		}else if(dateCaculationTerm.getCalculationTerm() == DateRule.DAYS_BEFORE_THE_TARGET_DATE) {
			if(dateCaculationTerm.getDateSettingNum() == null) {
				throw new BusinessException("MsgJ_JMM018_20");
			}
			return date.stream().map(c-> new EmployeeDateDto(c.getEmployeeId(), c.getTargetDate().addDays(dateCaculationTerm.getDateSettingNum()*-1))).collect(Collectors.toList());
		}else if(dateCaculationTerm.getCalculationTerm() == DateRule.DAYS_AFTER_THE_TARGET_DATE) {
			if(dateCaculationTerm.getDateSettingNum() == null) {
				throw new BusinessException("MsgJ_JMM018_21");
			}
			return date.stream().map(c-> new EmployeeDateDto(c.getEmployeeId(), c.getTargetDate().addDays(dateCaculationTerm.getDateSettingNum()))).collect(Collectors.toList());
		}else if(dateCaculationTerm.getCalculationTerm() == DateRule.MONTHS_BEFORE_THE_TARGET_DATE) {
			if(dateCaculationTerm.getDateSettingNum() == null) {
				throw new BusinessException("MsgJ_JMM018_20");
			}
			return date.stream().map(c-> new EmployeeDateDto(c.getEmployeeId(), c.getTargetDate().addMonths(dateCaculationTerm.getDateSettingNum()*-1))).collect(Collectors.toList());
		}else if(dateCaculationTerm.getCalculationTerm() == DateRule.MONTHS_AFTER_THE_TARGET_DATE) {
			if(dateCaculationTerm.getDateSettingNum() == null) {
				throw new BusinessException("MsgJ_JMM018_21");
			}
			return date.stream().map(c-> new EmployeeDateDto(c.getEmployeeId(), c.getTargetDate().addMonths(dateCaculationTerm.getDateSettingNum()))).collect(Collectors.toList());
		}else if(dateCaculationTerm.getCalculationTerm() == DateRule.DESIGNATED_DATE) {
			if(!dateCaculationTerm.getDateSettingDate().isPresent()) {
				throw new BusinessException("MsgJ_JMM018_22");
			}
			return date.stream().map(c-> {
				GeneralDate targetDate = c.getTargetDate();
				if(targetDate.lastDateInMonth() < dateCaculationTerm.getDateSettingDate().get().value) {
					targetDate = GeneralDate.ymd(targetDate.year(), targetDate.month(), targetDate.lastDateInMonth());
				}else if(targetDate.day() <= dateCaculationTerm.getDateSettingDate().get().value){
					targetDate = GeneralDate.ymd(targetDate.year(), targetDate.month(), dateCaculationTerm.getDateSettingDate().get().value);
				}else if(targetDate.day() > dateCaculationTerm.getDateSettingDate().get().value){
					targetDate = targetDate.addMonths(1);
					if(targetDate.lastDateInMonth() < dateCaculationTerm.getDateSettingDate().get().value) {
						targetDate = GeneralDate.ymd(targetDate.year(), targetDate.month(), targetDate.lastDateInMonth());
					}else {
						targetDate = GeneralDate.ymd(targetDate.year(), targetDate.month(), dateCaculationTerm.getDateSettingDate().get().value);
					}
				}
				return new EmployeeDateDto(c.getEmployeeId(), targetDate);
				}).collect(Collectors.toList());
		}else {
			return new ArrayList<EmployeeDateDto>();
		}
	}
}
