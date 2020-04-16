package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;

/**
 * @author anhdt
 *
 */
@Data
public class StampResultDisplayDto {
	
	private String companyId;
	private Integer notUseAttr;
	private List<Integer> displayItemId;
	
	public StampResultDisplayDto(Optional<StampResultDisplay> oStampRsDisplay) {
		if(oStampRsDisplay.isPresent()) {
			StampResultDisplay stampRsDisplay = oStampRsDisplay.get();
			this.companyId = stampRsDisplay.getCompanyId();
			this.notUseAttr = stampRsDisplay.getUsrAtr().value;
			this.displayItemId = stampRsDisplay.getLstDisplayItemId().stream().map(i -> i.getDisplayItemId()).collect(Collectors.toList());
		}
	}
}
