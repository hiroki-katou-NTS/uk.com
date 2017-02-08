/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.command.BaseInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.InsuranceBusinessTypeDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;

@Getter
@Setter
public class InsuranceBusinessTypeUpdateCommand extends BaseInsuranceCommand {

	/** The insurance business type. */
	private InsuranceBusinessTypeDto insuranceBusinessType;

	/** The company code. */
	private String companyCode;

	/**
	 * To domain.
	 *
	 * @return the list
	 */
	public List<InsuranceBusinessType> toDomain() {
		List<InsuranceBusinessType> lsInsuranceBusinessType = new ArrayList<>();
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz1St(), BusinessTypeEnum.Biz1St));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz2Nd(), BusinessTypeEnum.Biz2Nd));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz3Rd(), BusinessTypeEnum.Biz3Rd));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz4Th(), BusinessTypeEnum.Biz4Th));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz5Th(), BusinessTypeEnum.Biz5Th));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz6Th(), BusinessTypeEnum.Biz6Th));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz7Th(), BusinessTypeEnum.Biz7Th));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz8Th(), BusinessTypeEnum.Biz8Th));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz9Th(), BusinessTypeEnum.Biz9Th));
		lsInsuranceBusinessType.add(detaultInsuranceBusinessType(this.companyCode,
				this.insuranceBusinessType.getBizNameBiz10Th(), BusinessTypeEnum.Biz10Th));
		return lsInsuranceBusinessType;
	}
}
