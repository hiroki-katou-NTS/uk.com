package nts.uk.screen.at.app.kmk004.n;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingByEmploymentInputDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByEmployment;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.雇用別年度リストを表示する.雇用別年度リストを表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class SelectYearByEmployment {

	@Inject
	public MonthlyWorkingHoursByEmployment getWorkingHoursByEmployment;

	public List<WorkTimeComDto> getDeforDisplayMonthlyWorkingHoursByEmp(
			DisplayMonthlyWorkingByEmploymentInputDto param) {

		List<WorkTimeComDto> result = new ArrayList<>();

		// 雇用別年度リストを表示する
		List<DisplayMonthlyWorkingDto> list = getWorkingHoursByEmployment.get(param.employmentCode,
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
