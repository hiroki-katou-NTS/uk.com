package nts.uk.ctx.hr.shared.infra.repository.personalinformation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.shared.dom.personalinfo.personalinformation.PersonalInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.personalinformation.PersonalInformationRepository;
import nts.uk.ctx.hr.shared.infra.entity.personalinformation.PpedtData;

/**
 * 
 * @author chungnt
 *
 */

public class JpaPersonalInformationRepository extends JpaRepository implements PersonalInformationRepository {
	private PpedtData entity = new PpedtData();

	@Override
	public void insert(List<PersonalInformation> domains) {
		if (!domains.isEmpty()) {
			this.commandProxy().insertAll(
				domains.stream()
				.map(m -> entity.toEntity(m)).collect(Collectors.toList()));
		}
	}

	@Override
	public void update(List<PersonalInformation> domains) {
		if (!domains.isEmpty()) {
			List<PersonalInformation> entities = domains.stream().map(m -> {
				Optional<PersonalInformation> personalInformation = this.queryProxy().find(m,
						PersonalInformation.class);
				if (personalInformation.isPresent()) {
					return personalInformation.get();
				}
				return null;
			}).collect(Collectors.toList());

			if (!entities.isEmpty()) {
				this.commandProxy()
						.updateAll(entities.stream().map(m -> entity.toEntity(m)).collect(Collectors.toList()));
			}
		}
	}
}
