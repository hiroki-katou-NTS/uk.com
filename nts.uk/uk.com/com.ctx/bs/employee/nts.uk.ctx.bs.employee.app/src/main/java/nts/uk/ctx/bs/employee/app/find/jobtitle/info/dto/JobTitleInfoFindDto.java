/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.jobtitle.info.dto;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.HistoryId;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoSetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleName;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterSetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceName;

/**
 * The Class JobTitleInfoFindDto.
 */
@Data
public class JobTitleInfoFindDto implements JobTitleInfoSetMemento, SequenceMasterSetMemento {
	
	/** The company id. */
	public String companyId;

	/** The job title id. */
	public String jobTitleId;
	
	/** The job title history id. */
	public String jobTitleHistoryId;
	
	/** The job title code. */
	public String jobTitleCode;
	
	/** The job title name. */
	public String jobTitleName;
	
	/** The sequence code. */
	public String sequenceCode;
	
	/** The sequence name. */
	public String sequenceName;

	/** The order. */
	public short order;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoSetMemento#setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoSetMemento#setJobTitleHistoryId(nts.uk.ctx.bs.employee.dom.jobtitle.history.HistoryId)
	 */
	@Override
	public void setJobTitleHistoryId(HistoryId jobTitleHistoryId) {
		this.jobTitleHistoryId = jobTitleHistoryId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoSetMemento#setJobTitleId(nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId)
	 */
	@Override
	public void setJobTitleId(JobTitleId jobTitleId) {
		this.jobTitleId = jobTitleId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoSetMemento#setJobTitleCode(nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleCode)
	 */
	@Override
	public void setJobTitleCode(JobTitleCode jobTitleCode) {
		this.jobTitleCode = jobTitleCode.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoSetMemento#setJobTitleName(nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleName)
	 */
	@Override
	public void setJobTitleName(JobTitleName jobTitleName) {
		this.jobTitleName = jobTitleName.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoSetMemento#setSequenceCode(nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceCode)
	 */
	@Override
	public void setSequenceCode(SequenceCode sequenceCode) {
		this.sequenceCode = sequenceCode.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterSetMemento#setOrder(short)
	 */
	@Override
	public void setOrder(short order) {
		this.order = order;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterSetMemento#setSequenceName(nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceName)
	 */
	@Override
	public void setSequenceName(SequenceName sequenceName) {
		this.sequenceName = sequenceName.v();
	}
}
