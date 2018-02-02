package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
@Value
@AllArgsConstructor
public class ApplicationFullOutput {

	private Application_New application;
	private List<ApprovalPhaseStateImport_New> lstPhaseState;
	
}
