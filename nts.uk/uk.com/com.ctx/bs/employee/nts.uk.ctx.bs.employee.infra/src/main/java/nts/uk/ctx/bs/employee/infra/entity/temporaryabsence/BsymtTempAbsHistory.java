package nts.uk.ctx.bs.employee.infra.entity.temporaryabsence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "BSYMT_TEMP_ABS_HISTORY")
public class BsymtTempAbsHistory extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "HIST_ID")
	public String histId;
	
	@Basic(optional = false)
	@Column(name = "SID")
	public String sid;

	@Basic(optional = false)
	@Column(name = "START_DATE")
	public String startDate;
	
	@Basic(optional = false)
	@Column(name = "END_DATE")
	public String endDate;
	
	@Override
	protected Object getKey() {
		return this.histId;
	}

	public BsymtTempAbsHistory() {
		super();
	}

}
