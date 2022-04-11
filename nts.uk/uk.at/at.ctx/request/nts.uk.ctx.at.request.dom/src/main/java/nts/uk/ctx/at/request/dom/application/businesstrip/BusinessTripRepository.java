package nts.uk.ctx.at.request.dom.application.businesstrip;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;

public interface BusinessTripRepository {

    Optional<BusinessTrip> findById(String companyId, String appId, GeneralDate date);

    Optional<BusinessTrip> findByAppId(String companyId, String appId);

    void add (BusinessTrip domain);

    void update(BusinessTrip domain);

    void remove(BusinessTrip domain);

    Optional<BusinessTrip> findByAppId(String companyId, String appId, Application app);

}
