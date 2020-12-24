package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * entity 暫定年休管理データ
 * @author do_dt
 *
 */
@Entity
@Table(name = "KRCDT_INTERIM_HDPAID")
public class KrcdtInterimHdpaid extends UkJpaEntity{
	/**
	 * 暫定年休管理データID
	 */
	@Id
	@Column(name = "ANNUAL_MNG_ID")
	public String annualMngId;

	/**
	 * SID
	 */
	@Column(name = "SID")
	public String sid;

	/**
	 * 対象日
	 */
	@Column(name = "YMD")
	public GeneralDate ymd;

	/**
	 * 作成元区分
	 */
	@Column(name = "CREATOR_ATR")
	public Integer creatorAtr;

	/**
	 * 残数分類
	 */
	@Column(name = "REMAIN_ATR")
	public Integer remainAtr;

	/**
	 * 勤務種類
	 */
	@Column(name = "WORKTYPE_CODE")
	public String workTypeCode;

	/**
	 * 使用日数
	 */
	@Column(name = "USE_DAYS")
	public double useDays;

	/**
	 * 使用時間
	 */
	@Column(name = "USED_TIME")
	public double usedTime;

	/**
	 * 時間消化休暇かどうか
	 */
	@Column(name = "TIME_DIGESTIVE_ATR")
	public double timeDigestiveAtr;

	/**
	 * 時間休暇種類
	 */
	@Column(name = "TIME_HD_TYPE")
	public double timeHdType;



	@Override
	protected Object getKey() {
		return annualMngId;
	}

}
