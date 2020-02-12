package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * DS : 使用できるシフトマスタを取得する
 * 
 * @author tutk
 *
 */
public class GetUsableShiftMasterService {

	public static List<ShiftMater> getUsableShiftMaster(Require require, String companyId,
			TargetOrgIdenInfor targetOrg) {

		// 1: 組織別シフトマスタ = require.組織別シフトマスタを取得する( 会社ID, 対象組織 )
		Optional<ShiftMaterOrganization> shiftMaterOrgOpt = require.getByTargetOrg(companyId, targetOrg);
		// 2: *getAll(会社ID)
		if (!shiftMaterOrgOpt.isPresent()) {
			return require.getAllByCid(companyId);
		}
		// 3: *get(会社ID, List<シフトマスタコード>)
		return require.getByListShiftMaterCd(companyId, shiftMaterOrgOpt.get().getListShiftMaterCode());
	}

	public static interface Require {

		Optional<ShiftMaterOrganization> getByTargetOrg(String companyId, TargetOrgIdenInfor targetOrg);

		List<ShiftMater> getAllByCid(String companyId);

		List<ShiftMater> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode);
	}
}
