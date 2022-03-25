package nts.uk.screen.at.app.query.kdp.kdps01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;

/**
 * 
 * @author sonnlb
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeStampingAreaRestrictionSettingDto {
	// 社員ID
	private String employeeId;

	// 打刻エリア制限
	private StampingAreaRestrictionDto stampingAreaRestriction;

	public static EmployeeStampingAreaRestrictionSettingDto fromDomain(EmployeeStampingAreaRestrictionSetting domain) {

		return new EmployeeStampingAreaRestrictionSettingDto(domain.getEmployeeId(),
				StampingAreaRestrictionDto.fromDomain(domain.getStampingAreaRestriction()));

	}
}
