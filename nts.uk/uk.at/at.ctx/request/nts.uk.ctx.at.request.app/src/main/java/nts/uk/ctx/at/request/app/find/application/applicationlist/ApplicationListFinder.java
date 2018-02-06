package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applicationlist.service.ApplicationListInitialRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 11 - 申請一覧初期処理
 * @author hoatt
 *
 */
@Stateless
public class ApplicationListFinder {

	@Inject
	private ApplicationListInitialRepository repoAppListInit;
	@Inject
	private RequestSettingRepository repoRequestSet;
	
	public ApplicationListDto getAppList(AppListExtractConditionDto param){
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「承認一覧表示設定」を取得する-(Lấy domain Approval List display Setting)
		Optional<RequestSetting> requestSet = repoRequestSet.findByCompany(companyId);
		ApprovalListDisplaySetting appDisplaySet = null;
		if(requestSet.isPresent()){
			appDisplaySet = requestSet.get().getApprovalListDisplaySetting();
		}
		//URパラメータが存在する-(Check param)
		if(param.getAppListAtr() != null){//存在する場合
			//期間（開始日、終了日）が存する場合
			if(StringUtil.isNullOrEmpty(param.getStartDate(), false) || StringUtil.isNullOrEmpty(param.getEndDate(), false)){
				//アルゴリズム「申請一覧初期日付期間」を実行する-(Thực hiện thuật toán lấy ngày　－12)
				DatePeriod date = repoAppListInit.getInitialPeriod(companyId);
				param.setStartDate(date.start().toString());
				param.setEndDate(date.end().toString());
			}
			// TODO Auto-generated method stub
		}else{//存在しない場合
			//ドメインモデル「申請一覧抽出条件」を取得する
			// TODO Auto-generated method stub
			//申請一覧抽出条件.社員IDリストが空白
		}
		//ドメインモデル「申請一覧共通設定フォーマット.表の列幅」を取得-(Lấy 表の列幅)//xu ly o ui
		//アルゴリズム「申請一覧リスト取得」を実行する-(Thực hiện thuật toán Application List get): 1-申請一覧リスト取得
		AppListExtractCondition appListExCon = param.convertDtotoDomain(param);
		List<Application_New> lstApp = repoAppListInit.getApplicationList(appListExCon, appDisplaySet);
		return new ApplicationListDto();
	}
}
