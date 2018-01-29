package command.person.contact;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregPersonId;

@Getter
public class DeletePerContactCommand{
	//個人ID
	@PeregPersonId
	private String personId;
}
