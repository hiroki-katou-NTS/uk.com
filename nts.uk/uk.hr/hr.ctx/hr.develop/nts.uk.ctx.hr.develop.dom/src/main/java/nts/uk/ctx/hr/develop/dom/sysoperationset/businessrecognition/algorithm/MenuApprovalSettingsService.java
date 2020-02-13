package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettingsRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.BusinessApprovalSettingsDto;

public class MenuApprovalSettingsService {
	
	@Inject
	private MenuApprovalSettingsRepository repo;
	
	/**
	 * 業務承認設定を取得する
	 */
	public Optional<BusinessApprovalSettingsDto> getBusinessApprovalSettings(String cid) {
		return repo.getBusinessApprovalSettings(cid);
	}
}
