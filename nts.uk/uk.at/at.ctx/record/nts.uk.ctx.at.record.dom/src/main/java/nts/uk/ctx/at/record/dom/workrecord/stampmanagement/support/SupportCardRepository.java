/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import java.util.List;
import java.util.Optional;

/**
 * 応援カードRepo
 * @author laitv
 *
 */
public interface SupportCardRepository {

	public Optional<SupportCard> get(String cid, String supportCardNo);

	public void update(List<SupportCard> domains);

	public void insert(List<SupportCard> domains);

	public void delete(List<SupportCard> domains);
	
	public List<SupportCard> getAll();
	
	public Optional<SupportCard> getBySupportCardNo(String supportCardNo);
	
	public void delete(String cid, String supportCardNo);

}
