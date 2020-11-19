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
import nts.uk.ctx.bs.company.dom.company.MonthStr;
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
			DisplayMonthlyWorkingDto s = new DisplayMonthlyWorkingDto(this.setNameMonth(m.getYm().month()),
					new LaborTime(m.getLaborTime().getLegalLaborTime().v(),
							m.getLaborTime().getWithinLaborTime().map(c -> c.v()).orElse(null),
							m.getLaborTime().getWeekAvgTime().map(c -> c.v()).orElse(null)));
			return s;
		}).collect(Collectors.toList());

		return resutl;
	}

	private String setNameMonth(int param) {
		String name = "";

		switch (param) {
		case 1:
			name = MonthStr.ONE.month;
			break;
		case 2:
			name = MonthStr.TWO.month;
			break;
		case 3:
			name = MonthStr.THREE.month;
			break;
		case 4:
			name = MonthStr.FOUR.month;
			break;
		case 5:
			name = MonthStr.FIVE.month;
			break;
		case 6:
			name = MonthStr.SIX.month;
			break;
		case 7:
			name = MonthStr.SEVEN.month;
			break;
		case 8:
			name = MonthStr.EIGHT.month;
			break;
		case 9:
			name = MonthStr.NINE.month;
			break;
		case 10:
			name = MonthStr.TEN.month;
			break;
		case 11:
			name = MonthStr.ELEVEN.month;
			break;
		case 12:
			name = MonthStr.TWELVE.month;
			break;
		}

		return name;
	}

}
