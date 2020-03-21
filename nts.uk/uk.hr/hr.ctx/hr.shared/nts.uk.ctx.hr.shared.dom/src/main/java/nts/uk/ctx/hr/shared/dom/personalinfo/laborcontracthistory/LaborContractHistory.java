/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author laitv 年月日期間の汎用履歴項目 path :
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.年月日期間の汎用履歴項目
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaborContractHistory extends DomainObject {

	/** 会社ID */
	private String cid;

	/** 社員ID */
	private String sid;
	
	private DateHistoryItem historyItem;

	public static LaborContractHistory createFromJavaType(String cid, String sid, DateHistoryItem careerTypeHistory) {
		return new LaborContractHistory(cid, sid, careerTypeHistory);
	}

}
