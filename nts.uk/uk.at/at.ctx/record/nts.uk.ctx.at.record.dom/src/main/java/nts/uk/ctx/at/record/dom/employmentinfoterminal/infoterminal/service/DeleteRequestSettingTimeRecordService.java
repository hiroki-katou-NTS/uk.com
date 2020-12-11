package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author thanh_nx
 *
 *         就業情報端末のリクエスト一覧を消す
 */
public class DeleteRequestSettingTimeRecordService {

	public static AtomTask process(Require require, EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {

		Optional<TimeRecordReqSetting> setting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!setting.isPresent())
			return AtomTask.of(() -> {
			});

		return AtomTask.of(() -> {

			TimeRecordReqSetting domainUpdate = new ReqSettingBuilder(setting.get().getTerminalCode(), contractCode,
					setting.get().getCompanyId(), setting.get().getCompanyCode(), setting.get().getEmployeeIds(),
					setting.get().getBentoMenuFrameNumbers(), setting.get().getWorkTypeCodes())
							.workTime(setting.get().getWorkTimeCodes()).overTimeHoliday(false).applicationReason(false)
							.stampReceive(false).reservationReceive(false).applicationReceive(false).timeSetting(false)
							.sendBentoMenu(false).sendWorkType(false).sendWorkTime(false)
							.remoteSetting(setting.get().isRemoteSetting()).reboot(setting.get().isReboot()).build();

			require.updateSetting(domainUpdate);
		});
	}

	public static interface Require {

		/**
		 * [R-1] 就業情報端末のリクエスト一覧を取得する
		 * 
		 */
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

		/**
		 * [R-２] 就業情報端末のリクエスト一覧を更新する
		 */
		public void updateSetting(TimeRecordReqSetting setting);

	}
}
