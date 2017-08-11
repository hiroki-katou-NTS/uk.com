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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author TanLV
 *
 */

@Entity
@Table(name="KSHST_GRANTING_CONDITION")
@AllArgsConstructor
@NoArgsConstructor
public class KshstGrantingCondition extends UkJpaEntity {
	/*主キー*/
	@EmbeddedId
    public KshstGrantingConditionPK kshstGrantingConditionPK;
	
	/* 条件値 */
	@Column(name = "CONDITION_VALUE")
	public int conditionValue;
	
	/* 条件利用区分 */
	@Column(name = "USE_CONDITION_CLS")
	public int useConditionClassification;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "YEAR_HOLIDAY_CD", referencedColumnName = "YEAR_HOLIDAY_CD", insertable = false, updatable = false)
    })
	public KshstYearHoliday yearHoliday;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="grantingCondition", orphanRemoval = true)
	public List<KshstYearHolidayGrant> yearHolidayGrants;
	
	@Override
	protected Object getKey() {
		return kshstGrantingConditionPK;
	}

}
