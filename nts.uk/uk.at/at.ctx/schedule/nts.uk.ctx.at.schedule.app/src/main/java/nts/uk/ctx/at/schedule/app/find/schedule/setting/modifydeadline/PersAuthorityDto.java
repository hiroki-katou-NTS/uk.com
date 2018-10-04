package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PersAuthority;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersAuthorityDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 個人別権限制御: 利用できる*/
	private int availablePers;
	
	/** 個人別権限制御: 機能NO*/
	private Integer functionNoPers;
	
	public static PersAuthorityDto fromDomain(PersAuthority domain){
		return new PersAuthorityDto(domain.getCompanyId(), domain.getRoleId(), domain.getAvailablePers(), domain.getFunctionNoPers());
	}
}
