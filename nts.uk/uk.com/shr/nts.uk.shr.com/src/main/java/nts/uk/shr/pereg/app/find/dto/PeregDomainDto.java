/**
 * 
 */
package nts.uk.shr.pereg.app.find.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregPersonId;
import nts.uk.shr.pereg.app.PeregRecordId;

/**
 * @author danpv
 *
 */
@Getter
@Setter
public class PeregDomainDto {

	@PeregRecordId
	private String recordId;

	@PeregEmployeeId
	private String employeeId;

	@PeregPersonId
	private String personId;

	public PeregDomainDto() {
	}

	public PeregDomainDto(String recordId) {
		this.recordId = recordId;
	}

	public PeregDomainDto(String recordId, String employeeId, String personId) {
		this.recordId = recordId;
		this.employeeId = employeeId;
		this.personId = personId;
	}

}
