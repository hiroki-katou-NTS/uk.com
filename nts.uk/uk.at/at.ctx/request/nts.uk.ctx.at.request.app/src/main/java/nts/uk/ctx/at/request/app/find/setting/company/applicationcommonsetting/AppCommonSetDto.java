package nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AppCommonSetDto {
	// 会社ID
	public String companyId;
	// 所属職場名表示
	public int showWkpNameBelong;
	public static AppCommonSetDto convertToDto(AppCommonSet domain){
		return new AppCommonSetDto(domain.getCompanyId(), domain.getShowWkpNameBelong().value);
	}
}
