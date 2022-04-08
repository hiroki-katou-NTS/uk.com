/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pubimp.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireListWorkplaceByEmpIDService;
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireUserIDFromEmpIDService;
import nts.uk.ctx.sys.auth.dom.grant.service.RoleIndividualService;
import nts.uk.ctx.sys.auth.dom.otreferset.OvertimeReferSet;
import nts.uk.ctx.sys.auth.dom.otreferset.OvertimeReferSetRepository;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceAuthorityRepository;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.wkpmanager.WorkPlaceAuthorityExport;
import nts.uk.ctx.sys.auth.pub.workplace.ReferenceableWorkplaceExport;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceInfoExport;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceManagerExport;
import nts.uk.ctx.sys.auth.pubimp.wkpmanager.GetWorkPlaceRegerence;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplacePubImp.
 * @author NWS_HoangDD
 */
@Stateless
public class WorkplaceListPubImp implements WorkplaceListPub{

	@Inject
	private AcquireUserIDFromEmpIDService acquireUserIDFromEmpIDService;
	
	@Inject
	private RoleIndividualService roleIndividualService;
	
	@Inject
	private RoleExportRepo roleExportRepo;
	
	@Inject
	private AcquireListWorkplaceByEmpIDService acquireListWorkplace;
	
	@Inject
	private OvertimeReferSetRepository overtimeReferSetRepository;
	
	@Inject
	private WorkplaceManagerRepository workplaceManagerRepository;
	
//	@Inject
//	private SyWorkplacePub syWorkplacePub;
	
	@Inject
	private WorkplacePub workplacePub;
	
	@Inject
	private WorkplaceManagerRepository workplaceManagerRepo;
	
	@Inject
	private WorkPlaceAuthorityRepository workPlaceAuthorityRepository;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	@Inject
	private GetWorkPlaceFromReferenceRange getWorkPlaceFromReferenceRange;
	
