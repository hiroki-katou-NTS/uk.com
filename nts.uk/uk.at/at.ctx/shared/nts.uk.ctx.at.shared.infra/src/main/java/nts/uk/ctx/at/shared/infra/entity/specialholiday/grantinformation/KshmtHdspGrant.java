package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * 指定日付与
 * @author masaaki_jinno
 * ver4 テーブル名変更　KSHST_GRANT_REGULAR　→　KSHST_HDSP_GRANT
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_GRANT")
public class KshmtHdspGrant extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KshmtHdspGrantPK pk;

	/* 付与月日 */
	@Column(name = "GRANT_MD")
	public Integer grantMd;

	/* 付与日数 */
	@Column(name = "GRANTED_DAYS")
	public int grantedDays;

	@Override
	protected Object getKey() {
		return pk;
	}

	public KshmtHdspGrant(
			KshmtHdspGrantPK pk, Integer grantMd, int grantedDays) {
		this.pk = pk;
		this.grantMd = grantMd;
		this.grantedDays = grantedDays;
	}

	/**
	 * To Entity
	 *
	 * @param domain
	 * @return
	 */
	public static KshmtHdspGrant toEntity(
			String companyId,
			int specialHolidayCode,
			FixGrantDate domain){

		// 付与月日
		Integer grantMd = null;
		if ( domain.getGrantMonthDay().isPresent() ) {
			grantMd = domain.getGrantMonthDay().get().getMonth()*100
					+ domain.getGrantMonthDay().get().getDay();
		}

		return new KshmtHdspGrant(
				new KshmtHdspGrantPK(companyId, specialHolidayCode),
				grantMd,
				domain.getGrantDays().getGrantDays().v());
	}
}
