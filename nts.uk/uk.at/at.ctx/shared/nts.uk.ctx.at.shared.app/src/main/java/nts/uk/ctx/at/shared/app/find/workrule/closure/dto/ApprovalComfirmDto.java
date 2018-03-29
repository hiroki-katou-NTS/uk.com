package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

@Value
@AllArgsConstructor
public class ApprovalComfirmDto {
	private int selectedClosureId;
	private List<ClosuresDto> closuresDto;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private List<ClosureEmployment> employeesCode;
}
