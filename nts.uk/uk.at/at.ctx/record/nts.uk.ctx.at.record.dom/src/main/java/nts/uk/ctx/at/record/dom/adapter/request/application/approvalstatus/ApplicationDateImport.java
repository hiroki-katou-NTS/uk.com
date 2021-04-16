package nts.uk.ctx.at.record.dom.adapter.request.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class ApplicationDateImport {

	private GeneralDate appDate;

	private ApplicationImport application;
}
