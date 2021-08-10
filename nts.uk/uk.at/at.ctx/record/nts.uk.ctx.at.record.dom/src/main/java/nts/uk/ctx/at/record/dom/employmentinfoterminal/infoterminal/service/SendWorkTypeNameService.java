package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendWorkTypeName;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author ThanhNX
 *
 *         勤務種類をNRに 送信するデータに変換する
 */
public class SendWorkTypeNameService {

	private SendWorkTypeNameService() {
	};

	// [1] 各種名称送信に変換
	public static List<SendWorkTypeName> send(Require require, EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent() || requestSetting.get().getWorkTypeCodes().isEmpty())
			return Collections.emptyList();

		List<String> lstWorkTypeCode = requestSetting.get().getWorkTypeCodes().stream().map(x -> x.v())
				.collect(Collectors.toList());

		List<WorkType> lstWorkInfo = require.getPossibleWork(requestSetting.get().getCompanyId().v(), lstWorkTypeCode);

		return convert(lstWorkInfo);
	}

	// [pvt-1] 勤務種類コード名称送信に変換
	private static List<SendWorkTypeName> convert(List<WorkType> lstWorkInfo) {

		return lstWorkInfo.stream().map(x -> {
			String daily = "0";
			if (x.getDailyWork().isHolidayType()) {
				daily = "1";
			} 
			return new SendWorkTypeName(x.getWorkTypeCode().v(), daily, x.getName().v());
		}).sorted((x, y) -> x.getWorkTypeNumber().compareTo(y.getWorkTypeNumber())).collect(Collectors.toList());
	}

	public static interface Require {

		// [R-1] 指定した勤務種類をすべて取得する
		public List<WorkType> getPossibleWork(String companyId, List<String> workTypeCode);

		// [R-2]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
	}
}
