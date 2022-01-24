package nts.uk.ctx.at.record.dom.divergence.time;

import java.util.ArrayList;

public class DivergenceReasonInputMethodHelper {	
	public static DivergenceReasonInputMethod createByNoAndUseAtr(int divergenceTimeNo, boolean divergenceReasonInputed,boolean divergenceReasonSelected) {
		return new DivergenceReasonInputMethod(divergenceTimeNo, "companyId", divergenceReasonInputed, divergenceReasonSelected, new ArrayList<>());
	}
}
