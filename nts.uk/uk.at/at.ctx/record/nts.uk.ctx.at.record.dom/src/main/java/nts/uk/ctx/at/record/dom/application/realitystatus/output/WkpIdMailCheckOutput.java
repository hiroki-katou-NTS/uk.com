package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class WkpIdMailCheckOutput {
	/**
	 * 職場一覧
	 */
	private String wkpId;
	private boolean isCheckOn;
}
