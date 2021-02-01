package nts.uk.ctx.at.request.dom.application.holidayshipment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;

@Stateless
public class HolidayShipmentServiceImpl implements HolidayShipmentService {

	@Override
	public Optional<GeneralDate> detRefDate(Optional<GeneralDate> recDate, Optional<GeneralDate> absDate) {
		Optional<GeneralDate> resultDate = Optional.empty();
		// 日付の組み合わせをチェックする

		if (recDate.isPresent() && absDate.isPresent()) {
			// INPUT.振出日＝設定あり または INPUT.振休日＝設定あり
			boolean isRecDateAfterAbsDate = recDate.get().after(absDate.get());
			if (isRecDateAfterAbsDate) {
				resultDate = absDate;
			} else {
				resultDate = recDate;
			}
		} else {

			if (recDate.isPresent()) {
				// INPUT.振出日＝設定なし
				resultDate = recDate;
			}
			if (absDate.isPresent()) {
				// INPUT.振休日＝設定なし
				resultDate = absDate;
			}
		}
		return resultDate;
	}

}
