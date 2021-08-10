package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendTimeRecordSetting;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         タイムレコードに設定を送る
 */
public class SendTimeRecordSettingService {

	private SendTimeRecordSettingService() {
	};

	// [1] 各種名称送信に変換
	public static Optional<SendTimeRecordSetting> send(Require require, EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent())
			return Optional.empty();
		return Optional.of(convert(requestSetting.get()));
	}

	// [pvt-1] 変換
	private static SendTimeRecordSetting convert(TimeRecordReqSetting setting) {

		return new SendTimeRecordSetting.SettingBuilder(setting.isStampReceive(), setting.isTimeSetting(),
				setting.isReservationReceive(), setting.isApplicationReceive(), setting.isSendEmployeeId())
						.createReq7(setting.isSendWorkType()).createReq8(setting.isSendWorkTime())
						.createReq9(setting.isOverTimeHoliday()).createReq10(setting.isSendBentoMenu())
						.createReq11(setting.isApplicationReason()).build();
	}

	public static interface Require {

		// [R-1]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

	}

}
