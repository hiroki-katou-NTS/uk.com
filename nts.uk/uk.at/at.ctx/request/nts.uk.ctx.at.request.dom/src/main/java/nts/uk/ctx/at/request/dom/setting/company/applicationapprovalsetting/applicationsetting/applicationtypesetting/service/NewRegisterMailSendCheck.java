package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.アルゴリズム.新規登録時のメール送信判定
 * @author Doan Duy Hung
 *
 */
public interface NewRegisterMailSendCheck {
	
	/**
	 * 新規登録時のメール送信判定
	 * @param appTypeSetting
	 * @param application
	 * @param phaseNumber
	 * @return
	 */
	public List<String> sendMail(AppTypeSetting appTypeSetting, Application application, Integer phaseNumber);
}