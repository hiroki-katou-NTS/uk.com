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

	public static StampPromptApplication getStampPromptApplicationDefault() {
		return new StampPromptApplication("000-0000000000001", new ArrayList<StampRecordDis>());
	}

	public static List<StampRecordDis> getListStampRecordDisUSE() {

		List<StampRecordDis> lstStampRecordDis = new ArrayList<>();

		StampRecordDis dis = new StampRecordDis(NotUseAtr.USE, CheckErrorType.IMPRINT_LEAKAGE, Optional
				.ofNullable(new PromptingMessage(new MessageContent("messageContent"), new ColorCode("messageColor"))));

		lstStampRecordDis.add(dis);

		return lstStampRecordDis;

	}
	
	public static List<StampRecordDis> getListStampRecordDisNOTUSE() {

		List<StampRecordDis> lstStampRecordDis = new ArrayList<>();

		StampRecordDis dis = new StampRecordDis(NotUseAtr.NOT_USE, CheckErrorType.IMPRINT_LEAKAGE, Optional
				.ofNullable(new PromptingMessage(new MessageContent("messageContent"), new ColorCode("messageColor"))));

		StampRecordDis dis1 = new StampRecordDis(NotUseAtr.NOT_USE, CheckErrorType.IMPRINT_LEAKAGE, Optional
				.ofNullable(new PromptingMessage(new MessageContent("messageContent"), new ColorCode("messageColor"))));

		StampRecordDis dis2 = new StampRecordDis(NotUseAtr.NOT_USE, CheckErrorType.IMPRINT_LEAKAGE, Optional
				.ofNullable(new PromptingMessage(new MessageContent("messageContent"), new ColorCode("messageColor"))));

		lstStampRecordDis.add(dis);
		lstStampRecordDis.add(dis1);
		lstStampRecordDis.add(dis2);

		return lstStampRecordDis;

	}
	

}
