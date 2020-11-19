package nts.uk.screen.at.app.query.kmk004.b;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 会社別月単位労働時間を表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.会社別月単位労働時間を表示する.会社別月単位の労働時間を表示する
 * 
 * @author chungnt
 *
 */

@Stateless
public class DisplayMonthlyWorkingHoursByCompany {

	@Inject
	private CompanyAdapter companyRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	public List<DisplayMonthlyWorkingDto> get(DisplayMonthlyWorkingInput param) {
		String cid = AppContexts.user().companyId();
		List<DisplayMonthlyWorkingDto> resutl = new ArrayList<>();

		YearMonthPeriod yearMonthPeriod = companyRepository.get(cid, param.year);
		List<MonthlyWorkTimeSetCom> coms = new ArrayList<>();

		switch (param.workType) {
		case 0:
			coms = monthlyWorkTimeSetRepo.findCompanyByPeriod(cid, LaborWorkTypeAttr.REGULAR_LABOR, yearMonthPeriod);
			break;
		case 1:
			coms = monthlyWorkTimeSetRepo.findCompanyByPeriod(cid, LaborWorkTypeAttr.DEFOR_LABOR, yearMonthPeriod);
			break;
		case 2:
			coms = monthlyWorkTimeSetRepo.findCompanyByPeriod(cid, LaborWorkTypeAttr.FLEX, yearMonthPeriod);
			break;
		}

		resutl = coms.stream().map(m -> {
			DisplayMonthlyWorkingDto s = new DisplayMonthlyWorkingDto(m.getYm().v(),
					new LaborTime(m.getLaborTime().getLegalLaborTime().v(),
							m.getLaborTime().getWithinLaborTime().map(c -> c.v()).orElse(null),
							m.getLaborTime().getWeekAvgTime().map(c -> c.v()).orElse(null)));
			return s;
		}).collect(Collectors.toList());

		return resutl;
	}
}
