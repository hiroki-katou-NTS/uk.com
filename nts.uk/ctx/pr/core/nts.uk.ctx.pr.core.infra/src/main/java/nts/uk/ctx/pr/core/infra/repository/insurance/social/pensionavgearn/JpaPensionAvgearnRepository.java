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

@Stateless
public class JpaPensionAvgearnRepository extends JpaRepository implements PensionAvgearnRepository {

	@Override
	public void add(PensionAvgearn pensionAvgearn) {
		this.commandProxy().insert(toEntity(pensionAvgearn));

	}

	@Override
	public void update(PensionAvgearn pensionAvgearn) {
		this.commandProxy().update(toEntity(pensionAvgearn));
	}

	@Override
	public void remove(String id) {
		// TODO: can truyen vao hisID, ccd, officeCd, levelCode
		this.commandProxy().remove(QismtPensionAvgearn.class, new QismtPensionAvgearnPK());
	}

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

	@Override
	public Optional<PensionAvgearn> find(String historyId, Integer levelCode) {
		// fake ccd, officeCd, histId
		return this.queryProxy().find(new QismtPensionAvgearnPK("0001", "23", "11", BigDecimal.valueOf(levelCode)),
				QismtPensionAvgearn.class).map(entity -> toDomain(entity));
	}

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

	private QismtPensionAvgearn toEntity(PensionAvgearn pensionAvgearn) {
		QismtPensionAvgearn entity = new QismtPensionAvgearn();
		pensionAvgearn.saveToMemento(new JpaPensionAvgearnSetMemento(entity));
		//thua truong nay? phai fake data.
		entity.setPensionAvgEarn(BigDecimal.valueOf(5));
		entity.setPensionUpperLimit(BigDecimal.valueOf(5));
		return entity;
	}

}
