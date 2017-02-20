package nts.uk.ctx.basic.infra.entity.organization.position;


import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.LocalDateToDBConverter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CMNMT_JOB_HIST")
public class CmnmtJobTitleHistory {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CmnmtJobTitleHistoryPK cmnmtJobTitleHistoryPK;
	
	public CmnmtJobTitleHistoryPK geCmnmtJobTitleHistoryPK() {
		return cmnmtJobTitleHistoryPK;
	}
	
	public CmnmtJobTitleHistoryPK getCmnmtJobTitleHistoryPK() {
		return cmnmtJobTitleHistoryPK;
	}

	public void setCmnmtJobTitleHistoryPK(CmnmtJobTitleHistoryPK cmnmtJobTitleHistoryPK) {
		this.cmnmtJobTitleHistoryPK = cmnmtJobTitleHistoryPK;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Convert(converter = LocalDateToDBConverter.class)
	@Basic(optional = false)
	@Column(name ="STR_D")
	public LocalDate startDate;

	@Convert(converter = LocalDateToDBConverter.class)
	@Basic(optional = false)
	@Column(name = "END_D")
	public LocalDate endDate;
}
