package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Optional;

import lombok.Getter;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 	申請促すエラーの設定	
 * @author phongtq
 *
 */

@Value
@Getter
public class StampRecordDis implements DomainValue{
	
	/** 利用区分 */
	private NotUseAtr useArt;
	
	/** チェックエラー種類 */
	private final CheckErrorType checkErrorType;
	
	/** 促すメッセージ */
	private PromptingMessage promptingMssage;

	public StampRecordDis(NotUseAtr useArt, CheckErrorType checkErrorType, PromptingMessage promptingMssage) {
		this.useArt = useArt;
		this.checkErrorType = Optional.of(checkErrorType).get();
		this.promptingMssage = promptingMssage;
	}
	
}
