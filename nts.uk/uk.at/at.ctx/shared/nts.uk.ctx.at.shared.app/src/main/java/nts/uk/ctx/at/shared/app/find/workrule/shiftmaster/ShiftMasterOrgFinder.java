package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.WorkTimeSettingFinder;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetUsableShiftMasterService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetUsableShiftMasterService.Require;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 * 
 * @author anhdt
 *
 */
@Stateless
public class ShiftMasterOrgFinder {
	
	@Inject 
	private GetUsableShiftMasterService getShiftMasterSv;
	
	@Inject
	private ShiftMasterOrgRepository shiftMasterOrgRp;
	
	@Inject
	private ShiftMasterRepository shiftMasterRepo;
	
	@Inject
	private WorkTimeSettingFinder workTimeFinder;

	// 使用できるシフトマスタの勤務情報と補正済み所定時間帯を取得する
	public List<ShiftMasterDto> getShiftMastersByWorkPlace(String targetId, Integer targetUnit) {
		String companyId = AppContexts.user().companyId();
		Require require = new  RequireImpl(shiftMasterOrgRp, shiftMasterRepo);
		
		TargetOrgIdenInfor target = null;
		if(targetUnit != null && targetId != null) {
			TargetOrganizationUnit unit = EnumAdaptor.valueOf(targetUnit, TargetOrganizationUnit.class);
			target = new TargetOrgIdenInfor(unit, targetId, targetId);
		}
		
		@SuppressWarnings("static-access")
		List<ShiftMasterDto> shiftMasters = getShiftMasterSv.getUsableShiftMaster(require, companyId, target);
		
		if(CollectionUtil.isEmpty(shiftMasters)) {
			return Collections.emptyList();
		}
		
		List<String> workTimeCodes = shiftMasters.stream().map(s -> s.getWorkTimeCd()).collect(Collectors.toList());
		
		List<WorkTimeDto> workTimeInfos = workTimeFinder.findByCodes(workTimeCodes);
		
		shiftMasters.forEach(shiftMaster -> {
			Optional<WorkTimeDto> oWorkTime = workTimeInfos.stream().filter(wkt -> shiftMaster.getWorkTimeCd().equalsIgnoreCase(wkt.code)).findFirst();
			
			if(oWorkTime.isPresent()) {
				WorkTimeDto worktime = oWorkTime.get();
				shiftMaster.setWorkTime1(worktime.workTime1);
				shiftMaster.setWorkTime2(worktime.workTime2);
			}
			
		});
		
		return shiftMasters;

	}
	
	public AlreadySettingWorkplaceDto getAlreadySetting() {
		AlreadySettingWorkplaceDto result = new AlreadySettingWorkplaceDto();
		result.setWorkplaceIds(shiftMasterOrgRp.getAlreadySettingWorkplace(AppContexts.user().companyId()));
		return result;
	}
	
	@AllArgsConstructor
	private static class RequireImpl implements GetUsableShiftMasterService.Require {
		
		@Inject
		private ShiftMasterOrgRepository shiftMasterOrgRp;
		
		@Inject
		private ShiftMasterRepository shiftMasterRepo;
		
		@Override
		public Optional<ShiftMasterOrganization> getByTargetOrg(String companyId, TargetOrgIdenInfor targetOrg) {
			return shiftMasterOrgRp.getByTargetOrg(companyId, targetOrg);
		}

		@Override
		public List<ShiftMasterDto> getAllByCid(String companyId) {
			return shiftMasterRepo.getAllByCid(companyId);
		}

		@Override
		public List<ShiftMasterDto> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode) {
			return shiftMasterRepo.getByListShiftMaterCd(companyId, listShiftMaterCode);
		}

	}

}
