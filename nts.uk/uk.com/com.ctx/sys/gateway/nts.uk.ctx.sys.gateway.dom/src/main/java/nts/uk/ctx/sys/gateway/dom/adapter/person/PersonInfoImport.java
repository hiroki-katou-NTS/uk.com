package nts.uk.ctx.sys.gateway.dom.adapter.person;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonInfoImport {
	
	/** The person id. */
	private String personId;

	/** The person name. */
	private String personName;

	public PersonInfoImport(String personId, String personName) {
		super();
		this.personId = personId;
		this.personName = personName;
	}
	

}
