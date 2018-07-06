package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.setting.company.request.approvallistsetting.ApprovalListDisplaySetDto;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListApprovalRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListInitialRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.AppVersion;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.shr.com.context.AppContexts;
/**
 * 14 - 申請一覧承認登録
 * @author hoatt
 *
 */
@Stateless
public class ApprovalListAppCommandHandler extends CommandHandlerWithResult<List<ApprovalListAppCommand>, List<String>>{
	
	@Inject
	private AppListApprovalRepository repoAppListAppv;
	@Inject
	private RequestSettingRepository repoRequestSet;
	@Inject
	private AppCommonSetRepository repoAppCommonSet;
	@Override
	protected List<String> handle(CommandHandlerContext<List<ApprovalListAppCommand>> context) {
		// TODO Auto-generated method stub
		List<ApprovalListAppCommand> data = context.getCommand();
		//アルゴリズム「申請一覧承認登録」を実行する - 14
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「承認一覧表示設定」を取得する-(Lấy domain Approval List display Setting)
		Optional<RequestSetting> requestSet = repoRequestSet.findByCompany(companyId);
		ApprovalListDisplaySetting appDisplaySet = null;
		if(requestSet.isPresent()){
			appDisplaySet = requestSet.get().getApprovalListDisplaySetting();
		}
		//ドメインモデル「申請一覧共通設定」を取得する-(Lấy domain Application list common settings) - wait YenNTH
		Optional<AppCommonSet> appCommonSet = repoAppCommonSet.find();
		//アルゴリズム「申請一覧承認登録チェック」を実行する - 15
		boolean checkAppv = repoAppListAppv.checkResAppvListApp(appCommonSet.get(), PrePostAtr.POSTERIOR, "", "");
		List<String> lstRefAppId = new ArrayList<>();
		if(checkAppv){
			//アルゴリズム「申請一覧承認登録実行」を実行する - 16
			List<AppVersion> lstApp = new ArrayList<>();
			for (ApprovalListAppCommand app : data) {
				lstApp.add(new AppVersion(app.getAppId(), app.getVersion()));
			}
			lstRefAppId = repoAppListAppv.approvalListApp(lstApp, true, true, true);
		}
		//アルゴリズム「申請一覧リスト取得」を実行する - 1 => load lai du lieu: xu ly tren ui
//		repoAppListInit.getApplicationList(param, displaySet);
		return lstRefAppId;
	}

}
