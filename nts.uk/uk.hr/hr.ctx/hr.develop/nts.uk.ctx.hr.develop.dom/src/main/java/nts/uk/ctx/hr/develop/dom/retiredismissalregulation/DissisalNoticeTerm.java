/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.WageType;

/**
 * @author laitv
 * 解雇予告条件
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.解雇予告条件
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DissisalNoticeTerm extends DomainObject{
	
	/** 賃金種類  */
	private  WageType wageType;
	
	/** 条件有無  */
	private Boolean noticeTermFlg;
	
	/** 予告日条件  */
	private Optional<DateCaculationTerm> noticeDateTerm;
	
	public static DissisalNoticeTerm createFromJavaType(int wageType, Boolean noticeTermFlg, Optional<DateCaculationTerm> noticeDateTerm) {
		return new DissisalNoticeTerm(
				EnumAdaptor.valueOf(wageType, WageType.class), noticeTermFlg, noticeDateTerm);
	}
	
}
