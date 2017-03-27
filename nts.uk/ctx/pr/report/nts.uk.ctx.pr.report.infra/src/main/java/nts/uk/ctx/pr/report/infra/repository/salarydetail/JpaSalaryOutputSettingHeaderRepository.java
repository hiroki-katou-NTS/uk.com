/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.report.app.salarydetail.find.SalaryOutputSettingHeaderRepository;
import nts.uk.ctx.pr.report.app.salarydetail.find.dto.SalaryOutputSettingHeaderDto;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHead;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHeadPK;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHeadPK_;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHead_;

/**
 * The Class JpaSalaryOutputSettingHeaderRepository.
 */
@Stateless
public class JpaSalaryOutputSettingHeaderRepository extends JpaRepository
		implements SalaryOutputSettingHeaderRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.app.salarydetail.find.
	 * SalaryOutputSettingHeaderRepository#findAll(java.lang.String)
	 */
	@Override
	public List<SalaryOutputSettingHeaderDto> findAll(String companyCode) {
		EntityManager em = this.getEntityManager();

		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<?> cq = cb.createQuery();
		Root<QlsptPaylstFormHead> root = cq.from(QlsptPaylstFormHead.class);

		// Add codition.
		Path<QlsptPaylstFormHeadPK> pkPath = root.get(QlsptPaylstFormHead_.qlsptPaylstFormHeadPK);
		cq.multiselect(pkPath.get(QlsptPaylstFormHeadPK_.formCd), root.get(QlsptPaylstFormHead_.formName))
				.where(cb.equal(pkPath.get(QlsptPaylstFormHeadPK_.ccd), companyCode))
				.orderBy(cb.asc(pkPath.get(QlsptPaylstFormHeadPK_.formCd)));

		// Query.
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = (List<Object[]>) em.createQuery(cq).getResultList();

		// Return.
		if (CollectionUtil.isEmpty(resultList)) {
			return new ArrayList<>();
		}
		return resultList.stream().map(res -> {
			return SalaryOutputSettingHeaderDto.builder().code((String) res[0]).name((String) res[1]).build();
		}).collect(Collectors.toList());
	}

}
