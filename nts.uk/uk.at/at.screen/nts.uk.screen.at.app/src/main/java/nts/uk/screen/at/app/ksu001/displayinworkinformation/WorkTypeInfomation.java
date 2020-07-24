/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinworkinformation;

import lombok.Value;

/**
 * @author laitv
 * <勤務種類, 必須任意不要区分, 出勤休日区分>を作る
 */
@Value
public class WorkTypeInfomation {
	
	public WorkTypeDto workTypeDto;
	public int workTimeSetting;
	public int attHdAtr;

}
