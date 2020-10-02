package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.i18n.I18NText;
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
	/** 表示名 **/
	private final String displayName;
	/** 呼称 **/
	private final String genericTerm;
    
	/**
	 * [C-1] 職場グループの表示情報を作成する
	 * @return
	 */
	public static DisplayInfoOrganization createDisplayInforWorkplaceGroup(WorkplaceGroupImport workplaceGroupImport){
		return new DisplayInfoOrganization(I18NText.getText("Com_WorkplaceGroup"),
				workplaceGroupImport.getWorkplaceGroupCode(),
				workplaceGroupImport.getWorkplaceGroupName(),
				workplaceGroupImport.getWorkplaceGroupName(),
				workplaceGroupImport.getWorkplaceGroupName());
	} 
	/**
	 *[C-2] 職場の表示情報を作成する		
	 */
	public static DisplayInfoOrganization createWorkplaceDisplayInformation(WorkplaceInfo workplaceInfo ){
		
		return new DisplayInfoOrganization(
				I18NText.getText("Com_Workplace"),
				workplaceInfo.getWorkplaceCd().get(),
				workplaceInfo.getWorkplaceName().get(),
				workplaceInfo.getWkpDisplayName().get(),
				workplaceInfo.getWkpGenericName().get());
	}
}
