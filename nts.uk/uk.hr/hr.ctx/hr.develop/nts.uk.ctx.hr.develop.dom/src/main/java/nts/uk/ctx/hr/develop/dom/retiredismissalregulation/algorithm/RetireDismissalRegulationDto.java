/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.DismissRestrictionTerm;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.DissisalNoticeTerm;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RetireDismissalRegulationDto{
	
	/** 処理結果  */
	public Boolean processingResult;
	
	/** 会社ID */
	public String companyId;

	/** 履歴ID */
	public String historyId;

	/** 公開条件 */
	public DateCaculationTerm publicTerm;

	/** 解雇予告日アラーム */
	public Boolean dismissalNoticeAlerm;

	/** 解雇制限アラーム */
	public Boolean dismissalRestrictionAlerm;

	/** List<解雇予告条件> */
	public List<DissisalNoticeTerm> dismissalNoticeTermList;

	/** List<解雇制限条件> */
	public List<DismissRestrictionTerm> dismissalRestrictionTermList;


	
}
