package nts.uk.ctx.at.shared.dom.supportmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 応援形式
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援形式
 * @author kumiko_otake
 */
@Getter
@AllArgsConstructor
public enum SupportType {

	/** 終日応援 **/
	ALLDAY( 0 ),
	/** 時間帯応援 **/
	TIMEZONE( 1 ),
	;


	public final int value;

}
