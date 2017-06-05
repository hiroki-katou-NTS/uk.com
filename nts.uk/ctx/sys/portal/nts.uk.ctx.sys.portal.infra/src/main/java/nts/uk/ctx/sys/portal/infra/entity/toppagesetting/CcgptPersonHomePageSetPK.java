package nts.uk.ctx.sys.portal.infra.entity.toppagesetting;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CcgptPersonHomePageSetPK {

	/** The company id. */
	@Id
	@Column(name = "CID")
	public String companyId;
	
	/** The employee Id. */
	@Column(name = "SID")
	public String employeeId;
	
	/** The code. */
	@Column(name = "CODE")
	public String code;
}
