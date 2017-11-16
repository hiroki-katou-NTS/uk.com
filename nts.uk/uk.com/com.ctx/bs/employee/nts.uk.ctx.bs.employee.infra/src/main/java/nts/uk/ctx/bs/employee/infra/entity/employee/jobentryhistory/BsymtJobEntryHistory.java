package nts.uk.ctx.bs.employee.infra.entity.employee.jobentryhistory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.infra.entity.employee.BsymtEmployee;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BSYMT_JOB_ENTRY_HISTORY")
public class BsymtJobEntryHistory extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsymtJobEntryHistoryPk bsymtJobEntryHistoryPk;
	
	@Column(name = "CID")
	public String companyId;

	
	@Column(name = "HIRING_TYPE")
	public String hiringType;
	
	@Column(name = "RETIRE_DATE")
	public GeneralDate retireDate;
	
	@Column(name = "ADOPT_DATE")
	public GeneralDate adoptDate;

	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false)
    })
	public BsymtEmployee bsymtEmployee;
	
	
	@Override
	protected Object getKey() {
		return this.bsymtJobEntryHistoryPk;
	}
	
}
