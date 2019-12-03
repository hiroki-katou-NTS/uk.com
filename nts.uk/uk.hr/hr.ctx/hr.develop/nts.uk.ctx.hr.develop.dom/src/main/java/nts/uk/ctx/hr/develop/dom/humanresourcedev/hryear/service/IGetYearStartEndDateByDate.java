package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service;

import nts.arc.time.GeneralDate;

/**
 * @author hieult
 * 表示日の取得
 */
public interface IGetYearStartEndDateByDate {
		YearStartEnd getByDate (String companyId , GeneralDate baseDate);
}
