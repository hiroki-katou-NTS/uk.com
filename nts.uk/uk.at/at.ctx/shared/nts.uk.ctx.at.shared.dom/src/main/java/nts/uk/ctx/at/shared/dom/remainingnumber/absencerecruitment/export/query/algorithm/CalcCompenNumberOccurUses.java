package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenSuspensionAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

/**
 * @author ThanhNX
 *
 *         7.発生数・使用数を計算する
 */
public class CalcCompenNumberOccurUses {
	
	private CalcCompenNumberOccurUses() {};

	// 7.発生数・使用数を計算する
	public static CompenSuspensionAggrResult calc(List<AccumulationAbsenceDetail> lstAbsRec, DatePeriod period) {

		// 逐次発生の休暇明細を作成
		CompenSuspensionAggrResult result = new CompenSuspensionAggrResult(0d, 0d);
		lstAbsRec.stream().filter(x -> x.checkDataInPeriod(period)).forEach(data -> {
			// 処理中の逐次発生の休暇明細．発生消化区分をチェック
			if (data.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE) {
				// 振出発生日数 += 休暇発生明細．発生数.日数
				result.setSuOccurDay(result.getSuOccurDay() + data.getNumberOccurren().getDay().v());

			} else {
				// 振休使用日数 += 逐次発生の休暇明細．発生数．日数
				result.setSuDayUse(result.getSuDayUse() + data.getNumberOccurren().getDay().v());

			}

		});

		return result;
	}

}
