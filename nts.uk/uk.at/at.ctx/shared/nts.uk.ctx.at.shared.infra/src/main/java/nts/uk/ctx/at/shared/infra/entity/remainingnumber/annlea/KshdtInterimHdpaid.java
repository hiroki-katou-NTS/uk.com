package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * entity 暫定年休管理データ
 * @author do_dt
 *
 */
@Entity
@Table(name = "KSHDT_INTERIM_HDPAID")
public class KshdtInterimHdpaid extends ContractUkJpaEntity{
	/**
	 * 暫定年休管理データID
	 */
	@EmbeddedId
	public KshdtInterimHdpaidPK pk;

	/**
	 * 残数管理データID
	 */
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngId;

	/**
	 * 作成元区分
	 */
	@Column(name = "CREATOR_ATR")
	public int creatorAtr;

	/**
	 * 勤務種類コード
	 */
	@Column(name = "WORKTYPE_CODE")
	public String workTypeCode;

	/**
	 * 使用日数
	 */
	@Column(name = "USED_DAYS")
	public Double useDays;

	/**
	 * 使用時間
	 */
	@Column(name = "USED_TIME")
	public Integer useTime;

	@Override
	protected Object getKey() {
		return pk;
	}

	public void update(TempAnnualLeaveMngs domain) {

		this.remainMngId = domain.getRemainManaID();
		this.creatorAtr = domain.getCreatorAtr().value;
		this.useDays = domain.getUsedNumber().getUsedDayNumber().map(mapper->mapper.v()).orElse(null);
		this.useTime = domain.getUsedNumber().getUsedTime().map(x -> x.v()).orElse(null);
		this.workTypeCode = domain.getWorkTypeCode().v();

	}
}
