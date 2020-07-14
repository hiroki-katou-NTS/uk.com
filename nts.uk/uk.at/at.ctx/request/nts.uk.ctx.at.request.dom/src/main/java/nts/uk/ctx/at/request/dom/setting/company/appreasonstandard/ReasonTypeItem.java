package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

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
	private Optional<ReasonForFixedForm> opReasonForFixedForm;
	
	public ReasonTypeItem(AppStandardReasonCode appStandardReasonCD, int displayOrder,
			boolean defaultValue, Optional<ReasonForFixedForm> opReasonForFixedForm) {
		this.appStandardReasonCD = appStandardReasonCD;
		this.displayOrder = displayOrder;
		this.defaultValue = defaultValue;
		this.opReasonForFixedForm = opReasonForFixedForm;
	}
	
	public static ReasonTypeItem createNew(int appStandardReasonCD, int displayOrder,
			boolean defaultValue, String opReasonForFixedForm) {
		return new ReasonTypeItem(
				new AppStandardReasonCode(appStandardReasonCD), 
				displayOrder, 
				defaultValue, 
				Strings.isBlank(opReasonForFixedForm) ? Optional.empty() : Optional.of(new ReasonForFixedForm(opReasonForFixedForm)));
	}
	
}
