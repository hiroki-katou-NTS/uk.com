package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AppListApprovalRepository {

	/**
	 * 15 - 申請一覧承認登録チェック
	 * @param appCommonSet
	 * @param prePostAtr
	 * @param achievement
	 * @param workOperation
	 * @return
	 */
	public boolean checkResAppvListApp(AppCommonSet appCommonSet, PrePostAtr prePostAtr, String achievement, String workOperation);
	/**
	 * 16 - 申請一覧承認登録実行
	 * @param lstApp
	 * @param appCheck
	 * @param achievementCheck
	 * @param scheduleCheck
	 */
	public void approvalListApp(List<AppVersion> lstApp, boolean appCheck, boolean achievementCheck, boolean scheduleCheck);
}
