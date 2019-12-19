package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author hieult
 * 表示日の取得
 */
public interface IGetYearStartEndDateByDate {
	
	// 基準日から年度開始年月日、年度終了年月日の取得
	Optional<YearStartEnd> getYearStartEndDateByDate(String companyId, GeneralDate baseDate);
}
