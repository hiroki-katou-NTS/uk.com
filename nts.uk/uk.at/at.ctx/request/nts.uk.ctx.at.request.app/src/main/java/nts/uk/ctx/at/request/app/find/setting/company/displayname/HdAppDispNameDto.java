package nts.uk.ctx.at.request.app.find.setting.company.displayname;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.displayname.DispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppType;
/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class HdAppDispNameDto {
	// 会社ID
	private String companyId;
	// 休暇申請種類
	private int hdAppType;
	// 表示名
	private String dispName;
	public static HdAppDispNameDto convertToDto(HdAppDispName domain){
		return new HdAppDispNameDto(domain.getCompanyId(), domain.getHdAppType().value, domain.getDispName().v());
	}
}
