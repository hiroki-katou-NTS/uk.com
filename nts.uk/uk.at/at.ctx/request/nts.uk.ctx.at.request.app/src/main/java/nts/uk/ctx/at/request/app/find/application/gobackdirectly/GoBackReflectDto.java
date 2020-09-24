package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.ApplicationStatus;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
@AllArgsConstructor
@NoArgsConstructor
@Data
//直行直帰申請の反映
public class GoBackReflectDto {
//	会社ID
	public String companyId;
//	勤務情報を反映する
	public int reflectApplication;
	
	public static GoBackReflectDto fromDomain(GoBackReflect value) {
		return new GoBackReflectDto(value.getCompanyId(), value.getReflectApplication().value);
	}
	public GoBackReflect toDomain() {
		return new GoBackReflect(companyId, EnumAdaptor.valueOf(reflectApplication, ApplicationStatus.class));
	}
}
