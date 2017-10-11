package nts.uk.ctx.bs.employee.app.command.jobtitle.sequence;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.dto.SequenceMasterDto;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceName;

/**
 * The Class SaveSequenceCommand.
 */
@Getter
@Setter
public class SaveSequenceCommand implements SequenceMasterGetMemento {

	/** The is create mode. */
	private Boolean isCreateMode;

	/** The sequence master dto. */
	private SequenceMasterDto sequenceMasterDto;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.sequenceMasterDto.getCompanyId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento#getSequenceCode()
	 */
	@Override
	public SequenceCode getSequenceCode() {
		return new SequenceCode(this.sequenceMasterDto.getSequenceCode());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento#getSequenceName()
	 */
	@Override
	public SequenceName getSequenceName() {
		return new SequenceName(this.sequenceMasterDto.getSequenceName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento#getOrder()
	 */
	@Override
	public short getOrder() {
		return this.sequenceMasterDto.getOrder();
	}
}
