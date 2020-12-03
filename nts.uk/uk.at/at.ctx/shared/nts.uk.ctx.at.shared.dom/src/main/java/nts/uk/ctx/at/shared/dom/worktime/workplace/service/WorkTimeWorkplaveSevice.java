package nts.uk.ctx.at.shared.dom.worktime.workplace.service;

import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.List;

/**
 * 就業時間帯割り当て済みの職場を取得する.
 */
public class WorkTimeWorkplaveSevice {

	@Inject
	private WorkTimeWorkplaceRepository repository;

	/**
	 * 設定済みの職場を取得する
	 */
	public List<WorkTimeWorkplace> getByCid() {
		return repository.getByCId(AppContexts.user().companyId());
	}

}
