package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.Comparator;

import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

public class AccumulationAbsenceDetailComparator implements Comparator<AccumulationAbsenceDetail> {

	@Override
	public int compare(AccumulationAbsenceDetail o1, AccumulationAbsenceDetail o2) {

		CompensatoryDayoffDate comDay1 = o1.getDateOccur();
		CompensatoryDayoffDate comDay2 = o2.getDateOccur();
		if (comDay1.isUnknownDate() != comDay2.isUnknownDate()) {
			return Boolean.compare(comDay2.isUnknownDate(), comDay1.isUnknownDate());
		}
		if (!comDay1.getDayoffDate().isPresent() && !comDay2.getDayoffDate().isPresent())
			return 0;
		if (!comDay1.getDayoffDate().isPresent())
			return 1;
		if (!comDay2.getDayoffDate().isPresent())
			return -1;

		return comDay1.getDayoffDate().get().compareTo(comDay2.getDayoffDate().get());
	}

}
