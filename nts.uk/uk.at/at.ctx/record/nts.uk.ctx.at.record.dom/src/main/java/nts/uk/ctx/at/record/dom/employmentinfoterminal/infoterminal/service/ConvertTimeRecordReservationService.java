package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ErrorNRCom;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.NRErrorType;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.ReservationReceptionData;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * @author ThanhNX
 *
 *         データタイムレコードを予約に変換する
 */
public class ConvertTimeRecordReservationService {

	private ConvertTimeRecordReservationService() {
	};

	// 変換する
	public static Optional<AtomTask> convertData(Require require, EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode, ReservationReceptionData reservReceptData) {

		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerminal(empInfoTerCode, contractCode);

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode);

		if (!empInfoTerOpt.isPresent() || !requestSetting.isPresent())
			return Optional.empty();

		try {

			// $就業情報端末.予約(require, @予約受信データ)
			Pair<StampRecord, AtomTask> pairStampAtomTask = empInfoTerOpt.get().createReservRecord(require,
					reservReceptData);

			if (!canCreateNewData(require, contractCode, reservReceptData))
				return Optional.empty();
			AtomTask atomTask = AtomTask.of(() -> {
				require.insert(pairStampAtomTask.getLeft());
				pairStampAtomTask.getRight().run();
			});

			return Optional.of(atomTask);
		} catch (RuntimeException ex) {
			BusinessException bEx = (BusinessException) ex;
			// 処理にエラーがある場合、別の申請受信データの処理を続行
			Optional<StampCard> stampCardOpt = require.getByCardNoAndContractCode(contractCode,
					new StampNumber(reservReceptData.getIdNumber()));
			if (!stampCardOpt.isPresent())
				return Optional.empty();
			AtomTask atomTaskEx = AtomTask.of(() -> {
				require.insert(new ErrorNRCom.ErrorNRComBuilder(IdentifierUtil.randomUniqueId(), empInfoTerCode,
						requestSetting.get().getCompanyId(), new EmployeeId(stampCardOpt.get().getEmployeeId()),
						GeneralDateTime.now(), Optional.of(reservReceptData.getDateTime()))
								.typeError(NRErrorType.RESERVATION).createMessage(new MessageDisplay(bEx.getMessage()))
								.build());
			});
			return Optional.of(atomTaskEx);
		}
	}

	// [pvt-1] 新しいを作成することができる
	private static boolean canCreateNewData(Require require, ContractCode contractCode,
			ReservationReceptionData reservReceptData) {

		Optional<StampRecord> stampRecord = require.getStampRecord(contractCode,
				new StampNumber(reservReceptData.getIdNumber()), reservReceptData.getDateTime());
		return !stampRecord.isPresent();
	}

	public static interface Require extends BentoReserveService.Require {

		// [R-1]就業情報端末を取得する
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

		// [R-2]打刻記録を取得する
		public Optional<StampRecord> getStampRecord(ContractCode contractCode, StampNumber stampNumber,
				GeneralDateTime dateTime);

		// [R-3]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode);

		// [R-4] エラーNR-通信を作る
		public void insert(ErrorNRCom errorNR);

		// [R-5] insert(打刻記録)
		public void insert(StampRecord stampRecord);

		// [R-6]打刻カードを取得する
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber);

	}
}
