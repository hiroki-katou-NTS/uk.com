/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.SequenceMasterRepository;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.CsqmtSequenceMaster;

/**
 * The Class JpaSequenceMasterRepository.
 */
@Stateless
public class JpaSequenceMasterRepository extends JpaRepository implements SequenceMasterRepository {

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
