package nts.uk.file.at.app.export.roledaily;

import java.util.List;
import java.util.Map;

public interface BusinessDailyRepo {
	public Map<String, Map<Integer, List<BusinessDailyExcel>>> getAllByComp(String companyId);
}
