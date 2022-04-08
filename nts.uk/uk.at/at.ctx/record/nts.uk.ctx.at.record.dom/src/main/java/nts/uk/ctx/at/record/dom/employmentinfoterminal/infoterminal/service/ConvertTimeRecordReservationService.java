package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.List;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.ReservationReceptionData;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;

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
			ContractCode contractCode, ReservationReceptionData reservReceptData, String companyID) {

		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerminal(empInfoTerCode, contractCode);

		if (!empInfoTerOpt.isPresent())
			return Optional.empty();

		try {
			boolean login = ConvertTimeRecordStampService.login(require, contractCode.v(),
					new StampNumber(reservReceptData.getIdNumber()));
			if(!login) {
				return Optional.empty();
			}
			// $就業情報端末.予約(require, @予約受信データ)
			AtomTask pairStampAtomTask = empInfoTerOpt.get().createReservRecord(require,
					reservReceptData, companyID);
			 return Optional.of(pairStampAtomTask);
		} catch (BusinessException bEx) {
			//bEx.printStackTrace();
			System.out.print(bEx.getMessage());
		    return Optional.empty();
		}
	}

	public static interface Require extends EmpInfoTerminal.Require, ConvertTimeRecordStampService.RequireLogin {

		// [R-1]就業情報端末を取得する
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

		// [R-3]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

		// [R-4] 就業情報端末通信用トップページアラームを作る
		public void insertLogAll(TopPageAlarmEmpInfoTer alEmpInfo);

		// [R-6]打刻カードを取得する
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber);

		// [R-7] 就業担当者の社員ID（List）を取得する
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate);

	}
}
