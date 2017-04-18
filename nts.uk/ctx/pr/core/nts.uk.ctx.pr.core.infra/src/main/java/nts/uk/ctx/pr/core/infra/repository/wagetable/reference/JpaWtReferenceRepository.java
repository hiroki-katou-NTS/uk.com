/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.reference;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRefItem;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository;

/**
 * The Class JpaWtReferenceRepository.
 */
@Stateless
public class JpaWtReferenceRepository extends JpaRepository implements WtReferenceRepository {

	/** The comma. */
	private final String comma = ",";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository#
	 * getCodeRefItem(nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRef)
	 */
	@Override
	public List<WtCodeRefItem> getCodeRefItem(WtCodeRef codeRef, Object... params) {
		// Create query string.
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("SELECT ");
		strBuilder.append(codeRef.getWagePersonField());
		strBuilder.append(comma);
		strBuilder.append(codeRef.getWagePersonField());
		strBuilder.append(" FROM ");
		strBuilder.append(codeRef.getWagePersonTable());

		// Get entity manager
		EntityManager em = this.getEntityManager();

		TypedQuery<Object[]> query = em.createQuery(strBuilder.toString(), Object[].class);

		List<Object[]> results = query.getResultList();

		List<WtCodeRefItem> codeRefItems = results.stream()
				.map(result -> new WtCodeRefItem((String) result[0], (String) result[1]))
				.collect(Collectors.toList());

		return codeRefItems;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository#
	 * getMasterRefItem(nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRef)
	 */
	@Override
	public List<WtCodeRefItem> getMasterRefItem(WtMasterRef masterRef, Object... params) {
		// Create query string.
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("SELECT ");
		strBuilder.append(masterRef.getWageRefField());
		strBuilder.append(comma);
		strBuilder.append(masterRef.getWageRefDispField());
		strBuilder.append(" FROM ");
		strBuilder.append(masterRef.getWageRefTable());
		strBuilder.append(" WHERE ");
		strBuilder.append(masterRef.getWageRefQuery());

		// Get entity manager
		EntityManager em = this.getEntityManager();

		TypedQuery<Object[]> query = em.createQuery(strBuilder.toString(), Object[].class);

		List<Object[]> results = query.getResultList();

		List<WtCodeRefItem> codeRefItems = results.stream()
				.map(result -> new WtCodeRefItem((String) result[0], (String) result[1]))
				.collect(Collectors.toList());

		return codeRefItems;
	}

}
