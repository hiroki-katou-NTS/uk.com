package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.Value;

@Value
public class GrantRegularCommand {
	
	/** 	付与するタイミングの種類 */
	private int typeTime; 
	
	/** 	付与基準日 */
	private Integer grantDate; 
	
	/** 	指定日付与 */
	private FixGrantDateCommand fixGrantDate; 
	
	/** 	付与日テーブル参照付与 */
	private GrantDeadlineCommand grantPeriodic; 
	
	/** 	期間付与 */
	private PeriodGrantDateCommand periodGrantDate; 
	
}
