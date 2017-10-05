package nts.uk.ctx.bs.employee.infra.repository.jobtitle.info;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.HistoryId;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleName;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceCode;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobInfo;

/**
 * The Class JpaJobTitleInfoGetMemento.
 */
public class JpaJobTitleInfoGetMemento implements JobTitleInfoGetMemento {

	/** The bsymt job info. */
	private BsymtJobInfo bsymtJobInfo;

	/**
	 * Instantiates a new jpa job title info get memento.
	 *
	 * @param item the item
	 */
	public JpaJobTitleInfoGetMemento(BsymtJobInfo item) {
		this.bsymtJobInfo = item;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.bsymtJobInfo.getBsymtJobInfoPK().getCid());
	}

	/**
	 * Gets the job title history id.
	 *
	 * @return the job title history id
	 */
	@Override
	public HistoryId getJobTitleHistoryId() {
		return new HistoryId(this.bsymtJobInfo.getBsymtJobInfoPK().getHistId());
	}

	/**
	 * Gets the job title id.
	 *
	 * @return the job title id
	 */
	@Override
	public JobTitleId getJobTitleId() {
		return new JobTitleId(this.bsymtJobInfo.getBsymtJobInfoPK().getJobId());
	}

	/**
	 * Gets the job title code.
	 *
	 * @return the job title code
	 */
	@Override
	public JobTitleCode getJobTitleCode() {
		return new JobTitleCode(this.bsymtJobInfo.getJobCd());
	}

	/**
	 * Gets the job title name.
	 *
	 * @return the job title name
	 */
	@Override
	public JobTitleName getJobTitleName() {
		return new JobTitleName(this.bsymtJobInfo.getJobName());
	}

	/**
	 * Gets the sequence code.
	 *
	 * @return the sequence code
	 */
	@Override
	public SequenceCode getSequenceCode() {
		return new SequenceCode(this.bsymtJobInfo.getSequenceCd());
	}

}
