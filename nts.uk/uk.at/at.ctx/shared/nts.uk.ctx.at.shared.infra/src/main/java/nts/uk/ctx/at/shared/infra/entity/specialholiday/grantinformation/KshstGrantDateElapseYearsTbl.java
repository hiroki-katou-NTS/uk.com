package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 特別休暇経過付与日数テーブル
 * @author masaaki_jinno
 */
public class KshstGrantDateElapseYearsTbl extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KshstGrantDateElapseYearsTblPK pk;

	/* 付与回数 */
	@Column(name = "GRANT_CNT")
	public int grantCnt;

	/* 付与日数 */
	@Column(name = "GRANT_DAYS")
	public Integer grantDays;

	@Override
	protected Object getKey() {
		return pk;
	}

	public KshstGrantDateElapseYearsTbl(KshstGrantDateElapseYearsTblPK pk, int grantCnt, Integer grantDays) {
		this.pk = pk;
		this.grantCnt = grantCnt;
		this.grantDays = grantDays;
	}
}