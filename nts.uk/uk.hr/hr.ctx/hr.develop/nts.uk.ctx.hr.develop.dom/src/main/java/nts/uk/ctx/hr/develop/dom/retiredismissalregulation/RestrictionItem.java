/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author laitv
 * 制限条件
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.制限条件.制限条件
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestrictionItem extends DomainObject{
	
	/** 事由ID */
	private String causesId;
	
	/** 条件有無  */
	private Boolean restrictionTermFlg;
	
	/** 制限日数  */
	private Optional<Integer> settingNum;
	
	public static RestrictionItem createFromJavaType(String causesId, Boolean restrictionTermFlg, Optional<Integer> settingNum) {
		return new RestrictionItem(causesId, restrictionTermFlg, settingNum);
	}
	

}
