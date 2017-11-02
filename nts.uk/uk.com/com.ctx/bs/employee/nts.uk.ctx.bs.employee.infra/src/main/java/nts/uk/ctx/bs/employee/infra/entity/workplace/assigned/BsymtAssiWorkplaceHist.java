package nts.uk.ctx.bs.employee.infra.entity.workplace.assigned;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Entity
@Table(name = "BSYMT_AFFI_WORKPLACE_HIST")
public class BsymtAssiWorkplaceHist {
	
	/** The end D. */
	@Column(name = "HIST_ID")
	private String historyId;
	
	/** The end D. */
	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate strD;
	
	/** The end D. */
	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endD;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false)
	})
	public BsymtAssiWorkplace bsymtAssiWorkplace;
}
