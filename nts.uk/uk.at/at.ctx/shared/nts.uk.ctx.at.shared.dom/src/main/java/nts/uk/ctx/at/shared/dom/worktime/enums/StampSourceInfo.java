package nts.uk.ctx.at.shared.dom.worktime.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StampSourceInfo {
	
	/** タイムレコーダー*/
	TIME_RECORDER(0),
	/** 打刻申請*/
	STAMP_APPLICATION(1),
	/** 打刻申請(NR)*/
	STAMP_APPLICATION_NR(2),
	/** 直行直帰 */
	GO_STRAIGHT(3),
	/** 直行直帰申請*/
	GO_STRAIGHT_APPLICATION(4),
	/** 直行直帰(ボタン)*/
	GO_STRAIGHT_APPLICATION_BUTTON(5),
	/** 手修正(本人) */
	HAND_CORRECTION_BY_MYSELF(6),
	/** 手修正(他人) */
	HAND_CORRECTION_BY_ANOTHER(7),
	/** 打刻自動セット(個人情報) */
	STAMP_AUTO_SET_PERSONAL_INFO(8),
	/** 修正画面一括設定 */
	CORRECTION_RECORD_SET(9),
	/** タイムレコーダ(ID入力) */
	TIME_RECORDER_ID_INPUT(10),
	/** Web打刻入力 */
	WEB_STAMP_INPUT(11),
	/** タイムレコーダ(磁気カード) */
	TIME_RECORDER_MAGNET_CARD(12),
	/** タイムレコーダ(ICカード) */
	TIME_RECORDER_Ic_CARD(13),
	/** タイムレコーダ(指紋打刻) */
	TIME_RECORDER_FINGER_STAMP(14),
	/** モバイル打刻 */
	MOBILE_STAMP(15),
	/** モバイル打刻(エリア外)*/
	MOBILE_STAMP_OUTSIDE(16),
	/** 打刻漏れ補正 */
	STAMP_LEAKAGE_CORRECTION(17),
	/**連携打刻 */
	SPR(18);
	public final int value;

}
