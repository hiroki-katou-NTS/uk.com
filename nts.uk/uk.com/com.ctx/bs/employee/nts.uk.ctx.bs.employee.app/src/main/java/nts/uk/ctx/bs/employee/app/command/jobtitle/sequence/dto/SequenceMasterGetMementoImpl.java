package nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.dto;

import nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.SaveSequenceCommand;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceName;

/**
 * The Class SequenceMasterGetMementoImpl.
 */
public class SequenceMasterGetMementoImpl implements SequenceMasterGetMemento {

	/** The company id. */
	private String companyId;

	/** The save sequence command. */
	private SaveSequenceCommand saveSequenceCommand;

	public SequenceMasterGetMementoImpl(String companyId, SaveSequenceCommand saveSequenceCommand) {
		this.companyId = companyId;
		this.saveSequenceCommand = saveSequenceCommand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento#
	 * getSequenceCode()
	 */
	@Override
	public SequenceCode getSequenceCode() {
		return new SequenceCode(this.saveSequenceCommand.getSequenceMasterDto().getSequenceCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento#
	 * getSequenceName()
	 */
	@Override
	public SequenceName getSequenceName() {
		return new SequenceName(this.saveSequenceCommand.getSequenceMasterDto().getSequenceName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterGetMemento#
	 * getOrder()
	 */
	@Override
	public short getOrder() {
		return this.saveSequenceCommand.getSequenceMasterDto().getOrder();
	}
}
