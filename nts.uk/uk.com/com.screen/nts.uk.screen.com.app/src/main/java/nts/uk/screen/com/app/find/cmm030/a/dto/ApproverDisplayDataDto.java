package nts.uk.screen.com.app.find.cmm030.a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;

@Data
@AllArgsConstructor
public class ApproverDisplayDataDto {

	/**
	 * 承認者設定情報
	 */
	private List<ApprovalSettingInformationDto> approvalSettingInformations;
	
	/**
	 * 社員名<List>
	 */
	private List<ResultRequest600Export> employeeNames;
}
