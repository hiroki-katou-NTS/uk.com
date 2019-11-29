package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service;

import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
public class YearStartEnd {
	GeneralDate yearStartYMD;
	GeneralDate yearEndYMD;
	public YearStartEnd(GeneralDate yearStartYMD, GeneralDate yearEndYMD) {
		super();
		this.yearStartYMD = yearStartYMD;
		this.yearEndYMD = yearEndYMD;
	}
	
}
