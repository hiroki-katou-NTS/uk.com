package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;

/**
 * 申請種類別設定
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppTypeSetting extends DomainObject {
	
	/**
	 * 事前事後区分の初期表示
	 */
	private PrePostInitialAtr displayInitialSegment;
	
	/**
	 * 事前事後区分を変更できる
	 */
	private Boolean canClassificationChange;
	
	/**
	 * 定型理由の表示
	 */
	private DisplayAtr displayFixedReason;
	
	/**
	 * 承認処理時に自動でメールを送信する
	 */
	private Boolean sendMailWhenApproval;
	
	/**
	 * 新規登録時に自動でメールを送信する
	 */
	private Boolean sendMailWhenRegister;
	
	/**
	 * 申請理由の表示
	 */
	private DisplayAtr displayAppReason;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	
	public static AppTypeSetting toDomain(Integer displayInitialSegment, Integer canClassificationChange, 
			Integer displayFixedReason, Integer sendMailWhenApproval, 
			Integer sendMailWhenRegister, Integer displayAppReason, 
			Integer appType){
		return new AppTypeSetting(
				EnumAdaptor.valueOf(displayInitialSegment, PrePostInitialAtr.class), 
				canClassificationChange == 1 ? true : false, 
				EnumAdaptor.valueOf(displayFixedReason, DisplayAtr.class), 
				sendMailWhenApproval == 1 ? true : false, 
				sendMailWhenRegister == 1 ? true : false, 
				EnumAdaptor.valueOf(displayAppReason, DisplayAtr.class), 
				EnumAdaptor.valueOf(appType, ApplicationType.class));
	}
	
}
