package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 特別休暇経過年数テーブル
 * @author masaaki_jinno
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_ELAPSED_YEARS_TBL")
public class KshstElapseYearsTbl extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KshstElapseYearsTblPK pk;
	
	/* 経過年数 */
	@Column(name = "ELAPSED_YEARS")
	public int elapsedYears;
	
	/* 経過月数 */
	@Column(name = "ELAPSED_MONTHS")
	public int elapsedMonths;
	
	@Override
	protected Object getKey() {
		return pk;
	}

	/**
	 * コンストラクタ
	 * @param pk 
	 * @param elapsedYears 経過年数
	 * @param elapsedMonths 経過月数
	 */
	public KshstElapseYearsTbl(KshstElapseYearsTblPK pk, int elapsedYears, int elapsedMonths) {		
		this.pk = pk;
		this.elapsedYears = elapsedYears;
		this.elapsedMonths = elapsedMonths;
	}
}
