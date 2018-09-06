package nts.uk.ctx.sys.portal.infra.entity.toppagepart;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author LamDT
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CcgmtTopPagePartPK implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** Company ID */
	@Column(name = "CID")
	public String companyID;

	/** TopPage Part GUID */
	@Column(name = "TOPPAGE_PART_ID")
	public String topPagePartID;

}