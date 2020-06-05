package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDto;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendPerInfoName;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;

/**
 * @author ThanhNX
 *
 *         個人情報をNRに 送信するデータに変換する
 */
public class SendPerInfoNameService {

	private SendPerInfoNameService() {
	};

	// [1] 各種名称送信に変換
	public static List<SendPerInfoName> send(Require require, EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent() || requestSetting.get().getEmployeeIds().isEmpty())
			return Collections.emptyList();
		List<String> employeeIds = requestSetting.get().getEmployeeIds().stream().map(x -> x.v())
				.collect(Collectors.toList());
		List<SWkpHistRcImported> wplHists = require.findBySid(employeeIds, GeneralDate.today());

		List<StampCard> stampCards = require.getByCardNoAndContractCode(employeeIds);

		List<EmployeeDto> empDtos = require.getByListSID(employeeIds);

		return convertToPerInfo(requestSetting.get().getCompanyCode(), wplHists, empDtos, stampCards);
	}

	// [pvt-1] 個人情報名称送信に変換
	private static List<SendPerInfoName> convertToPerInfo(String companyCode, List<SWkpHistRcImported> wplHists,
			List<EmployeeDto> empDtos, List<StampCard> stampCards) {
		Map<String, String> mapWplHist = wplHists.stream()
				.collect(Collectors.toMap(x -> x.getEmployeeId(), x -> x.getWorkplaceCode(), (x, y) -> x));

		Map<String, EmployeeDto> mapEmpDto = empDtos.stream()
				.collect(Collectors.toMap(x -> x.getSid(), x -> x, (x, y) -> x));
		return stampCards.stream().map(x -> {
			EmployeeDto emp = mapEmpDto.getOrDefault(x.getEmployeeId(), null);
			return new SendPerInfoName(x.getStampCardId(),
					emp == null ? ""
							: emp.getBussinessName().length() > 20 ? emp.getBussinessName().substring(0, 20)
									: emp.getBussinessName(),
					mapWplHist.getOrDefault(x.getEmployeeId(), ""), "00", "0000", emp == null ? "" : emp.getScd());
		}).sorted((x, y) -> x.getPerCode().compareTo(y.getPerCode())).limit(1000).collect(Collectors.toList());

	}

	public static interface Require {

		// R-1] 所属職場の情報を取得する
		public List<SWkpHistRcImported> findBySid(List<String> employeeIds, GeneralDate baseDate);

		// [R-2] 打刻カードを取得する
		public List<StampCard> getByCardNoAndContractCode(List<String> employeeIds);

		// [R-3] 社員ID（List）から社員コードと表示名を取得
		public List<EmployeeDto> getByListSID(List<String> employeeIds);

		// [R-4]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

	}
}
