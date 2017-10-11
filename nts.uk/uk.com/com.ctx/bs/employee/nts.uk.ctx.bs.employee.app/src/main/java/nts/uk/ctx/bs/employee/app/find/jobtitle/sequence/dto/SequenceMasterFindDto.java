package nts.uk.ctx.bs.employee.app.find.jobtitle.sequence.dto;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterSetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceName;

/**
 * Instantiates a new sequence master find dto.
 */
@Data
public class SequenceMasterFindDto implements SequenceMasterSetMemento {
	
	/** The order. */
	private short order;

	/** The company id. */
	private String companyId;

	/** The sequence code. */
	private String sequenceCode;

	/** The sequence name. */
	private String sequenceName;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterSetMemento#
	 * setOrder(short)
	 */
	@Override
	public void setOrder(short order) {
		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterSetMemento#
	 * setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterSetMemento#
	 * setSequenceCode(nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceCode)
	 */
	@Override
	public void setSequenceCode(SequenceCode sequenceCode) {
		this.sequenceCode = sequenceCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterSetMemento#
	 * setSequenceName(nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceName)
	 */
	@Override
	public void setSequenceName(SequenceName sequenceName) {
		this.sequenceName = sequenceName.v();
	}
}
