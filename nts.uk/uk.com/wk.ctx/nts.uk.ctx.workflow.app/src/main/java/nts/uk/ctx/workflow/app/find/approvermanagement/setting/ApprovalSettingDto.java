package nts.uk.ctx.workflow.app.find.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalSettingDto {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * 本人による承認
	 */
	private int prinFlg;
}
