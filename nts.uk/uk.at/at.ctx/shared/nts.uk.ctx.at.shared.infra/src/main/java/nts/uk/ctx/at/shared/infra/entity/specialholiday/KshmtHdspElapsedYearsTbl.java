package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 特別休暇付与経過年数テーブル
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_GRANT_ELAPSED_YEARS_TBL")
public class KshmtHdspElapsedYearsTbl extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KshmtHdspElapsedYearsTblPK pk;

	/* 経過年数 */
	@Column(name = "ELAPSED_YEARS")
	public String elapsedYear;

	/* 経過月数 */
	@Column(name = "ELAPSED_MONTHS")
	public String elapsedMonths;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
