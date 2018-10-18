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
public class QxxmtEmpPayInfoPk {

	@Column(name = "SID")
	public String employeeId;

	@Column(name = "HIST_ID")
	public String historyId;
	
}
