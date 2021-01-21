package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.NRHelper;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendWorkTimeName;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * @author ThanhNX
 *
 *         就業時間帯をNRに 送信するデータに変換する
 */
public class SendWorkTimeNameService {

	private SendWorkTimeNameService() {
	};

	// [1] 各種名称送信に変換
	public static List<SendWorkTimeName> send(Require require, EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent() || requestSetting.get().getWorkTimeCodes().isEmpty())
			return Collections.emptyList();

		List<String> lstWorkTimeCode = requestSetting.get().getWorkTimeCodes().stream().map(x -> x.v())
				.collect(Collectors.toList());

		List<WorkTimeSetting> lstWorkTime = require.findByCodes(requestSetting.get().getCompanyId().v(),
				lstWorkTimeCode);

		List<PredetemineTimeSetting> lstPreTimeSet = require.findByCodeList(requestSetting.get().getCompanyId().v(),
				lstWorkTimeCode);

		return convert(lstWorkTime, lstPreTimeSet);
	}

	// [pvt-1] 就業時間帯コード名称送信に変換
	private static List<SendWorkTimeName> convert(List<WorkTimeSetting> lstWorkTime,
			List<PredetemineTimeSetting> lstPreTimeSet) {
		Map<String, PredetemineTimeSetting> mapPreTimeSet = lstPreTimeSet.stream()
				.collect(Collectors.toMap(x -> x.getWorkTimeCode().v(), x -> x, (x, y) -> x));

		return lstWorkTime.stream().map(x -> {
			PredetemineTimeSetting setting = mapPreTimeSet.getOrDefault(x.getWorktimeCode().v(), null);
			if (setting == null || setting.getPrescribedTimezoneSetting().getLstTimezone().isEmpty())
				return new SendWorkTimeName(x.getWorktimeCode().v(), x.getWorkTimeDisplayName().getWorkTimeName().v(),
						"");
			Integer startTime = setting.getPrescribedTimezoneSetting().getLstTimezone().get(0).getStart().v();

			Integer endTime = setting.getPrescribedTimezoneSetting().getLstTimezone().get(0).getEnd().v();

			return new SendWorkTimeName(x.getWorktimeCode().v(), x.getWorkTimeDisplayName().getWorkTimeName().v(),
					NRHelper.toStringNR(startTime, endTime));
		}).sorted((x, y) -> x.getWorkTimeNumber().compareTo(y.getWorkTimeNumber())).collect(Collectors.toList());
	}

	public static interface Require {

		// [R-1] 指定した就業時間帯をすべて取得する
		public List<WorkTimeSetting> findByCodes(String companyId, List<String> lstWorkTimeCode);

		// [R-2] 指定した所定時間設定をすべて取得する
		public List<PredetemineTimeSetting> findByCodeList(String companyID, List<String> lstWorkTimeCode);

		// [R-3]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
	}
}
