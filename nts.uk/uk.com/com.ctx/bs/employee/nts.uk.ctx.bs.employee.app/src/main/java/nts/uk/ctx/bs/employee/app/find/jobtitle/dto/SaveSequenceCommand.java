package nts.uk.ctx.bs.employee.app.find.jobtitle.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMaster;

/**
 * The Class SaveSequenceCommand.
 */
@Getter
@Setter
public class SaveSequenceCommand {

	/** The is create mode. */
	private Boolean isCreateMode;

	/** The sequence master dto. */
	private SequenceMasterDto sequenceMasterDto;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the sequence master
	 */
	public SequenceMaster toDomain(String companyId) {
		return new SequenceMaster(new SequenceMasterGetMementoImpl(companyId, this));
	}
}
