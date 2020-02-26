package nts.uk.ctx.hr.shared.ws.personalinfo.medicalhistory;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class TestDto {
	private List<String> listSid;
	private String sid;
	private GeneralDate baseDate;
}
