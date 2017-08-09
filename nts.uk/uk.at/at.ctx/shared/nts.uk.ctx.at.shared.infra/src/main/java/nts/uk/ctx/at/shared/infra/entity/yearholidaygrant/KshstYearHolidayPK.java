package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author TanLV
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshstYearHolidayPK {
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* コード */
	@Column(name = "YEAR_HOLIDAY_CD")
	public String yearHolidayCode;
}
