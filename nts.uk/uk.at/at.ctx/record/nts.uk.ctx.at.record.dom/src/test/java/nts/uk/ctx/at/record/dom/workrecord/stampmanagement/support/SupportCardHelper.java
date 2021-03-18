/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

/**
 * @author laitv
 *
 */
public class SupportCardHelper {

	public static SupportCard getDataDefault1(){
		return new SupportCard("cid", 9999, "wId");
	}
	
	public static SupportCard getDataDefault2(){
		return new SupportCard("cid", new SupportCardNumber(9999), "wId");
	}
}
