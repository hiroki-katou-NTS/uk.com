package nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HungTT
 *
 */

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class QxxmtEmpPayMethodPk {

	@Column(name = "HIST_ID")
	public String historyId;
	
	@Column(name = "PAY_METHOD_NO")
	public int paymentMethodNo;
	
}
