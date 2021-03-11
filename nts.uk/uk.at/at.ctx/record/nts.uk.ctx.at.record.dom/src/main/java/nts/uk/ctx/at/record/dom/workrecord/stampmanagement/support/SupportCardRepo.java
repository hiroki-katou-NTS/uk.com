/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import java.util.List;

/**
 * 応援カードRepo
 * @author laitv
 *
 */
public interface SupportCardRepo {

	public SupportCard get(String cid, int supportCardNo);

	public void update(List<SupportCard> domains);

	public void insert(List<SupportCard> domains);

	public void delete(List<SupportCard> domains);

}
