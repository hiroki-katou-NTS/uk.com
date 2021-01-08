package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Optional;

public interface AppOvertimeDetail_UpdateRepository {
	
	Optional<AppOvertimeDetail_Update> getAppOvertimeDetailById(String cid, String appId);

}
