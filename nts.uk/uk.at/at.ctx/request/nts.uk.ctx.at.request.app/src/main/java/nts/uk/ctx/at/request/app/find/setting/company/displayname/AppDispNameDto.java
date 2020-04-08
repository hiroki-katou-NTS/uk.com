package nts.uk.ctx.at.request.app.find.setting.company.displayname;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AppDispNameDto {
	// 会社ID
	private String companyId;
	// 申請種類
	private int appType;
	// 表示名
	private String dispName;
	
	public static AppDispNameDto convertToDto(AppDispName domain){
		return new AppDispNameDto(domain.getCompanyId(), domain.getAppType().value, 
				domain.getDispName() == null ? null : domain.getDispName().v());
	}
}
