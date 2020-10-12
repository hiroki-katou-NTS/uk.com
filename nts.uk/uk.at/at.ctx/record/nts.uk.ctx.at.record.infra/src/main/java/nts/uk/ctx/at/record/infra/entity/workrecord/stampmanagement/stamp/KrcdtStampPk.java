package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

/**
 * @author ThanhNX
 *
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcdtStampPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 契約コード
	 * ver2　属性追加
	 */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	public String contractCode;
	
	/**
	 * 打刻カード番号
	 */
	@Basic(optional = false)
	@Column(name = "CARD_NUMBER")
	public String cardNumber;

	/**
	 * 打刻日時
	 */
	@Basic(optional = false)
	@Column(name = "STAMP_DATE_TIME")
	public GeneralDateTime stampDateTime;
	
	/**
	 * 時刻変更区分 0:出勤 1:退勤 2:入門 3:退門 4:応援開始 5:応援終了 6:応援出勤 7:外出 8:戻り 9:臨時+応援出勤 10:臨時出勤
	 * 11:臨時退勤 12:PCログオン 13:PCログオフ
	 */
	@Basic(optional = false)
	@Column(name = "CHANGE_CLOCK_ART")
	public int changeClockArt;
}
