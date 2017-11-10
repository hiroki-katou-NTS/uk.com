package nts.uk.shr.sample.pereg.command;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class SampleUpdatePersonBaseCommand {

	@PeregItem("IS00001")
	private String fullName;
	
	@PeregItem("IS00002")
	private String fullNameKana;

}
