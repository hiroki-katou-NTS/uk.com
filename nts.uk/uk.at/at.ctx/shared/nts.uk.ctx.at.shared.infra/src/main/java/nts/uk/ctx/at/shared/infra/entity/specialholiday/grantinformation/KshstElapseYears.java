package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 特別休暇付与経過年数テーブル
 * @author masaaki_jinno
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_GRANT_ELAPSED_YEARS_TBL")
public class KshstElapseYears extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KshstElapseYearsPK pk;
	
	/* テーブル以降の固定付与をおこなう */
	@Column(name = "FIXED_ASSIGN")
	public int fixedAssign;
	
	/* テーブル以降の付与周期　経過年数.年数 */
	@Column(name = "CYCLE_YEARS")
	public int cycleYears;
	
	/* テーブル以降の付与周期　経過年数.月数 */
	@Column(name = "CYCLE_MONTHS")
	public int cycleMonths;
	
	@Override
	protected Object getKey() {
		return pk;
	}

	/**
	 * コンストラクタ
	 * @param pk
	 * @param fixedAssign テーブル以降の固定付与をおこなう
	 * @param years テーブル以降の付与周期　経過年数.年数
	 * @param months テーブル以降の付与周期　経過年数.月数
	 */
	public KshstElapseYears(KshstElapseYearsPK pk, int fixedAssign, int years, int months) {		
		this.pk = pk;
		this.fixedAssign = fixedAssign;
		this.cycleYears = years;
		this.cycleMonths = months;
	}
}
