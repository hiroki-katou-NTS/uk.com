package nts.uk.screen.at.app.shiftmanagement.shifttable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTable;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTableRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroupRespository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 起動時処理
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).U：公開.メニュー別OCD.起動時処理
 * 
 * @author quytb
 *
 */

@Stateless
public class Ksu001uScreenQuery {
	@Inject
	private PublicManagementShiftTableRepository shiftTableRepository;

	@Inject
	private AffWorkplaceGroupRespository affWorkplaceGroupRespository;

	@Inject
	private WorkplaceGroupAdapter groupAdapter;

	@Inject
	private WorkplaceExportServiceAdapter serviceAdapter;

	/** 取得する: 組織の表示情報、シフト表の公開管理 */
	public PublicInfoOganizationDto getPublicInfoOganization(Ksu001uRequest request) {
		TargetOrgIdenInfor targetOrgIdenInfor = request.getUnit() == 0
				? TargetOrgIdenInfor.creatIdentifiWorkplace(request.getWorkplaceId())
				: TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(request.getWorkplaceGroupId());
//		TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(unit, Optional.ofNullable(request.getWorkplaceId()),
//				Optional.ofNullable(request.getWorkplaceGroupId()));
		PublicInfoOganizationDto dto = new PublicInfoOganizationDto();
		dto.setUnit(targetOrgIdenInfor.getUnit().value);
		if (targetOrgIdenInfor.getWorkplaceId().isPresent()) {
			dto.setWorkplaceId(targetOrgIdenInfor.getWorkplaceId().get());
		}
		if (targetOrgIdenInfor.getWorkplaceGroupId().isPresent()) {
			dto.setWorkplaceGroupId(targetOrgIdenInfor.getWorkplaceGroupId().get());
		}

		/** 1. 取得する(対象組織識別情報) **/
		Optional<PublicManagementShiftTable> shiftTable = shiftTableRepository.get(targetOrgIdenInfor);
		TargetOrgIdenInforImpl require = new TargetOrgIdenInforImpl(affWorkplaceGroupRespository, groupAdapter,
				serviceAdapter);

		/** 2. 組織の表示情報を取得する(Require, 年月日) **/
		DisplayInfoOrganization displayInfoOrganization = targetOrgIdenInfor.getDisplayInfor(require, request.endDate());
		if (displayInfoOrganization != null) {
			dto.setDisplayName(displayInfoOrganization.getDisplayName());
		}
		if (shiftTable.isPresent()) {
				dto.setPublicDate(shiftTable.get().getEndDatePublicationPeriod().toString());
				if (shiftTable.get().getOptEditStartDate().isPresent()) {				
					dto.setEditDate(shiftTable.get().getOptEditStartDate().get().toString());
				}	
		}
		return dto;
	}

	@AllArgsConstructor
	private static class TargetOrgIdenInforImpl implements TargetOrgIdenInfor.Require {

		@Inject
		private AffWorkplaceGroupRespository affWorkplaceGroupRespository;

		@Inject
		private WorkplaceGroupAdapter groupAdapter;

		@Inject
		private WorkplaceExportServiceAdapter serviceAdapter;

		@Override
		public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
			return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
		}

		@Override
		public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
			String companyId = AppContexts.user().companyId();
			List<WorkplaceInfo> workplaceInfos = serviceAdapter
					.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate).stream()
					.map(mapper -> new WorkplaceInfo(mapper.getWorkplaceId(),
							Optional.ofNullable(mapper.getWorkplaceCode()),
							Optional.ofNullable(mapper.getWorkplaceName()),
							Optional.ofNullable(mapper.getWorkplaceExternalCode()),
							Optional.ofNullable(mapper.getWorkplaceGenericName()),
							Optional.ofNullable(mapper.getWorkplaceDisplayName()),
							Optional.ofNullable(mapper.getHierarchyCode())))
					.collect(Collectors.toList());
			return workplaceInfos;
		}

		@Override
		public List<String> getWKPID(String WKPGRPID) {
			String companyId = AppContexts.user().companyId();
			return affWorkplaceGroupRespository.getWKPID(companyId, WKPGRPID);
		}
	}
}
