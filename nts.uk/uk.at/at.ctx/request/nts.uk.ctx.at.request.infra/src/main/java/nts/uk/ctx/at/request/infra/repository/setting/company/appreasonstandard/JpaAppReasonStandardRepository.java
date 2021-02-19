package nts.uk.ctx.at.request.infra.repository.setting.company.appreasonstandard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationstandardreason.KrcmtAppReason;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationstandardreason.KrcmtAppReasonPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppReasonStandardRepository extends JpaRepository implements AppReasonStandardRepository {

	@Override
	public Optional<AppReasonStandard> findByAppType(String companyID, ApplicationType appType) {
		String sql = "select * from KRCMT_APP_REASON where CID = @companyID and APP_TYPE = @appType order by DISPORDER ASC";
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
		List<KrcmtAppReason> entities = this.queryProxy()
				.query("select a from KrcmtAppReason a where a.pk.applicationType = 1 AND a.pk.companyId = :companyId and a.pk.holidayAppType = :holidayAppType", KrcmtAppReason.class)
				.setParameter("companyId", companyID)
				.setParameter("holidayAppType", holidayAppType.value)
				.getList();
		return Optional.ofNullable(KrcmtAppReason.toDomain(entities));
	}

	@Override
	public Optional<AppReasonStandard> findByCD(ApplicationType appType, AppStandardReasonCode appStandardReasonCode) {
		String companyID = AppContexts.user().companyId();
		Optional<AppReasonStandard> opAppReasonStandard = this.findByAppType(companyID, appType);
		if(!opAppReasonStandard.isPresent()) {
			return Optional.empty();
		}
		List<ReasonTypeItem> reasonTypeItemLst = opAppReasonStandard.get().getReasonTypeItemLst();
		Optional<ReasonTypeItem> opReasonTypeItem = reasonTypeItemLst.stream().filter(x -> x.getAppStandardReasonCD().equals(appStandardReasonCode)).findAny();
		if(opReasonTypeItem.isPresent()) {
			return Optional.of(new AppReasonStandard(
								companyID, 
								opAppReasonStandard.get().getApplicationType(), 
								Arrays.asList(opReasonTypeItem.get()), 
								opAppReasonStandard.get().getOpHolidayAppType()));
		}
		return Optional.empty();
	}

	@Override
	public List<AppReasonStandard> findByCompanyId(String companyID) {
		List<AppReasonStandard> result = new ArrayList<>();
		List<KrcmtAppReason> entities = this.queryProxy().query("select a from KrcmtAppReason a where a.pk.companyId = :companyId", KrcmtAppReason.class)
				.setParameter("companyId", companyID)
				.getList();
		Map<Integer, List<KrcmtAppReason>> mapEntities = entities.stream().collect(Collectors.groupingBy(KrcmtAppReason::getAppType));
		mapEntities.forEach((appType, items) -> {
			if (appType == 1) {
				Map<Integer, List<KrcmtAppReason>> mapHdAppType = items.stream().collect(Collectors.groupingBy(KrcmtAppReason::getHolidayAppType));
				mapHdAppType.forEach((hdAppType, hdItems) -> {
					result.add(KrcmtAppReason.toDomain(hdItems));
				});
			} else {
				result.add(KrcmtAppReason.toDomain(items));
			}
		});
		return result;
	}

	@Override
	public void saveReasonTypeItem(String companyId, int appType, Integer holidayAppType, ReasonTypeItem reasonItem) {
		KrcmtAppReasonPk pk = new KrcmtAppReasonPk(companyId, appType, reasonItem.getAppStandardReasonCD().v(), appType == 1 ? holidayAppType : 0);
		Optional<KrcmtAppReason> optEntity = this.queryProxy().find(pk, KrcmtAppReason.class);
		if (optEntity.isPresent()) {
			KrcmtAppReason entity = optEntity.get();
			entity.setDisplayOrder(reasonItem.getDisplayOrder());
			entity.setReasonTemp(reasonItem.getReasonForFixedForm().v());
			entity.setDefaultAtr(BooleanUtils.toInteger(reasonItem.isDefaultValue()));
			this.commandProxy().update(entity);
		} else {
			KrcmtAppReason entity = new KrcmtAppReason(
					pk, reasonItem.getDisplayOrder(),
					reasonItem.getReasonForFixedForm().v(),
					BooleanUtils.toInteger(reasonItem.isDefaultValue())
			);
			this.commandProxy().insert(entity);
		}
	}

	@Override
	public void deleteReasonTypeItem(String companyId, int appType, Integer holidayAppType, int reasonCode) {
		this.commandProxy().remove(KrcmtAppReason.class, new KrcmtAppReasonPk(companyId, appType, reasonCode, appType == 1 ? holidayAppType : 0));
	}

	@Override
	public List<AppReasonStandard> findByListAppType(String companyID, List<Integer> appTypes) {
		List<AppReasonStandard> result = new ArrayList<>();
		List<KrcmtAppReason> entities = this.queryProxy().query("select a from KrcmtAppReason a where a.pk.companyId = :companyId and a.pk.applicationType IN :appTypes", KrcmtAppReason.class)
				.setParameter("companyId", companyID)
				.setParameter("appTypes", appTypes)
				.getList();
		Map<Integer, List<KrcmtAppReason>> mapEntities = entities.stream().collect(Collectors.groupingBy(KrcmtAppReason::getAppType));
		mapEntities.forEach((appType, items) -> {
			if (appType == 1) {
				Map<Integer, List<KrcmtAppReason>> mapHdAppType = items.stream().collect(Collectors.groupingBy(KrcmtAppReason::getHolidayAppType));
				mapHdAppType.forEach((hdAppType, hdItems) -> {
					result.add(KrcmtAppReason.toDomain(hdItems));
				});
			} else {
				result.add(KrcmtAppReason.toDomain(items));
			}
		});
		return result;
	}

}
