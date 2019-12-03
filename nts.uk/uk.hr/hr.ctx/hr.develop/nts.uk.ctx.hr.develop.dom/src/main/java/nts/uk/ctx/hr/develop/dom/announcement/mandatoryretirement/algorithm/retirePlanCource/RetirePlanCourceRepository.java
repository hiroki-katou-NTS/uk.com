package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;

public interface RetirePlanCourceRepository {

	/** 定年退職コースの取得*/
	List<RetirePlanCource> getRetirePlanCourse(String companyID);
	
	/** 定年退職コースの追加*/
	void addRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse);
	
	/** 定年退職コースの更新*/
	void updateRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse);
	
	/** 全ての定年退職コースの取得 */
	List<RetirePlanCource> getAllRetirePlanCource(String companyID);
	
	/** 定年退職コースIDリストから定年退職条件の取得 */
	RetirePlanCource getRetireTermByRetirePlanCourceIdList(String companyID, String retirePlanCourseIdList);
	
	/** 基準日で使用可能な定年退職コースの取得 */
	List<RetirePlanCource> getEnableRetirePlanCourceByBaseDate(String companyID, GeneralDate baseDate);
}
