package nts.uk.ctx.sys.portal.infra.entity.toppagepart;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author LamDT
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
@Setter
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