
package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulation;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulationRepository;
/**
 * @author hieult
 * 表示日の取得
 */
@Stateless
public class GetYearStartEndDateByDateImpl implements IGetYearStartEndDateByDate{

	@Inject
	private HrPeriodRegulationRepository hrPeriodRegulaRepo;
	
	@Inject
	private EmploymentRegulationHistoryInterface histInterface;

	@Override
	public YearStartEnd getByDate(String companyId, GeneralDate baseDate) {
		GeneralDate resultStartDate = null;
		GeneralDate resultEndDate = null;
		// アルゴリズム [基準日から就業規則の履歴IDの取得] を実行する(thực hiện thuật toán[lấy HistoryID của HrPeriodRegulation từ BaseDate])
		Optional<String> historyId = histInterface.getHistoryIdByDate(companyId, baseDate);
		if(!historyId.isPresent()){
			return null;
		}
		
		Optional<HrPeriodRegulation> hrPeriod = hrPeriodRegulaRepo.getByKeyAndDate(companyId, historyId.get());
		if(!hrPeriod.isPresent()){
			return null;
		}
		
			GeneralDate checkStartDate = GeneralDate.ymd(GeneralDate.today().year(), hrPeriod.get().getYearStartMonth(), hrPeriod.get().getYearStartDate());
			GeneralDate	checkEndDate = GeneralDate.ymd(GeneralDate.today().year(), hrPeriod.get().getYearEndMonth(), hrPeriod.get().getYearEndDate());
			if(checkStartDate.beforeOrEquals( GeneralDate.today())){
					resultStartDate = checkStartDate;
				}
				resultStartDate = checkStartDate.addYears(-1);
				if(checkEndDate.beforeOrEquals( GeneralDate.today())){
					resultEndDate = checkEndDate.addYears(1);
				}	
				resultEndDate = checkEndDate;
				YearStartEnd result = new YearStartEnd(resultStartDate, resultEndDate);
				return result;
				
				
	}
}
