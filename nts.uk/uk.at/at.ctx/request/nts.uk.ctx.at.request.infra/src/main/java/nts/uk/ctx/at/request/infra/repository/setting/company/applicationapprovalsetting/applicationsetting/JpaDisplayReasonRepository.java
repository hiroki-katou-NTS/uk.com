package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.KrqmtAppHDReason;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.KrqmtAppType;

/**
 * refactor 4
 *
 * @author Doan Duy Hung
 */
@Stateless
public class JpaDisplayReasonRepository extends JpaRepository implements DisplayReasonRepository {

	@Override
	public Optional<DisplayReason> findByAppType(String companyID, ApplicationType appType) {
		String sql = "select FIXED_REASON_DISP_ATR, REASON_DISP_ATR from KRQMT_APP_TYPE where CID = @companyID and APP_TYPE = @appType";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramInt("appType", appType.value)
				.getSingle(rec -> {
						return DisplayReason.createAppDisplayReason(
								companyID, 
								rec.getInt("FIXED_REASON_DISP_ATR"), 
								rec.getInt("REASON_DISP_ATR"), 
								appType.value);
				});
	}

	@Override
	public Optional<DisplayReason> findByHolidayAppType(String companyID, HolidayAppType holidayAppType) {
		String sql = "select * from KRQMT_APP_HD_REASON where CID = @companyID and HOLIDAY_APP_TYPE = @holidayAppType";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramInt("holidayAppType", holidayAppType.value)
				.getSingle(rec -> {
						return DisplayReason.createHolidayAppDisplayReason(
								companyID, 
								rec.getInt("FIXED_REASON_DISP_ATR"), 
								rec.getInt("REASON_DISP_ATR"), 
								holidayAppType.value);
				});
	}

    @Override
    public List<DisplayReason> findByCompanyId(String companyId) {
        List<DisplayReason> result = new ArrayList<>();
        result.addAll(findAppDisplayReasonByCompanyId(companyId).stream().map(KrqmtAppType::toDisplayReason).collect(Collectors.toList()));
        result.addAll(findAppHolidayReasonByCompanyId(companyId).stream().map(KrqmtAppHDReason::toDomain).collect(Collectors.toList()));
        return result;
    }

    private List<KrqmtAppType> findAppDisplayReasonByCompanyId(String companyId) {
        List<KrqmtAppType> result = this.queryProxy()
                .query("select a from KrqmtAppType a where a.pk.companyId = :companyId and a.pk.applicationType != 1", KrqmtAppType.class)
                .setParameter("companyId", companyId)
                .getList();
        return result;
    }

    private List<KrqmtAppHDReason> findAppHolidayReasonByCompanyId(String companyId) {
        List<KrqmtAppHDReason> result = this.queryProxy()
                .query("Select a from KrqmtAppHDReason a where a.pk.companyId = :companyId", KrqmtAppHDReason.class)
                .setParameter("companyId", companyId).getList();
        return result;
    }

    @Override
    public void saveHolidayAppReason(String companyId, List<DisplayReason> domains) {
        List<KrqmtAppHDReason> entities = this.findAppHolidayReasonByCompanyId(companyId);
        domains.stream().filter(d -> d.getAppType() == ApplicationType.ABSENCE_APPLICATION)
                .forEach(d -> {
                    Optional<KrqmtAppHDReason> opt = entities.stream().filter(e -> e.getPk().holidayApplicationType == d.getOpHolidayAppType().get().value).findFirst();
                    if (opt.isPresent()) {
                        KrqmtAppHDReason entity = opt.get();
                        entity.setDisplayFixedAtr(d.getDisplayFixedReason().value);
                        entity.setDisplayAppAtr(d.getDisplayAppReason().value);
                        this.commandProxy().update(entity);
                    } else {
                        this.commandProxy().insert(KrqmtAppHDReason.fromDomain(d));
                    }
                });
    }

}
