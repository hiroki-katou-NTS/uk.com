package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 */
@AllArgsConstructor
@Value
public class StatusWkpActivityOutput {
	private String wkpId;
	private int monthConfirm;
	private int monthUnconfirm;
	private int personConfirm;
	private int personUnconfirm;
	private int bossConfirm;
	private int bossUnconfirm;
}
