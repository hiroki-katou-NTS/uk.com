/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype.find.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;

/**
 * Instantiates a new insurance business type find out dto.
 */
@Data
public class InsuranceBusinessTypeFindOutDto {

	/** The biz name biz 1 st. */
	private String bizNameBiz1St;

	/** The biz name biz 2 nd. */
	private String bizNameBiz2Nd;

	/** The biz name biz 3 rd. */
	private String bizNameBiz3Rd;

	/** The biz name biz 4 th. */
	private String bizNameBiz4Th;

	/** The biz name biz 5 th. */
	private String bizNameBiz5Th;

	/** The biz name biz 6 th. */
	private String bizNameBiz6Th;

	/** The biz name biz 7 th. */
	private String bizNameBiz7Th;

	/** The biz name biz 8 th. */
	private String bizNameBiz8Th;

	/** The biz name biz 9 th. */
	private String bizNameBiz9Th;

	/** The biz name biz 10 th. */
	private String bizNameBiz10Th;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the insurance business type find out dto
	 */
	public static InsuranceBusinessTypeFindOutDto fromDomain(List<InsuranceBusinessType> domain) {
		InsuranceBusinessTypeFindOutDto insuranceBusinessTypeFindOutDto = new InsuranceBusinessTypeFindOutDto();
		for (InsuranceBusinessType insuranceBusinessType : domain) {
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz1St)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz1St(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz2Nd)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz2Nd(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz3Rd)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz3Rd(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz4Th)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz4Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz5Th)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz5Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz6Th)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz6Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz7Th)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz7Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz8Th)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz8Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz9Th)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz9Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz10Th)) {
				insuranceBusinessTypeFindOutDto.setBizNameBiz10Th(insuranceBusinessType.getBizName().v());
			}
		}
		return insuranceBusinessTypeFindOutDto;
	}
}
