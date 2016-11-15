package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.paymentdata.paymentsetting.PersonalPaymentSetting;

public interface PersonalPaymentSettingRepository {

	Optional<PersonalPaymentSetting> find(String companyCode, Integer personId);	

}
