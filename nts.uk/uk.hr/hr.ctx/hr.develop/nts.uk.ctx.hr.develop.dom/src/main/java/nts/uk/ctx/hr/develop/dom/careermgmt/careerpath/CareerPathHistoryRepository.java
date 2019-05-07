package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.List;

public interface CareerPathHistoryRepository {

	/**最新履歴の履歴IDの取得*/
	CareerPathHistory getLatestCareerPathHist();
	
	/**キャリアパスの履歴の取得*/
	List<CareerPathHistory> getCareerPathHistList();
	
	/**開始日の取得*/
	void getCareerPathStartDate();
	
	/**キャリアパスの履歴の追加*/
	void addCareerPathHist();
	
	/**キャリアパスの履歴の更新*/
	void updateCareerPathHist();
	
	/**キャリアパスの履歴の削除*/
	void removeCareerPathHist();
}
