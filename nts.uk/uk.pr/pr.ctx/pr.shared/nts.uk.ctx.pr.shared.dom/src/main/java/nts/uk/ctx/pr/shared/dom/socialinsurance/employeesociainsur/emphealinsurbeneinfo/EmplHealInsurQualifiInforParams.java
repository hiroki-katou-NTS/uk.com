package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;

@Value
public class EmplHealInsurQualifiInforParams {

	private String cid;

	private EmpHealthInsurBenefits itemAdded;
	
	private HealInsurNumberInfor hisItem;
	
	private EmplHealInsurQualifiInfor domain;

}
