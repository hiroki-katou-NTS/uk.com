/**
 * 
 */
package repository.person.info.widowhistory;

import javax.ejb.Stateless;

import entity.person.info.widowhistory.BpsmtWidowHis;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;

/**
 * @author danpv
 *
 */
@Stateless
public class WidowHisRepoImpl implements WidowHistoryRepository {

	
	
	@Override
	public WidowHistory get() {
		// se sua lai sau
		return null;
	}

	@Override
	public WidowHistory getWidowHistoryById(String widowHisId) {
		// TODO Auto-generated method stub
		return null;
	}

}
