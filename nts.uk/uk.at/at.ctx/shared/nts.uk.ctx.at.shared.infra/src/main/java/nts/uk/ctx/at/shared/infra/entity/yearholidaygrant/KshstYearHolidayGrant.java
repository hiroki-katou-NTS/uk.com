package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimes;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.NumberMonths;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.NumberYears;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author TanLV
 *
 */

@Entity
@Table(name="KSHST_YEAR_HOLIDAY_GRANT")
@AllArgsConstructor
@NoArgsConstructor
public class KshstYearHolidayGrant extends UkJpaEntity {
	/*主キー*/
	@EmbeddedId
    public KshstYearHolidayGrantPK kshstYearHolidayGrantPK;
	
	/* 年休付与日数 */
	@Column(name = "GRANT_DAYS")
	public GrantDays grantDays;
	
	/* 時間年休上限日数 */
	@Column(name = "LIMITED_DAYS")
	public LimitedDays limitedDays;
	
	/* 半日年休上限回数 */
	@Column(name = "LIMITED_CNT")
	public LimitedTimes limitedTimes;
	
	/* 勤続年数月数 */
	@Column(name = "NUMBER_MONTHS")
	public NumberMonths numberMonths;
	
	/* 勤続年数年数 */
	@Column(name = "NUMBER_YEARS")
	public NumberYears numberYears;
	
	/* 付与基準日 */
	@Column(name = "GRANT_DAY_TYPE")
	public int grantDayType;
	
	/* 一斉付与する */
	@Column(name = "GRANT_SIMULTANEITY")
	public int grantSimultaneity;
	
	@Override
	protected Object getKey() {
		return kshstYearHolidayGrantPK;
	}

}
