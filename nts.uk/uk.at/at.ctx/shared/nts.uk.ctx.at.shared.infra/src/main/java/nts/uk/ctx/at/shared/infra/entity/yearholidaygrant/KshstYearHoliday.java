package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayName;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayNote;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author TanLV
 *
 */

@Entity
@Table(name="KSHST_YEAR_HOLIDAY")
@AllArgsConstructor
@NoArgsConstructor
public class KshstYearHoliday extends UkJpaEntity {
	/*主キー*/
	@EmbeddedId
    public KshstYearHolidayPK kshstYearHolidayPK;

	/* 名称 */
	@Column(name = "YEAR_HOLIDAY_NAME")
	public YearHolidayName yearHolidayName;

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
	@Column(name = "YEAR_HOLIDAY_NOTE")
	public YearHolidayNote yearHolidayNote;
	
	@Override
	protected Object getKey() {
		return kshstYearHolidayPK;
	}

}
