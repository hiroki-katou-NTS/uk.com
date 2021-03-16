package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.2-3.新規画面登録後の処理(afterRegister)
 * @author Doan Duy Hung
 *
 */
public interface NewAfterRegister {
	
	/**
	 * 2-3.新規画面登録後の処理
	 * @param appID 申請ID
	 * @param appTypeSetting 申請種類別設定
	 * @param mailServerSet メールサーバ設定済区分
	 * @param 複数人Flag（boolean）
	 * @return 
	 */
	public ProcessResult processAfterRegister(List<String> appIDLst, AppTypeSetting appTypeSetting, boolean mailServerSet, boolean multiPeopleFlg);
	
}
