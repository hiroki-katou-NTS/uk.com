package nts.uk.ctx.basic.dom.company;

import lombok.AllArgsConstructor;
/**
 * 
 * @author lanlt
 *
 */
@AllArgsConstructor
public enum DepWorkPlaceSet {
	/** 0:区別しない	 */
	DISTINCTION_NOT_USE(0),
	/** 1:区別する	 */
	DISTINCTION_USE(1);
	/**
	 * value
	 */
	 public final int value;  
}
