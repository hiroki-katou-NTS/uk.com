package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Arrays;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class StamPromptApplicationHelper {

	public static class StamPrompt {
		public static StampPromptApplication DUMMY = new StampPromptApplication(
				"000000000000-0001", 
				Arrays.asList(
						new StampRecordDis(
								NotUseAtr.valueOf(1), 
								CheckErrorType.valueOf(1), 
								new PromptingMessage(
										new MessageContent("DUMMY"), 
										new ColorCode("#DUMMY")))));
	}
	public static class StamResult {
		public static StampResultDisplay DUMMY = new StampResultDisplay(
			"000000000000-0001", 
			 NotUseAtr.valueOf(1),
			 Arrays.asList(new StampAttenDisplay("000000000000-0001", 123458), new StampAttenDisplay("000000000000-0001", 122333)));
	}
}
