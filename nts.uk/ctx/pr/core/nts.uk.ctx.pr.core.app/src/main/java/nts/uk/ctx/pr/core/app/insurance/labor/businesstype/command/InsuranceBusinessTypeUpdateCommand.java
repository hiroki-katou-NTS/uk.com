/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.command.BaseInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.InsuranceBusinessTypeUpdateDto;
import nts.uk.ctx.pr.core.dom.insurance.BusinessName;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeGetMemento;

@Getter
@Setter
public class InsuranceBusinessTypeUpdateCommand extends BaseInsuranceCommand {

	/** The insurance business type. */
	private InsuranceBusinessTypeUpdateDto insuranceBusinessType;

	/** The company code. */
	private String companyCode;

	/**
	 * To domain.
	 *
	 * @return the list
	 */
	public List<InsuranceBusinessType> toDomain() {
		List<InsuranceBusinessType> lsInsuranceBusinessType = new ArrayList<>();
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz1St, this.insuranceBusinessType.getBizNameBiz1St()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz2Nd, this.insuranceBusinessType.getBizNameBiz2Nd()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz3Rd, this.insuranceBusinessType.getBizNameBiz3Rd()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz4Th, this.insuranceBusinessType.getBizNameBiz4Th()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz5Th, this.insuranceBusinessType.getBizNameBiz5Th()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz6Th, this.insuranceBusinessType.getBizNameBiz6Th()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz7Th, this.insuranceBusinessType.getBizNameBiz7Th()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz8Th, this.insuranceBusinessType.getBizNameBiz8Th()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz9Th, this.insuranceBusinessType.getBizNameBiz9Th()));
		lsInsuranceBusinessType.add(
				convertInsuranceBusinessType(BusinessTypeEnum.Biz10Th, this.insuranceBusinessType.getBizNameBiz10Th()));
		return lsInsuranceBusinessType;
	}

	public InsuranceBusinessType convertInsuranceBusinessType(BusinessTypeEnum businessTypeEnum, String bizName) {
		InsuranceBusinessTypeUpdateCommand command = this;
		return new InsuranceBusinessType(new InsuranceBusinessTypeGetMemento() {

			@Override
			public Long getVersion() {
				// TODO Auto-generated method stub
				return command.insuranceBusinessType.getVersion();
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return new CompanyCode(command.companyCode);
			}

			@Override
			public BusinessTypeEnum getBizOrder() {
				// TODO Auto-generated method stub
				return businessTypeEnum;
			}

			@Override
			public BusinessName getBizName() {
				// TODO Auto-generated method stub
				return new BusinessName(bizName);
			}
		});
	}
}
