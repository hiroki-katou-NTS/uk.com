package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.WorkInformation;

/**
 * DS : シフトマスタを作る
 * 
 * @author tutk
 *
 */
public class MakeShiftMasterService {

	public static AtomTask makeShiftMater(WorkInformation.Require requireWorkInfo,
			Require require, String companyId, String shiftMaterCode, String workTypeCd, Optional<String> workTimeCd,
			ShiftMasterDisInfor displayInfor) {
		String workTimeCdNew = workTimeCd.isPresent() ? workTimeCd.get() : null;
		// 1:作る(会社ID, シフトマスタコード, シフトマスタの表示情報, 勤務種類コード, 就業時間帯コード)
		ShiftMaster shiftMater = new ShiftMaster(companyId, new ShiftMasterCode(shiftMaterCode), displayInfor, workTypeCd,
				workTimeCdNew);
		// 2:エラー状態をチェックする
		shiftMater.checkError(requireWorkInfo);

		if (require.checkExists(companyId, workTypeCd, workTimeCdNew)) {
			throw new BusinessException("Msg_1610");
		}
		// 3:persist
		return AtomTask.of(() -> {
			require.insert(shiftMater, workTypeCd, workTimeCdNew);
		});
	}

	public static interface Require {
		boolean checkExists(String companyId, String workTypeCd, String workTimeCd);
		void insert(ShiftMaster shiftMater, String workTypeCd, String workTimeCd);
	}
	
}
