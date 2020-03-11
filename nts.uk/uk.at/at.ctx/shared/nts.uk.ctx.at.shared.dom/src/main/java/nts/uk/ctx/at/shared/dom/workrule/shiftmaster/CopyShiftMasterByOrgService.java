package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * DS : 組織別シフトマスタを複写する
 * 
 * @author tutk
 *
 */
@Stateless
public class CopyShiftMasterByOrgService {
	public static Optional<AtomTask> copyShiftMasterByOrg(Require require, String companyId,
			ShiftMasterOrganization shiftMaterOrg, TargetOrgIdenInfor targetOrg, boolean overwrite) {
		// 1: 組織別シフトマスタRepository.exists( 会社ID, 対象組織識別情報 )
		boolean checkExists = require.exists(companyId, targetOrg);
		// 2.1: if $存在するか && not 上書きするか
		if (checkExists && !overwrite) {
			return Optional.empty();
		}
		
		//4:persist
		return Optional.of(AtomTask.of(() -> {
			if (checkExists) {
				// 2.2:組織別シフトマスタRepository.delete( 会社ID, 対象組織識別情報 )
				require.delete(companyId, targetOrg);
			}
			// 3: 複写する(対象組織識別情報)
			ShiftMasterOrganization shiftMaterOrgNew = shiftMaterOrg.copy(targetOrg);
			// 3.1:組織別シフトマスタRepository.insert( 組織別シフトマスタ )
			require.insert(shiftMaterOrgNew);
		}));
	}

	public static interface Require {

		boolean exists(String companyId, TargetOrgIdenInfor targetOrg);

		void insert(ShiftMasterOrganization shiftMaterOrganization);

		void delete(String companyId, TargetOrgIdenInfor targetOrg);
	}
}
