package nts.uk.ctx.at.record.app.find.stamp.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.application.StampRecordDis;

@Data
@AllArgsConstructor
public class StampPronptAppDto {
	/** 利用区分 */
	private int useArt;
	
	/** チェックエラー種類 */
	private  int checkErrorType;
	
	/** エラー有時に促すメッセージ */
	private String messageContent;
	
	/** メッセージ色 */
	private String messageColor;
	
	
	public static StampPronptAppDto fromDomain (StampRecordDis x){
		return new StampPronptAppDto(x.getUseArt().value, x.getCheckErrorType().value, x.getPromptingMssage().get().getMessageContent().v(), x.getPromptingMssage().get().getMessageColor().v());
	}
}
