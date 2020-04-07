package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettingsRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.BusinessApprovalSettingsDto;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuInfoEx;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperationRepository;

@Stateless
public class MenuApprovalSettingsService {
	
	@Inject
	private MenuApprovalSettingsRepository repo;
	
	@Inject
	private MenuOperationRepository menuOperationRepository;
	
	/** 業務承認設定を取得する */
	public List<BusinessApprovalSettingsDto> getBusinessApprovalSettings(String cid) {
		return repo.getBusinessApprovalSettings(cid);
	}
	
	/** 業務承認するプログラムIDを取得する */
	public List<String> getProgramIdForBusinessApproval(String cId){
		List<MenuInfoEx> menuInfoEx = menuOperationRepository.findByApprUse(cId);
		return repo.getProgramId(cId, menuInfoEx.stream().map(c->c.getProgramId()).collect(Collectors.toList()));
	}
}
