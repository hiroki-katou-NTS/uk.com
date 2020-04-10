package nts.uk.ctx.at.record.app.command.stamp.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.application.CheckErrorType;
import nts.uk.ctx.at.record.dom.stamp.application.MessageContent;
import nts.uk.ctx.at.record.dom.stamp.application.PromptingMessage;
import nts.uk.ctx.at.record.dom.stamp.application.StampRecordDis;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampRecordDisCommand {
	
	/** 利用区分 */
	private int useArt;
	
	/** チェックエラー種類 */
	private  int checkErrorType;
	
	/** 促すメッセージ */
	private PromptingMessageCommand promptingMssage;
	
	public static StampRecordDis toDomain(StampRecordDisCommand x){
		
		PromptingMessage promptingMessage = new PromptingMessage(new MessageContent(x.promptingMssage.getMessageContent()), new ColorCode(x.promptingMssage.getMessageColor()));
		return new StampRecordDis(EnumAdaptor.valueOf(x.useArt, NotUseAtr.class), EnumAdaptor.valueOf(x.checkErrorType, CheckErrorType.class), promptingMessage);
	}
}
