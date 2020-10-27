package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 付与日定期
 *
 * @author tanlv
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_REGULAR")
public class KshstGrantRegular extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KshstGrantRegularPK pk;

	/* 付与するタイミングの種類 */
	@Column(name = "TYPE_TIME")
	public int typeTime;

	/* 付与基準日 */
	@Column(name = "GRANT_DATE")
	public int grantDate;

	/* 取得できなかった端数は消滅する */
	@Column(name = "ALLOW_DISAPPEAR")
	public int allowDisappear;

	/* 周期 */
	@Column(name = "INTERVAL")
	public Integer interval;

	/* 固定付与日数 */
	@Column(name = "GRANTED_DAYS")
	public Integer grantedDays;

	@Override
	protected Object getKey() {
		return pk;
	}

	public KshstGrantRegular(KshstGrantRegularPK pk, int typeTime, int grantDate, int allowDisappear, Integer interval, Integer grantedDays) {
		this.pk = pk;
		this.typeTime = typeTime;
		this.grantDate = grantDate;
		this.allowDisappear = allowDisappear;
		this.interval = interval;
		this.grantedDays = grantedDays;
	}

	/**
	 * To Entity
	 *
	 * @param domain
	 * @return
	 */
	public static KshstGrantRegular toEntity(GrantRegular domain){
		FixGrantDate fixGrantDate = domain.getGrantTime() != null ? domain.getGrantTime().getFixGrantDate() : null;

		return new KshstGrantRegular(new KshstGrantRegularPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()), domain.getTypeTime().value,
				domain.getGrantDate().value, domain.isAllowDisappear() ? 1 : 0, fixGrantDate != null ? fixGrantDate.getInterval().v() : 0,
						fixGrantDate != null ? fixGrantDate.getGrantDays().v() : 0);
	}
}
