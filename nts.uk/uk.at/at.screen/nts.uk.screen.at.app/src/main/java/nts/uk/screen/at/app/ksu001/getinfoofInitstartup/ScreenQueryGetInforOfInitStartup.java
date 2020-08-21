/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getinfoofInitstartup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.WorkScheDisplaySetting;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.WorkScheDisplaySettingRepo;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.workplace.export.EmpOrganizationPub;
import nts.uk.ctx.bs.employee.pub.workplace.workplacegroup.EmpOrganizationExport;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv 
 * ScreenQuery: 初期起動の情報取得 
 * path: UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正（職場別）.メニュー別OCD.初期起動の情報取得
 */
@Stateless
public class ScreenQueryGetInforOfInitStartup {

	@Inject
	private WorkScheDisplaySettingRepo workScheDisplaySettingRepo;
	@Inject
	private EmpOrganizationPub empOrganizationPub;
	
	@Inject
	private WorkplaceGroupAdapter workplaceGroupAdapter;
	@Inject
	private WorkplaceExportService workplaceExportService;
	@Inject
	private AffWorkplaceGroupRespository affWorkplaceGroupRepo;
	
	public DataScreenQueryGetInforDto getData() {
		// Step 1,2
		String companyID = AppContexts.user().companyId();
		Optional<WorkScheDisplaySetting> workScheDisplaySettingOpt = workScheDisplaySettingRepo.get(companyID);
		if (!workScheDisplaySettingOpt.isPresent()) {
			return new DataScreenQueryGetInforDto(null, null, null, null);
		}

		DatePeriod datePeriod = workScheDisplaySettingOpt.get().calcuInitDisplayPeriod();

		// step 3
		// goi domain service 社員の対象組織識別情報を取得する
		String sidLogin = AppContexts.user().employeeId();
		RequireImpl require = new RequireImpl(empOrganizationPub);
		TargetOrgIdenInfor targetOrgIdenInfor = GetTargetIdentifiInforService.get(require, datePeriod.end(), sidLogin);
		
		// step 4
		RequireWorkPlaceImpl requireWorkPlace = new RequireWorkPlaceImpl(workplaceGroupAdapter,workplaceExportService,affWorkplaceGroupRepo);
		DisplayInfoOrganization displayInfoOrganization =  targetOrgIdenInfor.getDisplayInfor(requireWorkPlace, datePeriod.end());
		
		TargetOrgIdenInforDto targetOrgIdenInforDto = new TargetOrgIdenInforDto( targetOrgIdenInfor );
		
		return new DataScreenQueryGetInforDto(datePeriod.start(), datePeriod.end(), targetOrgIdenInforDto, displayInfoOrganization);
	}
	
	@AllArgsConstructor
	private static class RequireImpl implements GetTargetIdentifiInforService.Require {
		
		@Inject
		private EmpOrganizationPub empOrganizationPub;
		
		@Override
		public List<EmpOrganizationImport> getEmpOrganization(GeneralDate referenceDate, List<String> listEmpId) {
			
			List<EmpOrganizationExport> exports = empOrganizationPub.getEmpOrganiztion(referenceDate, listEmpId);
			List<EmpOrganizationImport> data = exports.stream().map(i -> {
				return new EmpOrganizationImport (new EmployeeId(i.getEmpId()),i.getBusinessName(), i.getEmpCd(), i.getWorkplaceId(), i.getWorkplaceGroupId());
			}).collect(Collectors.toList());
			return data;
		}
	}
	
	@AllArgsConstructor
	private static class RequireWorkPlaceImpl implements TargetOrgIdenInfor.Require {
		
		@Inject
		private WorkplaceGroupAdapter workplaceGroupAdapter;
		@Inject
		private WorkplaceExportService workplaceExportService;
		@Inject
		private AffWorkplaceGroupRespository affWorkplaceGroupRepo;
		
		@Override
		public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
			List<WorkplaceGroupImport> data = workplaceGroupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
			return data;
		}

		@Override
		public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
			List<WorkplaceInforParam> data1 = workplaceExportService
					.getWorkplaceInforFromWkpIds(AppContexts.user().companyId(), listWorkplaceId, baseDate);
			if (data1.isEmpty()) {
				return new ArrayList<WorkplaceInfo>();
			}
			List<WorkplaceInfo> data = data1.stream().map(item -> {
				return new WorkplaceInfo(item.getWorkplaceId(),
						item.getWorkplaceCode() == null ? Optional.empty() : Optional.of(item.getWorkplaceCode()),
						item.getWorkplaceName() == null ? Optional.empty() : Optional.of(item.getWorkplaceName()),
						item.getHierarchyCode() == null ? Optional.empty() : Optional.of(item.getHierarchyCode()),
						item.getGenericName() == null ? Optional.empty() : Optional.of(item.getGenericName()),
						item.getDisplayName() == null ? Optional.empty() : Optional.of(item.getDisplayName()),
						item.getExternalCode() == null ? Optional.empty() : Optional.of(item.getExternalCode()));
			}).collect(Collectors.toList());
			return data;
		}

		@Override
		public List<String> getWKPID(String WKPGRPID) {
			List<String> data = affWorkplaceGroupRepo.getWKPID(AppContexts.user().companyId(), WKPGRPID);
			return data;
		}
		
		
	}
}
