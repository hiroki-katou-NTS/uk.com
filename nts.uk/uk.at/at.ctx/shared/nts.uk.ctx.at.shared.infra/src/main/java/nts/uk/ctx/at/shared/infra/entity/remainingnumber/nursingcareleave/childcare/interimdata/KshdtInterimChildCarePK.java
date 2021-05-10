package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.childcare.interimdata;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * プライマリキー：子の看護介護休暇暫定データ
 * @author yuri_tamakoshi
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KshdtInterimChildCarePK {

	/**会社ID	 */
	@Column(name = "CID")
	public String cID;

	/**社員ID	 */
	@Column(name = "SID")
	public String sID;

	/**	対象日 */
	@Column(name = "YMD")
	public GeneralDate ymd;

	/** 時間消化休暇かどうか */
	@Column(name = "TIME_DIGESTIVE_ATR")
	public Integer timeDigestiveAtr;

	/**時間休暇種類 */
	@Column(name = "TIME_HD_TYPE")
	public Integer timeHdType;

}
