package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * インターバルメッセージの表示設定
 */
@AllArgsConstructor
@Getter
public class IntervalMessageDisplaySettings extends ValueObject {
	/**
	 * メッセージを表示する
	 */
	
	private NotUseAtr messageDisplay;
	
	/**
	 * メッセージ内容
	 */
	private Optional<IntervalMessage> messageContent;
}
