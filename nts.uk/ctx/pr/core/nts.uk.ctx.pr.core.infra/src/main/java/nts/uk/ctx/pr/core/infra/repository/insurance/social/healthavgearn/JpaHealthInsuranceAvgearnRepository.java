package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

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
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearnPK;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearnPK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn_;

/**
 * The Class JpaHealthInsuranceAvgearnRepository.
 */
@Stateless
public class JpaHealthInsuranceAvgearnRepository extends JpaRepository implements HealthInsuranceAvgearnRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#add(nts.uk.ctx.pr.core.dom.insurance.
	 * social.healthavgearn.HealthInsuranceAvgearn)
	 */
	@Override
	public void add(HealthInsuranceAvgearn healthInsuranceAvgearn) {
		this.commandProxy().insert(toEntity(healthInsuranceAvgearn));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#update(nts.uk.ctx.pr.core.dom.insurance.
	 * social.healthavgearn.HealthInsuranceAvgearn)
	 */
	@Override
	public void update(HealthInsuranceAvgearn healthInsuranceAvgearn) {
		this.commandProxy().update(toEntity(healthInsuranceAvgearn));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO: can truyen vao hisID, ccd, officeCd, levelCode
		this.commandProxy().remove(QismtHealthInsuAvgearnPK.class, new QismtHealthInsuAvgearnPK());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#findById(java.lang.String)
	 */
	@Override
	public List<HealthInsuranceAvgearn> findById(String historyId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<QismtHealthInsuAvgearn> cq = criteriaBuilder.createQuery(QismtHealthInsuAvgearn.class);
		Root<QismtHealthInsuAvgearn> root = cq.from(QismtHealthInsuAvgearn.class);
		cq.select(root);
		List<Predicate> listpredicate = new ArrayList<>();
		listpredicate.add(criteriaBuilder.equal(
				root.get(QismtHealthInsuAvgearn_.qismtHealthInsuAvgearnPK).get(QismtHealthInsuAvgearnPK_.histId),
				historyId));
		cq.where(listpredicate.toArray(new Predicate[] {}));
		TypedQuery<QismtHealthInsuAvgearn> query = em.createQuery(cq);
		List<HealthInsuranceAvgearn> listHealthInsuranceAvgearn = query.getResultList().stream()
				.map(entity -> toDomain(entity)).collect(Collectors.toList());
		// return listHealthInsuranceAvgearn;
		// still mock
		return getMockData();
	}

	/**
	 * Gets the mock data.
	 *
	 * @return the mock data
	 */
	public List<HealthInsuranceAvgearn> getMockData() {
		List<HealthInsuranceAvgearn> list = new ArrayList<HealthInsuranceAvgearn>();
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}

			@Override
			public Integer getLevelCode() {
				return 1;
			}

			@Override
			public String getHistoryId() {
				return "1";
			}

			@Override
			public HealthInsuranceAvgearnValue getCompanyAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}
		}));
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}

			@Override
			public Integer getLevelCode() {
				return 2;
			}

			@Override
			public String getHistoryId() {
				return "1";
			}

			@Override
			public HealthInsuranceAvgearnValue getCompanyAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}
		}));
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}

			@Override
			public Integer getLevelCode() {
				return 3;
			}

			@Override
			public String getHistoryId() {
				return "1";
			}

			@Override
			public HealthInsuranceAvgearnValue getCompanyAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}
		}));
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}

			@Override
			public Integer getLevelCode() {
				return 4;
			}

			@Override
			public String getHistoryId() {
				return "1";
			}

			@Override
			public HealthInsuranceAvgearnValue getCompanyAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}
		}));

		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}

			@Override
			public Integer getLevelCode() {
				return 5;
			}

			@Override
			public String getHistoryId() {
				return "1";
			}

			@Override
			public HealthInsuranceAvgearnValue getCompanyAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}
		}));
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}

			@Override
			public Integer getLevelCode() {
				return 6;
			}

			@Override
			public String getHistoryId() {
				return "1";
			}

			@Override
			public HealthInsuranceAvgearnValue getCompanyAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(BigDecimal.valueOf(1223)),
						new CommonAmount(BigDecimal.valueOf(4321)), new CommonAmount(BigDecimal.valueOf(54632)),
						new CommonAmount(BigDecimal.valueOf(9876)));
			}
		}));

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnRepository#find(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public Optional<HealthInsuranceAvgearn> find(String historyId, Integer levelCode) {
		// TODO: mock ccd & officeCd. change later
		return this.queryProxy()
				.find(new QismtHealthInsuAvgearnPK("0001", "1", historyId, BigDecimal.valueOf(levelCode)),
						QismtHealthInsuAvgearn.class)
				.map(c -> toDomain(c));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the health insurance avgearn
	 */
	private static HealthInsuranceAvgearn toDomain(QismtHealthInsuAvgearn entity) {
		HealthInsuranceAvgearn domain = new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(entity.getPHealthBasicMny()),
						new CommonAmount(entity.getPHealthGeneralMny()),
						new CommonAmount(entity.getPHealthNursingMny()),
						new CommonAmount(entity.getPHealthSpecificMny()));
			}

			@Override
			public Integer getLevelCode() {
				return entity.getQismtHealthInsuAvgearnPK().getHealthInsuGrade().intValue();
			}

			@Override
			public String getHistoryId() {
				return entity.getQismtHealthInsuAvgearnPK().getHistId();
			}

			@Override
			public HealthInsuranceAvgearnValue getCompanyAvg() {
				return new HealthInsuranceAvgearnValue(new CommonAmount(entity.getCHealthBasicMny()),
						new CommonAmount(entity.getCHealthGeneralMny()),
						new CommonAmount(entity.getCHealthNursingMny()),
						new CommonAmount(entity.getCHealthSpecificMny()));
			}
		});

		return domain;
	}

	/**
	 * To entity.
	 *
	 * @param healthInsuranceAvgearn
	 *            the health insurance avgearn
	 * @return the qismt health insu avgearn
	 */
	private QismtHealthInsuAvgearn toEntity(HealthInsuranceAvgearn healthInsuranceAvgearn) {
		QismtHealthInsuAvgearn entity = new QismtHealthInsuAvgearn();
		healthInsuranceAvgearn.saveToMemento(new JpaHealthInsuranceAvgearnSetMemento(entity));
		// mock data de tranh loi. 2 field nay bi thua?
		entity.setHealthInsuAvgEarn(BigDecimal.valueOf(5));
		entity.setHealthInsuUpperLimit(BigDecimal.valueOf(3));
		return entity;
	}

}
