package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import java.util.Optional;

/**
 * 応援カード編集設定Repo
 * @author nws_namnv2
 *
 */
public interface SupportCardEditRepository {
	
	public Optional<SupportCardEdit> get(String cid);
	
	public void insert(SupportCardEdit domain);
	
	public void update(SupportCardEdit domain);
	
	public void delete(SupportCardEdit domain);

}
