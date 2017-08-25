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
public class KshstGrantConditionPK {
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* 年休付与テーブル設定コード */
	@Column(name = "YEAR_HD_CD")
	public String yearHolidayCode;
	
	/* 条件NO */
	@Column(name = "CONDITION_NO")
	public int conditionNo;
}
