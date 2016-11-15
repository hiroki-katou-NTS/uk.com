package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;

public interface PersonalAllotSettingRepository {

	Optional<PersonalAllotSetting> find(String companyCode, String personId, int startDate);	

}
