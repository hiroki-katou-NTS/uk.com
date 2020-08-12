package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterApproval;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class AppListApprovalImpl implements AppListApprovalRepository{

	@Inject
	private DetailBeforeUpdate detailBefUpdate;
	@Inject
	private DetailAfterApproval detailAfAppv;
	/**
	 * 15 - 申請一覧承認登録チェック
	 */
	@Override
	public boolean checkResAppvListApp(AppCommonSet appCommonSet, PrePostAtr prePostAtr, String achievement,
			String workOperation) {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * 16 - 申請一覧承認登録実行
	 */
	@Override
	public List<String> approvalListApp(List<AppVersion> lstApp, boolean appCheck, boolean achievementCheck,
			boolean scheduleCheck) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		List<String> lstRefAppId = new ArrayList<>();
		for (AppVersion app : lstApp) {
			//アルゴリズム「承認する」を実行する
            //EA修正履歴 No.3254
            //アルゴリズム「排他チェック」を実行する (thực hiện xử lý 「check version」) - (CMM045)
            boolean checkversion = detailBefUpdate.exclusiveCheckErr(companyID, app.getAppID(), app.getVersion());
			if(!checkversion){
				continue;
			}
			//共通アルゴリズム「詳細画面承認後の処理」を実行する(thực hiện xử lý 「詳細画面承認後の処理」) - 8.2
			// refactor 4 error
			/*ProcessResult result = detailAfAppv.doApproval(companyID, app.getAppID(), employeeID, "", "", false);
			if(!Strings.isBlank(result.getReflectAppId())){
				lstRefAppId.add(result.getReflectAppId());
			}*/
		}
		return lstRefAppId;
	}
}
