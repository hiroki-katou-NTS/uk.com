package nts.uk.ctx.at.aggregation.dom.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 予実区分
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.共通処理.予実区分
 * @author kumiko_otake
 */
@Getter
@RequiredArgsConstructor
public enum ScheRecAtr {

	/** 予定 **/
	SCHEDULE( 0 ),
	/** 実績 **/
	RECORD( 1 )
	;


	/** 内部値 **/
	private final int value;

}
