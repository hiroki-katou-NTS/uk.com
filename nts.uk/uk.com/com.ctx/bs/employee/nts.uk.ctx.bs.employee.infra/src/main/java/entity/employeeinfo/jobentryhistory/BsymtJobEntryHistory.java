package entity.employeeinfo.jobentryhistory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BSYDT_JOB_ENTRY_HISTORY")
public class BsymtJobEntryHistory extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsymtJobEntryHistoryPk bsydtJobEntryHistoryPk;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	
	@Basic(optional = true)
	@Column(name = "HIRING_TYPE")
	public String hiringType;
	
	@Basic(optional = true)
	@Column(name = "RETIRE_DATE")
	public GeneralDate retireDate;
	
	@Basic(optional = true)
	@Column(name = "ADOPT_DATE")
	public GeneralDate adoptDate;

	
	@Override
	protected Object getKey() {
		return this.bsydtJobEntryHistoryPk;
	}
}
