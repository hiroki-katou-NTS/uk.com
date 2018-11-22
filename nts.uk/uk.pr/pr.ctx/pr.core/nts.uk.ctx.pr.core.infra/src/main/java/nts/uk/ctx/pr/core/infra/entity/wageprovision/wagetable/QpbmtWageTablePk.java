package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HungTT
 *
 */

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtWageTablePk {
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "CD")
	public String code;
	
}
