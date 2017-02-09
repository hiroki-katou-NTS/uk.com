/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceBusinessTypeUpdateDto {

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

	/** The version. */
	private long version;

	public static InsuranceBusinessTypeUpdateDto fromDomain(List<InsuranceBusinessType> domain) {
		InsuranceBusinessTypeUpdateDto insuranceBusinessTypeUpdateDto = new InsuranceBusinessTypeUpdateDto();
		for (InsuranceBusinessType insuranceBusinessType : domain) {
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz1St)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz1St(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz2Nd)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz2Nd(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz3Rd)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz3Rd(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz4Th)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz4Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz5Th)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz5Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz6Th)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz6Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz7Th)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz7Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz8Th)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz8Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz9Th)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz9Th(insuranceBusinessType.getBizName().v());
			}
			if (insuranceBusinessType.getBizOrder().equals(BusinessTypeEnum.Biz10Th)) {
				insuranceBusinessTypeUpdateDto.setBizNameBiz10Th(insuranceBusinessType.getBizName().v());
			}
		}
		return insuranceBusinessTypeUpdateDto;
	}
}
