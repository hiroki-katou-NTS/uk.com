/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.info.widowhistory;

/**
 * @author danpv
 *
 */
public interface WidowHistoryRepository {
	
	public WidowHistory get();
	
	public WidowHistory getWidowHistoryById(String widowHisId);
}
