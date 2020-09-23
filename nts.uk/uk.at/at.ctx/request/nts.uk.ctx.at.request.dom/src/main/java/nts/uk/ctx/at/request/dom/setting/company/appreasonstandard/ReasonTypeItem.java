package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import lombok.Getter;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請定型理由.定型理由項目
 * @author Doan Duy Hung
 *
 */
@Getter
public class ReasonTypeItem {
	
	/**
	 * 理由コード
	 */
	private AppStandardReasonCode appStandardReasonCD; 
	
	/**
	 * 表示順
	 */
	private int displayOrder;
	
	/**
	 * 既定
	 */
	private boolean defaultValue;
	
	/**
	 * 定型理由
	 */
	private ReasonForFixedForm reasonForFixedForm;
	
	public ReasonTypeItem(AppStandardReasonCode appStandardReasonCD, int displayOrder,
			boolean defaultValue, ReasonForFixedForm reasonForFixedForm) {
		this.appStandardReasonCD = appStandardReasonCD;
		this.displayOrder = displayOrder;
		this.defaultValue = defaultValue;
		this.reasonForFixedForm = reasonForFixedForm;
	}
	
	public static ReasonTypeItem createNew(int appStandardReasonCD, int displayOrder,
			boolean defaultValue, String opReasonForFixedForm) {
		return new ReasonTypeItem(
				new AppStandardReasonCode(appStandardReasonCD), 
				displayOrder, 
				defaultValue, 
				new ReasonForFixedForm(opReasonForFixedForm)
		);
	}
	
}
