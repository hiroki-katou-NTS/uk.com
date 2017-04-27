package nts.uk.ctx.sys.portal.infra.entity.toppage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Embeddable
public class CcgmtTopPagePK implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	public String cid;

	/** The top page code. */
	@Column(name = "TOP_PAGE_CODE")
	public String topPageCode;

}
