package nts.uk.ctx.at.record.dom.worktime.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StampSourceInfo {
	
	/* タイムレコーダー*/
	Time_Recorder(0),
	/* 打刻申請*/
	Stamp_Application(1),
	/* 打刻申請(NR)*/
	Stamp_Application_NR(2),
	/* 直行直帰 */
	Go_Straight(3),
	/* 直行直帰申請*/
	Go_Straight_Application(4),
	/* 直行直帰(ボタン)*/
	Go_Straight_Application_Button(5),
	/* 手修正(本人) */
	Hand_Correction_By_Myself(6),
	/* 手修正(他人) */
	Hand_Correction_By_Another(7),
	/* 打刻自動セット(個人情報) */
	Stamp_Auto_Set_Personal_Info(8),
	/* 修正画面一括設定 */
	Correction_Record_Set(9),
	/* タイムレコーダ(ID入力) */
	Time_Recorder_Id_Input(10),
	/* Web打刻入力 */
	Wed_Stamp_Input(11),
	/* タイムレコーダ(磁気カード) */
	Time_Recorder_Magnet_Card(12),
	/* タイムレコーダ(ICカード) */
	Time_Recorder_Ic_Card(13),
	/* タイムレコーダ(指紋打刻) */
	Time_Recorder_Finger_Stamp(14),
	/* モバイル打刻 */
	Mobile_Stamp(15),
	/* モバイル打刻(エリア外)*/
	Mobile_Stamp_Outside(16),
	/* 打刻漏れ補正 */
	Stamp_Leakage_Correction(17);
	
	public final int value;

}
