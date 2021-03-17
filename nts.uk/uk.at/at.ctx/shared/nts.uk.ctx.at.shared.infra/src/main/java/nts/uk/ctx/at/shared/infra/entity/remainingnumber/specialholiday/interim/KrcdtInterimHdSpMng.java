package nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 暫定特別休暇管理データ
 * @author
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="KRCDT_INTERIM_HD_SP_MNG")
public class KrcdtInterimHdSpMng extends ContractUkJpaEntity implements Serializable {
	@EmbeddedId
	public KrcmtInterimSpeHolidayPK pk;

	/** 特別休暇コード */
	@Column(name = "SPECIAL_HOLIDAY_CODE")
	public int specialHolidayCode;

//	/** 社員ID */
//	@Column(name ="SID")
//	public String sid;
//	/** 対象日 */
//	@Column(name ="YMD")
//	public GeneralDate ymd;
//	/** 作成元区分	 */
//	@Column(name ="CREATOR_ATR")
//	public int createAtr;
//	/** 残数分類 */
//	@Column(name ="REMAIN_ATR")
//	public int remainAtr;
	/** 管理単位区分 */
	@Column(name ="MNG_ATR")
	public int mngAtr;
	/** 使用日数 */
	@Column(name ="USE_DAYS")
	public Double usedDays;
	/** 使用時間 */
	@Column(name ="USE_TIMES")
	public Integer usedTime;
//	/** 時間消化休暇かどうか */
//	@Column(name ="TIME_DIGESTIVE_ATR")
//	public Integer timeDigestiveAtr;
//	/** 時間休暇種類*/
//	@Column(name ="TIME_HD_TYPE")
//	public Integer timeHdType;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}

}
