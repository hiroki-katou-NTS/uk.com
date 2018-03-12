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
public class ShiftPermisson {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用できる*/
	private int availableShift; 
	
	/** 機能NO*/
	private Integer functionNoShift;
	
	public static ShiftPermisson createFromJavaType(String companyId, String roleId, int availableShift, Integer functionNoShift){
		return new ShiftPermisson(companyId, roleId,availableShift, functionNoShift );
	}
}
