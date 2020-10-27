package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.query.DBCharPaddingAs;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;

/**
 * 
 * @author TanLV
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshmtHdpaidTblSetPK {
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* コード */
	@Column(name = "YEAR_HD_CD")
	@DBCharPaddingAs(YearHolidayCode.class)
	public String yearHolidayCode;
}
