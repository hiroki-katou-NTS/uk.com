/**
 * 
 */
package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author laitv
 *
 */
public class CreateStampInfoHelper {

	public static CreateStampInfo getCreateStampInfoDefault() {
		return new CreateStampInfo(
				new NRConvertInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.of(GoingOutReason.PRIVATE)),
						NotUseAtr.NOT_USE),
				Optional.empty(), Optional.empty());
	}
}
