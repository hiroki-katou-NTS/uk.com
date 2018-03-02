package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.Value;

@Value
public class TimeResultOutput {

	private boolean checkColor;
	private List<OverTimeFrame> lstFrameResult;
}
