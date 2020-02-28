package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class RetirementDateDto {
	// 社員ID
	private String employeeId;
	// 雇用コード.
	private String employmentCode;
	//退職日
	private GeneralDate retirementDate;
	
	public void setDay(int settingDate) {
		if(settingDate > this.retirementDate.lastDateInMonth()) {
			this.retirementDate = GeneralDate.ymd(this.retirementDate.year(), this.retirementDate.month(), this.retirementDate.lastDateInMonth());
		}else if(settingDate >= this.retirementDate.day()) {
			this.retirementDate = GeneralDate.ymd(this.retirementDate.year(), this.retirementDate.month(), settingDate);
		}else if(settingDate < this.retirementDate.day()) {
			this.retirementDate = this.retirementDate.addMonths(1);
			if(settingDate > this.retirementDate.lastDateInMonth()) {
				this.retirementDate = GeneralDate.ymd(this.retirementDate.year(), this.retirementDate.month(), this.retirementDate.lastDateInMonth());
			}else {
				this.retirementDate = GeneralDate.ymd(this.retirementDate.year(), this.retirementDate.month(), settingDate);
			}
		}
		
	}
	
	public void setLastDateInMonth() {
		this.retirementDate = GeneralDate.ymd(this.retirementDate.year(), this.retirementDate.month(), this.retirementDate.lastDateInMonth());
	}
	
	public void setRetirementDateByYear(GeneralDate endDate) {
		if(this.retirementDate.beforeOrEquals(endDate)) {
			this.retirementDate = endDate;
		}else {
			this.retirementDate = endDate.addYears(1);
		}
	}
}
