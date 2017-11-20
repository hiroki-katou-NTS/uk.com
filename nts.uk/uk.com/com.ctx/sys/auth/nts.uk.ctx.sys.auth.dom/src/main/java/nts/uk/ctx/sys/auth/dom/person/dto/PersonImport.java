package nts.uk.ctx.sys.auth.dom.person.dto;

import lombok.Data;

/**
 * 
 * @author HungTT
 *
 */

@Data
public class PersonImport {

	/** The person id. */
	private String personId;

	/** The person name. */
	private String personName;

	/**
	 * Instantiates a new person dto.
	 *
	 * @param personId
	 *            the person id
	 * @param personName
	 *            the person name
	 */
	public PersonImport(String personId, String personName) {
		super();
		this.personId = personId;
		this.personName = personName;
	}

}