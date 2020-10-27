package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

/**
 * @author ThanhNX
 * 
 *         1.未相殺の代休(確定)を取得する
 * 
 *         Refactor
 */
public class GetUnbalanceLeaveFixed {

	private GetUnbalanceLeaveFixed() {
	};

	public static List<AccumulationAbsenceDetail> getUnbalanceUnused(Require require, String companyId,
			String employeeId, GeneralDate startDateAggr, GeneralDate endDateAggr,
			FixedManagementDataMonth fixManaDataMonth) {
		// アルゴリズム「確定代休から未相殺の代休を取得する」を実行する
		List<CompensatoryDayOffManaData> lstUnBalPay = getUnbalancePayment(require, companyId, employeeId,
				startDateAggr);
		addDataFixManaMonth(fixManaDataMonth, lstUnBalPay);
		if (lstUnBalPay.isEmpty())
			return Collections.emptyList();

		// アルゴリズム「暫定休出と紐付けをしない確定代休を取得する」を実行する
		return lstUnBalPay.stream().map(x -> acquireTemporaryHoliday(require, x)).filter(x -> x.isPresent())
				.map(x -> x.get()).collect(Collectors.toList());
	}

	// 1-1.確定代休から未相殺の代休を取得する
	public static List<CompensatoryDayOffManaData> getUnbalancePayment(Require require, String cid, String sid,
			GeneralDate startDate) {
		// ドメインモデル「代休管理データ」
		List<CompensatoryDayOffManaData> lstDayOffConfirm = require.getBySidYmd(cid, sid, startDate);

		// 取得した「代休管理データ」をを未相殺のドメインモデル「代休管理データ」として追加する
		if (lstDayOffConfirm.size() > 0)
			return lstDayOffConfirm;
		return new ArrayList<>();

	}

	// 1-3.暫定休出と紐付けをしない確定代休を取得する
	public static Optional<AccumulationAbsenceDetail> acquireTemporaryHoliday(Require require,
			CompensatoryDayOffManaData unBalPay) {
		// ドメインモデル「休出代休紐付け管理」を取得する(get domain model 「休出代休紐付け管理」)
		List<LeaveComDayOffManagement> interimTyingData = new ArrayList<>();
		if (!unBalPay.getDayOffDate().isUnknownDate() && unBalPay.getDayOffDate().getDayoffDate().isPresent()) {
			interimTyingData.addAll(
					require.getBycomDayOffID(unBalPay.getSID(), unBalPay.getDayOffDate().getDayoffDate().get()));
		}
	
		// 未相殺日数と未相殺時間を設定する
		double unOffsetDays = unBalPay.getRemainDays().v();
		int unOffsetTimes = unBalPay.getRemainTimes().v();

		// 未相殺日数と未相殺時間を設定する
		if (!interimTyingData.isEmpty()) {
			for (LeaveComDayOffManagement dt : interimTyingData) {
				unOffsetDays -= dt.getAssocialInfo().getDayNumberUsed().v();
			}
		}
		// 未相殺日数と未相殺時間をチェックする
		if (unOffsetDays <= 0 && unOffsetTimes <= 0) {
			return Optional.empty();
		}

		AccumulationAbsenceDetail result = new AccuVacationBuilder(unBalPay.getSID(), unBalPay.getDayOffDate(),
				OccurrenceDigClass.DIGESTION, MngDataStatus.CONFIRMED, unBalPay.getComDayOffID())
						.numberOccurren(
								new NumberConsecuVacation(new ManagementDataRemainUnit(unBalPay.getRequireDays().v()),
										Optional.of(new AttendanceTime(unBalPay.getRequiredTimes().v()))))
						.unbalanceNumber(
								new NumberConsecuVacation(new ManagementDataRemainUnit(unOffsetDays),
										Optional.of(new AttendanceTime(unOffsetTimes))))
						.build();
		return Optional.of(result);
	}

	// 追加用確定管理データをリストに追加する
	public static void addDataFixManaMonth(FixedManagementDataMonth fixManaDataMonth,
			List<CompensatoryDayOffManaData> lstUnBalPay) {
		
		lstUnBalPay.addAll(fixManaDataMonth.getCompenDayOffMagData());

	}

	public static interface Require {

		// ComDayOffManaDataRepository;
		public List<CompensatoryDayOffManaData> getBySidYmd(String companyId, String employeeId,
				GeneralDate startDateAggr);
         //LeaveComDayOffManaRepository
		public List<LeaveComDayOffManagement> getBycomDayOffID(String sid,  GeneralDate digestDate);
	}

}
