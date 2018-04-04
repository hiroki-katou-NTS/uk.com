package nts.uk.ctx.at.record.app.find.realitystatus;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 */
@AllArgsConstructor
@Value
public class RealityStatusActivityDto {
	private String wkpId;
	private int monthConfirm;
	private int monthUnconfirm;
	private int personConfirm;
	private int personUnconfirm;
	private int bossConfirm;
	private int bossUnconfirm;
}
