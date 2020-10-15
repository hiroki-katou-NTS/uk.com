package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.company.request.stamp.AppStampReflect;
/**
 * Refactor4
 * @author hoangnd
 *
 */
public interface AppStampReflectRepository {
	
	public Optional<AppStampReflect> findByAppID(String companyID);

	public void addStamp(AppStampReflect appStamp);

	public void updateStamp(AppStampReflect appStamp);

	public void delete(String companyID, String appID);
}
