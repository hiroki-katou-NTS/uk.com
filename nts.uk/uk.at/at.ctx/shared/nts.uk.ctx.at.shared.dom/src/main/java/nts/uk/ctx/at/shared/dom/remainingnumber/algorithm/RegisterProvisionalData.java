package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;
/**
 * 暫定データの登録（複数社員）
 * @author Doan Duy Hung
 *
 */
public interface RegisterProvisionalData {
	
	/**
	 * 暫定データの登録（複数社員）
	 * @param companyID
	 * @param empProLst 社員リスト
	 */
	public void registerProvisionalData(String companyID, List<EmpProvisionalInput> empProLst); 
	
}
