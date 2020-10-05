package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.事後の受付制限
 * @author Doan Duy Hung
 *
 */
@Setter
@NoArgsConstructor
@Getter
public class AfterhandRestriction {
	
	/**
	 * 未来日許可しない
	 */
	private boolean allowFutureDay;
	
	public AfterhandRestriction(boolean allowFutureDay) {
		this.allowFutureDay = allowFutureDay;
	}
	
}
