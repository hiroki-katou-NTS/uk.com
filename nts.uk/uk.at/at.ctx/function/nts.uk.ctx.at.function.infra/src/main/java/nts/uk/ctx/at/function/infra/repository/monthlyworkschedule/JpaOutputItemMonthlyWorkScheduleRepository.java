package nts.uk.ctx.at.function.infra.repository.monthlyworkschedule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkSchedule;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleRepository;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonAttenDisplay;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonAttenDisplayPK_;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonAttenDisplay_;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSche;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSchePK;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSchePK_;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSche_;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaOutputItemMonthlyWorkScheduleRepository.
 */
@Stateless
public class JpaOutputItemMonthlyWorkScheduleRepository extends JpaRepository
		implements OutputItemMonthlyWorkScheduleRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#findByCidAndCode(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public Optional<OutputItemMonthlyWorkSchedule> findByCidAndCode(String companyId, String code) {
		// TODO Auto-generated method stub
		KfnmtMonthlyWorkSchePK key = new KfnmtMonthlyWorkSchePK();
		key.setCid(companyId);
		key.setItemCd(code);
		return this.queryProxy().find(key, KfnmtMonthlyWorkSche.class).map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#findByCid(java.lang.String)
	 */
	@Override
	public List<OutputItemMonthlyWorkSchedule> findByCid(String companyId) {
		// TODO Auto-generated method stub
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create builder
		CriteriaBuilder builder = em.getCriteriaBuilder();

		// Create query
		CriteriaQuery<KfnmtMonthlyWorkSche> cq = builder.createQuery(KfnmtMonthlyWorkSche.class);

		// From table
		Root<KfnmtMonthlyWorkSche> root = cq.from(KfnmtMonthlyWorkSche.class);

		// Add where condition
		cq.where(builder.equal(root.get(KfnmtMonthlyWorkSche_.id).get(KfnmtMonthlyWorkSchePK_.cid), companyId));
		cq.orderBy(builder.asc(root.get(KfnmtMonthlyWorkSche_.id).get(KfnmtMonthlyWorkSchePK_.cid)));

		// Get results
		List<KfnmtMonthlyWorkSche> results = em.createQuery(cq).getResultList();

		// Check empty
		if (CollectionUtil.isEmpty(results)) {
			return Collections.emptyList();
		}

		return results.stream()
				.map(item -> new OutputItemMonthlyWorkSchedule(new JpaOutputItemMonthlyWorkScheduleGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#add(nts.uk.ctx.at.function.dom.
	 * monthlyworkschedule.OutputItemMonthlyWorkSchedule)
	 */
	@Override
	public void add(OutputItemMonthlyWorkSchedule domain) {
		// TODO Auto-generated method stub
		this.commandProxy().insert(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#update(nts.uk.ctx.at.function.dom
	 * .monthlyworkschedule.OutputItemMonthlyWorkSchedule)
	 */
	@Override
	public void update(OutputItemMonthlyWorkSchedule domain) {
		// TODO Auto-generated method stub
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// create delete
		CriteriaDelete<KfnmtMonAttenDisplay> delete = cb.createCriteriaDelete(KfnmtMonAttenDisplay.class);

		// set the root class
		Root<KfnmtMonAttenDisplay> root = delete.from(KfnmtMonAttenDisplay.class);

		// set where clause
		delete.where(
				cb.equal(root.get(KfnmtMonAttenDisplay_.id).get(KfnmtMonAttenDisplayPK_.cid), domain.getCompanyID()),
				cb.equal(root.get(KfnmtMonAttenDisplay_.id).get(KfnmtMonAttenDisplayPK_.itemCd),
						domain.getItemCode().v()));

		// perform update
		em.createQuery(delete).executeUpdate();

		this.commandProxy().update(this.toEntity(domain));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#delete(nts.uk.ctx.at.function.dom
	 * .monthlyworkschedule.OutputItemMonthlyWorkSchedule)
	 */
	@Override
	public void delete(OutputItemMonthlyWorkSchedule domain) {
		// TODO Auto-generated method stub
		this.commandProxy().remove(this.toEntity(domain));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#deleteByCidAndCode(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void deleteByCidAndCode(String companyId, String code) {
		// TODO Auto-generated method stub
		KfnmtMonthlyWorkSchePK primaryKey = new KfnmtMonthlyWorkSchePK();
		primaryKey.setCid(companyId);
		primaryKey.setItemCd(code);
		this.commandProxy().remove(KfnmtMonthlyWorkSche.class, primaryKey);
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the output item monthly work schedule
	 */
	private OutputItemMonthlyWorkSchedule toDomain(KfnmtMonthlyWorkSche entity) {
		return new OutputItemMonthlyWorkSchedule(new JpaOutputItemMonthlyWorkScheduleGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kfnmt monthly work sche
	 */
	private KfnmtMonthlyWorkSche toEntity(OutputItemMonthlyWorkSchedule domain) {
		KfnmtMonthlyWorkSche entity = new KfnmtMonthlyWorkSche();
		domain.saveToMemento(new JpaOutputItemMonthlyWorkScheduleSetMemento(entity));
		return entity;
	}

}
