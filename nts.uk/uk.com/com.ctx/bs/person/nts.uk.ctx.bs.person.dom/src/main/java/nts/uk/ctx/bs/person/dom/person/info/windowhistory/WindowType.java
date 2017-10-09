package nts.uk.ctx.bs.person.dom.person.info.windowhistory;

import lombok.AllArgsConstructor;

/**
 * 寡夫寡婦区分
 * @author xuan vinh
 * */

@AllArgsConstructor
public enum WindowType {
	//そうでない
	NOPE(1),
	//寡夫
	HUSBAND_WINDOW(2),
	//寡婦特別
	WIFE_WINDOW(3),
	//寡婦一般
	GENERAL_WINDOW(4);
	public final int value;
			
}
