package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.RemainUnDigestedDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

/**
 * @author ThanhNX
 *
 *
 *         7.発生数・使用数を計算する
 */
public class CalcNumberOccurUses {

	private CalcNumberOccurUses() {
	};

	// 7.発生数・使用数を計算する
	public static RemainUnDigestedDayTimes process(List<AccumulationAbsenceDetail> lstAccAbsem, DatePeriod period) {

		// 代休休出集計結果を作成
		RemainUnDigestedDayTimes outputData = new RemainUnDigestedDayTimes(0, 0, 0, 0, false);
		lstAccAbsem.stream().filter(x -> x.checkDataInPeriod(period)).forEach(detail -> {
			// 処理中の逐次発生の休暇明細．発生消化区分をチェック
			if (detail.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE) {
				// 代休発生日数 += 休暇発生明細．発生数.日数
				outputData.setRemainDays(outputData.getRemainDays() + detail.getNumberOccurren().getDay().v());
				outputData.setRemainTimes(
						outputData.getRemainTimes() + detail.getNumberOccurren().getTime().map(x -> x.v()).orElse(0));

			} else {
				// 代休使用日数 += 逐次発生の休暇明細．発生数．日数
				outputData.setUnDigestedDays(outputData.getUnDigestedDays() + detail.getNumberOccurren().getDay().v());
				outputData.setUnDigestedTimes(outputData.getUnDigestedTimes()
						+ detail.getNumberOccurren().getTime().map(x -> x.v()).orElse(0));

			}
		});

		return outputData;

	}
}
