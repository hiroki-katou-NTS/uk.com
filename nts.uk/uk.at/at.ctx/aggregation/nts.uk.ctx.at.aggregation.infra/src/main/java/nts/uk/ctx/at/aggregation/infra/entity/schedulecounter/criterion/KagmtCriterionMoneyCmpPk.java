package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KagmtCriterionMoneyCmpPk {
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "YEAR_MONTH_ATR")
	public String yearMonthAtr;
	
	@Column(name = "FRAME_NO")
	public String frameNo;
}
