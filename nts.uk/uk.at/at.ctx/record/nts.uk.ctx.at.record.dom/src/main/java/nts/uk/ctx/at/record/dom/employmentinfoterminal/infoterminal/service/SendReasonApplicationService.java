package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.ArrayList;
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
	public static List<SendReasonApplication> send(Require require, EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent() || requestSetting.get().isApplicationReason())
			return Collections.emptyList();

		List<ApplicationReasonRc> lstAppReason = require.getReasonByAppType(requestSetting.get().getCompanyId().v(),
				NRHelper.APPREASON_TYPE);
		return convert(lstAppReason);
	}

	// [pvt-1] 申請理由名称送信に変換
	private static List<SendReasonApplication> convert(List<ApplicationReasonRc> lstAppReason) {
		return lstAppReason.stream().flatMap(x -> createAppReasonNo(x).stream()).collect(Collectors.toList());
	}

	private static List<SendReasonApplication> createAppReasonNo(ApplicationReasonRc appType) {

		switch (appType.appType) {
		case OVER_TIME_APPLICATION:
			return appType.reasonTemp.stream()
					.map(x -> new SendReasonApplication(String.valueOf(10 + x.getLeft()), x.getRight()))
					.collect(Collectors.toList());

		case ABSENCE_APPLICATION:

			return appType.reasonTemp.stream()
					.map(x -> new SendReasonApplication(String.valueOf(20 + x.getLeft()), x.getRight()))
					.collect(Collectors.toList());

		case WORK_CHANGE_APPLICATION:

			return appType.reasonTemp.stream()
					.map(x -> new SendReasonApplication(String.valueOf(30 + x.getLeft()), x.getRight()))
					.collect(Collectors.toList());

		case BREAK_TIME_APPLICATION:

			return appType.reasonTemp.stream()
					.map(x -> new SendReasonApplication(String.valueOf(40 + x.getLeft()), x.getRight()))
					.collect(Collectors.toList());

		case STAMP_APPLICATION:

			return appType.reasonTemp.stream()
					.map(x -> new SendReasonApplication(String.format("%02d", x.getLeft()), x.getRight()))
					.collect(Collectors.toList());

		case ANNUAL_HOLIDAY_APPLICATION:

			return appType.reasonTemp.stream()
					.map(x -> new SendReasonApplication(String.valueOf(60 + x.getLeft()), x.getRight()))
					.collect(Collectors.toList());

		case EARLY_LEAVE_CANCEL_APPLICATION:

			return appType.reasonTemp.stream()
					.map(x -> new SendReasonApplication(String.valueOf(50 + x.getLeft()), x.getRight()))
					.collect(Collectors.toList());
		default:
			return new ArrayList<>();
		}

	}

	public static interface Require {

		// [R-1] 申請理由を取得する
		List<ApplicationReasonRc> getReasonByAppType(String companyId, List<Integer> lstAppType);

		// [R-2]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);
	}

}
