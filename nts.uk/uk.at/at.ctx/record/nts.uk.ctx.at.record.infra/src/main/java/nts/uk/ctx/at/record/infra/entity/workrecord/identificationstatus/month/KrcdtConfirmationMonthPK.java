package nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.month;

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
public class KrcdtConfirmationMonthPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "SID")
	public String employeeId;
	
	@Column(name = "CLOSURE_ID")
	public int closureId;
	
	@Column(name = "CLOSURE_DAY")
	public int closureDay;
	
	@Column(name = "PROCESS_YM")
	public int processYM;
}
