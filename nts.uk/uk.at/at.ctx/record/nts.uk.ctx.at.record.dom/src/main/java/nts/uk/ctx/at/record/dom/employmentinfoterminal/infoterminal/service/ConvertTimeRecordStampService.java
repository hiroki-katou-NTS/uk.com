package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectProcessService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author ThanhNX
 * 
 *         データタイムレコードを打刻に変換する
 */
public class ConvertTimeRecordStampService {

	private ConvertTimeRecordStampService() {
	};

	// 変換する
	public static Optional<StampDataReflectResult> convertData(Require require,
			EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode, StampReceptionData stampReceptData) {

		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerminal(empInfoTerCode, contractCode);

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!empInfoTerOpt.isPresent() || !requestSetting.isPresent())
			return Optional.empty();

		// $就業情報端末.打刻(打刻受信データ)
		Pair<Optional<Stamp>, AtomTask> stamp = createStamp(require, empInfoTerOpt, empInfoTerCode, contractCode, stampReceptData);
		
		if(!stamp.getLeft().isPresent()) {
			return Optional.empty();
		}
	
		Optional<String> employeeId = getEmployeeId(require, contractCode,
				new StampNumber(stampReceptData.getIdNumber()));
		Optional<String> cid = getCompanyId(require, employeeId);
		
		return createDailyData(require, cid, employeeId, stamp.getLeft(), stamp.getRight());

		// TODO: 処理にエラーがある場合、別の申請受信データの処理を続行
//		if (!strampReflectResult.getReflectDate().isPresent()) {
//			Optional<TopPageAlarmEmpInfoTer> alEmpTer = createLogEmpTer(require, stampCard.get().getEmployeeId(),
//					requestSetting.get().getCompanyId().v(), empInfoTerCode.v(), stampReceptData.getIdNumber(), "");
//			if (alEmpTer.isPresent())
//				require.insertLogAll(alEmpTer.get());
//		}
	}

	// [pvt-1] 新しいを作成することができる
	private static boolean canCreateNewData(Require require, ContractCode contractCode,
			StampReceptionData stampReceptData) {

		Optional<StampRecord> stampRecord = require.getStampRecord(contractCode,
				new StampNumber(stampReceptData.getIdNumber()), stampReceptData.getDateTime());
		return !stampRecord.isPresent();
	}
	
	private static Pair<Optional<Stamp>, AtomTask> createStamp(Require require, Optional<EmpInfoTerminal> empInfoTerOpt,
			EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode, StampReceptionData stampReceptData){
		Optional<Pair<Stamp, StampRecord>> stamp = empInfoTerOpt.get().getCreateStampInfo().createStamp(contractCode, stampReceptData, empInfoTerCode);
		if(!stamp.isPresent()) return Pair.of(Optional.empty(), AtomTask.none());
		
		if (!canCreateNewData(require, contractCode, stampReceptData))
			return Pair.of(Optional.empty(), AtomTask.none());
		AtomTask stampReflectResult = StampDataReflectProcessService.registerStamp(require,
				stamp.get().getRight(), Optional.of(stamp.get().getLeft()));
		return  Pair.of(Optional.of(stamp.get().getLeft()), stampReflectResult);
	}

	private static Optional<String> getEmployeeId(Require require, ContractCode contractCode, StampNumber stampNumber) {
		Optional<StampCard> stampCard = require.getByCardNoAndContractCode(contractCode,
				new StampNumber(stampNumber.v()));
		return stampCard.map(x -> x.getEmployeeId());
	}

	private static Optional<String> getCompanyId(Require require, Optional<String> employeeId) {
		return employeeId.map(sid -> require.getEmpData(Arrays.asList(employeeId.get())).stream().findFirst()
				.map(x -> x.getCompanyId()).orElse(null));

	}
	
	private static Optional<StampDataReflectResult> createDailyData(Require require, Optional<String> cid,
			Optional<String> sid, Optional<Stamp> stamp, AtomTask atomTask) {

		if (!sid.isPresent() || !cid.isPresent()) {
			return Optional.of(new StampDataReflectResult(Optional.empty(), atomTask));
		}
		// $反映対象日 = [prv-3] いつの日別実績に反映するか(require, 社員ID, 打刻)
		Optional<GeneralDate> reflectDate = StampDataReflectProcessService.reflectDailyResult(require, cid.get(), sid,
				stamp);
		if (reflectDate.isPresent()) {
			val domdaily = StampDataReflectProcessService.updateStampToDaily(require, cid.get(), sid.get(),
					reflectDate.get(), stamp.get());

			if (domdaily.isPresent()) {
				AtomTask task = atomTask.then(() -> {
					require.addAllDomain(domdaily.get());
				});
				return Optional.of(new StampDataReflectResult(reflectDate, task));
			}
		}
		return Optional.of(new StampDataReflectResult(Optional.empty(), atomTask));
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

	public static interface Require extends StampDataReflectProcessService.Require, StampDataReflectProcessService.Require2 {

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
		
		//[R-7] 日別実績を更新する
		//DailyRecordAdUpService - 日別実績を登録する
		void addAllDomain(IntegrationOfDaily domain);
		
		// [R-8] 社員IDListから管理情報を取得する
		//GetMngInfoFromEmpIDListAdapter
		List<EmpDataImport> getEmpData(List<String> empIDList); 
	}
}
