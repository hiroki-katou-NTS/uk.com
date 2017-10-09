/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle_old;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.SequenceMasterRepository;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.CsqmtSequenceMaster;

/**
 * The Class JpaSequenceMasterRepository.
 */
@Stateless
public class JpaSequenceMasterRepositoryOld extends JpaRepository implements SequenceMasterRepository {

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the csqmt sequence master
	 */
	private CsqmtSequenceMaster toEntity(SequenceMaster domain) {
		CsqmtSequenceMaster entity = new CsqmtSequenceMaster();
		domain.saveToMemento(new JpaSequenceMasterSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the sequence master
	 */
	private SequenceMaster toDomain(CsqmtSequenceMaster entity) {
		return new SequenceMaster(new JpaSequenceMasterGetMemento(entity));
	}

}
