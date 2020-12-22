package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ProcessDataTemporary;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

/**
 * @author ThanhNX
 *
 *         3.未相殺の代休(暫定)を取得する refactor
 */
public class GetUnbalancedLeaveTemporary {

	private GetUnbalancedLeaveTemporary() {
	};

	// 3.未相殺の代休(暫定)を取得する
	public static List<AccumulationAbsenceDetail> process(Require require, BreakDayOffRemainMngRefactParam param) {

		List<AccumulationAbsenceDetail> result = new ArrayList<>();
		List<InterimRemain> lstInterimDayoff = new ArrayList<>();
		List<InterimDayOffMng> lstDayoffMng = new ArrayList<>();

		if (param.isModeMonth()) {
			// INPUT．上書き用の暫定管理データを受け取る
			// 代休
			lstInterimDayoff = param.getInterimMng().stream()
					.filter(x -> x.getYmd().afterOrEquals(param.getDateData().start())
							&& x.getYmd().beforeOrEquals(param.getDateData().end())
							&& x.getRemainType() == RemainType.SUBHOLIDAY)
					.collect(Collectors.toList());
			lstInterimDayoff.stream().forEach(a -> {
				List<InterimDayOffMng> temp = param.getDayOffMng().stream()
						.filter(y -> y.getDayOffManaId().equals(a.getRemainManaID())).collect(Collectors.toList());
				lstDayoffMng.addAll(temp);
			});

		} else {
			// ドメインモデル「暫定代休管理データ」を取得する
			lstInterimDayoff
					.addAll(require.getRemainBySidPriod(param.getSid(), param.getDateData(), RemainType.SUBHOLIDAY));
			lstDayoffMng.addAll(require.getDayOffBySidPeriod(param.getSid(), param.getDateData()));
		}

		// 対象期間のドメインモデル「暫定代休管理データ」を上書き用の暫定管理データに置き換える
		ProcessDataTemporary.processOverride(param, param.getDayOffMng(), lstInterimDayoff, lstDayoffMng);

		// アルゴリズム「休出と紐付けをしない代休を取得する」を実行する
		for (InterimDayOffMng interimDayOffMng : lstDayoffMng) {
			// アルゴリズム「休出と紐付けをしない代休を取得する」を実行する
			InterimRemain interimData = lstInterimDayoff.stream()
					.filter(x -> x.getRemainManaID().equals(interimDayOffMng.getDayOffManaId()))
					.collect(Collectors.toList()).get(0);
			AccumulationAbsenceDetail outData = getNotTypeBreak(require, Pair.of(interimData, interimDayOffMng));
			result.add(outData);
		}
		return result;
	}

	// 3-1.休出と紐付けをしない代休を取得する
	public static AccumulationAbsenceDetail getNotTypeBreak(Require require,
			Pair<InterimRemain, InterimDayOffMng> interimDay) {

		// ドメインモデル「休出代休紐付け管理」を取得する(get domain model 「休出代休紐付け管理」)
		List<LeaveComDayOffManagement> interimTyingData = require.getBycomDayOffID(interimDay.getLeft().getSID(),
				interimDay.getLeft().getYmd());

		double unOffsetDays = interimDay.getRight().getRequiredDay().v();
		Integer unOffsetTimes = interimDay.getRight().getRequiredTime().v();
		// 未相殺日数と未相殺時間を設定する
		for (LeaveComDayOffManagement interimMng : interimTyingData) {
			unOffsetDays -= interimMng.getAssocialInfo().getDayNumberUsed().v();
		}

		MngDataStatus dataAtr = MngDataStatus.NOTREFLECTAPP;
		if (interimDay.getLeft().getCreatorAtr() == CreateAtr.SCHEDULE) {
			dataAtr = MngDataStatus.SCHEDULE;
		} else if (interimDay.getLeft().getCreatorAtr() == CreateAtr.RECORD) {
			dataAtr = MngDataStatus.RECORD;
		}

		return new AccuVacationBuilder(interimDay.getLeft().getSID(),
				new CompensatoryDayoffDate(false, Optional.of(interimDay.getLeft().getYmd())),
				OccurrenceDigClass.DIGESTION, dataAtr, interimDay.getLeft().getRemainManaID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(interimDay.getRight().getRequiredDay().v()),
								Optional.of(new AttendanceTime(interimDay.getRight().getRequiredTime().v()))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(unOffsetDays),
								Optional.of(new AttendanceTime(unOffsetTimes))))
						.build();

	}

	public static interface Require {

		// 暫定残数管理データ
		// InterimRemainRepository
		List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData, RemainType remainType);

		// 暫定代休管理データ
		// InterimBreakDayOffMngRepository
		List<InterimDayOffMng> getDayOffBySidPeriod(String sid, DatePeriod period);

		//ドメインモデル「休出代休紐付け管理」を取得する
		 //LeaveComDayOffManaRepository
		List<LeaveComDayOffManagement> getBycomDayOffID(String sid,  GeneralDate digestDate);

	}

}
