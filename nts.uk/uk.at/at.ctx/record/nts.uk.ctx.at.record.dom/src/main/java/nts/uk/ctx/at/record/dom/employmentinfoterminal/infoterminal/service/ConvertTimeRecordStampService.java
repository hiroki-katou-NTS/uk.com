package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.RogerFlag;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlEmpInfoTerDetail;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmManagerTr;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectProcessService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * @author ThanhNX
 * 
 *         データタイムレコードを打刻に変換する
 */
public class ConvertTimeRecordStampService {

	private ConvertTimeRecordStampService() {
	};

	// 変換する
	public static Pair<Optional<AtomTask>, Optional<StampDataReflectResult>> convertData(Require require,
			EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode, StampReceptionData stampReceptData) {

		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerminal(empInfoTerCode, contractCode);

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!empInfoTerOpt.isPresent() || !requestSetting.isPresent())
			return Pair.of(Optional.empty(), Optional.empty());

		// $就業情報端末.打刻(打刻受信データ)
		Pair<Stamp, StampRecord> stamp = empInfoTerOpt.get().createStamp(stampReceptData);

		if (!canCreateNewData(require, contractCode, stampReceptData))
			return Pair.of(Optional.empty(), Optional.empty());

		Optional<StampCard> stampCard = require.getByCardNoAndContractCode(contractCode,
				new StampNumber(stampReceptData.getIdNumber()));

		if (!stampCard.isPresent())
			return Pair.of(Optional.empty(), Optional.empty());

		StampDataReflectResult strampReflectResult = StampDataReflectProcessService.reflect(require,
				Optional.of(stampCard.get().getEmployeeId()), stamp.getRight(), Optional.of(stamp.getLeft()));

		// TODO: 処理にエラーがある場合、別の申請受信データの処理を続行
//		if (!strampReflectResult.getReflectDate().isPresent()) {
//			Optional<TopPageAlarmEmpInfoTer> alEmpTer = createLogEmpTer(require, stampCard.get().getEmployeeId(),
//					requestSetting.get().getCompanyId().v(), empInfoTerCode.v(), stampReceptData.getIdNumber(), "");
//			if (alEmpTer.isPresent())
//				require.insertLogAll(alEmpTer.get());
//		}

		return Pair.of(Optional.empty(), Optional.of(strampReflectResult));
	}

	// [pvt-1] 新しいを作成することができる
	private static boolean canCreateNewData(Require require, ContractCode contractCode,
			StampReceptionData stampReceptData) {

		Optional<StampRecord> stampRecord = require.getStampRecord(contractCode,
				new StampNumber(stampReceptData.getIdNumber()), stampReceptData.getDateTime());
		return !stampRecord.isPresent();
	}

	// [pvt-2] 就業情報端末通信用トップページアラームを作る
//	private static Optional<TopPageAlarmEmpInfoTer> createLogEmpTer(Require require, String sid, String companyId,
//			Integer terCode, String cardNumber, String message) {
//		List<String> lstSidApproval = require.getListEmpID(companyId, GeneralDate.today());
//
//		if (lstSidApproval.isEmpty())
//			return Optional.empty();
//
//		List<TopPageAlarmManagerTr> lstManagerTr = lstSidApproval.stream()
//				.map(x -> new TopPageAlarmManagerTr(x, RogerFlag.ALREADY_READ)).collect(Collectors.toList());
//		TopPageAlEmpInfoTerDetail detail = new TopPageAlEmpInfoTerDetail(0, message, new EmployeeId(sid),
//				new StampNumber(cardNumber));
//
//		return Optional.of(new TopPageAlarmEmpInfoTer(companyId, lstManagerTr, new EmpInfoTerminalCode(terCode),
//				Arrays.asList(detail)));
//
//	}

	public static interface Require extends StampDataReflectProcessService.Require {

		// [R-1]就業情報端末を取得する
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

		// [R-2]打刻記録を取得する
		public Optional<StampRecord> getStampRecord(ContractCode contractCode, StampNumber stampNumber,
				GeneralDateTime dateTime);

		// [R-3]打刻カードを取得する
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber);

		// [R-4]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

		// [R-5] 就業情報端末通信用トップページアラームを作る
		public void insertLogAll(TopPageAlarmEmpInfoTer alEmpInfo);

		// [R-6] 就業担当者の社員ID（List）を取得する
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate);
	}
}
