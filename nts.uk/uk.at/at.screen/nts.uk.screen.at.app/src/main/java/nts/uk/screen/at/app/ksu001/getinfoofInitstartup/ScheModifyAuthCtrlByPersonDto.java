package nts.uk.screen.at.app.ksu001.getinfoofInitstartup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByPerson;

/**
 * できる／できない権限の設定
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheModifyAuthCtrlByPersonDto {

	/** 会社ID */
	public String companyId;
	
	/** ロールID */
	public String roleId;
	
	/** 機能NO */
	public Integer functionNo;
	
	/** 利用できる */
	public Boolean isAvailable;
	
	public static ScheModifyAuthCtrlByPersonDto fromDomain(ScheModifyAuthCtrlByPerson domain) {
		return new ScheModifyAuthCtrlByPersonDto(
				domain.getCompanyId(),
				domain.getRoleId(),
				domain.getFunctionNo(),
				domain.isAvailable()
				);
	}
}
