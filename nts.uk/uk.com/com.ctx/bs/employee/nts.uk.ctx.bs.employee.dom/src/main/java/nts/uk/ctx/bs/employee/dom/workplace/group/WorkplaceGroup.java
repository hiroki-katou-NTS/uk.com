package nts.uk.ctx.bs.employee.dom.workplace.group;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.text.IdentifierUtil;

/**
 * 職場グループ
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.職場.職場グループ.職場グループ
 * @author phongtq
 */
@Getter
@AllArgsConstructor
public class WorkplaceGroup implements DomainAggregate {

	/** 会社ID */
	private final String CID;
	/** 職場グループID */
	private final String id;
	/** 職場グループコード */
	private final WorkplaceGroupCode code;
	/** 職場グループ名称 */
	@Setter
	private WorkplaceGroupName name;
	/** 職場グループ種別 */
	@Setter
	private WorkplaceGroupType type;



	/**
	 * 職場グループを作成する
	 * @param cid 会社ID
	 * @param code 職場グループコード
	 * @param name 職場グループ名称
	 * @param type 職場グループ種別
	 * @return
	 */
	public static WorkplaceGroup create(String cid, WorkplaceGroupCode code, WorkplaceGroupName name, WorkplaceGroupType type) {
		return new WorkplaceGroup(cid, IdentifierUtil.randomUniqueId(), code, name, type);
	}



	/**
	 * 所属する職場を追加する
	 * @param id 職場グループID
	 * @return
	 */
	public AffWorkplaceGroup addAffWorkplaceGroup(String id){
		return new AffWorkplaceGroup(this.id, id);
	}

	/**
	 * 職場グループに属する職場を取得する
	 * @param require
	 * @return
	 */
	public List<String> getAffWorkplace(Require require){
		return require.getWorkplacesInGroup(this.CID, this.id);
	}





	public static interface Require {

		/**
		 * 職場グループに属する職場を取得する
		 * @param cid 会社ID
		 * @param id 職場グループID
		 * @return
		 */
		List<String> getWorkplacesInGroup(String cid, String id);

	}

}
