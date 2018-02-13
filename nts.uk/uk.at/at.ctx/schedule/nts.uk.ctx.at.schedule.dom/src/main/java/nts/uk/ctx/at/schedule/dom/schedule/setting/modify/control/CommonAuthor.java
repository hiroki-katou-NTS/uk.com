package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 
 * @author phongtq
 *
 */
@Getter
@AllArgsConstructor
public class CommonAuthor {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用できる*/
	private int availableCommon;
	
	/** 機能NO*/
	private Integer functionNoCommon;
	
	public static CommonAuthor createFromJavaType(String companyId, String roleId, int availableCommon,Integer functionNoCommon){
		return new CommonAuthor(companyId, roleId, availableCommon,functionNoCommon );
	}
}
