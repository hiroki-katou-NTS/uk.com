/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum DailyRecreateClassification {
	
	//もう一度作り直す
	Rebuild(0),

	//一部修正
	PartlyModified(1);
	
	public final int value;
	
	private DailyRecreateClassification (int value ) {
		this.value = value;
	}

}
