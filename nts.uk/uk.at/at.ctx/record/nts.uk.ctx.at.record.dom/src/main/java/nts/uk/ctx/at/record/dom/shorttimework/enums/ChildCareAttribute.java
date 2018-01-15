package nts.uk.ctx.at.record.dom.shorttimework.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 育児介護区分
 *
 */
@AllArgsConstructor
public enum ChildCareAttribute {
	
	/* 育児 */
	CHILD_CARE(0),
	/* 介護 */
	CARE(1);
	
	public final int value;
	
	/**
	 * 育児か判定する
	 * @author ken_takasu
	 * @return 育児
	 */
	public boolean isChildCare() {
		return CHILD_CARE.equals(this);
	}
	

}
