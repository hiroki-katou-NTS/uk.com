package nts.uk.ctx.at.request.dom.setting.workplace.appuseset;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.職場別設定.申請利用設定.申請承認機能設定
 * @author Doan Duy Hung
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ApprovalFunctionSet {
	
	/**
	 * 申請利用設定
	 */
	private List<ApplicationUseSetting> appUseSetLst;
	
	public ApprovalFunctionSet(List<ApplicationUseSetting> appUseSetLst) {
		this.appUseSetLst = appUseSetLst;
	}
	
}
