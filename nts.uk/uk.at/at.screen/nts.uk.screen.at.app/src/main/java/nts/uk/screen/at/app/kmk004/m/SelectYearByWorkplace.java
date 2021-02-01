package nts.uk.screen.at.app.kmk004.m;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.eclipse.persistence.jpa.jpql.parser.FunctionExpressionFactory.ParameterCount;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingByWkpInputDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByWorkplace;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.M：職場別法定労働時間の登録（変形労働）.メニュー別OCD.職場別年度（変形労働）を選択する
 * @author tutt
 *
 */
@Stateless
public class SelectYearByWorkplace {
	
	@Inject
	public MonthlyWorkingHoursByWorkplace getWorkingHoursByWorkplace;
	
	public List<WorkTimeComDto> getDeforDisplayMonthlyWorkingHoursByWkp(DisplayMonthlyWorkingByWkpInputDto param) {
		
		List<WorkTimeComDto> result = new ArrayList<>();
		
		//職場別月単位労働時間を表示する
		List<DisplayMonthlyWorkingDto> list = getWorkingHoursByWorkplace.get(param.getWorkplaceId(), LaborWorkTypeAttr.DEFOR_LABOR, param.getYear());
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
