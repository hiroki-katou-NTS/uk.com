package nts.uk.ctx.hr.develop.infra.repository.announcement.mandatoryretirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCourceRepository;
import nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement.JshmtRetirePlanCourse;

@Stateless
public class JpaMandatoryRetirementRegulationRepositoryImpl extends JpaRepository implements RetirePlanCourceRepository {

	private static final String GET_BY_CID = "SELECT c FROM JshmtRetirePlanCourse c "
			+ "WHERE c.companyId = :companyId ";
	
	private static final String GET_BY_CID_ASC = GET_BY_CID 
			+ "ORDER BY c.retirePlanCourseClass, c.retirePlanCourseCode";
	
	private static final String GET_BY_CID_RETIRE_PLAN_ID_LIST = "SELECT c FROM JshmtRetirePlanCourse c "
			+ "WHERE c.companyId = :companyId "
			+ "AND c.retirePlanCourseId IN :retirePlanCourseIds";
	
	private static final String GET_BY_CID_DATE = "SELECT c FROM JshmtRetirePlanCourse c "
			+ "WHERE c.companyId = :companyId "
			+ "AND c.notUsageFlg = 0 "
			+ "AND c.usageStartDate <= :baseDate "
			+ "AND c.usageEndDate >= :baseDate ";
	
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

	@Override
	public List<RetirePlanCource> getlistRetirePlanCourceAsc(String companyId) {
		 return this.queryProxy().query(GET_BY_CID_ASC, JshmtRetirePlanCourse.class)
				.setParameter("companyId", companyId)
				.getList(c->c.toDomain());
	}

	@Override
	public void addAll(List<RetirePlanCource> retirePlanCource) {
		this.commandProxy().insertAll(retirePlanCource.stream().map(c->new JshmtRetirePlanCourse(c)).collect(Collectors.toList()));
	}

	@Override
	public void updateAll(List<RetirePlanCource> retirePlanCource) {
		this.commandProxy().updateAll(retirePlanCource.stream().map(c->new JshmtRetirePlanCourse(c)).collect(Collectors.toList()));
	}
	
	@Override
	public List<RetirePlanCource> getlistRetirePlanCource(String companyId) {
		 return this.queryProxy().query(GET_BY_CID, JshmtRetirePlanCourse.class)
				.setParameter("companyId", companyId)
				.getList(c->c.toDomain());
	}

	@Override
	public List<RetirePlanCource> getRetireTermByRetirePlanCourceIdList(String companyId, List<String> retirePlanCourseId) {
		List<RetirePlanCource> result = new ArrayList<>();
		CollectionUtil.split(retirePlanCourseId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			result.addAll(this.queryProxy().query(GET_BY_CID_RETIRE_PLAN_ID_LIST, JshmtRetirePlanCourse.class)
					.setParameter("companyId", companyId)
					.setParameter("retirePlanCourseIds", subList)
					.getList(c->c.toDomain()));
		});
		return result;
	}

	@Override
	public List<RetirePlanCource> getEnableRetirePlanCourceByBaseDate(String companyId, GeneralDate baseDate) {
		return this.queryProxy().query(GET_BY_CID_DATE, JshmtRetirePlanCourse.class)
				.setParameter("companyId", companyId)
				.setParameter("baseDate", baseDate)
				.getList(c->c.toDomain());
	}

}
