package nts.uk.ctx.bs.employee.dom.workplace.group;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.text.IdentifierUtil;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.職場.職場グループ.職場グループ
 * @author phongtq
 *
 */

@Getter
@Setter
public class WorkplaceGroup implements DomainAggregate {
										
	/** 会社ID */
	private final String CID;
	
	/** 職場グループID */
	private final String WKPGRPID;
	
	/** 職場グループコード */
	private final WorkplaceGroupCode WKPGRPCode;
	
	/** 職場グループ名称 */
	private WorkplaceGroupName WKPGRPName;
	
	/** 職場グループ種別 */
	private WorkplaceGroupType WKPGRPType;

	/**
	 * [C-1] 職場グループを作成する
	 * @param cID
	 * @param wKPGRPID
	 * @param wKPGRPCode
	 * @param wKPGRPName
	 * @param wKPGRPType
	 */
	public WorkplaceGroup(String cID, String wKPGRPID, WorkplaceGroupCode wKPGRPCode, WorkplaceGroupName wKPGRPName, WorkplaceGroupType wKPGRPType) {
		super();
		CID = cID;
		WKPGRPID = wKPGRPID == null ? IdentifierUtil.randomUniqueId() : wKPGRPID;
		WKPGRPCode = wKPGRPCode;
		WKPGRPName = wKPGRPName;
		WKPGRPType = wKPGRPType;
	}
	/**
	 * [1] 所属する職場を追加する
	 * @param WKPID
	 * @return
	 */
	public AffWorkplaceGroup addAffWorkplaceGroup(String WKPID){
		return new AffWorkplaceGroup(this.WKPGRPID, WKPID);
	}
	
	/**
	 * 	[2] 職場グループに属する職場を取得する
	 * @param require
	 * @return
	 */
	public List<String> getAffWorkplace(Require require){
		return require.getWKPID(this.CID, this.WKPGRPID);
	}
	
	/**
	 * [R-1] 職場グループに属する職場を取得する	
	 *
	 */
	public static interface Require {
		// 職場グループに属する職場を取得する																					
		// 職場グループ所属情報Repository.職場グループに所属する職場を取得する(会社ID, 職場グループID)
		List<String> getWKPID(String CID, String WKPGRPID);
	}
}
