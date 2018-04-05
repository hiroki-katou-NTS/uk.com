package nts.uk.ctx.at.record.dom.realitystatus.service;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class WkpIdMailCheck {
	/**
	 * 職場一覧
	 */
	private String wkpId;
	private boolean isCheckOn;
}
