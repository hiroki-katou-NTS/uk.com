package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.Value;

@Value
public class GrantRegularCommand {
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private int specialHolidayCode;
	
	/** 付与するタイミングの種類 */
	private int typeTime;
	
	/** 付与基準日 */
	private int grantDate;
	
	/** 取得できなかった端数は消滅する */
	private boolean allowDisappear;
	
	/** 取得できなかった端数は消滅する */
	private GrantTimeCommand grantTime;
}
