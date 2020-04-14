/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.hr.shared.dom.enumeration.RestrictionCase;

/**
 * @author laitv
 * 解雇制限条件
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.解雇制限条件
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DismissRestrictionTerm extends DomainObject{

	/** 制限ケース */
	private RestrictionCase restrictionCase;
	
	/** 制限有無 */
	private Boolean restrictionFlg;
	
	/** 制限ケース */
	private List<RestrictionItem> restrictionItems;
	
	public static DismissRestrictionTerm createFromJavaType(int restrictionCase, Boolean restrictionFlg, List<RestrictionItem> restrictionItems) {
		return new DismissRestrictionTerm(
				EnumAdaptor.valueOf(restrictionCase, RestrictionCase.class), restrictionFlg, restrictionItems);
	}
}
