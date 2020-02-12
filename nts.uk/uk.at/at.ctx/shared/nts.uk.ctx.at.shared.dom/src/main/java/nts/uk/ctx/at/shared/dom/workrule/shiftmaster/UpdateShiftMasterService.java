package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.shr.com.context.AppContexts;
/**
 * DS: シフトマスタを更新する
 * @author tutk
 *
 */
public class UpdateShiftMasterService {
	public static AtomTask updateShiftMater(WorkInformation.Require requireWorkInfo, Require require,
			String shiftMaterCode, ShiftMasterDisInfor displayInfor, WorkInformation workInformation) {
		String companyId = AppContexts.user().companyId();
		// 1:get(会社ID, コード):Optional<シフトマスタ>
		Optional<ShiftMater> shiftMaterOpt = require.getByShiftMaterCd(companyId, shiftMaterCode);
		// 2: 変更する(シフトマスタの表示情報, 勤務情報)
		shiftMaterOpt.get().change(displayInfor, workInformation);
		// エラーチェックする
		shiftMaterOpt.get().checkError(requireWorkInfo);
		Optional<ShiftMater> shiftMaterByWorkTypeAndWorkTime = require.getByWorkTypeAndWorkTime(companyId,
				workInformation.getSiftCode().v(),
				workInformation.getWorkTimeCode() == null ? null : workInformation.getWorkTimeCode().v());
		if (shiftMaterByWorkTypeAndWorkTime.isPresent()
				&& !shiftMaterByWorkTypeAndWorkTime.get().getShiftMaterCode().v().equals(shiftMaterCode)) {
			throw new BusinessException("Msg_1610");
		}
		// 3:persist
		return AtomTask.of(() -> {
			require.update(shiftMaterOpt.get());
		});
	}

	public static interface Require {

		void update(ShiftMater shiftMater);

		public Optional<ShiftMater> getByShiftMaterCd(String companyId, String shiftMaterCode);

		public Optional<ShiftMater> getByWorkTypeAndWorkTime(String companyId, String workTypeCd, String workTimeCd);
	}
}
