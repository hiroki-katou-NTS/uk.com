package nts.uk.ctx.hr.shared.infra.entity.laborcontracthistory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.DateHistoryItem;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEDT_JM_WORK_HIST")
public class PpedtJmWorkHist extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "HIST_ID")
	public String histId;

	@Basic(optional = false)
	@Column(name = "CID")
	public String cId;

	@Basic(optional = false)
	@Column(name = "SID")
	public String sId;

	@Basic(optional = false)
	@Column(name = "START_DATE")
	public GeneralDate startDate;

	@Basic(optional = false)
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	@Override
	public Object getKey() {
		return histId;
	}

	public LaborContractHistory toDomain() {
		return LaborContractHistory.createFromJavaType(this.cId, this.sId,
				DateHistoryItem.createFromJavaType(this.histId, new DatePeriod(startDate, endDate)));
	}

	public PpedtJmWorkHist(LaborContractHistory domain) {
		super();
		this.histId = domain.getHistoryItem().getHisId();
		this.cId = domain.getCid();
		this.sId = domain.getSid();
		this.startDate = domain.getHistoryItem().getPeriod().start();
		this.endDate = domain.getHistoryItem().getPeriod().end();
	}

}
