package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.SwapEmpOnScheduleTeamService;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.AffWorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class ShiftPalletsOrgFinder {
	

	@Inject
	private ShiftPalletsOrgRepository shiftPalletsOrgRepository;

	@Inject
	private WorkplaceGroupAdapter groupAdapter;
	
	@Inject
	private WorkplaceExportServiceAdapter serviceAdapter;
	
	@Inject
	private AffWorkplaceAdapter wplAdapter;
	
	public List<ShiftPalletsOrgDto> getbyWorkPlaceId(String workplaceId) {
	
			// 0 = work place
			List<ShiftPalletsOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(0, workplaceId);
			List<ShiftPalletsOrgDto> result = shiftPalletsOrg.stream().map(c -> new ShiftPalletsOrgDto(c, workplaceId))
					.collect(Collectors.toList());
			return result;
		}
		
	public ShiftPalletsOrgDtoJB getbyWorkPlaceIdJb(String workplaceId) {
	//	List<ShiftPalletsOrgDtoJB> data = new ArrayList();
		// 0 = work place
		RequireImpl require = new RequireImpl(groupAdapter, serviceAdapter, wplAdapter);
		List<ShiftPalletsOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(0, workplaceId);
		TargetOrgIdenInfor target = TargetOrgIdenInfor.creatIdentifiWorkplace(workplaceId);
		DisplayInfoOrganization data = target.getDisplayInfor(require, GeneralDate.today());
		String displayName = data.getDisplayName();
			List<ShiftPalletsOrgDto> list = shiftPalletsOrg.stream().map(c -> new ShiftPalletsOrgDto(c, workplaceId))
					.collect(Collectors.toList());
			ShiftPalletsOrgDtoJB result = new ShiftPalletsOrgDtoJB(list, displayName);
			return result;
	}
	
	public ShiftPalletsOrgDtoJB getbyWorkPlaceGrId(String workplaceGrId) {
		// 1 = work place group
		RequireImpl require = new RequireImpl(groupAdapter, serviceAdapter, wplAdapter);
		TargetOrgIdenInfor target = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(workplaceGrId);
		List<ShiftPalletsOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(1, workplaceGrId);
		DisplayInfoOrganization data = target.getDisplayInfor(require, GeneralDate.today());
		String displayName = data.getDisplayName();
		List<ShiftPalletsOrgDto> list = shiftPalletsOrg.stream().map(c -> new ShiftPalletsOrgDto(c, workplaceGrId ))
				.collect(Collectors.toList());
		ShiftPalletsOrgDtoJB result = new ShiftPalletsOrgDtoJB(list, displayName);
		return result;
	}
	
	/**
	 * <<Query>> 職場別シフトパレットの一覧を取得する
	 */
	public List<PageDto> getShiftPaletteByWP(String workplaceId) {
		
		List<PageDto> data = shiftPalletsOrgRepository.findbyWorkPlaceId(0, workplaceId)
				.stream().map(i-> new PageDto(i.getPage(), i.getShiftPallet().getDisplayInfor().getShiftPalletName().v()))
				.collect(Collectors.toList());
		
		return data;
	}
	
	/**
	 * <<Query>> 職場グループ別シフトパレットの一覧を取得する
	 */
	public List<PageDto> getShiftPaletteByWPG(String workplaceId) {
		return shiftPalletsOrgRepository.findbyWorkPlaceId(1, workplaceId)
				.stream().map(i-> new PageDto(i.getPage(), i.getShiftPallet().getDisplayInfor().getShiftPalletName().v()))
				.collect(Collectors.toList());
	}
	
	@AllArgsConstructor
	private static class RequireImpl implements  TargetOrgIdenInfor.Require {
		
		@Inject
		private WorkplaceGroupAdapter groupAdapter;
		
		@Inject
		private WorkplaceExportServiceAdapter serviceAdapter;
		
		@Inject
		private AffWorkplaceAdapter wplAdapter; 
		@Override
		public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
			
			 return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
		}

		@Override
		public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
			String companyId = AppContexts.user().companyId();
			List<WorkplaceInfo> workplaceInfos = serviceAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate).stream()
			.map(mapper-> new WorkplaceInfo(mapper.getWorkplaceId(), Optional.ofNullable(mapper.getWorkplaceCode()), Optional.ofNullable(mapper.getWorkplaceName()), Optional.ofNullable(mapper.getWorkplaceExternalCode()), 
					Optional.ofNullable(mapper.getWorkplaceGenericName()), Optional.ofNullable(mapper.getWorkplaceDisplayName()), Optional.ofNullable(mapper.getHierarchyCode()))).collect(Collectors.toList());
			return workplaceInfos;
		}

		@Override
		public List<String> getWKPID(String WKPGRPID) {
			String CID = AppContexts.user().companyId();
			return wplAdapter.getWKPID(CID, WKPGRPID);
		}
		
	}
	
	@Value
	public class PageDto {
		public int page;
		public String name;
	}
	@Value
	public class ShiftPalletsOrgDtoJB {
		public List<ShiftPalletsOrgDto> listShiftPalletsOrgDto;
		public String displayName;
	}
}
