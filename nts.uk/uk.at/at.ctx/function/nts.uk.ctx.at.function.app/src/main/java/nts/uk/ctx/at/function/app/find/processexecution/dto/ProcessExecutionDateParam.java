package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@AllArgsConstructor
public class ProcessExecutionDateParam {
	String execItemCd;
	GeneralDate startDate;
	GeneralDate endDate;
}
