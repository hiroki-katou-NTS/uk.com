package entity.employeeinfo.jobentryhistory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BsymtJobEntryHistoryPk implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SID")
	public String sId;
	
	@Column(name = "ENTRY_DATE")
	public GeneralDate entryDate;

}
