package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition;

import java.util.List;

import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.BusinessApprovalSettingsDto;

public interface MenuApprovalSettingsRepository {

	List<BusinessApprovalSettingsDto> getBusinessApprovalSettings(String cid);
	
	void update(List<BusinessApprovalSettingsDto> domain);
}
