package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsDaysRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

/**
 * @author ThanhNX
 *
 *         6.残数と未消化を集計する
 */
public class TotalRemainUndigest {

	private TotalRemainUndigest() {
	};

	public static AbsDaysRemain process(List<AccumulationAbsenceDetail> lstData, GeneralDate date, boolean isMode) {

		AbsDaysRemain outData = new AbsDaysRemain(0, 0, false);
		for (AccumulationAbsenceDetail data : lstData) {

			if (data.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE) {

				UnbalanceCompensation dataCast = (UnbalanceCompensation) data;

				if (((isMode && dataCast.getDeadline().beforeOrEquals(date))
						|| (!isMode && dataCast.getDeadline().before(date)))) {
					outData.setUnDigestedDays(outData.getUnDigestedDays() + dataCast.getUnbalanceNumber().getDay().v());
				} else {
					outData.setRemainDays(outData.getRemainDays() + dataCast.getUnbalanceNumber().getDay().v());
					
					if (!isMode && dataCast.getDeadline().equals(date)) {
						outData.setUnDigestedDays(
								outData.getUnDigestedDays() + dataCast.getUnbalanceNumber().getDay().v());
					}
				}
			} else {

				// 残日数 -= ループ中の「逐次発生の休暇明細」．未相殺数

				outData.setRemainDays(outData.getRemainDays() - data.getUnbalanceNumber().getDay().v());
			}
		}

		return outData;

	}

}
