package nts.uk.ctx.at.record.infra.entity.reservation.reservationsetting;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmpPreparationRolesPK {
	@Column(name = "CID")
    public String companyID;
	
	@Column(name = "ROLE_ID")
    public String roleID;
}
