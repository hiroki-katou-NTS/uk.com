package nts.uk.ctx.at.record.infra.entity.reservation.reservationsetting;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_PREPARATION_ROLES")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtPreparationRoles extends ContractUkJpaEntity {
	
	@EmbeddedId
    public KrcmpPreparationRolesPK pk;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID")
    })
	public KrcmtReservationSetting krcmtReservationSetting;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KrcmtPreparationRoles fromDomain(String companyID, String roleID) {
		return new KrcmtPreparationRoles(new KrcmpPreparationRolesPK(companyID, roleID), null);
	}
	
}
