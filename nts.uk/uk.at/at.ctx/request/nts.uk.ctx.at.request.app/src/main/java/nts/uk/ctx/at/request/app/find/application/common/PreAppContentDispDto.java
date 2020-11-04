package nts.uk.ctx.at.request.app.find.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.PreAppContentDisplay;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PreAppContentDispDto {
	
	private String date;
	
	public static PreAppContentDispDto fromDomain(PreAppContentDisplay preAppContentDisplay) {
		return new PreAppContentDispDto(preAppContentDisplay.getDate().toString());
	}
	
	public PreAppContentDisplay toDomain() {
		return new PreAppContentDisplay(GeneralDate.fromString(date, "yyyy/MM/dd"), null);
	}
	
}
