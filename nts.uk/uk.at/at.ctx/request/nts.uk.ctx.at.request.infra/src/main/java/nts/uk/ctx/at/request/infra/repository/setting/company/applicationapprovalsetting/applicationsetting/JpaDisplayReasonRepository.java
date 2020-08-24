package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
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

}
