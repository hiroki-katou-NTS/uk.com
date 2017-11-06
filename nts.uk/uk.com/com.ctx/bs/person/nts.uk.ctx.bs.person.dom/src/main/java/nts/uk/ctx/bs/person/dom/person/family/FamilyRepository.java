package nts.uk.ctx.bs.person.dom.person.family;

import java.util.List;

public interface FamilyRepository {
	
	Family getFamilyById(String familyId);
	
	List<Family> getListByPid(String pid);
	/**
	 * Add family ドメインモデル「家族」を新規登録する
	 * @param family
	 */
	void addFamily(Family family);
	/**
	 * Update family 取得した「家族」を更新する
	 * @param family
	 */
	void updateFamily(Family family);
}
