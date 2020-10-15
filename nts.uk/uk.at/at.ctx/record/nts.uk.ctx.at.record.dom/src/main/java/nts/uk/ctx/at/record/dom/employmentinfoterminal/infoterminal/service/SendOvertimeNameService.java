package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendOvertimeName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendOvertimeName.SendOvertimeDetail;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

/**
 * @author ThanhNX
 *
 *         残業・休日出勤をNRに 送信するデータに変換する
 */
public class SendOvertimeNameService {

	private SendOvertimeNameService() {
	};

	// [1] 各種名称送信に変換
	public static Optional<SendOvertimeName> send(Require require, EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
		
		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent() || !requestSetting.get().isOverTimeHoliday())
			return Optional.empty();

		List<OvertimeWorkFrame> lstOvertime = require.getAllOvertimeWorkFrame(requestSetting.get().getCompanyId().v());

		List<WorkdayoffFrame> lstWorkDay = require.getAllWorkdayoffFrame(requestSetting.get().getCompanyId().v());

		return Optional.of(convert(lstOvertime, lstWorkDay));

	}

	// [pvt-1] 時間外名称送信に変換

	private static SendOvertimeName convert(List<OvertimeWorkFrame> lstOvertime, List<WorkdayoffFrame> lstWorkDay) {

		List<SendOvertimeDetail> overtimes = lstOvertime.stream().map(x -> {
			return new SendOvertimeName.SendOvertimeDetail(String.valueOf(x.getOvertimeWorkFrNo().v()),
					x.getOvertimeWorkFrName().v());
		}).sorted((x, y) -> Integer.parseInt(x.getSendOvertimeNo()) - Integer.parseInt((y.getSendOvertimeNo()))).collect(Collectors.toList());

		List<SendOvertimeDetail> vacations = lstWorkDay.stream().map(x -> {
			return new SendOvertimeName.SendOvertimeDetail(String.valueOf(x.getWorkdayoffFrNo().v()),
					x.getWorkdayoffFrName().v());
		}).sorted((x, y) ->  Integer.parseInt(x.getSendOvertimeNo()) -  Integer.parseInt(y.getSendOvertimeNo())).collect(Collectors.toList());

		return new SendOvertimeName(overtimes, vacations);
	}

	public static interface Require {

		// [R-1] 残業枠の取得
		public List<OvertimeWorkFrame> getAllOvertimeWorkFrame(String companyId);

		// [R-2] 休出枠の取得
		public List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId);

		// [R-3]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
	}

}
