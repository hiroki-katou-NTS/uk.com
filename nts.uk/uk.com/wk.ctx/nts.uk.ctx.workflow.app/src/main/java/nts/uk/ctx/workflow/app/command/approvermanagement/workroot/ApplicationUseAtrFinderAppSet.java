package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;


import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.RegisterQCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.SettingUseUnitCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.StartQCommand;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
import nts.uk.ctx.workflow.dom.service.ApprovalSettingService;
import nts.uk.ctx.workflow.dom.service.SettingUseUnitRegisterService;
import nts.uk.ctx.workflow.dom.service.SettingUseUnitService;
import nts.uk.ctx.workflow.dom.service.SettingUseUnitServiceImp;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;
/**
 * Refactor5 
 * change Setting kaf022 to workflow
 * @author hoangnd
 *
 */
@Stateless
public class ApplicationUseAtrFinderAppSet {
	@Inject 
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Inject
	private SettingUseUnitService settingUseUnitService;
	
	@Inject 
	private SettingUseUnitRegisterService settingUseUnitRegisterService;
	
	@Inject
	private ApprovalSettingService approvalSettingService;
	
	public ApproverRegisterSetDto getAppSet(String companyId){
		Optional<ApprovalSetting> approvalSetting = approvalSettingRepository.getApprovalByComId(companyId);
		if(approvalSetting.isPresent()) {
			ApproverRegisterSet approverRegsterSet = approvalSetting.get().getApproverRegsterSet();
			return ApproverRegisterSetDto.fromDomain(approverRegsterSet);
		}
		return new ApproverRegisterSetDto(0, 0, 0);
	}
	public SettingUseUnitDto getStartQ(StartQCommand command) {
		SettingUseUnitOutput setting = settingUseUnitService.start(command.companyId, command.systemAtr);
		
		return SettingUseUnitDto.fromDomain(setting);
	}
	
	public ApproverRegisterSetDto getStartM(StartQCommand command) {
		
		return ApproverRegisterSetDto.fromDomain(approvalSettingService.getSettingUseUnit(command.companyId, command.systemAtr));
	}
	
	public void checkRegisterQ(RegisterQCommand command) {
		settingUseUnitService.checkBeforeRegister(
				EnumAdaptor.valueOf(BooleanUtils.toInteger(command.companyUnit), UseClassification.class), 
				EnumAdaptor.valueOf(BooleanUtils.toInteger(command.workplaceUnit), UseClassification.class), 
				EnumAdaptor.valueOf(BooleanUtils.toInteger(command.employeeUnit), UseClassification.class));
	}
	
	public void registerQ(SettingUseUnitCommand command) {
		SettingUseUnitOutput setting = command.toDomain();
		// this is not description in excel 
		Integer systemAtr = SettingUseUnitServiceImp.EMPLOYMENT;
		if (setting.getHrApprovalRouteSetting().isPresent()) {
			systemAtr = SettingUseUnitServiceImp.HUMAN_RESOURCE;
		}
		settingUseUnitRegisterService.register(systemAtr, setting);
	}
	
}
