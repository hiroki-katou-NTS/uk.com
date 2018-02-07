package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersAuthority {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用できる*/
	private int availablePers;
	
	/** 機能NO*/
	private Integer functionNoPers;
	
	public static PersAuthority createFromJavaType(String companyId, String roleId, int availablePers, Integer functionNoPers){
		return new PersAuthority(companyId, roleId,availablePers, functionNoPers );
	}
}
