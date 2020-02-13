package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition;

import java.util.Optional;

import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.BusinessApprovalSettingsDto;

public interface MenuApprovalSettingsRepository {

	Optional<BusinessApprovalSettingsDto> getBusinessApprovalSettings(String cid);

}
