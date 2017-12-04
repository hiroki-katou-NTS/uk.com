package nts.uk.ctx.pereg.app.find.employment.history;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author sonnlb
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DateHistoryItemDto extends PeregDomainDto {

	// 期間
	@PeregItem("IS00065")
	private DatePeriod period;

	// 開始日
	@PeregItem("IS00066")
	private GeneralDate startDate;

	// 終了日
	@PeregItem("IS00067")
	private GeneralDate endDate;

	public DateHistoryItemDto(String recordId, DatePeriod period, GeneralDate startDate, GeneralDate endDate) {
		super(recordId, null, null);
		this.period = period;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public static DateHistoryItemDto createFromDomain(DateHistoryItem domain) {

		return new DateHistoryItemDto(domain.identifier(), new DatePeriod(domain.start(), domain.end()), domain.start(),
				domain.end());

	}

}
