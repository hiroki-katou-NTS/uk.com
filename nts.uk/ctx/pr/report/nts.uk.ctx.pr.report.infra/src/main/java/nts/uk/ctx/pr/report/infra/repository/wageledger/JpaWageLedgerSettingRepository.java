/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.pr.report.app.wageledger.find.WageLedgerSettingRepository;
import nts.uk.ctx.pr.report.app.wageledger.find.dto.HeaderSettingDto;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHead;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHeadPK;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHeadPK_;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHead_;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHead;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHeadPK;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHeadPK_;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHead_;

/**
 * The Class JpaWageLedgerSettingRepository.
 */
@Stateless
public class JpaWageLedgerSettingRepository extends JpaRepository implements WageLedgerSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.app.wageledger.find.WageLedgerSettingRepository#
	 * findHeaderOutputSettings(nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public List<HeaderSettingDto> findHeaderOutputSettings(CompanyCode companyCode) {
		EntityManager em = this.getEntityManager();

		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<?> cq = cb.createQuery();
		Root<QlsptLedgerFormHead> root = cq.from(QlsptLedgerFormHead.class);

		// Add codition.
		Path<QlsptLedgerFormHeadPK> pkPath = root.get(QlsptLedgerFormHead_.qlsptLedgerFormHeadPK);
		cq.multiselect(pkPath.get(QlsptLedgerFormHeadPK_.formCd),
				root.get(QlsptLedgerFormHead_.formName))
				.where(cb.equal(pkPath.get(QlsptLedgerFormHeadPK_.ccd), companyCode.v()))
				.orderBy(cb.asc(pkPath.get(QlsptLedgerFormHeadPK_.formCd)));

		// Query.
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = (List<Object[]>) em.createQuery(cq).getResultList();
		
		// Return.
		if (ListUtil.isEmpty(resultList)) {
			return new ArrayList<>();
		}
		return resultList.stream().map(res -> {
			return HeaderSettingDto.builder()
					.code((String) res[0])
					.name((String) res[1])
					.build();
		}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.app.wageledger.find.WageLedgerSettingRepository#
	 * findHeaderAggregateItemsByCategory(nts.uk.ctx.pr.report.dom.company.CompanyCode,
	 * nts.uk.ctx.pr.report.dom.wageledger.WLCategory, nts.uk.ctx.pr.report.dom.wageledger.PaymentType)
	 */
	@Override
	public List<HeaderSettingDto> findHeaderAggregateItemsByCategory(CompanyCode companyCode, WLCategory category,
			PaymentType paymentType) {
		EntityManager em = this.getEntityManager();

		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<?> cq = cb.createQuery();
		Root<QlsptLedgerAggreHead> root = cq.from(QlsptLedgerAggreHead.class);
		
		// Create condition list.
		Path<QlsptLedgerAggreHeadPK> pkPath = root.get(QlsptLedgerAggreHead_.qlsptLedgerAggreHeadPK);
		List<Predicate> conditions = new ArrayList<>();
		conditions.add(cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.ccd), companyCode.v()));
		if (category != null) {
			conditions.add(cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.ctgAtr), category.value));
		}
		if (paymentType != null) {
			conditions.add(cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.payBonusAtr), paymentType.value));
		}
		
		// Create select.
		cq.multiselect(pkPath.get(QlsptLedgerAggreHeadPK_.aggregateCd), root.get(QlsptLedgerAggreHead_.aggregateName))
			.where(conditions.toArray(new Predicate[conditions.size()]))
			.orderBy(cb.asc(pkPath.get(QlsptLedgerAggreHeadPK_.aggregateCd)));
		
		// Query.
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = (List<Object[]>) em.createQuery(cq).getResultList();
		
		// Return.
		if (ListUtil.isEmpty(resultList)) {
			return new ArrayList<>();
		}
		return resultList.stream().map(res -> {
			return HeaderSettingDto.builder()
					.code((String) res[0])
					.name((String) res[1])
					.build();
		}).collect(Collectors.toList());
	}

}
