package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author TanLV
 *
 */

@Entity
@Table(name="KSHST_GRANT_HD_TBL")
@AllArgsConstructor
@NoArgsConstructor
public class KshstGrantHdTbl extends UkJpaEntity {
	/*主キー*/
	@EmbeddedId
    public KshstGrantHdTblPK kshstGrantHdTblPK;
	
	/* 年休付与日数 */
	@Column(name = "GRANT_DAYS")
	public BigDecimal grantDays;
	
	/* 時間年休上限日数 */
	@Column(name = "LIMITED_TIME_HD_DAYS")
	public int limitedTimeHdDays;
	
	/* 半日年休上限回数 */
	@Column(name = "LIMITED_HALF_HD_CNT")
	public int limitedHalfHdCnt;
	
	/* 勤続年数月数 */
	@Column(name = "LENGTH_OF_SERVICE_MONTHS")
	public int lengthOfServiceMonths;
	
	/* 勤続年数年数 */
	@Column(name = "LENGTH_OF_SERVICE_YEARS")
	public int lengthOfServiceYears;
	
	/* 付与基準日 */
	@Column(name = "GRANT_REFERENCE_DATE")
	public int grantReferenceDate;
	
	/* 一斉付与する */
	@Column(name = "GRANT_SIMULTANEITY")
	public int grantSimultaneity;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "YEAR_HD_CD", referencedColumnName = "YEAR_HD_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CONDITION_NO", referencedColumnName = "CONDITION_NO", insertable = false, updatable = false)
    })
	public KshstGrantCondition grantCondition;
	
	@Override
	protected Object getKey() {
		return kshstGrantHdTblPK;
	}

	public KshstGrantHdTbl(KshstGrantHdTblPK kshstGrantHdTblPK, BigDecimal grantDays, int limitedTimeHdDays,
			int limitedHalfHdCnt, int lengthOfServiceMonths, int lengthOfServiceYears, int grantReferenceDate,
			int grantSimultaneity) {
		super();
		this.kshstGrantHdTblPK = kshstGrantHdTblPK;
		this.grantDays = grantDays;
		this.limitedTimeHdDays = limitedTimeHdDays;
		this.limitedHalfHdCnt = limitedHalfHdCnt;
		this.lengthOfServiceMonths = lengthOfServiceMonths;
		this.lengthOfServiceYears = lengthOfServiceYears;
		this.grantReferenceDate = grantReferenceDate;
		this.grantSimultaneity = grantSimultaneity;
	}
	
}
