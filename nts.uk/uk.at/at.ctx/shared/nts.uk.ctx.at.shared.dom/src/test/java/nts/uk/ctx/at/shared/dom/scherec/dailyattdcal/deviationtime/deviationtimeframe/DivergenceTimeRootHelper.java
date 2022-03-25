package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe;

import java.util.ArrayList;

public class DivergenceTimeRootHelper {
	public static DivergenceTimeRoot createDivergenceTimeRootByNoAndUseAtr(int divergenceTimeNo,
			DivergenceTimeUseSet divTimeUseSet) {
		return new DivergenceTimeRoot(divergenceTimeNo, "companyId", divTimeUseSet,
				new DivergenceTimeName("divTimeName"), DivergenceType.ARBITRARY_DIVERGENCE_TIME,
				new DivergenceTimeErrorCancelMethod(true, true), new ArrayList<>());

	}
}
