package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelection;

@Value
public class PerInfoHistorySelectionDto {
	private String histId;
	private String selectionItemId;
	private String companyId;
	private GeneralDate startDate;
	private GeneralDate endDate;

	public static PerInfoHistorySelectionDto fromDomainHistorySelection(PerInfoHistorySelection domain) {

		return new PerInfoHistorySelectionDto(domain.getHistId(), domain.getSelectionItemId(), domain.getCompanyId(),
				domain.getPeriod().start(), domain.getPeriod().end());
	}
}
