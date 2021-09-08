package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CheckExceededThousandPeoplesOutput {
	private boolean isExceeded;
	private int numberExceeded;
}
