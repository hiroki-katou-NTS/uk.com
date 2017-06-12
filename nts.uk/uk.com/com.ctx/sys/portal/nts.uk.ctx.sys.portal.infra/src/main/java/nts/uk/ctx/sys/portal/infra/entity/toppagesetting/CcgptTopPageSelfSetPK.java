package nts.uk.ctx.sys.portal.infra.entity.toppagesetting;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CcgptTopPageSelfSetPK {
	/** The employee Id. */
	@Column(name = "SID")
	public String employeeId;
	
	/** The code. */
	@Column(name = "CODE")
	public String code;
	
	/** The division. */
	@Column(name = "DIVISION")
	public int division;
}
