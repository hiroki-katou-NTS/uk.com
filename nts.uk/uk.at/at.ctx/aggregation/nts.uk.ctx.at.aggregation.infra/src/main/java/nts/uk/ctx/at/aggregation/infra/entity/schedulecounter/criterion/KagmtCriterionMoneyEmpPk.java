package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KagmtCriterionMoneyEmpPk {
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "EMPLOYEMENT_CD")
	public String employmentCd;
	
	@Column(name = "YEAR_MONTH_ATR")
	public int yearMonthAtr;
	
	@Column(name = "FRAME_NO")
	public int frameNo;
}
