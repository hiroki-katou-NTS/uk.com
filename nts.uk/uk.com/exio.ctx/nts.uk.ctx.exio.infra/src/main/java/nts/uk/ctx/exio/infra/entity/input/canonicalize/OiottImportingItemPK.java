package nts.uk.ctx.exio.infra.entity.input.canonicalize;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OiottImportingItemPK {
	@Column(name = "CID")
	public String cid;
	
	@Column(name = "NAME")
	public String name;
}
