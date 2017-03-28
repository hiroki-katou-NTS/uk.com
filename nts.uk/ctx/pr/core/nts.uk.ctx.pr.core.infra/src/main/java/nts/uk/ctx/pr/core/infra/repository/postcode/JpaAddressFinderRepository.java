/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.postcode;

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
import nts.uk.ctx.pr.core.infra.entity.postcode.CpcstPostcode;
import nts.uk.ctx.pr.core.infra.entity.postcode.CpcstPostcode_;

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
		CriteriaQuery<CpcstPostcode> cq = criteriaBuilder.createQuery(CpcstPostcode.class);

		// root data
		Root<CpcstPostcode> root = cq.from(CpcstPostcode.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// like zipcode
		lstpredicateWhere.add(criteriaBuilder.like(root.get(CpcstPostcode_.zipCode), zipCode + "%"));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<CpcstPostcode> query = em.createQuery(cq).setFirstResult(FIRST_RESULT)
			.setMaxResults(MAX_RESULT);

		// exclude select
		List<AddressSelection> lstAddressSelection = query.getResultList().stream().map(item -> {
			AddressSelection addressSelection = new AddressSelection();
			// TODO
//			addressSelection.setCity(item.getCity());
//			addressSelection.setId(String.valueOf(item.getId()));
//			addressSelection.setPrefecture(item.getPrefecture());
//			addressSelection.setTown(item.getTown());
//			addressSelection.setZipCode(item.getZipCode());
			return addressSelection;

		}).collect(Collectors.toList());

		return lstAddressSelection;
	}

}
