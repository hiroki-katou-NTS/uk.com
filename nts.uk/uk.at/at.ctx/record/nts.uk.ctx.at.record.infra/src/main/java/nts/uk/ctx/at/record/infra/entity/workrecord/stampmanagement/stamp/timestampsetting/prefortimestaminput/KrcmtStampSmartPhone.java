package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.KrcdtStamp;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author Laitv
 * スマホ打刻の打刻設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_STAMP_SMART_PHONE")
public class KrcmtStampSmartPhone extends ContractUkJpaEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID									
	 */
	@Id
	@Column(name = "CID")
	public String cid;

	/**
	 * 契約コード
	 */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	public String contractCode;

	/**
	 * 打刻画面のサーバー時刻補正間隔									
	 */
	@Basic(optional = false)
	@Column(name = "CORRECTION_INTERVAL")
	public int correctionInteval;
	
	/**
	 * 	打刻結果自動閉じる時間									
	 */
	@Basic(optional = false)
	@Column(name = "RESULT_DISPLAY_TIME")
	public int resultDisplayTime;
	
	/**
	 * 	文字色									
	 */
	@Basic(optional = false)
	@Column(name = "TEXT_COLOR")
	public String textColor;
	
	/**
	 * 	背景色									
	 */
	@Basic(optional = false)
	@Column(name = "BACK_GROUND_COLOR")
	public String backGroundColor;
	
	/**
	 * 出退勤ボタンを強調する  0:利用しない  1:利用する									
	 */
	@Basic(optional = false)
	@Column(name = "BUTTON_EMPHASIS_ART")
	public boolean btnEmphasisArt;

	@Override
	protected Object getKey() {
		return this.cid;
	}
	
}
