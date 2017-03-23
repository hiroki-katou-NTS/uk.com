package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionavgearn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearnPK;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearnPK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn_;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate.QismtPensionRate;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate.QismtPensionRatePK;

/**
 * The Class JpaPensionAvgearnRepository.
 */
@Stateless
public class JpaPensionAvgearnRepository extends JpaRepository implements PensionAvgearnRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnRepository#update(nts.uk.ctx.pr.core.dom.insurance.social.
	 * pensionavgearn.PensionAvgearn)
	 */
	@Override
	public void update(List<PensionAvgearn> pensionAvgearns, String ccd, String officeCd) {
		// Find PensionRate
		QismtPensionRate pensionRate = this.queryProxy()
				.find(new QismtPensionRatePK(ccd, officeCd, pensionAvgearns.get(0).getHistoryId()),
						QismtPensionRate.class)
				.get();

		// Set Updated pensionAvgearnList
		pensionRate.setQismtPensionAvgearnList(
				pensionAvgearns.stream().map(domain -> toEntity(domain, ccd, officeCd)).collect(Collectors.toList()));

		// Update PensionRate
		this.commandProxy().update(pensionRate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnRepository#remove(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.Integer)
	 */
	@Override
	public void remove(String histId, String ccd, String officeCd, Integer pensionGrade) {
		this.commandProxy().remove(QismtPensionAvgearn.class,
				new QismtPensionAvgearnPK(ccd, officeCd, histId, BigDecimal.valueOf(pensionGrade)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnRepository#find(java.lang.String)
	 */
	@Override
	public List<PensionAvgearn> find(String historyId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtPensionAvgearn> cq = criteriaBuilder.createQuery(QismtPensionAvgearn.class);
		Root<QismtPensionAvgearn> root = cq.from(QismtPensionAvgearn.class);
		cq.select(root);
		List<Predicate> listpredicate = new ArrayList<>();
		listpredicate.add(criteriaBuilder.equal(
				root.get(QismtPensionAvgearn_.qismtPensionAvgearnPK).get(QismtPensionAvgearnPK_.histId), historyId));
		cq.where(listpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtPensionAvgearn> query = em.createQuery(cq);
		List<PensionAvgearn> listPensionAvgearn = query.getResultList().stream().map(entity -> toDomain(entity))
				.collect(Collectors.toList());
		return listPensionAvgearn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnRepository#find(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.Integer)
	 */
	@Override
	public Optional<PensionAvgearn> find(String histId, String ccd, String officeCd, Integer pensionGrade) {
		return this.queryProxy()
				.find(new QismtPensionAvgearnPK(ccd, officeCd, histId, BigDecimal.valueOf(pensionGrade)),
						QismtPensionAvgearn.class)
				.map(entity -> toDomain(entity));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the pension avgearn
	 */
	private static PensionAvgearn toDomain(QismtPensionAvgearn entity) {
		PensionAvgearn domain = new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return new PensionAvgearnValue(new CommonAmount(entity.getPPensionMaleMny()),
						new CommonAmount(entity.getPPensionFemMny()), new CommonAmount(entity.getPPensionMinerMny()));
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(entity.getPFundExemptMaleMny()),
						new CommonAmount(entity.getPFundExemptFemMny()),
						new CommonAmount(entity.getPFundExemptMinerMny()));
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return new PensionAvgearnValue(new CommonAmount(entity.getPFundMaleMny()),
						new CommonAmount(entity.getPFundFemMny()), new CommonAmount(entity.getPFundMinerMny()));
			}

			@Override
			public Integer getLevelCode() {
				return entity.getQismtPensionAvgearnPK().getPensionGrade().intValue();
			}

			@Override
			public String getHistoryId() {
				return entity.getQismtPensionAvgearnPK().getHistId();
			}

			@Override
			public PensionAvgearnValue getCompanyPension() {
				return new PensionAvgearnValue(new CommonAmount(entity.getCPensionMaleMny()),
						new CommonAmount(entity.getCPensionFemMny()), new CommonAmount(entity.getCPensionMinerMny()));
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(entity.getCFundExemptMaleMny()),
						new CommonAmount(entity.getCFundExemptFemMny()),
						new CommonAmount(entity.getCFundExemptMinerMny()));
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return new PensionAvgearnValue(new CommonAmount(entity.getCFundMaleMny()),
						new CommonAmount(entity.getCFundFemMny()), new CommonAmount(entity.getCFundMinerMny()));
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(entity.getChildContributionMny());
			}
		});
		return domain;
	}

	/**
	 * To entity.
	 *
	 * @param pensionAvgearn
	 *            the pension avgearn
	 * @return the qismt pension avgearn
	 */
	private QismtPensionAvgearn toEntity(PensionAvgearn pensionAvgearn, String ccd, String officeCd) {
		QismtPensionAvgearn entity = new QismtPensionAvgearn();
		pensionAvgearn.saveToMemento(new JpaPensionAvgearnSetMemento(entity));
		// thua truong nay? phai fake data.
		entity.setPensionAvgEarn(BigDecimal.valueOf(5));
		entity.setPensionUpperLimit(BigDecimal.valueOf(5));
		QismtPensionAvgearnPK pk = entity.getQismtPensionAvgearnPK();

		// Set company code & office code
		pk.setCcd(ccd);
		pk.setSiOfficeCd(officeCd);
		return entity;
	}

}
