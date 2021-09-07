package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.LeaveCategory;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampTypeDisplay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.WorkInformationStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author ThanhNX
 * 打刻情報の作成
 */
@Value
public class CreateStampInfo implements DomainValue {

	/**
	 * 打刻変換
	 */
	private final StampInfoConversion stampInfoConver;

	/**
	 * 設置場所コード
	 */
	private final Optional<WorkLocationCD> workLocationCd;

	/**
	 * 設置職場ID
	 */
	private final Optional<WorkplaceId> workPlaceId;

	// [1] 打刻
	public Optional<Pair<Stamp, StampRecord>> createStamp(ContractCode contractCode, StampReceptionData recept,  EmpInfoTerminalCode empInfoTerCode) {
		// 実績への反映内容
		RefectActualResult refActualResults  = new RefectActualResult(
				new WorkInformationStamp(workPlaceId.map(x -> x.v()), Optional.of(empInfoTerCode), this.getWorkLocationCd(),
						Optional.ofNullable(recept.getSupportCode().isEmpty() ? null : new SupportCardNumber(Integer.parseInt(recept.getSupportCode())))),
				(recept.getLeavingCategory().equals(LeaveCategory.GO_OUT.value)
						|| recept.getLeavingCategory().equals(LeaveCategory.RETURN.value)
						|| recept.getShift().isEmpty()) ? null : new WorkTimeCode(recept.getShift()),
				(recept.getOverTimeHours().isEmpty() || recept.getMidnightTime().isEmpty()) ? null
				: new OvertimeDeclaration(new AttendanceTime(Integer.parseInt(recept.getOverTimeHours())),
						new AttendanceTime(Integer.parseInt(recept.getMidnightTime()))));
		// 打刻する方法
		Relieve relieve = new Relieve(recept.convertAuthcMethod(), StampMeans.TIME_CLOCK);

		// 打刻種類
		val stampType = createStampType(recept);
		if (!stampType.isPresent())
			return Optional.empty();

		Stamp stamp = new Stamp(contractCode, new StampNumber(recept.getIdNumber().trim()), recept.getDateTime(), relieve,
				stampType.get(), refActualResults, Optional.empty());

		StampRecord stampRecord = createStampRecord(contractCode, recept, stamp);
		return Optional.of(Pair.of(stamp, stampRecord));
	}

	// [pvt-1] 打刻種類を作成する
	private Optional<StampType> createStampType(StampReceptionData nrData) {
		String category = nrData.getLeavingCategory().trim();
		// 勤務種類を半休に変更する
		boolean changeHalfDay = false;
		if (category.equals(LeaveCategory.WORK_HALF.value) || category.equals(LeaveCategory.LEAVE_HALF.value)
				|| category.equals(LeaveCategory.WORK_HALF_ENTRANCE.value)) {
			changeHalfDay = true;
		}

		val leavCategory = LeaveCategory.valueStringOf(category);
		Optional<ChangeClockArt>  changeClockArt = (leavCategory == null ? Optional.empty() : this.stampInfoConver.convertFromNR(leavCategory));
		if (!changeClockArt.isPresent())
			return Optional.empty();

		return Optional.of(new StampType(changeHalfDay, leavCategory == LeaveCategory.GO_OUT ? convertReason(leavCategory, nrData.getShift()) : Optional.empty(),
				SetPreClockArt.NONE, changeClockArt.get(), nrData.convertChangeCalArt()));
	}

	// [pvt-2] 外出理由を取得
	public Optional<GoingOutReason> convertReason(LeaveCategory leavCategory, String shift) {
		// 外出理由
		GoingOutReason goOutArt = null;
		// if ＠設置場所コード.isPresent
		if (this.getWorkLocationCd().isPresent()) {
			// 置換する ＝ する && 打刻変換のタイプ = NRLの変換情報)
			if ((this.stampInfoConver instanceof NRConvertInfo)
					&& ((NRConvertInfo) this.stampInfoConver).getOutPlaceConvert().getReplace() == NotUseAtr.USE) {
				goOutArt = ((NRConvertInfo) this.stampInfoConver).getOutPlaceConvert().getGoOutReason()
						.map(x -> GoingOutReason.valueOf(x.value)).orElse(null);
			}
			// 打刻変換のタイプ = ｍsの変換情報)
			if ((this.stampInfoConver instanceof MSConversionInfo)) {
				goOutArt = ((MSConversionInfo) this.stampInfoConver).convertReason(leavCategory).orElse(null);
			}
			// 出退区分NR.value ＝’O’
		} else if (leavCategory == LeaveCategory.GO_OUT && !shift.isEmpty()) {
			goOutArt = GoingOutReason.valueOf(Integer.parseInt(shift.substring(0, 1)));
		}
		return Optional.ofNullable(goOutArt);
	}

	// [pvt-3] 打刻の打刻記録を作成
	private StampRecord createStampRecord(ContractCode contractCode, StampReceptionData recept, Stamp stamp) {
		ButtonType bt = new ButtonType(ReservationArt.NONE, Optional.of(stamp.getType()));
		return new StampRecord(contractCode, new StampNumber(recept.getIdNumber()), recept.getDateTime(),
				new StampTypeDisplay(bt.getStampTypeDisplay()));
	}
}
