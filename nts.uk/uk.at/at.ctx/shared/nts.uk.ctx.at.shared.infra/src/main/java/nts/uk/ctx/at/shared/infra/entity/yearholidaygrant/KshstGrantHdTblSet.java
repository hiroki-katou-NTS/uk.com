package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
@Table(name="KSHST_GRANT_HD_TBL_SET")
@AllArgsConstructor
@NoArgsConstructor
public class KshstGrantHdTblSet extends ContractUkJpaEntity {
	/*主キー*/
	@EmbeddedId
    public KshstGrantHdTblSetPK kshstGrantHdTblSetPK;

	/* 名称 */
	@Column(name = "YEAR_HD_NAME")
	public String yearHolidayName;

	/* 計算方法 */
	@Column(name = "CALCULATION_METHOD")
	public int calculationMethod;

	/* 計算基準 */
	@Column(name = "STANDARD_CALCULATION")
	public int standardCalculation;

	/* 一斉付与を利用する */
	@Column(name = "USE_SIMULTANEOUS_GRANT")
	public int useSimultaneousGrant;

	/* 一斉付与日 */
	@Column(name = "SIMULTANEOUS_GRANT_MD")
	public int simultaneousGrandMonthDays;

	/* 備考 */
	@Column(name = "YEAR_HD_NOTE")
	public String yearHolidayNote;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="grantHdTblSet", orphanRemoval = true)
	@OrderBy("kshstGrantConditionPK.conditionNo ASC")
	public List<KshstGrantCondition> grantConditions;
	
	@Override
	protected Object getKey() {
		return kshstGrantHdTblSetPK;
	}

}