	@Inject
	private GetWorkPlaceRegerence getWorkPlaceRegerence;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.auth.pub.workplace.WorkplacePub#getWorkplaceListId(nts.arc
	 * .time.GeneralDate, java.lang.String, boolean)
	 */
	@Override
	public WorkplaceInfoExport getWorkplaceListId(GeneralDate referenceDate, String employeeID, boolean referEmployee) {

		WorkplaceInfoExport workplaceInfoExport = new WorkplaceInfoExport();

		String companyId = AppContexts.user().companyId();
		
		// 社員IDからユーザIDを取得する (Lấy userID từ employeeID)
		Optional<String> optUserID = acquireUserIDFromEmpIDService.getUserIDByEmpID(employeeID);
		if (optUserID.isPresent()) {
			// ユーザIDからロールを取得する (lấy role từ userID)
			String roleID = roleIndividualService.getRoleFromUserId(optUserID.get(), RoleType.EMPLOYMENT.value, referenceDate);
			
			// 社員参照範囲を取得する (Lấy phạm vi tham chiếu employee)
			OptionalInt optEmpRange = roleExportRepo.findEmpRangeByRoleID(roleID);
			int empRange;
			if (optEmpRange.isPresent()) {
				// INPUT．全社員参照をチェックする
				empRange = optEmpRange.getAsInt();
				workplaceInfoExport.setEmployeeRange(empRange);
				if (!referEmployee && empRange == EmployeeReferenceRange.ALL_EMPLOYEE.value) {
					// 取得した社員参照範囲＝部門・職場（配下含む）にする
					empRange = EmployeeReferenceRange.DEPARTMENT_AND_CHILD.value;
				}
			
				// ドメインモデル「時間外労働時間参照設定」を取得する
				OvertimeReferSet overtimeReferSet = overtimeReferSetRepository.getOvertimeReferSet(companyId).get();
				
				// 職場管理者参照＝するの場合
				List<String> listWorkPlaceID = new ArrayList<>();
				if (overtimeReferSet.isReferWkpAdmin()) {
					// 指定社員が参照可能な職場リストを取得する (Lấy list workplace của employee chỉ định)
					listWorkPlaceID = acquireListWorkplace.getListWorkPlaceID(employeeID, empRange, referenceDate);
				} else { // 職場管理者参照＝しないの場合
					listWorkPlaceID = acquireListWorkplace.getListWorkPlaceIDNoWkpAdmin(employeeID, empRange,
							referenceDate);
				}
				workplaceInfoExport.setLstWorkPlaceID(listWorkPlaceID);
			}
		}
		return workplaceInfoExport;
	}

	@Override
	public List<String> getWorkplaceId(GeneralDate baseDate, String employeeId) {
		String companyID = AppContexts.user().companyId();
		List<String> subListWorkPlace = new ArrayList<>();
		// (Lấy all domain 「職場管理者」)
		List<WorkplaceManager> listWorkplaceManager = workplaceManagerRepository.findListWkpManagerByEmpIdAndBaseDate(employeeId, GeneralDate.today());
		for (WorkplaceManager workplaceManager : listWorkplaceManager) {
			if(!subListWorkPlace.contains(workplaceManager.getWorkplaceId())){
				subListWorkPlace.add(workplaceManager.getWorkplaceId());
				// [No.567]職場の下位職場を取得する
				subListWorkPlace.addAll(workplacePub.getAllChildrenOfWorkplaceId(companyID, GeneralDate.today(), workplaceManager.getWorkplaceId()));
			}
		}
		return subListWorkPlace.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> getListWorkPlaceIDNoWkpAdmin(String employeeID, int empRange, GeneralDate referenceDate) {
		
		List<String> result = acquireListWorkplace.getListWorkPlaceIDNoWkpAdmin(employeeID, empRange, referenceDate);
		return result;
	}

	@Override
	public List<WorkplaceManagerExport> findListWkpManagerByEmpIdAndBaseDate(String employeeId, GeneralDate baseDate) {
		
		List<WorkplaceManager> listDomain =  workplaceManagerRepo.findListWkpManagerByEmpIdAndBaseDate(employeeId, baseDate);
		if (listDomain.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<WorkplaceManagerExport> result = listDomain.stream().map(i -> {
			WorkplaceManagerExport export = new WorkplaceManagerExport(i.getWorkplaceManagerId(), i.getEmployeeId(), i.getWorkplaceId(), i.getHistoryPeriod());
			return export;
		}).collect(Collectors.toList());
		
		return result;
	}
	
	@Override
	public Optional<WorkPlaceAuthorityExport> getWorkPlaceAuthorityById(String companyId, String roleId,
			int functionNo) {
		return workPlaceAuthorityRepository.getWorkPlaceAuthorityById(companyId, roleId, functionNo)
			.map(x -> new WorkPlaceAuthorityExport(
					x.getRoleId(), 
					x.getCompanyId(), 
					x.getFunctionNo().v(), 
					x.isAvailability()));
	}

	@Override
	public ReferenceableWorkplaceExport getWorkPlace(String userID, String employeeID, GeneralDate date) {
		// return 参照可能社員の所属職場を取得する#取得する(require,ユーザID,社員ID,基準日)
		
		return ReferenceableWorkplaceExport.fromDomain(this.getWorkPlaceRegerence.get(userID, employeeID, date));
	}

	@Override
	public ReferenceableWorkplaceExport getWorkPlaceByReference(String employeeID, GeneralDate date) {
		// $参照範囲 = 社員参照範囲.全社員

		Integer range = EmployeeReferenceRangeExport.ALL_EMPLOYEE.value;
		// return 参照範囲から参照できる職場・社員を取得する#取得する(require,社員ID,基準日,$参照範囲)
		
		return ReferenceableWorkplaceExport.fromDomain(this.getWorkPlaceFromReferenceRange.get(new RequireImpl(workplaceAdapter), employeeID, date, range));
	}
	
	@AllArgsConstructor 
	private class RequireImpl implements GetWorkPlaceFromReferenceRange.Require {
		private WorkplaceAdapter workplaceAdapter;

		@Override
		public Map<String, String> getAWorkplace(String employeeID, GeneralDate date) {
			return this.workplaceAdapter.getAWorkplace(employeeID, date);
		}

		@Override
		public Map<String, String> getByListIds(List<String> workPlaceIds, GeneralDate baseDate) {
			return this.workplaceAdapter.getByListIds(workPlaceIds, baseDate);
		}
		
		
	}

	@Override
	public Optional<WorkplaceManagerExport> findWkpMngByEmpWkpDate(String employeeID, String workplaceID,
			GeneralDate date) {
		return workplaceManagerRepo.findWkpMngByEmpWkpDate(employeeID, workplaceID, date).map(i -> {
			WorkplaceManagerExport export = new WorkplaceManagerExport(i.getWorkplaceManagerId(), i.getEmployeeId(), i.getWorkplaceId(), i.getHistoryPeriod());
			return export;
		});
	}
}

