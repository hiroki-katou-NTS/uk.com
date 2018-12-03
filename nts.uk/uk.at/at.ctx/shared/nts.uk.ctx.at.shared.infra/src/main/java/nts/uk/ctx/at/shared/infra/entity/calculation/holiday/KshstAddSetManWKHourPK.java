package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshstAddSetManWKHourPK {
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
}
