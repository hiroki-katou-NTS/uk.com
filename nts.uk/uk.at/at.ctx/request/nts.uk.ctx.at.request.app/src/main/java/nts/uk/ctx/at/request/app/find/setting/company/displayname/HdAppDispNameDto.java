package nts.uk.ctx.at.request.app.find.setting.company.displayname;

/*import nts.uk.ctx.at.request.dom.setting.company.displayname.DispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppType;*/
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationTypeDisplayName;
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

	public static HdAppDispNameDto convertToDto(String companyId, HolidayApplicationTypeDisplayName domain){
		return new HdAppDispNameDto(companyId, domain.getHolidayApplicationType().value, domain.getDisplayName().v());
	}
}
