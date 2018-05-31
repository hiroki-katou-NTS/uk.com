package nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffWorkplaceImport {
	// 社員ID
	private String sId;

	// 入社年月日
	private GeneralDate jobEntryDate;

	// 退職年月日
	private GeneralDate retirementDate;
}
