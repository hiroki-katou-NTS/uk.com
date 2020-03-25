/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;

/**
 * @author laitv 退職・解雇の就業規則
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.退職・解雇.退職・解雇の就業規則
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetireDismissalRegulation extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 履歴ID */
	private String historyId;

	/** 公開条件 */
	private DateCaculationTerm publicTerm;

	/** 解雇予告日アラーム */
	private Boolean dismissalNoticeAlerm;

	/** 解雇制限アラーム */
	private Boolean dismissalRestrictionAlerm;

	/** 解雇予告条件 */
	private List<DissisalNoticeTerm> dismissalNoticeTermList;

	/** 解雇制限条件 */
	private List<DismissRestrictionTerm> dismissalRestrictionTermList;

	public static RetireDismissalRegulation createFromJavaType(String companyId, String historyId,
			DateCaculationTerm publicTerm, Boolean dismissalNoticeAlerm, Boolean dismissalRestrictionAlerm,
			List<DissisalNoticeTerm> dismissalNoticeTermList,
			List<DismissRestrictionTerm> dismissalRestrictionTermList) {
		return new RetireDismissalRegulation(companyId, historyId, publicTerm, dismissalNoticeAlerm,
				dismissalRestrictionAlerm, dismissalNoticeTermList, dismissalRestrictionTermList);
	}

}
