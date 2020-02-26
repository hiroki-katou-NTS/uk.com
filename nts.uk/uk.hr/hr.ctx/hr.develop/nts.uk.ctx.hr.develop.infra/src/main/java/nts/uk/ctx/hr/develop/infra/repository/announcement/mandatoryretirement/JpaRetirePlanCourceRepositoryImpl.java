package nts.uk.ctx.hr.develop.infra.repository.announcement.mandatoryretirement;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulationRepository;
import nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement.JshmtMandatRetireReg;

@Stateless
public class JpaRetirePlanCourceRepositoryImpl extends JpaRepository implements MandatoryRetirementRegulationRepository {

	private static final String GET_BY_HISTORYID_CID = "SELECT c FROM JshmtMandatRetireReg c "
			+ "WHERE c.companyId = :companyId "
			+ "AND c.historyId = :historyId ";
	
	@Override
	public Optional<MandatoryRetirementRegulation> findByKey(String historyId) {
		Optional<JshmtMandatRetireReg> entity = this.queryProxy().find(historyId, JshmtMandatRetireReg.class);
		if(entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
	}

	@Override
	public void add(MandatoryRetirementRegulation mandatoryRetirementRegulation) {
		this.commandProxy().insert(new JshmtMandatRetireReg(mandatoryRetirementRegulation));
	}

	@Override
	public void update(MandatoryRetirementRegulation mandatoryRetirementRegulation) {
		this.commandProxy().update(new JshmtMandatRetireReg(mandatoryRetirementRegulation));
	}

	@Override
	public void remove(String historyId) {
		this.commandProxy().remove(JshmtMandatRetireReg.class, historyId);
	}

	@Override
	public Optional<MandatoryRetirementRegulation> getMandatoryRetirementRegulation(String historyId, String companyId) {
		Optional<JshmtMandatRetireReg> e = this.queryProxy().query(GET_BY_HISTORYID_CID, JshmtMandatRetireReg.class)
				.setParameter("companyId", companyId)
				.setParameter("historyId", historyId)
				.getSingle();
		if(e.isPresent()) {
			return Optional.of(e.get().toDomain());
		}
		return Optional.empty();
	}

}
