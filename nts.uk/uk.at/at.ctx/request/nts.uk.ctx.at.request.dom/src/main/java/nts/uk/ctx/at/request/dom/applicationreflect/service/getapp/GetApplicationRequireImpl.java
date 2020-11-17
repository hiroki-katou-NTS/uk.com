package nts.uk.ctx.at.request.dom.applicationreflect.service.getapp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;

@Stateless
public class GetApplicationRequireImpl {

	@Inject
	private GoBackDirectlyRepository repoGoBack;
	@Inject
	private BusinessTripRepository repoBusTrip;
	@Inject
	private ArrivedLateLeaveEarlyRepository repoLateLeave;
	@Inject
	private AppStampRepository repoStamp;
	@Inject
	private AppWorkChangeRepository repoWorkChange;

	public RequireImpl createImpl() {

		return new RequireImpl(repoGoBack, repoBusTrip, repoLateLeave, repoStamp, repoWorkChange);
	}

	@AllArgsConstructor
	public class RequireImpl implements GetApplicationForReflect.Require {

		private final GoBackDirectlyRepository repoGoBack;

		private final BusinessTripRepository repoBusTrip;

		private final ArrivedLateLeaveEarlyRepository repoLateLeave;

		private final AppStampRepository repoStamp;

		private final AppWorkChangeRepository repoWorkChange;

		@Override
		public Optional<AppWorkChange> findAppWorkCg(String companyId, String appID) {
			return repoWorkChange.findbyID(companyId, appID);
		}

		@Override
		public Optional<GoBackDirectly> findGoBack(String companyId, String appID) {
			return repoGoBack.find(companyId, appID);
		}

		@Override
		public Optional<AppStamp> findAppStamp(String companyId, String appID) {
			return repoStamp.findByAppID(companyId, appID);
		}

		@Override
		public Optional<ArrivedLateLeaveEarly> findArrivedLateLeaveEarly(String companyId, String appID, Application application) {
			ArrivedLateLeaveEarly app = repoLateLeave.getLateEarlyApp(companyId, appID, application);
			return app == null ? Optional.empty() : Optional.of(app);
			
		}

		@Override
		public Optional<BusinessTrip> findBusinessTripApp(String companyId, String appID) {
			return repoBusTrip.findByAppId(companyId, appID);
		}

	}
}
