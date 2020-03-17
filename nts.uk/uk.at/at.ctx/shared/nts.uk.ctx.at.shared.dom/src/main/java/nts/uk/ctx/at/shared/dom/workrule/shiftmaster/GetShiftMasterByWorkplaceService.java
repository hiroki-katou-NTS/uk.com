package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * DS : 職場用の組織別シフトマスタを取得する
 * 
 * @author anhdt
 *
 */
public class GetShiftMasterByWorkplaceService {

	public static List<ShiftMasterDto> getShiftMasterByWorkplaceService(Require require,
			TargetOrgIdenInfor targetOrg) {
		
		String companyId = AppContexts.user().companyId();

        Optional<ShiftMasterOrganization> shiftMaterOrgOpt = require.getByTargetOrg(companyId, targetOrg);
        
        if (!shiftMaterOrgOpt.isPresent()) {
            return Collections.emptyList();
        }
        
        return require.getByListShiftMaterCd(companyId, shiftMaterOrgOpt.get().getListShiftMaterCode());
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
