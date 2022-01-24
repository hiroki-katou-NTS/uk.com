package nts.uk.ctx.at.shared.dom.worktype.absenceframe;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

public class AbsenceFrameHelper {
	public static AbsenceFrame createAbsenceFrame (int absenceFrameNo) {
		AbsenceFrame frame = new AbsenceFrame("000000000008-0006", absenceFrameNo, new WorkTypeName("Name"), ManageDistinct.NO);
		return frame;
	}
	
	public static AbsenceFrame createAbsenceFrame (ManageDistinct classification, int absenceFrameNo) {
		AbsenceFrame frame = new AbsenceFrame("000000000008-0006", absenceFrameNo, new WorkTypeName("Name"), classification);
		return frame;
	}
}
