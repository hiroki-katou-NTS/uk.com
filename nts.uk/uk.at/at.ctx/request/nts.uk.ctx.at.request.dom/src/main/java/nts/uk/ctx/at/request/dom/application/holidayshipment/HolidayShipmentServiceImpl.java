package nts.uk.ctx.at.request.dom.application.holidayshipment;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;

@Stateless
public class HolidayShipmentServiceImpl implements HolidayShipmentService {

	@Override
	public GeneralDate detRefDate(GeneralDate recDate, GeneralDate absDate) {
		boolean isBothDateNotNull = absDate != null && recDate != null;
		//// INPUT.振出日＝設定なし かつ INPUT.振休日＝設定なし
		GeneralDate resultDate = null;
		// 日付の組み合わせをチェックする

		if (isBothDateNotNull) {
			// INPUT.振出日＝設定あり または INPUT.振休日＝設定あり
			boolean isRecDateAfterAbsDate = recDate.after(absDate);
			if (isRecDateAfterAbsDate) {
				resultDate = absDate;
			} else {
				resultDate = recDate;
			}
		} else {

			if (recDate != null) {
				// INPUT.振出日＝設定なし
				resultDate = recDate;
			}
			if (absDate != null) {
				// INPUT.振休日＝設定なし
				resultDate = absDate;
			}
		}
		return resultDate;
	}

}
