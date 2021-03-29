package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import lombok.Getter;
import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;

/**
 * VO : 対象組織識別情報
 * 
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

	
	public TargetOrgIdenInfor(TargetOrganizationUnit unit, Optional<String> workplaceId,
			Optional<String> workplaceGroupId) {
		super();
		this.unit = unit;
		this.workplaceId = workplaceId;
		this.workplaceGroupId = workplaceGroupId;
	}

	/**
	 * [C-1] 職場グループを指定して識別情報を作成する
	 * 
	 * @param workplaceGroupId
	 * @return
	 */
	public static TargetOrgIdenInfor creatIdentifiWorkplaceGroup(String workplaceGroupId) {
		return new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE_GROUP, Optional.empty(), Optional.ofNullable(workplaceGroupId));

	}

	/**
	 * [C-2] 職場を指定して識別情報を作成する
	 * 
	 * @param workplaceId
	 * @return
	 */
	public static TargetOrgIdenInfor creatIdentifiWorkplace(String workplaceId) {
		return new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE,Optional.ofNullable( workplaceId), Optional.empty());
	}
	
	/**
	 * [C-3] 単位と対象IDを指定して識別情報を作成する
	 * @param unit
	 * @param targetId
	 * @return
	 */
	public static TargetOrgIdenInfor createFromTargetUnit(TargetOrganizationUnit unit, String targetId) {
		
		switch (unit) {
			case WORKPLACE_GROUP:
				return creatIdentifiWorkplaceGroup(targetId);
			case WORKPLACE:
				return creatIdentifiWorkplace(targetId);
		}
		
		throw new RuntimeException("unit out of range."); 
	}

	// [1] 組織の表示情報を取得する
	public DisplayInfoOrganization getDisplayInfor(Require require, GeneralDate referenceDate) {

		switch (this.unit) {
		case WORKPLACE_GROUP:
			// $職場グループリスト = require.職場グループIDを指定して職場グループを取得する( list: @職場グループID )
			List<WorkplaceGroupImport> dataWorkplaceGroup = require
					.getSpecifyingWorkplaceGroupId(Arrays.asList(this.workplaceGroupId.get()));
			if (dataWorkplaceGroup.isEmpty()) {
				throw new BusinessException("Msg_37");
			}
			// return 組織の表示情報#職場グループの表示情報を作成する( $職場グループリスト.first() )
			return DisplayInfoOrganization.createDisplayInforWorkplaceGroup(dataWorkplaceGroup.get(0));
	
		default:
			// $職場情報一覧 = require.運用している職場をすべて取得する( list: @職場ID, 基準日 )
			List<WorkplaceInfo> dataWorkplace = require
					.getWorkplaceInforFromWkpIds(Arrays.asList(this.workplaceId.get()), referenceDate);
			if (dataWorkplace.isEmpty()) {
				throw new BusinessException("Msg_37");
			}
			// return 組織の表示情報#職場の表示情報を作成する( $職場情報一覧.first() )
			return DisplayInfoOrganization.createWorkplaceDisplayInformation(dataWorkplace.get(0));
		
		}
	}

	/**
	 * [2] 組織に属する職場を取得する
	 * 
	 * @param require
	 * @return
	 */
	public List<String> getWorkplaceBelongsOrganization(Require require) {
		List<String> result = new ArrayList<String>();
		// if @単位＝対象組織の単位．職場
		// return list: @職場ID
		if (this.unit.value == TargetOrganizationUnit.WORKPLACE.value) {
			result.add(this.workplaceId.get());
			return result;
		}
		// return require.職場グループに属する職場を取得する( @会社ID, @職場グループID )
		result.addAll(require.getWKPID(this.workplaceGroupId.get()));
		return result;
	}
	
	/**
	 * [3] 対象IDを返す
	 * @return unit = 職場 : 職場ID, unit = 職場グループ :  職場グループID
	 */
	public String getTargetId () {
		return this.unit == TargetOrganizationUnit.WORKPLACE ? 
				this.workplaceId.orElse(null) : this.workplaceGroupId.orElse(null);
	}
	

	public static interface Require {
		/**
		 * [R-1] 職場グループIDを指定して職場グループを取得する 
		 * 職場グループAdapter.職場グループIDを指定して取得する(
		 * List<職場グループID> )
		 * 
		 * @param workplacegroupId
		 * @return
		 */
		List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId);

		/**
		 * アルゴリズム.運用している職場の情報をすべて取得する( 会社ID, List<職場ID>, 基準日 )
		 * WorkplaceExportService
		 * @param companyId
		 * @param listWorkplaceId
		 * @param baseDate
		 * @return
		 */
		List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate);

		
		/**
		 * [R-3] 職場グループに属する職場を取得する 
		 * 職場グループ所属情報Repository.職場グループに属する職場を取得する(会社ID, 職場グループID)
		 * @param WKPGRPID
		 * @return
		 */
		List<String> getWKPID( String WKPGRPID);

	}

}
