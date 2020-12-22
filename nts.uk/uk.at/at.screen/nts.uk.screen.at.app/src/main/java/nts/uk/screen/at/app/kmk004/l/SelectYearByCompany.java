package nts.uk.screen.at.app.kmk004.l;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingHoursByCompany;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingInput;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.L：会社別法定労働時間の登録（変形労働）.メニュー別OCD.会社別年度（変形労働）を選択する
 * @author tutt
 *
 */
@Stateless
public class SelectYearByCompany {

	@Inject
	public DisplayMonthlyWorkingHoursByCompany getWorkingHours; 
	
	public List<WorkTimeComDto> getDeforDisplayMonthlyWorkingHoursByCompany(DisplayMonthlyWorkingInput param) {
		
		List<WorkTimeComDto> result = new ArrayList<>();
		
		//会社別月単位の労働時間を表示する
		List<DisplayMonthlyWorkingDto> list = getWorkingHours.get(param);
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
