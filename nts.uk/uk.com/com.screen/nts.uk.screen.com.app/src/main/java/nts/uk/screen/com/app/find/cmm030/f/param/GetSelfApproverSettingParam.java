package nts.uk.screen.com.app.find.cmm030.f.param;

import java.util.List;

import lombok.Value;

@Value
public class GetSelfApproverSettingParam {

	/**
	 * 社員ID
	 */
	private String sid;
	
	/**
	 * 承認ID<List>
	 */
	private List<String> approvalIds;
}
