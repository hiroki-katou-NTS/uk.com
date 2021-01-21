package nts.uk.ctx.at.request.infra.repository.setting.employment.appemploymentsetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.TargetWorkTypeByApp;
import nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting.KrqmtAppApvEmp;
import nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting.KrqmtAppApvEmpPK;

/**
 * refactor 4
 *
 * @author Doan Duy Hung
 */
@Stateless
public class JpaAppEmploymentSetRepository extends JpaRepository implements AppEmploymentSetRepository {

    @Override
    public List<AppEmploymentSet> findByCompanyID(String companyID) {
        List<KrqmtAppApvEmp> entities = this.queryProxy().query("select a from KrqmtAppApvEmp a where a.pk.cid = :companyId", KrqmtAppApvEmp.class)
                .setParameter("companyId", companyID)
                .getList();
        Map<String, List<KrqmtAppApvEmp>> mapEntity = entities.stream().collect(Collectors.groupingBy(KrqmtAppApvEmp::getEmploymentCode));
        return mapEntity.values().stream().map(KrqmtAppApvEmp::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<AppEmploymentSet> findByCompanyIDAndEmploymentCD(String companyID, String employmentCD) {
        return Optional.ofNullable(
                KrqmtAppApvEmp.toDomain(
                        this.queryProxy().query("select a from KrqmtAppApvEmp a where a.pk.cid = :companyId and a.pk.employmentCode = :employmentCode", KrqmtAppApvEmp.class)
                                .setParameter("companyId", companyID)
                                .setParameter("employmentCode", employmentCD)
                                .getList()
                )
        );
    }

    @Override
    public void insert(AppEmploymentSet domain) {
        this.commandProxy().insertAll(KrqmtAppApvEmp.fromDomain(domain));
    }

    @Override
    public void update(AppEmploymentSet domain) {
        List<KrqmtAppApvEmp> entities = this.queryProxy().query("select a from KrqmtAppApvEmp a where a.pk.cid = :companyId and a.pk.employmentCode = :employmentCode", KrqmtAppApvEmp.class)
                .setParameter("companyId", domain.getCompanyID())
                .setParameter("employmentCode", domain.getEmploymentCD())
                .getList();
        if (!entities.isEmpty()) {
            domain.getTargetWorkTypeByAppLst().forEach(t -> {
                Optional<KrqmtAppApvEmp> optEntity;
                switch (t.getAppType()) {
                    case ABSENCE_APPLICATION:
                        optEntity = entities.stream().filter(e -> e.getPk().getAppType() == t.getAppType().value && e.getPk().getHolidayOrPauseType() == t.getOpHolidayAppType().get().value).findFirst();
                        break;
                    case BUSINESS_TRIP_APPLICATION:
                        optEntity = entities.stream().filter(e -> e.getPk().getAppType() == t.getAppType().value && e.getPk().getHolidayOrPauseType() == t.getOpBusinessTripAppWorkType().get().value).findFirst();
                        break;
                    case COMPLEMENT_LEAVE_APPLICATION:
                        optEntity = entities.stream().filter(e -> e.getPk().getAppType() == t.getAppType().value && e.getPk().getHolidayOrPauseType() == t.getOpBreakOrRestTime().get().value).findFirst();
                        break;
                    default:
                        optEntity = entities.stream().filter(e -> e.getPk().getAppType() == t.getAppType().value && e.getPk().getHolidayOrPauseType() == 9).findFirst();
                        break;
                }
                if (optEntity.isPresent()) {
                    KrqmtAppApvEmp entity = optEntity.get();
                    entity.update(t);
                    this.commandProxy().update(entity);
                } else {
                    this.commandProxy().insert(KrqmtAppApvEmp.fromDomain(domain.getCompanyID(), domain.getEmploymentCD(), t));
                }
            });
        }
    }

    @Override
    public void delete(String companyId, String employmentCode) {
        List<KrqmtAppApvEmp> entities = this.queryProxy().query("select a from KrqmtAppApvEmp a where a.pk.cid = :companyId and a.pk.employmentCode = :employmentCode", KrqmtAppApvEmp.class)
                .setParameter("companyId", companyId)
                .setParameter("employmentCode", employmentCode)
                .getList();
        this.commandProxy().removeAll(entities);
        this.getEntityManager().flush();
    }

}
