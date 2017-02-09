/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.businesstype;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.BusinessName;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;

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
	public List<InsuranceBusinessType> findAll(String companyCode) {
		// TODO Auto-generated method stub
		return findAllDataDemo(companyCode);
	}

	@Override
	public InsuranceBusinessType findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Find all data demo.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	// DEMO
	public List<InsuranceBusinessType> findAllDataDemo(String companyCode) {
		// TODO Auto-generated method stub
		List<InsuranceBusinessType> lstInsuranceBusinessType = new ArrayList<InsuranceBusinessType>();
		lstInsuranceBusinessType.add(fromDomain("事業種類名1", BusinessTypeEnum.Biz1St, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名2", BusinessTypeEnum.Biz2Nd, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名3", BusinessTypeEnum.Biz3Rd, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名4", BusinessTypeEnum.Biz4Th, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名5", BusinessTypeEnum.Biz5Th, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名6", BusinessTypeEnum.Biz6Th, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名7", BusinessTypeEnum.Biz7Th, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名8", BusinessTypeEnum.Biz8Th, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名9", BusinessTypeEnum.Biz9Th, companyCode));
		lstInsuranceBusinessType.add(fromDomain("事業種類名10", BusinessTypeEnum.Biz10Th, companyCode));
		return lstInsuranceBusinessType;
	}

	/**
	 * From domain.
	 *
	 * @param bizName
	 *            the biz name
	 * @param bizOrder
	 *            the biz order
	 * @param companyCode
	 *            the company code
	 * @return the insurance business type
	 */
	public InsuranceBusinessType fromDomain(String bizName, BusinessTypeEnum bizOrder, String companyCode) {

		return new InsuranceBusinessType(new InsuranceBusinessTypeGetMemento() {

			@Override
			public Long getVersion() {
				// TODO Auto-generated method stub
				return 11L;
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return new CompanyCode(companyCode);
			}

			@Override
			public BusinessTypeEnum getBizOrder() {
				// TODO Auto-generated method stub
				return bizOrder;
			}

			@Override
			public BusinessName getBizName() {
				// TODO Auto-generated method stub
				return new BusinessName(bizName);
			}
		});
	}

}
