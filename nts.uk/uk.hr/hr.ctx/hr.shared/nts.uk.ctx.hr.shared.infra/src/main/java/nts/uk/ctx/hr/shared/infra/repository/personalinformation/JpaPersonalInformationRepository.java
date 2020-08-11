package nts.uk.ctx.hr.shared.infra.repository.personalinformation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformationRepository;
import nts.uk.ctx.hr.shared.infra.entity.personalinformation.PpedtData;

/**	
 * 
 * @author chungnt
 *
 */
@Stateless
public class JpaPersonalInformationRepository extends JpaRepository implements PersonalInformationRepository {
	private PpedtData entity = new PpedtData();

	// Insert
	@Override
	public void insert(PersonalInformation domain) {
		this.commandProxy().insert(entity.toEntity(domain));
	}

	// Update
	@Override
	public void update(PersonalInformation domain) {
		Optional<PersonalInformation> personal = this.queryProxy().find(domain.getHistId(), PersonalInformation.class);

		if (personal.isPresent()) {
			this.commandProxy().update(entity.toEntity(domain));
		}
	}

	// Delete
	@Override
	public void delete(String hisId) {
		Optional<PpedtData> personal = this.queryProxy().find(hisId, PpedtData.class);
		if (personal.isPresent()) {
			this.commandProxy().remove(personal.get());
		}
	}
}