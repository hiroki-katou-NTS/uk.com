package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KfnmtMonthlyActualResultRCPK implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "MON_ACTUAL_RESULTS_ID")
	public String monthlyActualID;
	
	@Column(name = "SHEET_NO")
	public int sheetNo;

}
