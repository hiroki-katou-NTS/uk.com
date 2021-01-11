package nts.uk.ctx.at.shared.dom.worktime.workplace.service;

import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 就業時間帯割り当て済みの職場を取得する.
 */
@Stateless
public class WorkTimeWorkplaceService {

	/**
	 * 設定済みの職場を取得する
	 */
	public List<String> getByCid(Require require) {
		return require.getByCId().stream().map(WorkTimeWorkplace::getWorkplaceID).distinct().collect(Collectors.toList());
	}

	public static interface Require {
		List<WorkTimeWorkplace> getByCId();
	}

}
