package find.person.setting.selectionitem;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Value
public class PerInfoHistorySelectionDto {
	private String histId;
	private String selectionItemId;
	private String companyCode;
	// private GeneralDate startDate;
	// private GeneralDate endDate;
	private DatePeriod period;

	public static PerInfoHistorySelectionDto fromDomainHistorySelection(PerInfoHistorySelection domain) {
		return new PerInfoHistorySelectionDto(domain.getHistId(), domain.getSelectionItemId(), domain.getCompanyCode(),
				// domain.getStartDate(),
				// domain.getEndDate());
				domain.getPeriod());

	}
}
