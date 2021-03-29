package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author chungnt
 *
 */

public class StampPromptApplicationHelper {

	public static StampPromptApplication getDefault() {
		List<StampRecordDis> lstStampRecordDis = new ArrayList<StampRecordDis>();
		PromptingMessage promptingMessage = new PromptingMessage(new MessageContent("DUMMY"), new ColorCode("DUMMY"));
		
		lstStampRecordDis.add(new StampRecordDis(NotUseAtr.NOT_USE, CheckErrorType.HOKIDAY_EMBOSSING, Optional.of(promptingMessage)));
		lstStampRecordDis.add(new StampRecordDis(NotUseAtr.USE, CheckErrorType.OVERTIME_DIVERGGENCE, Optional.of(promptingMessage)));
		lstStampRecordDis.add(new StampRecordDis(NotUseAtr.USE, CheckErrorType.HOKIDAY_EMBOSSING, Optional.of(promptingMessage)));
		
		StampPromptApplication stampPromptApplication = new StampPromptApplication("DUMMY", lstStampRecordDis);

		return stampPromptApplication;
	}
	
}
