package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.WorkInformation;
/**
 * DS: シフトマスタを更新する
 * @author tutk
 *
 */
public class UpdateShiftMasterService {
	public static AtomTask updateShiftMater(WorkInformation.Require requireWorkInfo, Require require,
			String shiftMaterCode, ShiftMasterDisInfor displayInfor, WorkInformation workInformation) {
		// 1:get(会社ID, コード):Optional<シフトマスタ>
		Optional<ShiftMaster> shiftMaterOpt = require.getByShiftMaterCd(shiftMaterCode); //truyen cid tư app
		// 2: 変更する(シフトマスタの表示情報, 勤務情報)
		shiftMaterOpt.get().change(displayInfor, workInformation);
		// エラーチェックする
		shiftMaterOpt.get().checkError(requireWorkInfo);
		Optional<ShiftMaster> shiftMaterByWorkTypeAndWorkTime = require.getByWorkTypeAndWorkTime(//truyen cid tư app
				workInformation.getWorkTypeCode().v(),
				workInformation.getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null));
		if (shiftMaterByWorkTypeAndWorkTime.isPresent()
				&& !shiftMaterByWorkTypeAndWorkTime.get().getShiftMasterCode().v().equals(shiftMaterCode)) {
			throw new BusinessException("Msg_1610");
		}
		// 3:persist
		return AtomTask.of(() -> {
			require.update(shiftMaterOpt.get());
		});
	}

	public static interface Require {

		void update(ShiftMaster shiftMater);

		public Optional<ShiftMaster> getByShiftMaterCd(String shiftMaterCode);

		public Optional<ShiftMaster> getByWorkTypeAndWorkTime(String workTypeCd, String workTimeCd);
	}
}
