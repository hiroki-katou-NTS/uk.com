/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshmtLegaltimeDDefSya;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshmtLegaltimeDRegSya;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaTransLabTimePK;

/**
 * The Class JpaShainTransLaborTimeRepository.
 */
@Stateless
public class JpaShainTransLaborTimeRepository extends JpaRepository implements DeforLaborTimeShaRepo {

	private static final String SELECT_BY_CID = "SELECT c FROM KshmtLegaltimeDDefSya c"
			+ " WHERE c.kshstShaTransLabTimePK.cid = :cid";
	
	private static final String SELECT_BY_CID_EMPLOYEE = SELECT_BY_CID + " AND c.kshstShaTransLabTimePK.sid IN :sid ";
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void add(DeforLaborTimeSha emplDeforLaborWorkingHour) {
		commandProxy().insert(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void update(DeforLaborTimeSha emplDeforLaborWorkingHour) {
		commandProxy().update(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void delete(String cid, String empId) {
		commandProxy().remove(KshmtLegaltimeDDefSya.class, new KshstShaTransLabTimePK(cid, empId));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<DeforLaborTimeSha> find(String cid, String empId) {
		Optional<KshmtLegaltimeDDefSya> optEntity = this.queryProxy().find(new KshstShaTransLabTimePK(cid, empId), KshmtLegaltimeDDefSya.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha trans lab time
	 */
	private KshmtLegaltimeDDefSya toEntity(DeforLaborTimeSha domain) {
		KshmtLegaltimeDDefSya entity = new KshmtLegaltimeDDefSya();

		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		entity.setKshstShaTransLabTimePK(new KshstShaTransLabTimePK(domain.getComId(), domain.getEmpId()));
		
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain spe defor labor time
	 */
	private DeforLaborTimeSha toDomain(KshmtLegaltimeDDefSya entity) {
		return DeforLaborTimeSha.of(entity.getKshstShaTransLabTimePK().getCid(),
				entity.getKshstShaTransLabTimePK().getSid(),
				new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime())), 
				new DailyUnit(new TimeOfDay(entity.getDailyTime())));
	}

	@Override
	public List<DeforLaborTimeSha> findDeforLaborTimeShaByCid(String cid) {
		List<KshmtLegaltimeDDefSya> entitys = this.queryProxy().query(SELECT_BY_CID, KshmtLegaltimeDDefSya.class)
				.setParameter("cid", cid).getList();
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}

	@Override
	public List<DeforLaborTimeSha> findList(String cid, List<String> empId) {
		if (empId.isEmpty())
			return Collections.emptyList();
		List<DeforLaborTimeSha> result = new ArrayList<>();
		CollectionUtil.split(empId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
			result.addAll(this.queryProxy().query(SELECT_BY_CID_EMPLOYEE, KshmtLegaltimeDDefSya.class)
					.setParameter("cid", cid)
					.setParameter("sid", subIdList)
					.getList(f -> toDomain(f)));
		});
		return result;
	}
}
