package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import java.util.ArrayList;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;

public class TimeRecordSetFormatBakHelper {

	public static TimeRecordSetFormatBak createTimeRecordSetFormatBak() {
		return new TimeRecordSetFormatBak(
				new EmpInfoTerminalCode("1111"),
				new EmpInfoTerminalName("Model name"),
				new NRRomVersion("003"),
				EnumAdaptor.valueOf(7, ModelEmpInfoTer.class),
				new ArrayList<TimeRecordSetFormat>(),
				GeneralDateTime.now());
	}
}
