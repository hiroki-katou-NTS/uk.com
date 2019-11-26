
package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service;

import java.util.List;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulation;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulationRepository;

public class GetYearStartEndDateByDateImpl implements IGetYearStartEndDateByDate{

	@Inject
	private HrPeriodRegulationRepository hrPeriodRegulaRepo;
	@Override
	public YearStartEnd getByDate(String companyId, GeneralDate baseDate) {
		int year = 0;
		int month = 0;
		int date = 0;
		// アルゴリズム [基準日から就業規則の履歴IDの取得] を実行する(thực hiện thuật toán[lấy HistoryID của HrPeriodRegulation từ BaseDate])
		List<HrPeriodRegulation> listHrPeriod = hrPeriodRegulaRepo.getByCIDandBaseDate(companyId, GeneralDate.today());
	/*	if(!listHrPeriod.isEmpty()){
			for (HrPeriodRegulation hrPeriodRegulation : listHrPeriod) {
				GeneralDate YMD = new GeneralDate(year,month,date);
				GeneralDate baseDate = GeneralDate.ymd(year, month, date);
			}
		}*/
		return null;
	}

}
