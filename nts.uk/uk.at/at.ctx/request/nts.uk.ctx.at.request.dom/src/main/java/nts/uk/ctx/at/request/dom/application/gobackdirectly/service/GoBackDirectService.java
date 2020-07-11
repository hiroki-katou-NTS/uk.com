package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkGoBackDirectOutput;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface GoBackDirectService {
	/**
	 * アルゴリズム「直行直帰画面初期（新規）」を実行する Refactor4
	 * @param companyId
	 * @param sids
	 * @param dates
	 * @param appDispInfoStartup
	 * @return
	 */
	public InforGoBackCommonDirectOutput getDataAlgorithm(String companyId, Optional<List<String>> sids, Optional<List<GeneralDate>> dates, AppDispInfoStartupOutput appDispInfoStartup);
	
	/**
	 * 直行直帰申請起動時初期データを取得する Refactor4
	 * @param companyId
	 * @param employeeId
	 * @param appDate
	 * @param baseDate
	 * @param appEmployment
	 * @param lstWts
	 * @return
	 */
	public InforWorkGoBackDirectOutput getInfoWorkGoBackDirect(String companyId, String employeeId, GeneralDate appDate, GeneralDate baseDate,
			AppEmploymentSet appEmployment, List<WorkTimeSetting> lstWts);
	/**
	 * 起動時勤務種類リストを取得する Refactor4
	 * @param companyId
	 * @param appEmploymentSet
	 * @return
	 */
	public List<WorkType> getWorkTypes(String companyId, AppEmploymentSet appEmploymentSet);
}
