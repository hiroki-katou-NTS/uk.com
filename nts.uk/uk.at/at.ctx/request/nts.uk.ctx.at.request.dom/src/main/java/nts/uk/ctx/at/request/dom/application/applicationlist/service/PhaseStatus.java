package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.Value;

@Value
public class PhaseStatus {
	
	private String appID;
	private String phaseStatus;
	private List<Integer> phaseAtr;
}
