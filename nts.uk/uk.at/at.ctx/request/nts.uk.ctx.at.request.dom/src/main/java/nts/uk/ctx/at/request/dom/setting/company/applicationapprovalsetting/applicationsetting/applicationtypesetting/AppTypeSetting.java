package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

import java.util.Optional;

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
	private Optional<PrePostInitAtr> displayInitialSegment;
	
	/**
	 * 事前事後区分を変更できる
	 */
	private Optional<Boolean> canClassificationChange;
	
	public AppTypeSetting(ApplicationType appType, boolean sendMailWhenRegister,
			boolean sendMailWhenApproval, PrePostInitAtr displayInitialSegment,
			boolean canClassificationChange) {
		this.appType = appType;
		this.sendMailWhenRegister = sendMailWhenRegister;
		this.sendMailWhenApproval = sendMailWhenApproval;
		this.displayInitialSegment = Optional.ofNullable(displayInitialSegment);
		this.canClassificationChange = Optional.ofNullable(canClassificationChange);
	}
	
	public static AppTypeSetting createNew(int appType, boolean sendMailWhenRegister,
			boolean sendMailWhenApproval, Integer displayInitialSegment,
			Boolean canClassificationChange) {
		return new AppTypeSetting(
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				sendMailWhenRegister, 
				sendMailWhenApproval,
				displayInitialSegment == null ? null : EnumAdaptor.valueOf(displayInitialSegment, PrePostInitAtr.class),
				canClassificationChange);
	}
	
}
