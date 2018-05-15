package nts.uk.ctx.at.record.app.find.stamp.card.stampcard;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

public class PeregStampCardDto extends PeregDomainDto {


	@PeregEmployeeId
	private String employeeId;

	@PeregItem("IS00779")
	private String stampNumber;

	public PeregStampCardDto(String stampCardId, String employeeId, String stampNumber) {
		super(stampCardId);
		this.employeeId = employeeId;
		this.stampNumber = stampNumber;
	}

	public static PeregStampCardDto createFromDomain(StampCard domain) {
		return new PeregStampCardDto(domain.getStampCardId(), domain.getEmployeeId(), domain.getStampNumber().v());
	}

}
