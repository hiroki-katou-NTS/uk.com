package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * 指定日付与
 * @author masaaki_jinno
 * ver4 テーブル名変更　KSHST_GRANT_REGULAR　→　KSHST_HDSP_GRANT
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHST_HDSP_GRANT")
public class KshstHdspGrant extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KshstHdspGrantPK pk;

	/* 付与月日 */
	@Column(name = "GRANT_MD")
	public int grantMd;

	/* 付与日数 */
	@Column(name = "GRANTED_DAYS")
	public Integer grantedDays;

	@Override
	protected Object getKey() {
		return pk;
	}

	public KshstHdspGrant(
			KshstHdspGrantPK pk, int grantMd, int grantedDays) {
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
	public static KshstHdspGrant toEntity(
			String companyId,
			int specialHolidayCode,
			FixGrantDate domain){

		// 付与月日
		int grantMd = 0;
		if ( domain.getGrantMonthDay().isPresent() ) {
			grantMd = domain.getGrantMonthDay().get().getMonth()*100
					+ domain.getGrantMonthDay().get().getDay();
		}

		return new KshstHdspGrant(
				new KshstHdspGrantPK(companyId, specialHolidayCode),
				grantMd,
				domain.getGrantDays().getGrantDays().v());
	}
}