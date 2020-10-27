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
public class KshmtHdpaidConditionPK {
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* 年休付与テーブル設定コード */
	@Column(name = "YEAR_HD_CD")
	@DBCharPaddingAs(YearHolidayCode.class)
	public String yearHolidayCode;
	
	/* 条件NO */
	@Column(name = "CONDITION_NO")
	public int conditionNo;
}
