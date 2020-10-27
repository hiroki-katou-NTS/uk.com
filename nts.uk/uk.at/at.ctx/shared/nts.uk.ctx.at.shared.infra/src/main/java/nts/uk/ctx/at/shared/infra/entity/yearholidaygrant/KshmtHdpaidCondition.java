package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="KSHMT_HDPAID_CONDITION")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtHdpaidCondition extends ContractUkJpaEntity {
	/*主キー*/
	@EmbeddedId
    public KshmtHdpaidConditionPK kshmtHdpaidConditionPK;
	
	/* 条件値 */
	@Column(name = "CONDITION_VALUE")
	public Double conditionValue;
	
	/* 条件利用区分 */
	@Column(name = "USE_CONDITION_ATR")
	public int useConditionAtr;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "YEAR_HD_CD", referencedColumnName = "YEAR_HD_CD", insertable = false, updatable = false)
    })
	public KshmtHdpaidTblSet grantHdTblSet;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="grantCondition")
	public List<KshstGrantHdTbl> yearHolidayGrants;
	
	@Override
	protected Object getKey() {
		return kshmtHdpaidConditionPK;
	}

	public KshmtHdpaidCondition(KshmtHdpaidConditionPK kshmtHdpaidConditionPK, Double conditionValue, int useConditionAtr) {
		super();
		this.kshmtHdpaidConditionPK = kshmtHdpaidConditionPK;
		this.conditionValue = conditionValue;
		this.useConditionAtr = useConditionAtr;
	}

}
