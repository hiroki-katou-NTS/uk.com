/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHead;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHeadPK;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHeadPK_;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHead_;

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
	public void remove(WLOutputSettingCode code, CompanyCode companyCode) {
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
	public WLOutputSetting findByCode(WLOutputSettingCode code, CompanyCode companyCode) {
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
	public boolean isExist(WLOutputSettingCode code, CompanyCode companyCode) {
		Optional<QlsptLedgerFormHead> entity = this.queryProxy()
				.find(new QlsptLedgerFormHeadPK(companyCode.v(), code.v()), QlsptLedgerFormHead.class);

		if (!entity.isPresent()) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.
	 * WLOutputSettingRepository#findAll(nts.uk.ctx.pr.report.dom.company.
	 * CompanyCode, boolean)
	 */
	@Override
	public List<WLOutputSetting> findAll(CompanyCode companyCode, boolean isLoadHeaderDataOnly) {
		EntityManager em = this.getEntityManager();

		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QlsptLedgerFormHead> cq = cb.createQuery(QlsptLedgerFormHead.class);
		Root<QlsptLedgerFormHead> root = cq.from(QlsptLedgerFormHead.class);

		// Add codition.
		cq.select(root)
				.where(cb.equal(root.get(QlsptLedgerFormHead_.qlsptLedgerFormHeadPK).get(QlsptLedgerFormHeadPK_.ccd),
						companyCode.v()))
				.orderBy(cb
						.asc(root.get(QlsptLedgerFormHead_.qlsptLedgerFormHeadPK).get(QlsptLedgerFormHeadPK_.formCd)));

		// Query.
		List<QlsptLedgerFormHead> resultList = em.createQuery(cq).getResultList();

		if (ListUtil.isEmpty(resultList)) {
			return new ArrayList<>();
		}

		// Convert to Domain
		return resultList.stream().map(res -> {
			return new WLOutputSetting(new JpaWLOutputSettingGetMemento(res, isLoadHeaderDataOnly));
		}).collect(Collectors.toList());
	}

}
