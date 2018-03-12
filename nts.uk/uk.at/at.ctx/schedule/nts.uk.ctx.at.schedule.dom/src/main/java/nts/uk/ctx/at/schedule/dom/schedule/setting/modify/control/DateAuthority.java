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
public class DateAuthority {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 日付別権限制御: 利用できる*/
	private int availableDate;
	
	/** 日付別権限制御: 機能NO*/
	private Integer functionNoDate;
	
	public static DateAuthority createFromJavaType(String companyId, String roleId, int availableDate, Integer functionNoDate){
		return new DateAuthority(companyId, roleId,availableDate, functionNoDate );
	}
}
