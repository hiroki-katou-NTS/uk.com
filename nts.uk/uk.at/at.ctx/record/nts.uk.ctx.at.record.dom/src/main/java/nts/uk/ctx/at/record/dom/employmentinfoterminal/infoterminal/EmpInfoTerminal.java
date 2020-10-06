package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.LeaveCategory;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.ReservationReceptionData;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.ConvertTimeRecordReservationService;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveService;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampTypeDisplay;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * @author ThanhNX
 *
 *         就業情報端末
 */
public class EmpInfoTerminal implements DomainAggregate {

	/**
	 * IPアドレス
	 */
	@Getter
	private final IPAddress ipAddress;

	/**
	 * MACアドレス
	 */
	@Getter
	private final MacAddress macAddress;

	/**
	 * コード
	 */
	@Getter
	private final EmpInfoTerminalCode empInfoTerCode;

	/**
	 * シリアルNO
	 */
	@Getter
	private final EmpInfoTerSerialNo terSerialNo;

	/**
	 * 名称
	 */
	@Getter
	private final EmpInfoTerminalName empInfoTerName;

	/**
	 * 契約コード
	 */
	@Getter
	private final ContractCode contractCode;

	/**
	 * 打刻情報の作成
	 */
	@Getter
	private final CreateStampInfo createStampInfo;

	/**
	 * 機種
	 */
	@Getter
	private final ModelEmpInfoTer modelEmpInfoTer;

	/**
	 * 監視間隔時間
	 */
	@Getter
	private final MonitorIntervalTime intervalTime;

	// 就業情報端末からの電文解読 not use

	public EmpInfoTerminal(EmpInfoTerminalBuilder builder) {
		super();
		this.ipAddress = builder.ipAddress;
		this.macAddress = builder.macAddress;
		this.empInfoTerCode = builder.empInfoTerCode;
		this.terSerialNo = builder.terSerialNo;
		this.empInfoTerName = builder.empInfoTerName;
		this.contractCode = builder.contractCode;
		this.createStampInfo = builder.createStampInfo;
		this.modelEmpInfoTer = builder.modelEmpInfoTer;
		this.intervalTime = builder.intervalTime;
	}

	// [1] 打刻
	public Pair<Stamp, StampRecord> createStamp(StampReceptionData recept) {
		StampRecord stampRecord = createStampRecord(recept);
		// 実績への反映内容
		RefectActualResult refActualResults = new RefectActualResult(
				recept.getSupportCode().isEmpty() ? null : recept.getSupportCode(),
				createStampInfo.getWorkLocationCd().orElse(null),
				(recept.getLeavingCategory().equals(LeaveCategory.GO_OUT.value)
						|| recept.getLeavingCategory().equals(LeaveCategory.RETURN.value)
						|| recept.getShift().isEmpty()) ? null : new WorkTimeCode(recept.getShift()),
				(recept.getOverTimeHours().isEmpty() || recept.getMidnightTime().isEmpty()) ? null
						: new OvertimeDeclaration(new AttendanceTime(Integer.parseInt(recept.getOverTimeHours())),
								new AttendanceTime(Integer.parseInt(recept.getMidnightTime()))));
		// 打刻する方法
		Relieve relieve = new Relieve(recept.convertAuthcMethod(), StampMeans.TIME_CLOCK);

		// 打刻種類
		Stamp stamp = new Stamp(contractCode, 
				new StampNumber(recept.getIdNumber()), recept.getDateTime(), relieve,
				recept.createStampType(this), refActualResults, Optional.empty());
		return Pair.of(stamp, stampRecord);
	}

	// [２] 予約
	public Pair<StampRecord, AtomTask> createReservRecord(ConvertTimeRecordReservationService.Require require,
			ReservationReceptionData reservReceptData) {
		StampRecord stampRecord = createStampRecord(reservReceptData);
		AtomTask createReserv = createReserv(require, reservReceptData);
		return Pair.of(stampRecord, createReserv);
	}

	// [pvt-1] 打刻の打刻記録を作成
		private StampRecord createStampRecord(ReservationReceptionData reservReceptData) {
			// TODO: contractCode
			return new StampRecord(new ContractCode(""), new StampNumber(reservReceptData.getIdNumber()),
					reservReceptData.getDateTime(), new StampTypeDisplay(""), Optional.of(empInfoTerCode));
		}

		// [pvt-2] 予約の打刻記録を作成
		private StampRecord createStampRecord(StampReceptionData recept) {
			// TODO: contractCode
			return new StampRecord(new ContractCode(""), new StampNumber(recept.getIdNumber()), recept.getDateTime(),
					new StampTypeDisplay(""), Optional.of(empInfoTerCode));
		}

	// [pvt-3] 弁当予約を作成
	private AtomTask createReserv(ConvertTimeRecordReservationService.Require require,
			ReservationReceptionData reserv) {
		Map<Integer, BentoReservationCount> bentoDetails = new HashMap<>();
		bentoDetails.put(reserv.getBentoFrame(), new BentoReservationCount(Integer.parseInt(reserv.getQuantity())));
		return BentoReserveService.reserve(require, new ReservationRegisterInfo(reserv.getIdNumber()),
				new ReservationDate(reserv.getDateTime().toDate(), ReservationClosingTimeFrame.FRAME1),
				reserv.getDateTime(), bentoDetails, Optional.empty());
	}

	public static class EmpInfoTerminalBuilder {
		/**
		 * IPアドレス
		 */
		private IPAddress ipAddress;

		/**
		 * MACアドレス
		 */
		private MacAddress macAddress;

		/**
		 * コード
		 */
		private EmpInfoTerminalCode empInfoTerCode;

		/**
		 * シリアルNO
		 */
		private EmpInfoTerSerialNo terSerialNo;

		/**
		 * 名称
		 */
		private EmpInfoTerminalName empInfoTerName;

		/**
		 * 契約コード
		 */
		private ContractCode contractCode;

		/**
		 * 打刻情報の作成
		 */
		private CreateStampInfo createStampInfo;

		/**
		 * 機種
		 */
		private ModelEmpInfoTer modelEmpInfoTer;

		/**
		 * 監視間隔時間
		 */
		private MonitorIntervalTime intervalTime;

		public EmpInfoTerminalBuilder(IPAddress ipAddress, MacAddress macAddress, EmpInfoTerminalCode empInfoTerCode,
				EmpInfoTerSerialNo terSerialNo, EmpInfoTerminalName empInfoTerName, ContractCode contractCode) {
			this.ipAddress = ipAddress;
			this.macAddress = macAddress;
			this.empInfoTerCode = empInfoTerCode;
			this.terSerialNo = terSerialNo;
			this.empInfoTerName = empInfoTerName;
			this.contractCode = contractCode;
		}

		public EmpInfoTerminalBuilder createStampInfo(CreateStampInfo createStampInfo) {
			this.createStampInfo = createStampInfo;
			return this;
		}

		public EmpInfoTerminalBuilder modelEmpInfoTer(ModelEmpInfoTer modelEmpInfoTer) {
			this.modelEmpInfoTer = modelEmpInfoTer;
			return this;
		}

		public EmpInfoTerminalBuilder intervalTime(MonitorIntervalTime intervalTime) {
			this.intervalTime = intervalTime;
			return this;
		}

		public EmpInfoTerminal build() {
			return new EmpInfoTerminal(this);
		}
	}
}
