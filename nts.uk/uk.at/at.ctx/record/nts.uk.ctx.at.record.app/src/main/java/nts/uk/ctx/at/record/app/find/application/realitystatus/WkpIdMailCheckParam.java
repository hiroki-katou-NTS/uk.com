package nts.uk.ctx.at.record.app.find.application.realitystatus;

import lombok.Value;

/**
 * @author dat.lh
 *
 */
@Value
public class WkpIdMailCheckParam {
	/**
	 * 職場一覧
	 */
	private String wkpId;
	private boolean isCheckOn;
}
