package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApplicationForRemandOutput {
	public String appId;
	public int version;
	private String applicantId;
	private String applicantName;
	public String applicantJob;
	private int phaseLogin;
	public List<RemandInfoKDL034> lstApprover;
}
