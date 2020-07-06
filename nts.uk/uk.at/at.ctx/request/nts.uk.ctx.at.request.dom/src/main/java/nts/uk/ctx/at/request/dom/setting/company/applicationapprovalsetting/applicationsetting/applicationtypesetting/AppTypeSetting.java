package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.申請種類別設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppTypeSetting {
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 新規登録時に自動でメールを送信する
	 */
	private boolean sendMailWhenRegister;
	
	/**
	 * 承認処理時に自動でメールを送信する
	 */
	private boolean sendMailWhenApproval;
	
	/**
	 * 事前事後区分の初期表示
	 */
	private PrePostInitAtr displayInitialSegment;
	
	/**
	 * 事前事後区分を変更できる
	 */
	private boolean canClassificationChange;
	
	public AppTypeSetting(ApplicationType appType, boolean sendMailWhenRegister,
			boolean sendMailWhenApproval, PrePostInitAtr displayInitialSegment,
			boolean canClassificationChange) {
		this.appType = appType;
		this.sendMailWhenRegister = sendMailWhenRegister;
		this.sendMailWhenApproval = sendMailWhenApproval;
		this.displayInitialSegment = displayInitialSegment;
		this.canClassificationChange = canClassificationChange;
	}
	
	public static AppTypeSetting createNew(int appType, boolean sendMailWhenRegister,
			boolean sendMailWhenApproval, int displayInitialSegment,
			boolean canClassificationChange) {
		return new AppTypeSetting(
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				sendMailWhenRegister, 
				sendMailWhenApproval, 
				EnumAdaptor.valueOf(displayInitialSegment, PrePostInitAtr.class), 
				canClassificationChange);
	}
	
}
