package nts.uk.ctx.at.record.infra.entity.stampmanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 打刻画面の表示設定
 * @author phongtq
 *
 */

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_PAGE_LAYOUT")
public class KrcctStampDisplay extends UkJpaEntity implements Serializable{
private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public KrcctStampDisplayPk pk;
	
	/** 打刻画面のサーバー時刻補正間隔 */
	@Column(name ="CORRECTION_INTERVAL")
	public int correctionInterval;
	
	/** 打刻履歴表示方法 0:表示しない 1:打刻一覧を表示 2:タイムカードを表示 */
	@Column(name ="HIST_DISPLAY_METHOD")
	public int histDisplayMethod;
	
	/** 打刻結果自動閉じる時間 */
	@Column(name ="RESULT_DISPLAY_TIME")
	public int resultDisplayTime;
	
	/** 文字色 */
	@Column(name ="TEXT_COLOR")
	public String textColor;
	
	/** 背景色 */
	@Column(name ="BACK_GROUND_COLOR")
	public String backGroundColor;
	
	/** 出退勤ボタンを強調する 0:利用しない 1:利用する */
	@Column(name ="BUTTON_EMPHASIS_ART")
	public boolean buttonEmphasisArt;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}