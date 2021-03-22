package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectedInformationItemDto {
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;
	
	/** The layout id. */
	private String layoutId;
	
	/** The remark input no. */
	private int remarkInputNo;
	
	/** The work type name display. */
	private int workTypeNameDisplay;
	
	/** The font size. */
	private int fontSize;
	
	/** The print remarks content dtos. */
	private List<PrintRemarksContentDto> printRemarksContentDtos;
	
	/** The lst can used. */
	private List<InformationItemDto> lstCanUsed;
	
	/** The display attendance item. */
	private List<InformationItemDto> displayAttendanceItem;
}
