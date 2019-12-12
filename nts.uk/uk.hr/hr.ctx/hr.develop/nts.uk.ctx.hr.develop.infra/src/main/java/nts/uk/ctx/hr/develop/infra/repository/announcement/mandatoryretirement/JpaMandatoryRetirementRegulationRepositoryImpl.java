package nts.uk.ctx.hr.develop.infra.repository.announcement.mandatoryretirement;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCourceRepository;
import nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement.JshmtRetirePlanCourse;

@Stateless
public class JpaMandatoryRetirementRegulationRepositoryImpl extends JpaRepository implements RetirePlanCourceRepository {

	@Override
	public Optional<RetirePlanCource> findByKey(String retirePlanCourseId) {
		Optional<JshmtRetirePlanCourse> entity = this.queryProxy().find(retirePlanCourseId, JshmtRetirePlanCourse.class);
		if(entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
	}

	@Override
	public void add(RetirePlanCource retirePlanCource) {
		this.commandProxy().insert(new JshmtRetirePlanCourse(retirePlanCource));
	}

	@Override
	public void update(RetirePlanCource retirePlanCource) {
		this.commandProxy().update(new JshmtRetirePlanCourse(retirePlanCource));
	}

	@Override
	public void remove(String historyId) {
		this.commandProxy().remove(JshmtRetirePlanCourse.class, historyId);
	}

}
