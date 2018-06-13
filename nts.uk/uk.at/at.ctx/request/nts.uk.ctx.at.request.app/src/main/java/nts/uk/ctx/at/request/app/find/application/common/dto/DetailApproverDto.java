package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.JobtitleSearchSetImport;

@Value
@AllArgsConstructor
public class DetailApproverDto {
	public String approverID;
	
	public String approverName;
	
	public String representerID;
	
	public String representerName;
	
	public String jobtitle;
	
	public String jobtitleAgent;
}
