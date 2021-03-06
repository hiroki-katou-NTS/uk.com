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
public class PerWorkplace {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 職場別権限制御: 利用できる*/
	private int availableWorkplace;
	
	/** 職場別権限制御: 機能NO*/
	private Integer functionNoWorkplace;
	
	public static PerWorkplace createFromJavaType(String companyId, String roleId, int availableWorkplace, Integer functionNoWorkplace){
		return new PerWorkplace(companyId, roleId,availableWorkplace, functionNoWorkplace );
	}
}
