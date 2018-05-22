package nts.uk.ctx.sys.log.app.finder.datacorrectionlog;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class DataCorrectionLogDto {

	private String targetDate;
	private String targetUser;
	private String item;
	private String valueBefore;
	private String valueAfter;
	private String modifiedPerson;
	private GeneralDateTime modifiedDateTime;
	private String correctionAttr;
	
}
