package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author TanLV
 *
 */

@Entity
@Table(name="KSHMT_HDPAID_GRANT_TBL")
@AllArgsConstructor
@NoArgsConstructor
public class KshstGrantHdTbl extends ContractUkJpaEntity {
	/*主キー*/
	@EmbeddedId
    public KshstGrantHdTblPK kshstGrantHdTblPK;
	
	/* 年休付与日数 */
	@Column(name = "GRANT_DAY")
	public Double grantDay;
	
	/* 半日年休上限回数 */
	@Column(name = "LIMIT_TIME_HD")
	public Integer limitTimeHd;
	
	/* 時間年休上限日数 */
	@Column(name = "LIMIT_DAY_YEAR")
	public Integer limitDayYear;
	
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "YEAR_HD_CD", referencedColumnName = "YEAR_HD_CD", insertable = false, updatable = false),
        @JoinColumn(name = "CONDITION_NO", referencedColumnName = "CONDITION_NO", insertable = false, updatable = false)
    })
	public KshmtHdpaidCondition grantCondition;
	
	@Override
	protected Object getKey() {
		return kshstGrantHdTblPK;
	}

	public KshstGrantHdTbl(KshstGrantHdTblPK kshstGrantHdTblPK, Double grantDays, 
			Integer limitTimeHd, Integer limitDayYear) {
		super();
		this.kshstGrantHdTblPK = kshstGrantHdTblPK;
		this.grantDay = grantDays;
		this.limitTimeHd = limitTimeHd;
		this.limitDayYear = limitDayYear;
	}
	
}
