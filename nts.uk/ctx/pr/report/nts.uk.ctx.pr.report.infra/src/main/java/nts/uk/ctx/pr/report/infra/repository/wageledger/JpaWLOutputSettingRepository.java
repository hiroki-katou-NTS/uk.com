/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLItemSubject;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLItemType;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetail;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetailPK;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetailPK_;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetail_;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHead;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHeadPK;
import nts.uk.ctx.pr.report.infra.repository.wageledger.memento.JpaWLOutputSettingGetMemento;
import nts.uk.ctx.pr.report.infra.repository.wageledger.memento.JpaWLOutputSettingSetMemento;

/**
 * The Class JpaWLOutputSettingRepository.
 */
@Stateless
public class JpaWLOutputSettingRepository extends JpaRepository implements WLOutputSettingRepository {

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSettingRepository
	 * #create(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSetting)
	 */
	@Override
	public void create(WLOutputSetting outputSetting) {
		QlsptLedgerFormHead entity = new QlsptLedgerFormHead();
		outputSetting.saveToMemento(new JpaWLOutputSettingSetMemento(entity));

		// Insert into db.
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSettingRepository
	 * #update(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSetting)
	 */
	@Override
	public void update(WLOutputSetting outputSetting) {
		Optional<QlsptLedgerFormHead> entity = this.queryProxy().find(
				new QlsptLedgerFormHeadPK(outputSetting.getCompanyCode().v(), outputSetting.getCode().v()),
				QlsptLedgerFormHead.class);
		if (entity.isPresent()) {
			QlsptLedgerFormHead realEntity = entity.get();
			// Convert to Memento;
			outputSetting.saveToMemento(new JpaWLOutputSettingSetMemento(realEntity));
			// Update to db.
			this.commandProxy().update(realEntity);
		}
		throw new RuntimeException("Cannot update entity not exist!");
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSettingRepository
	 * #remove(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSettingCode, nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public void remove(CompanyCode companyCode, WLOutputSettingCode code) {
		Optional<QlsptLedgerFormHead> entity = this.queryProxy()
				.find(new QlsptLedgerFormHeadPK(companyCode.v(), code.v()), QlsptLedgerFormHead.class);
		if (entity.isPresent()) {
			this.commandProxy().remove(entity.get());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSettingRepository#findByCode(nts.uk.ctx.pr.report.dom.wageledger.
	 * outputsetting.WLOutputSettingCode,
	 * nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public WLOutputSetting findByCode(CompanyCode companyCode, WLOutputSettingCode code) {
		Optional<QlsptLedgerFormHead> entity = this.queryProxy()
				.find(new QlsptLedgerFormHeadPK(companyCode.v(), code.v()), QlsptLedgerFormHead.class);

		if (entity.isPresent()) {
			// To Domain.
			return new WLOutputSetting(new JpaWLOutputSettingGetMemento(entity.get()));
		}

		throw new RuntimeException("Not found entity.");
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSettingRepository#isExist(nts.uk.ctx.pr.report.dom.wageledger.
	 * outputsetting.WLOutputSettingCode,
	 * nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public boolean isExist(CompanyCode companyCode, WLOutputSettingCode code) {
		Optional<QlsptLedgerFormHead> entity = this.queryProxy()
				.find(new QlsptLedgerFormHeadPK(companyCode.v(), code.v()), QlsptLedgerFormHead.class);

		if (!entity.isPresent()) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository
	 * #removeAggregateItemUsed(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLItemSubject)
	 */
	@Override
	public void removeAggregateItemUsed(WLItemSubject itemSubject) {
		EntityManager em = this.getEntityManager();
		
		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<QlsptLedgerFormDetail> cd = cb.createCriteriaDelete(QlsptLedgerFormDetail.class);
		Root<QlsptLedgerFormDetail> root = cd.from(QlsptLedgerFormDetail.class);
		Path<QlsptLedgerFormDetailPK> pkPath = root.get(QlsptLedgerFormDetail_.qlsptLedgerFormDetailPK);
		
		// Create condition.
		List<Predicate> conditions = new ArrayList<>();
		conditions.add(cb.equal(pkPath.get(QlsptLedgerFormDetailPK_.ccd), itemSubject.getCompanyCode().v()));
		conditions.add(cb.equal(pkPath.get(QlsptLedgerFormDetailPK_.formCd), itemSubject.getCode().v()));
		conditions.add(cb.equal(pkPath.get(QlsptLedgerFormDetailPK_.aggregateAtr), WLItemType.Aggregate.value));
		conditions.add(cb.equal(pkPath.get(QlsptLedgerFormDetailPK_.ctgAtr), itemSubject.getCategory().value));
		conditions.add(cb.equal(pkPath.get(QlsptLedgerFormDetailPK_.payBonusAtr), itemSubject.getPaymentType().value));
		
		// Excute.
		cd.where(conditions.toArray(new Predicate[conditions.size()]));
		em.createQuery(cd).executeUpdate();
	}

}
