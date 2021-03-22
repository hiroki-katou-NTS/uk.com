package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class KshdtInterimHdpaidPK {
	
	/**
	 * CID
	 */
	@Column(name = "CID")
	public String cid;
	
	/**
	 * 社員ID
	 */
	@Column(name = "SID")
	public String sid;
	
	/**
	 * 対象日
	 */
	@Column(name = "YMD")
	public GeneralDate ymd;
	
	/**
	 * 時間消化休暇かどうか	
	 */
	@Column(name = "TIME_DIGESTIVE_ATR")
	public int timeDigestiveAtr;
	
	/**
	 * 時間休暇種類
	 */
	@Column(name = "TIME_HD_TYPE")
	public int timeHdType;
}
