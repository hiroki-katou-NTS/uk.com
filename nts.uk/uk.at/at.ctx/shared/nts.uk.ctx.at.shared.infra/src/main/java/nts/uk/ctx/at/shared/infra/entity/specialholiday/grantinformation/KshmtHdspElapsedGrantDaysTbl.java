package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 特別休暇経過付与日数テーブル
 * @author masaaki_jinno
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_ELAPSED_GRANT_DAYS_TBL")
public class KshmtHdspElapsedGrantDaysTbl extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KshmtHdspElapsedGrantDaysTblPK pk;

	/* 付与日数 */
	@Column(name = "GRANT_DAYS")
	public int grantDays;

	@Override
	protected Object getKey() {
		return pk;
	}

	public KshmtHdspElapsedGrantDaysTbl(KshmtHdspElapsedGrantDaysTblPK pk, int grantDays) {
		this.pk = pk;
		this.grantDays = grantDays;
	}
}