package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.paymentdata.paymentsetting.CompanyPaymentSetting;

public interface CompanyPaymentSettingRepository {
	Optional<CompanyPaymentSetting> find(String companyCode, int startDate);	
}
