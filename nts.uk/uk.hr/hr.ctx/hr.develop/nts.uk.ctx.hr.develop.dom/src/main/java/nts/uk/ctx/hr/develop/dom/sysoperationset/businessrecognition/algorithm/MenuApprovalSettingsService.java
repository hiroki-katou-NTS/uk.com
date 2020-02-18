package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettingsRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.BusinessApprovalSettingsDto;

@Stateless
public class MenuApprovalSettingsService {
	
	@Inject
	private MenuApprovalSettingsRepository repo;
	
	/** 業務承認設定を取得する */
	public List<BusinessApprovalSettingsDto> getBusinessApprovalSettings(String cid) {
		return repo.getBusinessApprovalSettings(cid);
	}
}
