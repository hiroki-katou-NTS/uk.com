package nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.職場別設定.会社別申請承認設定.会社別申請承認設定
 * @author Doan Duy Hung
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestByCompany {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 申請承認機能設定
	 */
	private ApprovalFunctionSet approvalFunctionSet;
	
}
