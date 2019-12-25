package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;

public interface RetirePlanCourceService {

	/** 定年退職コースの取得*/
	List<RetirePlanCource> getRetirePlanCourse(String companyId);
	
	/** 定年退職コースの追加*/
	void addRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse);
	
	/** 定年退職コースの更新*/
	void updateRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse);
	
	/** 全ての定年退職コースの取得 */
	List<RetirePlanCource> getAllRetirePlanCource(String companyId);
	
	/** 定年退職コースIDリストから定年退職条件の取得 */
	List<RetirePlanCource> getRetireTermByRetirePlanCourceIdList(String companyId, List<String> retirePlanCourseId);
	
	/** 基準日で使用可能な定年退職コースの取得 */
	List<RetirePlanCource> getEnableRetirePlanCourceByBaseDate(String companyId, GeneralDate baseDate);
}
