package nts.uk.ctx.at.record.app.find.dailyperformanceformat.businesstype;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
public class BusinessTypeDto extends PeregDomainDto {

	// 開始日
	@PeregItem("IS00255")
	private GeneralDate startDate;

	// 終了日
	@PeregItem("IS00256")
	private GeneralDate endDate;

	// 勤務種別CD
	@PeregItem("IS00257")
	private String businessTypeCode;

	private boolean latestHistory;

	public BusinessTypeDto(String recordId) {
		super(recordId);
	}

	public BusinessTypeDto(String recordId, GeneralDate startDate, GeneralDate endDate, String businessTypeCode) {
		super(recordId);
		this.startDate = startDate;
		this.endDate = endDate;
		this.businessTypeCode = businessTypeCode;
	}

	public BusinessTypeDto(String recordId, GeneralDate startDate, GeneralDate endDate, String businessTypeCode,
			boolean isLatestHistory) {
		super(recordId);
		this.startDate = startDate;
		this.endDate = endDate;
		this.businessTypeCode = businessTypeCode;
		this.latestHistory = isLatestHistory;
	}

}
