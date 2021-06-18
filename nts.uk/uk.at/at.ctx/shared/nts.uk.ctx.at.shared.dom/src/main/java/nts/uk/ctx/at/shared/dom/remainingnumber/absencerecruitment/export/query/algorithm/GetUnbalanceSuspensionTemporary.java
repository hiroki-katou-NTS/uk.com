package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ProcessDataTemporary;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;

/**
 * @author ThanhNX
 *
 *
 *         3.未相殺の振休(暫定)を取得する
 */
public class GetUnbalanceSuspensionTemporary {

	private GetUnbalanceSuspensionTemporary() {
	};

	// 3.未相殺の振休(暫定)を取得する
	public static List<AccumulationAbsenceDetail> process(Require require, AbsRecMngInPeriodRefactParamInput input) {

		List<InterimAbsMng> lstAbsMng = new ArrayList<>();
		//List<InterimRemain> lstInterimMng = new ArrayList<>();
		List<AccumulationAbsenceDetail> lstOutput = new ArrayList<>();

		// INPUT．モードをチェックする
		if (input.isMode()) {
			// INPUT．上書き用の暫定管理データを受け取る
			// INPUT．上書き用の暫定管理データから「暫定振休管理データ」を取得する
			lstAbsMng.addAll(input.getUseAbsMng().stream()
					.filter(x -> x.getSID().equals(input.getSid())
							&& x.getYmd().afterOrEquals(input.getDateData().start())
							&& x.getYmd().beforeOrEquals(input.getDateData().end())
							&& x.getRemainType() == RemainType.PAUSE)
					.collect(Collectors.toList()));

			// INPUT．上書き用の暫定管理データから「暫定振休管理データ」を取得する

		} else {
			// ドメインモデル「暫定振休管理データ」を取得する
			lstAbsMng.addAll(require.getAbsBySidDatePeriod(input.getSid(), input.getDateData()));

		}

		// 対象期間のドメインモデル「暫定振休管理データ」を上書き用の暫定管理データに置き換える
		ProcessDataTemporary.processOverride(input, input.getUseAbsMng(), lstAbsMng);

		// 取得した件数をチェックする
		for (InterimAbsMng interimAbsMng : lstAbsMng) {
//			Optional<InterimRemain> remainData = lstInterimMng.stream()
//					.filter(x -> x.getRemainManaID().equals(interimAbsMng.getRemainManaID())).findFirst();
			//if (absMng.isPresent()) {
				// アルゴリズム「振出と紐付けをしない振休を取得する」を実行する
				lstOutput.add(getNotTypeRec(require, interimAbsMng));
			//}
		}
		return lstOutput;

	}

	// 3-1.振出と紐付けをしない振休を取得する
	public static AccumulationAbsenceDetail getNotTypeRec(Require require, InterimAbsMng absMng) {

		// ドメインモデル「暫定振出振休紐付け管理」を取得する
		List<PayoutSubofHDManagement> lstInterimRecAbsMng = require.getBySubId(absMng.getSID(), absMng.getYmd());
		double unOffsetDays = absMng.getRequeiredDays().v();
		for (PayoutSubofHDManagement recAbsData : lstInterimRecAbsMng) {
			unOffsetDays -= recAbsData.getAssocialInfo().getDayNumberUsed().v();
		}

		MngDataStatus dataAtr = MngDataStatus.NOTREFLECTAPP;
		if (absMng.getCreatorAtr() == CreateAtr.SCHEDULE) {
			dataAtr = MngDataStatus.SCHEDULE;
		} else if (absMng.getCreatorAtr() == CreateAtr.RECORD) {
			dataAtr = MngDataStatus.RECORD;
		}

		CompensatoryDayoffDate date = new CompensatoryDayoffDate(false, Optional.of(absMng.getYmd()));

		AccumulationAbsenceDetail result = new AccuVacationBuilder(absMng.getSID(), date,
				OccurrenceDigClass.DIGESTION, dataAtr, absMng.getRemainManaID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(absMng.getRequeiredDays().v()), Optional.empty()))
						.unbalanceNumber(
								new NumberConsecuVacation(new ManagementDataRemainUnit(unOffsetDays), Optional.empty()))
						.build();

		return result;
	}

	public static interface Require {

		// InterimRecAbasMngRepository
		List<InterimAbsMng> getAbsBySidDatePeriod(String sid, DatePeriod period);

		// PayoutSubofHDManaRepository
		List<PayoutSubofHDManagement> getBySubId(String sid, GeneralDate digestDate);
	}

}
