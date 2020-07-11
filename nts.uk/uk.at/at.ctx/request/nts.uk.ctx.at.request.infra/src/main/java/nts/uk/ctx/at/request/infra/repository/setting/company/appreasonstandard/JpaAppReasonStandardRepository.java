package nts.uk.ctx.at.request.infra.repository.setting.company.appreasonstandard;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppReasonStandardRepository extends JpaRepository implements AppReasonStandardRepository {

	@Override
	public Optional<AppReasonStandard> findByAppType(String companyID, ApplicationType appType) {
		String sql = "select * from KRCMT_APP_REASON where CID = @companyID and APP_TYPE = @appType";
		List<ReasonTypeItem> reasonTypeItemLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramInt("appType", appType.value)
				.getList(rec -> {
					return ReasonTypeItem.createNew(
							rec.getInt("REASON_CD"), 
							rec.getInt("DISPORDER"), 
							BooleanUtils.toBoolean(rec.getInt("DEFAULT_ATR")), 
							rec.getString("REASON_TEMP"));
				});
		if(CollectionUtil.isEmpty(reasonTypeItemLst)) {
			return Optional.empty();
		}
		return Optional.of(new AppReasonStandard(companyID, appType, reasonTypeItemLst, Optional.empty()));
	}

	@Override
	public Optional<AppReasonStandard> findByHolidayAppType(String companyID, HolidayAppType holidayAppType) {
		return Optional.empty();
	}

}
