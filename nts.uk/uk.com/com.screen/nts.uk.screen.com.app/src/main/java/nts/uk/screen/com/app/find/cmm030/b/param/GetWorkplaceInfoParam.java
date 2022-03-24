package nts.uk.screen.com.app.find.cmm030.b.param;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class GetWorkplaceInfoParam {

	/**
	 * List<社員ID>
	 */
	private List<String> sids;
	
	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
}
