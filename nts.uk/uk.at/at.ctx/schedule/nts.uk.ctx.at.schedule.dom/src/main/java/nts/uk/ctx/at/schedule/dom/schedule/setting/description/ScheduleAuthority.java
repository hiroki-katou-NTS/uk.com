package nts.uk.ctx.at.schedule.dom.schedule.setting.description;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 
 * @author phongtq
 *
 */
@Getter
@AllArgsConstructor
public class ScheduleAuthority {
	/** 利用可否権限の機能NO*/
	private int functionNoAuth;
	
	/** 表示順*/	
	private int displayOrderAuth;
	
	/** 利用可否権限の機能名*/
	private DisplayName displayNameAuth;
	
	/** 説明文*/
	private Descripption descripptionAuth;
	
	/** 初期値*/
	private int initialValueAuth;
	
	public static ScheduleAuthority createFromJavaType(int functionNoAuth, int displayOrderAuth, String displayNameAuth,String descripptionAuth, int initialValueAuth){
		return new ScheduleAuthority(functionNoAuth, displayOrderAuth, new DisplayName(displayNameAuth), new Descripption(descripptionAuth), initialValueAuth );
	}
}
