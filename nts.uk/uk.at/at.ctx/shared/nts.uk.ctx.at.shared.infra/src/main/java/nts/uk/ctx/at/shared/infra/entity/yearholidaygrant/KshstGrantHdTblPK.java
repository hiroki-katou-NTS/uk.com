package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.query.DBCharPaddingAs;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;

/**
 * 
 * @author yennth
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshstGrantHdTblPK {
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* 付与回数 */
	@Column(name = "GRANT_NUM")
	public int grantNum;
	
	/* 条件NO */
	@Column(name = "CONDITION_NO")
	public int conditionNo;
	
	/* 年休付与テーブル設定コード */
	@Column(name = "YEAR_HD_CD")
	@DBCharPaddingAs(YearHolidayCode.class)
	public String yearHolidayCode;
}
