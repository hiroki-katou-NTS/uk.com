package nts.uk.ctx.sys.portal.infra.entity.mypage.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Embeddable
public class CcgmtPartItemSettingPK implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	public String cid;

	/** The top page part code. */
	@Column(name = "TOP_PAGE_PART_CODE")
	public String topPagePartCode;
	

	/** The top page part type. */
	@Column(name = "TOP_PAGE_PART_TYPE")
	public String topPagePartType;
}
