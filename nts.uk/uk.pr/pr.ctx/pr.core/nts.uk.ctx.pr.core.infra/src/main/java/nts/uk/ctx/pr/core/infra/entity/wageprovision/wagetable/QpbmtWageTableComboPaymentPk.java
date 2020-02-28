package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author HungTT
 *
 */

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtWageTableComboPaymentPk {

	@Column(name = "CID")
	public String companyId;

	@Column(name = "WAGE_TABLE_CD")
	public String wageTableCode;

	@Column(name = "HIST_ID")
	public String historyId;
	
	@Column(name = "PAY_AMOUNT_ID")
	public String id;

}
