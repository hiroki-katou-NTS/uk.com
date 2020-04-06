package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;

/**
 * @author anhdt
 *
 */
@Data
public class TimeActualStampDto {
	
	private Integer numberOfReflectionStamp;
	
	private String actualAfterRoundingTime;
	private String actualTimeWithDay;
	private String actualLocationCode;
	private Integer actualStampSourceInfo;
	
	private String stampAfterRoundingTime;
	private String stampTimeWithDay;
	private String stampLocationCode;
	private Integer stampStampSourceInfo;
	
	public TimeActualStampDto (Optional<TimeActualStamp> oDomain) {
		if(oDomain.isPresent()) {
			TimeActualStamp domain = oDomain.get();
			
			Optional<WorkStamp> oActualWt = domain.getActualStamp();
			if(oActualWt.isPresent()) {
				WorkStamp actualWt = oActualWt.get();
				this.actualAfterRoundingTime = actualWt.getAfterRoundingTime().getFullText();
				this.actualTimeWithDay = actualWt.getTimeWithDay().getFullText();
				this.actualLocationCode = actualWt.getLocationCode().isPresent() ? actualWt.getLocationCode().get().v() : null;
				this.actualStampSourceInfo = actualWt.getStampSourceInfo().value;
			}
			
			Optional<WorkStamp> oStampWt = domain.getStamp();
			if(oStampWt.isPresent()) {
				WorkStamp stampWt = oStampWt.get();
				this.stampAfterRoundingTime = stampWt.getAfterRoundingTime().getFullText();
				this.stampTimeWithDay = stampWt.getTimeWithDay().getFullText();
				this.stampLocationCode = stampWt.getLocationCode().isPresent() ? stampWt.getLocationCode().get().v() : null;
				this.stampStampSourceInfo = stampWt.getStampSourceInfo().value;
			}
			
		}
	}
	
}
