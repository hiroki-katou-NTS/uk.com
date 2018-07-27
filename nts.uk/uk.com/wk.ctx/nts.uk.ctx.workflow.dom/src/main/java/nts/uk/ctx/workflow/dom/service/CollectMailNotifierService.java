package nts.uk.ctx.workflow.dom.service;

import java.util.List;
/**
 * 削除時のメール通知者を取得する
 * @author Doan Duy Hung
 *
 */
public interface CollectMailNotifierService {
	
	/**
	 * 削除時のメール通知者を取得する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @return
	 */
	public List<String> getMailNotifierList(String companyID, String rootStateID, Integer rootType);
	
}
