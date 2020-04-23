package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ErrorNRCom;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectProcessService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;

/**
 * @author ThanhNX
 * 
 *         データタイムレコードを打刻に変換する
 */
public class ConvertTimeRecordStampService {

	// 変換する
	public static Pair<Optional<AtomTask>, Optional<StampDataReflectResult>> convertData(Require require,
			EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode, StampReceptionData stampReceptData) {

		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerminal(empInfoTerCode, contractCode);

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode);

		// $就業情報端末 = Optional.empty() || $タイムレコードのﾘｸｴｽﾄ設定 = Optional.empty()
		if (!empInfoTerOpt.isPresent() || !requestSetting.isPresent())
			return Pair.of(Optional.empty(), Optional.empty());

		// $就業情報端末.打刻(打刻受信データ)
		Pair<Stamp, StampRecord> stamp = empInfoTerOpt.get().createStamp(stampReceptData);

		// [pvt-1] 新しいを作成することがでる(require, 契約コード, @打刻受信データ);
		if (!canCreateNewData(require, contractCode, stampReceptData))
			return Pair.of(Optional.empty(), Optional.empty());

		Optional<StampCard> stampCard = require.getByCardNoAndContractCode(contractCode,
				new StampNumber(stampReceptData.getIdNumber()));

		if (!stampCard.isPresent())
			return Pair.of(Optional.empty(), Optional.empty());

		StampDataReflectResult strampReflectResult = StampDataReflectProcessService.reflect(require,
				Optional.of(stampCard.get().getEmployeeId()), stamp.getRight(), Optional.of(stamp.getLeft()));

		// TODO: 処理にエラーがある場合、別の申請受信データの処理を続行
		return Pair.of(Optional.empty(), Optional.of(strampReflectResult));
	}

	// [pvt-1] 新しいを作成することができる
	private static boolean canCreateNewData(Require require, ContractCode contractCode,
			StampReceptionData stampReceptData) {

		Optional<StampRecord> stampRecord = require.getStampRecord(contractCode,
				new StampNumber(stampReceptData.getIdNumber()), stampReceptData.getDateTime());
		return !stampRecord.isPresent();
	}

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
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode);

		// [R-5] エラーNR-通信を取得する
		public void insert(ErrorNRCom errorNR);
	}
}
