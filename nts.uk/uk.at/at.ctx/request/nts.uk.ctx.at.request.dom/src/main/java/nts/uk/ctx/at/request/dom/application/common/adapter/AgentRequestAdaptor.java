package nts.uk.ctx.at.request.dom.application.common.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AgentRequestAdaptor {
	public List<AgentAdaptorDto> find(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);
}
