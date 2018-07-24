/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pubimp.workplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireListWorkplaceByEmpIDService;
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireUserIDFromEmpIDService;
import nts.uk.ctx.sys.auth.dom.grant.service.RoleIndividualService;
import nts.uk.ctx.sys.auth.dom.otreferset.OvertimeReferSet;
import nts.uk.ctx.sys.auth.dom.otreferset.OvertimeReferSetRepository;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;
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
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.pub.workplace.WorkplacePub#getWorkplaceListId(nts.arc.time.GeneralDate, java.lang.String, boolean)
	 */
	@Override
	public List<String> getWorkplaceListId(GeneralDate referenceDate, String employeeID, boolean referEmployee) {
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
					listWorkPlaceID = acquireListWorkplace.getListWorkPlaceIDNoWkpAdmin(employeeID, empRange, referenceDate);
				}
				
				if (listWorkPlaceID.size() >= 1) {
					return listWorkPlaceID;
				}
			}
		}
		
		return Collections.emptyList();
	}
}

