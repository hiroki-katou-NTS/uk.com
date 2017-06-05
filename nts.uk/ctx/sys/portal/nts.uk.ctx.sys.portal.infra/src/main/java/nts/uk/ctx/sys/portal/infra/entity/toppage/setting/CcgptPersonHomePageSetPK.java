package nts.uk.ctx.sys.portal.infra.entity.toppage.setting;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CcgptPersonHomePageSetPK {

	/** The cid. */
	@Id
	@Column(name = "CID")
	public String cId;
	
	/** The sid. */
	@Column(name = "SID")
	public String sId;
	
	/** The code. */
	@Column(name = "CODE")
	public String code;
}
