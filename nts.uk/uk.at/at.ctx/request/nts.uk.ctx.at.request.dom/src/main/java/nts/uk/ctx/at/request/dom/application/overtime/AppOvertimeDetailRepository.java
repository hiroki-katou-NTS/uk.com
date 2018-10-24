package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Optional;

/**
 * 時間外時間の詳細
 */
public interface AppOvertimeDetailRepository {
	Optional<AppOvertimeDetail> getAppOvertimeDetailById(String cid, String appId);

}
