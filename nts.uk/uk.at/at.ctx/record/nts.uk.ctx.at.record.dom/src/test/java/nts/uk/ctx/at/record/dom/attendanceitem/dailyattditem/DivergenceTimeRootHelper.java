package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

import java.util.ArrayList;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceType;

public class DivergenceTimeRootHelper {
	public static DivergenceTimeRoot createDivergenceTimeRootByNoAndUseAtr(int divergenceTimeNo,
			DivergenceTimeUseSet divTimeUseSet) {
		return new DivergenceTimeRoot(divergenceTimeNo, "companyId", divTimeUseSet,
				new DivergenceTimeName("divTimeName"), DivergenceType.ARBITRARY_DIVERGENCE_TIME,
				new DivergenceTimeErrorCancelMethod(true, true), new ArrayList<>());

	}
}
