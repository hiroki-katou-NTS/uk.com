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
	public List<PersonalInformation> get(GetPersonInfoHRInput input) {
		String GET_PERSONALINFO = "SELECT a FROM PpedtData a WHERE a.cId = :cId AND a.workId = :workId";
		List<PpedtData> personalInformations = new ArrayList<>();

		if (input.getColumnName().isEmpty() && !input.getTypeSort().isEmpty()) {

			if (input.getTypeSort().toUpperCase().equals("ASC") || input.getTypeSort().toUpperCase().equals("DESC")) {
				GET_PERSONALINFO = GET_PERSONALINFO.concat(" ORDER BY a." + input.getColumnName() + " " + input.getTypeSort());
			}
		}
		
		personalInformations = this.queryProxy().query(GET_PERSONALINFO, PpedtData.class)
				.setParameter("cId", input.getCompanyId()).setParameter("workId", input.getBusinessId()).getList();
		
		if (!input.getEmployeeId().isEmpty()) {
			List<PpedtData> ppdetData = new ArrayList<>();
			
			for (int i = 0 ; i < input.getEmployeeId().size() ; i++) {
				
				for (int j = 0 ; j < personalInformations.size() ; j++) {
					
					if (input.getEmployeeId().get(i).equals(personalInformations.get(j).getSid())) {
						ppdetData.add(personalInformations.get(j));
					}
				}
			}
			
			personalInformations = ppdetData;
		}
		
		if (!input.getPersonalId().isEmpty()) {
			List<PpedtData> ppdetData = new ArrayList<>();
			
			for (int i = 0 ; i < input.getPersonalId().size() ; i++) {
				
				for (int j = 0 ; j < personalInformations.size() ; j++) {
					
					if (input.getPersonalId().get(i).equals(personalInformations.get(j).getPersonName())) {
						ppdetData.add(personalInformations.get(j));
					}
				}
			}
			
			personalInformations = ppdetData;
		}

		return personalInformations.stream().map(m -> m.toDomain(m)).collect(Collectors.toList());
	}
}