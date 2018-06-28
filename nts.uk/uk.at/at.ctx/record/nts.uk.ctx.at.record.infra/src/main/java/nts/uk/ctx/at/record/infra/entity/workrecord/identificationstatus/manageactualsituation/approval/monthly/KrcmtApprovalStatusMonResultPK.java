package nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.manageactualsituation.approval.monthly;

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
public class KrcmtApprovalStatusMonResultPK implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SID")
	public String employeeId;
	
	@Column(name = "PROCESSING_DATE")
	public int processDate;
	
	@Column(name = "CLOSURE_ID")
	public Integer closureId;
	
	@Column(name = "APPROVAL_ROUTE_ID")
	public String rootInstanceID;

}
