package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.adapter.application.setting.ApplicationReasonRc;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.NRHelper;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendReasonApplication;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         申請理由をNRに 送信するデータに変換する
 */
public class SendReasonApplicationService {

	private SendReasonApplicationService() {
	};

	// [1] 各種名称送信に変換
	public static List<SendReasonApplication> send(Require require, EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent() || requestSetting.get().isApplicationReason())
			return Collections.emptyList();

		List<ApplicationReasonRc> lstAppReason = require.getReasonByAppType(requestSetting.get().getCompanyId().v(),
				NRHelper.APPREASON_TYPE);
		return convert(lstAppReason);
	}

	// [pvt-1] 申請理由名称送信に変換
	private static List<SendReasonApplication> convert(List<ApplicationReasonRc> lstAppReason) {
		return lstAppReason.stream().map(x -> new SendReasonApplication(createAppReasonNo(x), x.getReasonTemp()))
				.collect(Collectors.toList());
	}

	private static String createAppReasonNo(ApplicationReasonRc appType) {
		// TODO: Domain chua mao EA ko co filed 理由コード
		switch (appType.appType) {
		case OVER_TIME_APPLICATION:

			return 10 + "";

		case ABSENCE_APPLICATION:

			return 20 + "";

		case WORK_CHANGE_APPLICATION:

			return 30 + "";

		case BREAK_TIME_APPLICATION:

			return 40 + "";

		case STAMP_APPLICATION:

			return String.format("%02d", 0);

		case ANNUAL_HOLIDAY_APPLICATION:

			return 60 + "";

		case EARLY_LEAVE_CANCEL_APPLICATION:

			return 50 + "";

		default:
			return "";
		}

	}

	public static interface Require {

		// [R-1] 申請理由を取得する
		List<ApplicationReasonRc> getReasonByAppType(String companyId, List<Integer> lstAppType);

		// [R-2]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
	}

}
