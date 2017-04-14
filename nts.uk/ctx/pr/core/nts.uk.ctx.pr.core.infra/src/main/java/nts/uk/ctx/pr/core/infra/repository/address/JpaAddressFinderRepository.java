/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.address;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.app.address.find.AddressFinder;
import nts.uk.ctx.pr.core.app.address.find.AddressSelection;
import nts.uk.ctx.pr.core.infra.entity.address.XxxxxAddress;
import nts.uk.ctx.pr.core.infra.entity.address.XxxxxAddress_;

/**
 * The Class JpaAddressFinderRepository.
 */

@Stateless
public class JpaAddressFinderRepository extends JpaRepository implements AddressFinder {

	/** The Constant MAX_RESULT. */
	public static final int MAX_RESULT = 100;

	/** The Constant FIRST_RESULT. */
	public static final int FIRST_RESULT = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.app.address.find.AddressFinder#
	 * findAddressSelectionList(java.lang.String)
	 */
	@Override
	public List<AddressSelection> findAddressSelectionList(String zipCode) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call XXXXX_ADDRESS (XxxxxAddress SQL)
		CriteriaQuery<XxxxxAddress> cq = criteriaBuilder.createQuery(XxxxxAddress.class);

		// root data
		Root<XxxxxAddress> root = cq.from(XxxxxAddress.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// like zipcode
		lstpredicateWhere.add(criteriaBuilder.like(root.get(XxxxxAddress_.zipCode), zipCode + "%"));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<XxxxxAddress> query = em.createQuery(cq).setFirstResult(FIRST_RESULT)
			.setMaxResults(MAX_RESULT);

		// exclude select
		List<AddressSelection> lstAddressSelection = query.getResultList().stream().map(item -> {
			AddressSelection addressSelection = new AddressSelection();
			addressSelection.setCity(item.getCity());
			addressSelection.setId(String.valueOf(item.getId()));
			addressSelection.setPrefecture(item.getPrefecture());
			addressSelection.setTown(item.getTown());
			addressSelection.setZipCode(item.getZipCode());
			return addressSelection;

		}).collect(Collectors.toList());

		return lstAddressSelection;
	}

}
