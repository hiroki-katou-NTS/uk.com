package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.List;

public interface CareerPathRepository {

	/**キャリア条件の取得*/
	List<Career> getCareerPathRequirement(String companyId, String historyId, String careerTypeItem);
	
	/**キャリアパスの取得*/
	CareerPath getCareerPath(String companyId, String historyId);
	
	/**キャリアパスの登録*/
	void addCareerPath();
	
	/**キャリアパスの削除*/
	void removeCareerPath();
}
