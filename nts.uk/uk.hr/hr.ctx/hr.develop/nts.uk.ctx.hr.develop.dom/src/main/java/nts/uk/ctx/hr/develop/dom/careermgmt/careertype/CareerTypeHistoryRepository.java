package nts.uk.ctx.hr.develop.dom.careermgmt.careertype;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CareerTypeHistoryRepository {

	List<String> getLisHisId(String cId, GeneralDate referDate);
}
