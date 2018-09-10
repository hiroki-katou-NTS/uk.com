/**
 * 10:58:31 AM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author hungnm
 *
 */
@Data
@NoArgsConstructor
public class ClosureDto {
	private String companyId;
	private Integer closureId;
	private Integer useAtr;
	private Integer closureMonth;
	private String sid;
	private String employmentCode;
	//KDW003a
	private DatePeriod datePeriod;

	public ClosureDto(String companyId, Integer closureId, Integer useAtr, Integer closureMonth) {
		this.companyId = companyId;
		this.closureId = closureId;
		this.useAtr = useAtr;
		this.closureMonth = closureMonth;
	}

	public ClosureDto(String companyId, Integer closureId, Integer useAtr, Integer closureMonth, String sid) {
		super();
		this.companyId = companyId;
		this.closureId = closureId;
		this.useAtr = useAtr;
		this.closureMonth = closureMonth;
		this.sid = sid;
	}

	public ClosureDto(String companyId, Integer closureId, Integer useAtr, Integer closureMonth, String sid,
			String employmentCode) {
		super();
		this.companyId = companyId;
		this.closureId = closureId;
		this.useAtr = useAtr;
		this.closureMonth = closureMonth;
		this.sid = sid;
		this.employmentCode = employmentCode;
	}
	
	public ClosureDto(String companyId, Integer closureId, Integer useAtr, Integer closureMonth, String sid,
			String employmentCode, DatePeriod datePeriod) {
		super();
		this.companyId = companyId;
		this.closureId = closureId;
		this.useAtr = useAtr;
		this.closureMonth = closureMonth;
		this.sid = sid;
		this.employmentCode = employmentCode;
		this.datePeriod = datePeriod;
	}

}
