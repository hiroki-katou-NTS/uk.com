package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.List;
import java.util.Map;

public interface BusinessDailyRepo {
	public Map<String, Map<Integer, List<BusinessDailyExcel>>> getAllByComp(String companyId);
}
