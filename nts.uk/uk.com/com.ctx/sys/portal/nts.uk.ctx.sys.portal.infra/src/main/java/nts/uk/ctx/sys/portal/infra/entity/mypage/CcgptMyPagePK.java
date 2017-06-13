package nts.uk.ctx.sys.portal.infra.entity.mypage;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CcgptMyPagePK {
	/** The employee id. */
	@Column(name = "SID")
	public String employeeId;
	
	/** The layout id. */
	@Column(name = "LAYOUT_ID")
	public String layoutId;
}
