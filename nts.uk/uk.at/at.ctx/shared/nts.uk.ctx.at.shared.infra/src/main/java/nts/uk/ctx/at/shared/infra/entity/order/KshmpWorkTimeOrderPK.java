package nts.uk.ctx.at.shared.infra.entity.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmpWorkTimeOrderPK {
	@Column(name = "CID")
	public String companyID;

	@Column(name = "WORKTIME_CD")
	public String workTimeCode;
}
