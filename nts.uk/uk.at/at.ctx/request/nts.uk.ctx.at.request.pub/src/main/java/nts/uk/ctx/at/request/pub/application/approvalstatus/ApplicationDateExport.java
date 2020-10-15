package nts.uk.ctx.at.request.pub.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class ApplicationDateExport {

	private GeneralDate appDate;

	private ApplicationExport application;
}
