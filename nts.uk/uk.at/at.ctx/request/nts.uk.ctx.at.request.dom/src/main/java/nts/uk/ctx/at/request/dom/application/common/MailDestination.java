package nts.uk.ctx.at.request.dom.application.common;

import java.math.BigDecimal;
import java.util.List;

import lombok.Value;
@Value
public class MailDestination {
	private List<String> listMailDestination;
	private int phaseFrameNumber;

	public MailDestination(List<String> listMailDestination, int phaseFrameNumber) {
		super();
		this.listMailDestination = listMailDestination;
		this.phaseFrameNumber = phaseFrameNumber;
	}

}
