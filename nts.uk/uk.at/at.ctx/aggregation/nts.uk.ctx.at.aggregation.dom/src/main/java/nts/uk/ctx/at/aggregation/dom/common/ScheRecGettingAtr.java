package nts.uk.ctx.at.aggregation.dom.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 予実取得区分
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.共通処理.予実取得区分
 * @author kumiko_otake
 */
@Getter
@RequiredArgsConstructor
public enum ScheRecGettingAtr {

	/** 予定のみ **/
	ONLY_SCHEDULE( 0 ),
	/** 実績のみ **/
	ONLY_RECORD( 1 ),
	/** 予定＋実績 **/
	SCHEDULE_WITH_RECORD( 2 ),
	;


	/** 内部値 **/
	private final int value;


	/**
	 * 予定を取得するか
	 * @return true:取得する/false:取得しない
	 */
	public boolean isNeedSchedule() {
		return this != ONLY_RECORD;
	}

	/**
	 * 実績を取得するか
	 * @return true:取得する/false:取得しない
	 */
	public boolean isNeedRecord() {
		return this != ONLY_SCHEDULE;
	}


	/**
	 * 予実区分に変換する
	 * @return 予実区分
	 */
	public ScheRecAtr toScheRecAtr() {

		switch( this ) {
			case ONLY_SCHEDULE:	return ScheRecAtr.SCHEDULE;
			case ONLY_RECORD:	return ScheRecAtr.RECORD;
			default: throw new RuntimeException("Out of Range");
		}

	}

}
