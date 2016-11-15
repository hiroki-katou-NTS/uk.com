package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSetting;

public interface CompanyAllotSettingRepository {
	Optional<CompanyAllotSetting> find(String companyCode, int startDate);	
}
