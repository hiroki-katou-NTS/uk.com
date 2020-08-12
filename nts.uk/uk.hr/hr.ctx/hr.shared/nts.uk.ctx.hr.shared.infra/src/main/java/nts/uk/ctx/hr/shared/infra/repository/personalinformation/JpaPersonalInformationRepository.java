package nts.uk.ctx.hr.shared.infra.repository.personalinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformationRepository;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.get.GetPersonInfoHRInput;
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
		Optional<PpedtData> personal = this.queryProxy().find(domain.getHistId(), PpedtData.class);

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

	// Get
	@Override
	public List<PersonalInformation> getPersonInfoHR(GetPersonInfoHRInput input) {
		String GET_PERSONALINFO = "SELECT a FROM PpedtData a WHERE a.cId = :cId AND a.workId = :workId";
		List<PpedtData> personalInformations = new ArrayList<>();
		
		if (input.getColumnName().isEmpty() && !input.getTypeSort().isEmpty()) {
		
//			if (input.getTypeSort().toUpperCase().equals("ASC") || input.getTypeSort().toUpperCase().equals("DESC")) {
//				GET_PERSONALINFO = GET_PERSONALINFO.concat(" ORDER BY ")
//			}
			
		} else {
			personalInformations = this.queryProxy().query(GET_PERSONALINFO, PpedtData.class)
					.setParameter("cId", input.getCompanyId())
					.setParameter("workId", input.getBusinessId()).getList();
		}

		return personalInformations.stream().map(m -> m.toDomain(m)).collect(Collectors.toList());
	}
}