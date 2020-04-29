/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation.algorithm;

import java.util.Optional;

import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.RetireDismissalRegulation;

/**
 * @author laitv
 *
 */
public interface RetireDismissalRegulationRepository {
	
	Optional<RetireDismissalRegulation> getDomain(String cid, String histId);
	
	void addRetireDismissalRegulation(RetireDismissalRegulation domain);
	
	void updateRetireDismissalRegulation(RetireDismissalRegulation domain);
	

}
