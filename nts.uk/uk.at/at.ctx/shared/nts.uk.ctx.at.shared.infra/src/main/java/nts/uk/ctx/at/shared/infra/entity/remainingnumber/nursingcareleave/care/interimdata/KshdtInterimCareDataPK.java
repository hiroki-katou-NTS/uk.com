package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.care.interimdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnlb
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshdtInterimCareDataPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 会社ID */
	@Column(name = "CID")
	public String companyID;

	/** 社員ID */
	@Column(name = "SID")
	public String sid;

	/** 対象日 */
	@Column(name = "YMD")
	public GeneralDate ymd;

	/** 時間消化休暇かどうか */
	@Column(name = "TIME_DIGESTIVE_ATR")
	public Integer timeDigestiveAtr;

	/** 時間休暇種類 */
	@Column(name = "TIME_HD_TYPE")
	public Integer timeHdType;

}
