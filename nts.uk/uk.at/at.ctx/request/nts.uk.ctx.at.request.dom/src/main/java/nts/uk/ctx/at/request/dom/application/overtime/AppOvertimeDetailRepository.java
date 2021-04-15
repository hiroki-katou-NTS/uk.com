package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Optional;

public interface AppOvertimeDetailRepository {
	
	Optional<AppOvertimeDetail> getAppOvertimeDetailById(String cid, String appId);

}
