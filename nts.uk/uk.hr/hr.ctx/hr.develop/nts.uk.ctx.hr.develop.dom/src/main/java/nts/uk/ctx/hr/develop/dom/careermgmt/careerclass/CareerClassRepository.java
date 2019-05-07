package nts.uk.ctx.hr.develop.dom.careermgmt.careerclass;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CareerClassRepository {

	/**キャリアマスタリストの取得*/
	List<CareerClass> getCareerClassList(String companyId, GeneralDate referenceDate);
}
