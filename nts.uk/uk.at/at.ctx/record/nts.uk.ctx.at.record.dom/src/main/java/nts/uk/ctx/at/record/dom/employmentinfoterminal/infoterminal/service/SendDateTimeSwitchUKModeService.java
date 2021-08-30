package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.DateTimeSwitchUKMode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author thanh_nx
 *
 *         UKモードへの切替日時をNRに 送信する
 */
public class SendDateTimeSwitchUKModeService {

	// 設定を送る
	public static Optional<DateTimeSwitchUKMode> process(Require require, EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent() || !requestSetting.get().getTimeSwitchUKMode().isPresent())
			return Optional.empty();

		return Optional.of(DateTimeSwitchUKMode.create(requestSetting.get().getTimeSwitchUKMode().get()));

	}

	public static interface Require {
		// [R-1] タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);
	}
}
