package nts.uk.ctx.bs.person.dom.person.family;

import java.util.List;

public interface FamilyMemberRepository {
	
	FamilyMember getFamilyById(String familyId);
	
	List<FamilyMember> getListByPid(String pid);
	/**
	 * Add family ドメインモデル「家族」を新規登録する
	 * @param family
	 */
	void addFamily(FamilyMember family);
	/**
	 * Update family 取得した「家族」を更新する
	 * @param family
	 */
	void updateFamily(FamilyMember family);
	
	/**
	 * ドメインモデル「家族」を取得する
	 * @param pid
	 * @param relationShipCodes
	 * @return
	 */
	List<FamilyMember> getListByPidAndRelationCode(String pid, List<String> relationShipCodes);
}
