package nts.uk.ctx.bs.employee.infra.entity.temporaryabsence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "BSYMT_TEMP_ABS_HISTORY")
public class BsymtTempAbsHistory extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "HIST_ID")
	public String histId;

	@Column(name = "CID")
	public String cid;

	@Column(name = "SID")
	public String sid;

	@Column(name = "START_DATE")
	public GeneralDate startDate;

	@Column(name = "END_DATE")
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		return this.histId;
	}

	public BsymtTempAbsHistory() {
		super();
	}

	public BsymtTempAbsHistory(String histId, String cid, String sid, GeneralDate startDate, GeneralDate endDate) {
		super();
		this.histId = histId;
		this.cid = cid;
		this.sid = sid;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
