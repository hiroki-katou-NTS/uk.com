/**
 * 
 */
package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author laitv
 *
 */
public class CreateStampInfoHelper {

	public static CreateStampInfo getCreateStampInfoDefault() {
		return new CreateStampInfo(
				new OutPlaceConvert(NotUseAtr.USE, Optional.empty()),
				new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE), 
				Optional.empty(),
				Optional.empty());
	}
}
