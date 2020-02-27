package nts.uk.ctx.at.record.infra.entity.stampmanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 打刻ボタン詳細設定
 * @author phongtq
 *
 */

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_PAGE_LAYOUT")
public class KrcctStampLayoutDetail extends UkJpaEntity implements Serializable{
private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public KrcctStampLayoutDetailPk pk;
	
	/**
	 * 使用区分 0:使用しない 1:使用する
	 */
	@Column(name ="USE_ART")
	public String useArt;
	
	/** ボタン名称 */
	@Column(name ="BUTTON_NAME")
	public int buttonName;
	
	/**
	 * 予約区分 0:なし 1:予約 2:予約取消
	 */
	@Column(name ="RESERVATION_ART")
	public String reservationArt;
	
	/**
	 * 時刻変更区分 0:出勤 1:退勤 2:入門 3:退門 4:応援開始 5:応援終了 6:応援出勤 7:外出 8:戻り 9:臨時+応援出勤
	 * 10:臨時出勤 11:臨時退勤 12:PCログオン 13:PCログオフ
	 */
	@Column(name ="CHANGE_CLOCK_ART")
	public String changeClockArt;
	
	/**
	 * 計算区分変更対象 0:なし 1:早出 2:残業 3:休出 4:ﾌﾚｯｸｽ
	 */
	@Column(name ="CHANGE_CAL_ART")
	public int changeCalArt;
	
	/**
	 * 所定時刻セット区分 0:なし 1:直行 2:直帰
	 */
	@Column(name ="SET_PRE_CLOCK_ART")
	public String setPreClockArt;
	
	/**
	 * 勤務種類を半休に変更する 0:False 1:True
	 */
	@Column(name ="CHANGE_HALF_DAY")
	public String changeHalfDay;
	
	/**
	 * 外出区分 0:私用 1:公用 2:有償 3:組合
	 */
	@Column(name ="GO_OUT_ART")
	public String goOutArt;
	
	/** コメント色 */
	@Column(name ="TEXT_COLOR")
	public String textColor;
	
	/** コメント色 */
	@Column(name ="BACK_GROUND_COLOR")
	public String backGroundColor;
	
	/**
	 * 音声使用方法 0:なし 1:おはようございます 2:お疲れ様でした
	 */
	@Column(name ="AUDIO_TYPE")
	public String aidioType;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
