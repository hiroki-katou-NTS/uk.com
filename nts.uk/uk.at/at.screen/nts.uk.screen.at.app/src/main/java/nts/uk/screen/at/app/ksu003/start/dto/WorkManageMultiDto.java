package nts.uk.screen.at.app.ksu003.start.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;

/**
 * 
 * @author phongtq
 *
 */
@Value
@AllArgsConstructor
public class WorkManageMultiDto {
	/** 会社ID */
	private String companyID;
	/** 使用区分 */
	private Integer useATR;

	public static WorkManageMultiDto convert(Optional<WorkManagementMultiple> manageMulti) {
		if (manageMulti.isPresent()) {
			return new WorkManageMultiDto(manageMulti.get().getCompanyID(),
					manageMulti.get().getUseATR().value);
		}
		return null;
	}
}
