
package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulation;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulationRepository;

/**
 * @author hieult 表示日の取得
 */
@Stateless
public class GetYearStartEndDateByDateImpl implements IGetYearStartEndDateByDate {

	@Inject
	private HrPeriodRegulationRepository hrPeriodRegulaRepo;

	@Inject
	private EmploymentRegulationHistoryInterface histInterface;

	@Override
	public Optional<YearStartEnd> getYearStartEndDateByDate(String companyId, GeneralDate baseDate) {
		GeneralDate resultStartDate = null;
		GeneralDate resultEndDate = null;
		// アルゴリズム [基準日から就業規則の履歴IDの取得] を実行する(thực hiện thuật toán[lấy HistoryID
		// của HrPeriodRegulation từ BaseDate])
		Optional<String> historyId = histInterface.getHistoryIdByDate(companyId, baseDate);

		if(!historyId.isPresent()){
			return Optional.empty();
		}

		Optional<HrPeriodRegulation> hrPeriod = hrPeriodRegulaRepo.getByKeyAndDate(companyId, historyId.get());

		if(!hrPeriod.isPresent()){
			return Optional.empty();
		}
		int year = baseDate.year();
		// 年度開始年月日オブジェクトを生成する(tạo object YearStartYMD)
		GeneralDate checkStartDate = null;
		try {
			 checkStartDate = GeneralDate.ymd(year, hrPeriod.get().getYearStartMonth(),
					hrPeriod.get().getYearStartDate());
		} catch (Exception e) {
			checkStartDate = GeneralDate.ymd(year, hrPeriod.get().getYearStartMonth(), 1);
			checkStartDate =  GeneralDate.ymd(year, hrPeriod.get().getYearStartMonth(), checkStartDate.lastDateInMonth());
		}
		
		if(checkStartDate.beforeOrEquals(baseDate)) {
			resultStartDate = checkStartDate;
		}
		

		if(checkStartDate.after(baseDate)) {
			resultStartDate = checkStartDate.addYears(-1);
		}
		
		// 年度終了年月日オブジェクトを生成する(Tạo Object YearEndYMD)
		GeneralDate checkEndDate = null;
		try {
			checkEndDate = GeneralDate.ymd(year, hrPeriod.get().getYearEndMonth(),
					hrPeriod.get().getYearEndDate());
		} catch (Exception e) {
			checkEndDate = GeneralDate.ymd(year, hrPeriod.get().getYearEndMonth(), 1);
			checkEndDate =  GeneralDate.ymd(year, hrPeriod.get().getYearEndMonth(), checkEndDate.lastDateInMonth());
		}
		
		if (checkEndDate.beforeOrEquals(baseDate)) {
			resultEndDate = checkEndDate.addYears(1);
		}
		if (checkEndDate.after(baseDate)) {
			resultEndDate = checkEndDate;
		}
		
		YearStartEnd result = new YearStartEnd(resultStartDate, resultEndDate);
		return Optional.of(result);

	}
}
