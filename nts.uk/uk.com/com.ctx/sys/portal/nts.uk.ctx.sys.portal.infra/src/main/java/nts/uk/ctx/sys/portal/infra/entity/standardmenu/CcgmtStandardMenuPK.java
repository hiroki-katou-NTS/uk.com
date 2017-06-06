package nts.uk.ctx.sys.portal.infra.entity.standardmenu;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CcgmtStandardMenuPK {
	@Column(name = "CID")
	private String companyId;
}
