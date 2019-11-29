package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service;

import nts.arc.time.GeneralDate;

public interface IGetYearStartEndDateByDate {
		YearStartEnd getByDate (String companyId , GeneralDate baseDate);
}
