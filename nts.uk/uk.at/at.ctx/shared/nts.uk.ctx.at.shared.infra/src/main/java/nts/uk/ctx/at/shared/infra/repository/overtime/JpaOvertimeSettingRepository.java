/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeRepository;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeSetting;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.overtime.KshstOverTime;
import nts.uk.ctx.at.shared.infra.entity.overtime.KshstOverTimeSet;

/**
 * The Class JpaOvertimeSettingRepository.
 */
@Stateless
public class JpaOvertimeSettingRepository extends JpaRepository
		implements OvertimeSettingRepository {

	/** The repository. */
	@Inject
	private OvertimeRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingRepository#findById(java
	 * .lang.String)
	 */
	@Override
	public Optional<OvertimeSetting> findById(String companyId) {
		Optional<KshstOverTimeSet> entity = this.queryProxy().find(companyId,
				KshstOverTimeSet.class);
		List<Overtime> domainOvertime = this.repository.findAll(companyId);

		List<KshstOverTime> entityOvertime = domainOvertime.stream().map(domain -> {
			KshstOverTime entityItem = new KshstOverTime();
			domain.saveToMemento(new JpaOvertimeSetMemento(entityItem, companyId));
			return entityItem;
		}).collect(Collectors.toList());

		if (entity.isPresent()) {
			return Optional.ofNullable(this.toDomain(entity.get(), entityOvertime));
		}
		return Optional.ofNullable(this.toDomain(new KshstOverTimeSet(), entityOvertime));
	}

	private OvertimeSetting toDomain(KshstOverTimeSet entity, List<KshstOverTime> entityOvertime) {
		return new OvertimeSetting(new JpaOvertimeSettingGetMemento(entity, entityOvertime));
	}

}
