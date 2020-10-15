//package nts.uk.ctx.at.request.infra.entity.setting.workplace;
//
//import java.io.Serializable;
//
//
//import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
//import javax.persistence.PrimaryKeyJoinColumn;
//import javax.persistence.PrimaryKeyJoinColumns;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import nts.uk.shr.infra.data.entity.UkJpaEntity;
///**
// * 職場-申請承認機能設定
// * @author Doan Duy Hung
// *
// */
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "KRQST_WP_APP_CF_DETAIL")
//public class KrqstWpAppConfigDetail  extends UkJpaEntity implements Serializable{
//	
//	@EmbeddedId
//	public KrqstWpAppConfigDetailPK krqstWpAppConfigDetailPK;
//	/**
//	 * 備考
//	 */
//	@Column(name = "MEMO")
//	public String memo;
//	/**
//	 * 利用区分
//	 */
//	@Column(name = "USE_ATR")
//	public int useAtr;
//	/**
//	 * 休出時間申請の事前必須設定
//	 */
//	@Column(name = "PREREQUISITE_FORPAUSE_FLG")
//	public int prerequisiteForpauseFlg;
//	/**
//	 * 残業申請の事前必須設定
//	 */
//	@Column(name = "OT_APP_SETTING_FLG")
//	public int otAppSettingFlg;
//	/**
//	 * 時間年休申請の時刻計算を利用する
//	 */
//	@Column(name = "HOLIDAY_TIME_APP_CAL_FLG")
//	public int holidayTimeAppCalFlg;
//	/**
//	 * 遅刻早退取消申請の実績取消
//	 */
//	@Column(name = "LATE_OR_LEAVE_APP_CANCEL_FLG")
//	public int lateOrLeaveAppCancelFlg;
//	/**
//	 * 遅刻早退取消申請の実績取消を申請時に選択
//	 */
//	@Column(name = "LATE_OR_LEAVE_APP_SETTING_FLG")	
//	public int lateOrLeaveAppSettingFlg;
//	/**
//	 * 休憩入力欄を表示する
//	 */
//	@Column(name = "BREAK_INPUTFIELD_DIS_FLG")
//	public int breakInputFieldDisFlg;
//	/**
//	 * 休憩時間を表示する
//	 */
//	@Column(name = "BREAK_TIME_DISPLAY_FLG")
//	public int breakTimeDisFlg;
//	/**
//	 * 出退勤時刻初期表示区分
//	 */
//	@Column(name = "ATWORK_TIME_BEGIN_DIS_FLG")
//	public int atworkTimeBeginDisFlg;
//	/**
//	 * 実績から外出を初期表示する
//	 */
//	@Column(name = "GOOUT_TIME_BEGIN_DIS_FLG")
//	public int goOutTimeBeginDisFlg;
//	/**
//	 * 時刻計算利用区分
//	 */
//	@Column(name = "TIME_CAL_USE_ATR")
//	public int timeCalUseAtr;
//	
//	/**
//	 * 時間入力利用区分
//	 */
//	@Column(name = "TIME_INPUT_USE_ATR")
//	public int timeInputUseAtr;
//	
//	/**
//	 * 退勤時刻初期表示区分
//	 */
//	@Column(name = "TIME_END_DIS_FLG")
//	public int timeEndDispFlg;
//	
//	/**
//	 * 指示が必須
//	 */
//	@Column(name = "REQUIRED_INSTRUCTION_FLG")
//	public int requiredInstructionFlg;
//	/**
//	 * 指示利用設定 - 指示区分
//	 */
//	@Column(name = "INSTRUCTION_ATR")
//	public int instructionAtr;
//	/**
//	 * 指示利用設定 - 備考
//	 */
//	@Column(name = "INSTRUCTION_MEMO")
//	public String instructionMemo;
//	/**
//	 * 指示利用設定 - 利用区分
//	 */
//	@Column(name ="INSTRUCTION_USE_ATR")
//	public int instructionUseAtr;
//	@ManyToOne
//	@PrimaryKeyJoinColumns({
//		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"),
//		@PrimaryKeyJoinColumn(name="WKP_ID",referencedColumnName="WKP_ID")
//	})
//	private KrqstWpAppConfig krqstWpAppConfig;
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	protected Object getKey() {
//		return krqstWpAppConfigDetailPK;
//	}
//
//}
