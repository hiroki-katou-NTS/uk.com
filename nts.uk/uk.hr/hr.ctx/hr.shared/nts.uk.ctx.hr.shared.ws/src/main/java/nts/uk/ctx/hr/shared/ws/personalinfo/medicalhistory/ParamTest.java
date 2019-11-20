package nts.uk.ctx.hr.shared.ws.personalinfo.medicalhistory;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class ParamTest {
	public List<String> listSid;
	public String sid;
	public GeneralDate baseDate;
}
