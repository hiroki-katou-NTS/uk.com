package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentAdaptorDto;

public interface AgentRequestAdaptor {
	public List<AgentAdaptorDto> find(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);
	public List<AgentAdaptorDto> findAll(String companyId, List<String> employeeId, GeneralDate startDate, GeneralDate endDate);
	
}
