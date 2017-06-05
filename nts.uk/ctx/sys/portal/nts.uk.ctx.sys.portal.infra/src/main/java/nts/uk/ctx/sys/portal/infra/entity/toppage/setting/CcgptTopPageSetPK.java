package nts.uk.ctx.sys.portal.infra.entity.toppage.setting;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CcgptTopPageSetPK {
	/** The cid. */
	@Id
	@Column(name = "CID")
	public String cId;
	
	/** The category setting. */
	@Column(name = "CTG_SET")
	public int ctgSet;
}
