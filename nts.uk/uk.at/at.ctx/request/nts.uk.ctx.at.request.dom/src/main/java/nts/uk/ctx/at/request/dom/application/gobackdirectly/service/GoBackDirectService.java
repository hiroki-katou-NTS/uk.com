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
	public InforGoBackCommonDirectOutput getDataAlgorithm(String companyId, Optional<List<GeneralDate>> dates, Optional<String> sids, AppDispInfoStartupOutput appDispInfoStartup);
	
	/**
	 * 直行直帰申請起動時初期データを取得する Refactor4
	 * @param companyId
	 * @param employeeId
	 * @param appDate
	 * @param baseDate
	 * @param appEmployment
	 * @param lstWts
	 * @return //直行直帰申請起動時の表示情報
	 */
	public InforWorkGoBackDirectOutput getInfoWorkGoBackDirect(String companyId, String employeeId, GeneralDate appDate, GeneralDate baseDate,
			AppEmploymentSet appEmployment, List<WorkTimeSetting> lstWts, AppDispInfoStartupOutput appDispInfoStartupOutput);
	/**
	 * 起動時勤務種類リストを取得する Refactor4
	 * @param companyId
	 * @param appEmploymentSet
	 * @return
	 */
	public List<WorkType> getWorkTypes(String companyId, AppEmploymentSet appEmploymentSet);
	/**
	 * Refactor 4
	 * @param companyId
	 * @param dates
	 * @param sids
	 * @param appDispInfoStartup
	 * @return //直行直帰申請起動時の表示情報
	 */
	public InforGoBackCommonDirectOutput getDateChangeAlgorithm(String companyId, List<GeneralDate> dates, List<String> sids, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput);
	/**
	 * 直行直帰画面初期（更新） Refactor4 get data Detail
	 * @param companyId
	 * @param appId
	 * @return //直行直帰申請起動時の表示情報
	 */
	public InforGoBackCommonDirectOutput getDataDetailAlgorithm(String companyId, String appId, AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	/**
	 * Refactor5
	 * @param companyId
	 * @param dates
	 * @param sids
	 * @param inforGoBackCommonDirectOutput
	 * @return
	 */
	public InforGoBackCommonDirectOutput getDateChangeMobileAlgorithm(String companyId, List<GeneralDate> dates, List<String> sids, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput);
	
	/**
	 * Refactor5
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS09_直行直帰申請（スマホ）.A：直行直帰申請（新規・編集）.アルゴリズム.直行直帰申請の初期（新規・編集）
	 * @param companyId
	 * @param sids
	 * @param dates
	 * @param appDispInfoStartup
	 * @return
	 */
	public InforGoBackCommonDirectOutput getDataAlgorithmMobile(String companyId, Optional<List<GeneralDate>> dates, Optional<String> sids, AppDispInfoStartupOutput appDispInfoStartup);
	
	/**
	 * Refactor5
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS09_直行直帰申請（スマホ）.B：直行直帰申請（見るだけ）.ユースケース
	 * @param companyId
	 * @param appId
	 * @return //直行直帰申請起動時の表示情報
	 */
	public InforGoBackCommonDirectOutput getDataDetailAlgorithmMobile(String companyId, String appId, AppDispInfoStartupOutput appDispInfoStartupOutput);
}


