package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;

/**
 * 組織の表示情報 --- 	Temporary	
 * @author Hieult
 *
 */
@Getter
@RequiredArgsConstructor
public class DisplayInfoOrganization {
	
	/** 呼称 **/
	private final String designation;	
	/** コード **/
	private final String code;

	/** 名称 **/
	private final String name;
	/** 呼称 **/
	private final String displayName;
	/** 呼称 **/
	private final String genericTerm;
    
	/**
	 * [C-1] 職場グループの表示情報を作成する
	 * @return
	 */
	public DisplayInfoOrganization createDisplayInforWorkplaceGroup(WorkplaceGroupImport workplaceGroupImport){
		//http://192.168.50.4:3000/issues/110650 chờ QA  
		return null;
	} 
}
