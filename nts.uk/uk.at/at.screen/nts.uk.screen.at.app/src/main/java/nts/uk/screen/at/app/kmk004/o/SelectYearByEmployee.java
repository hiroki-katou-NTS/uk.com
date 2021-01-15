package nts.uk.screen.at.app.kmk004.o;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingByShaInputDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByEmployee;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.O：社員別法定労働時間の登録（変形労働）.メニュー別OCD.社員別年度（変形労働）を選択する
 * 
 * @author tutt
 *
 */
@Stateless
public class SelectYearByEmployee {

	@Inject
	public MonthlyWorkingHoursByEmployee getWorkingHoursByEmployee;

	public List<WorkTimeComDto> getDeforDisplayMonthlyWorkingHoursByEmployee(DisplayMonthlyWorkingByShaInputDto param) {

		List<WorkTimeComDto> result = new ArrayList<>();

		// 社員別年度（変形労働）を選択する
		// input:
		// 社員ID＝選択中の社員ID
		// 勤務区分＝1：変形労働
		// 年度＝選択中の年度
		List<DisplayMonthlyWorkingDto> list = getWorkingHoursByEmployee.get(param.getSId(),
				LaborWorkTypeAttr.DEFOR_LABOR, param.getYear());
		result = list.stream().map(m -> {
			WorkTimeComDto w = new WorkTimeComDto();

			w.setYearMonth(m.getYearMonth());
			if (m.getLaborTime().getLegalLaborTime() == null) {
				w.setLaborTime(0);
			} else {
				w.setLaborTime(m.getLaborTime().getLegalLaborTime());
			}

			return w;
		}).collect(Collectors.toList());
		return result;
	}

}
