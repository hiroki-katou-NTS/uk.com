package nts.uk.screen.at.app.schedule.scheduleteam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOrganizationInfoDto {
	
	/** 社員ID **/
	private String employeeId;
	
	/** 社員コード **/
	private String employeeCd;
	
	/** ビジネスネーム **/
	private String businessName;
	/** チーム **/
	private String teamCd;
	
	/** 名称 **/
	private String teamName;

}
