package nts.uk.ctx.sys.portal.infra.entity.layout;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LamDT
 */
@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class CcgmtLayoutPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** Company ID */
	@Column(name = "CID")
	private String companyID;

	/** Layout GUID */
	@Column(name = "LAYOUT_ID")
	private String layoutID;

}