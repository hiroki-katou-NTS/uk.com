package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflect;
@AllArgsConstructor
@NoArgsConstructor
@Data
//直行直帰申請の反映
public class GoBackReflectDto {
//	会社ID
	private String companyId;
//	勤務情報を反映する
	private int reflectApplication;
	
	public static GoBackReflectDto convertDto(GoBackReflect value) {
		return new GoBackReflectDto(value.getCompanyId(), value.getReflectApplication().value);
	}
}
