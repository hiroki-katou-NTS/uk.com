package nts.uk.file.at.app.export.worktype;

import java.util.List;

public interface ApprovalFunctionConfigRepository {
	List<Object[]> getAllApprovalFunctionConfig(String cid, String baseDate);
	List<Object[]> getAllEmploymentApprovalSetting(String cid);
}
