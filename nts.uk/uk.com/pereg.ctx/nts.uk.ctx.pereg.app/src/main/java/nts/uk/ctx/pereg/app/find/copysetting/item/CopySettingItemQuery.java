package nts.uk.ctx.pereg.app.find.copysetting.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class CopySettingItemQuery {

	private String categoryCd;
	
	private String selectedEmployeeId;
	
	private GeneralDate baseDate;

}
