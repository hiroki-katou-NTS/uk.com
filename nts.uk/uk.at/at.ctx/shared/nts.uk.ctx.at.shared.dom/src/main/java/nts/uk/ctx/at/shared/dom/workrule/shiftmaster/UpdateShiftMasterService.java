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
	public static AtomTask updateShiftMater(WorkInformation.Require requireWorkInfo, Require require, String companyId,
			String shiftMaterCode, ShiftMasterDisInfor displayInfor, Optional<ShiftMasterImportCode> importCode , WorkInformation workInformation) {
		// 1:get(会社ID, コード):Optional<シフトマスタ>
		Optional<ShiftMaster> shiftMaterOpt = require.getByShiftMaterCd(shiftMaterCode); //truyen cid tư app
		String presentImportCode = shiftMaterOpt.get().getImportCode().map(ip -> ip.v()).orElse("");
		String newImportCode = importCode.map(ip -> ip.v()).orElse("");
		if (importCode.isPresent() && !newImportCode.equals(presentImportCode) && require.checkExistsCaptureCode(companyId, importCode.get())){
			throw new BusinessException("Msg_2163");
		}
		// 2: 変更する(シフトマスタの表示情報, 勤務情報)
		shiftMaterOpt.get().change(displayInfor, workInformation, importCode);
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

		boolean checkExistsCaptureCode(String companyId, ShiftMasterImportCode importCode);

	}
}
