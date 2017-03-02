/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.businesstype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.businesstype.QismtBusinessType;

/**
 * The Class JpaAccidentInsuranceRateRepository.
 */
@Stateless
public class JpaInsuranceBusinessTypeRepository extends JpaRepository implements InsuranceBusinessTypeRepository {

	@Override
	public void add(InsuranceBusinessType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InsuranceBusinessType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<InsuranceBusinessType> findAll(CompanyCode companyCode) {
		Optional<QismtBusinessType> optionalQismtBusinessType = this.queryProxy().find(companyCode.v(),
				QismtBusinessType.class);
		if (optionalQismtBusinessType.isPresent()) {
			return toDomain(optionalQismtBusinessType.get());
		}
		return null;
	}

	@Override
	public InsuranceBusinessType findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.
	 * InsuranceBusinessTypeRepository#update(java.util.List)
	 */
	@Override
	public void update(List<InsuranceBusinessType> lstInsuranceBusinessType) {
		this.commandProxy().update(toEntity(lstInsuranceBusinessType));
	}

	/**
	 * To entity.
	 *
	 * @param lstInsuranceBusinessType
	 *            the lst insurance business type
	 * @return the qismt business type
	 */
	public QismtBusinessType toEntity(List<InsuranceBusinessType> lstInsuranceBusinessType) {
		QismtBusinessType entity = new QismtBusinessType();
		for (InsuranceBusinessType itemInsuranceBusinessType : lstInsuranceBusinessType) {
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz1St)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz1StSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz2Nd)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz2NdSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz3Rd)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz3RdSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz4Th)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz4ThSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz5Th)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz5ThSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz6Th)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz6ThSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz7Th)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz7ThSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz8Th)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz8ThSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz9Th)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz9ThSetMemento(entity));
			}
			if (itemInsuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz10Th)) {
				itemInsuranceBusinessType.saveToMemento(new JpaInsuranceBusinessTypeBiz10ThSetMemento(entity));
			}
		}
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the list
	 */
	public List<InsuranceBusinessType> toDomain(QismtBusinessType entity) {
		List<InsuranceBusinessType> lstInsuranceBusinessType = new ArrayList<>();
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz1StGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz2NdGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz3RdGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz4ThGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz5ThGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz6ThGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz7ThGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz8ThGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz9ThGetMemento(entity)));
		lstInsuranceBusinessType.add(new InsuranceBusinessType(new JpaInsuranceBusinessTypeBiz10ThGetMemento(entity)));
		return lstInsuranceBusinessType;
	}

}
