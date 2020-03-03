package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;

/**
 * DS : 使用できるシフトマスタを取得する
 * 
 * @author tutk
 *
 */
@Stateless
public class GetUsableShiftMasterService {

	public static List<ShiftMasterDto> getUsableShiftMaster(Require require, String companyId,
			TargetOrgIdenInfor targetOrg) {

		// 2: *getAll(会社ID)
		if(targetOrg == null) {
			return require.getAllByCid(companyId);
		}
		
		// 1: 組織別シフトマスタ = require.組織別シフトマスタを取得する( 会社ID, 対象組織 )
		Optional<ShiftMasterOrganization> shiftMaterOrgOpt = require.getByTargetOrg(companyId, targetOrg);
		
		// 3: *get(会社ID, List<シフトマスタコード>)
		if (shiftMaterOrgOpt.isPresent()) {
			return require.getByListShiftMaterCd(companyId, shiftMaterOrgOpt.get().getListShiftMaterCode());
		}
		
		return Collections.emptyList();
	}

	public static interface Require {

		/**
		 * 	[R-1] 組織別シフトマスタを取得する	
		 * @param companyId
		 * @param targetOrg
		 * @return
		 */
		Optional<ShiftMasterOrganization> getByTargetOrg(String companyId, TargetOrgIdenInfor targetOrg);

		/**
		 * [R-2] シフトマスタをすべて取得する
		 * @param companyId
		 * @return
		 */
		List<ShiftMasterDto> getAllByCid(String companyId);

		/**
		 * [R-3] シフトマスタを取得する	
		 * @param companyId
		 * @param listShiftMaterCode
		 * @return
		 */
		List<ShiftMasterDto> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode);
	}
}
