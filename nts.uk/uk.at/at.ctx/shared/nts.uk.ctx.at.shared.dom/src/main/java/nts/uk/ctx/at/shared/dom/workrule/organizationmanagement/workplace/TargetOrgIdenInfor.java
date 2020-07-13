package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;


/**
 * VO : 対象組織識別情報
 * @author tutk
 *
 */
@Value
public class TargetOrgIdenInfor implements DomainValue {

	/**
	 * 単位
	 */
	@Getter
	private final TargetOrganizationUnit unit;
	
	/**
	 * 職場ID
	 */
	@Getter
	private final Optional<String> workplaceId;
	
	/**
	 * 職場グループID
	 */
	@Getter
	private final Optional<String> workplaceGroupId;
	
	
	
	
	public TargetOrgIdenInfor(TargetOrganizationUnit unit, String workplaceId, String workplaceGroupId) {
		super();
		this.unit = unit;
		this.workplaceId = Optional.ofNullable(workplaceId);
		this.workplaceGroupId = Optional.ofNullable(workplaceGroupId);
	}
	/**
	 * 	[C-1] 職場グループを指定して識別情報を作成する	
	 * @param workplaceGroupId
	 * @return
	 */
	public static TargetOrgIdenInfor creatIdentifiWorkplaceGroup(String workplaceGroupId){
		return new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE_GROUP, null , workplaceGroupId);
		
	}
	/**
	 * [C-2] 職場を指定して識別情報を作成する	
	 * @param workplaceId
	 * @return
	 */
	public static TargetOrgIdenInfor creatIdentifiWorkplace(String workplaceId){
		return new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE_GROUP, workplaceId , null);
	}
	
	//	[1] 組織の表示情報を取得する
	public DisplayInfoOrganization getDisplayInfor(Require require , GeneralDate referenceDate , TargetOrgIdenInfor targetOrgIdenInfor ){
		//http://192.168.50.4:3000/issues/110650
		return null;
	}
	/**
	 * [2] 組織に属する職場を取得する	
	 * @param require
	 * @return
	 */
	public List<String> getWorkplaceBelongsOrganization (Require require){
		List<String> result = new ArrayList<String>();
		if(this.unit.value == TargetOrganizationUnit.WORKPLACE.value){
			result.add(this.workplaceId.get());
		}
		//http://192.168.50.4:3000/issues/110653 QA
		return null;
		
	}
	
	
	
	
	public static interface Require{
		/**
		 * [R-1] 職場グループIDを指定して職場グループを取得する
		 * 職場グループAdapter.職場グループIDを指定して取得する( List<職場グループID> )
		 * @param workplacegroupId
		 * @return
		 */
		List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId( List<String> workplacegroupId);
		/**
		 * アルゴリズム.運用している職場の情報をすべて取得する( 会社ID, List<職場ID>, 基準日 )	
		 * WorkplaceExportService
		 * @param companyId
		 * @param listWorkplaceId
		 * @param baseDate
		 * @return
		 */
		List<WorkplaceInforParam> getWorkplaceInforFromWkpIds(String companyId, List<String> listWorkplaceId,
				GeneralDate baseDate); 
		
		/** 																																				
		 * [R-3] 職場グループに属する職場を取得する	
		 * 職場グループ所属情報Repository.職場グループに属する職場を取得する(会社ID, 職場グループID)			
		 * @param CID
		 * @param WKPID
		 * @return
		 */
		Optional<AffWorkplaceGroupImport> getByWKPID(String CID, String WKPID);							

		
	}
	
	
}
