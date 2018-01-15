package nts.uk.ctx.at.request.infra.repository.setting.employment.appemploymentsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting.KrqdtAppEmployWorktype;
import nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting.KrqstAppEmploymentSet;

@Stateless
public class JpaAppEmploymentSettingRepository extends JpaRepository implements AppEmploymentSettingRepository {
	private static final String FINDER_ALL ="SELECT e FROM KrqdtAppEmployWorktype e";
	private static final String FIND_EMPLOYMENT_SET = "SELECT c FROM KrqstAppEmploymentSet c "
			+ "WHERE c.krqstAppEmploymentSetPK.cid = :companyId "
			+ "AND c.krqstAppEmploymentSetPK.employmentCode = :employmentCode AND c.krqstAppEmploymentSetPK.appType = :appType";
			
	
	private static final String FIND;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FINDER_ALL);
		query.append(" WHERE e.krqdtAppEmployWorktypePK.cid = :companyID");
		query.append(" AND e.krqdtAppEmployWorktypePK.employmentCode = :employmentCode");
		query.append(" AND e.krqdtAppEmployWorktypePK.appType = :appType");
		query.append(" ORDER BY e.krqdtAppEmployWorktypePK.workTypeCode ASC");
		FIND = query.toString();
	}
	
	@Override
	public List<AppEmployWorkType> getEmploymentWorkType(String companyID, String employmentCode, int appType) {
		return this.queryProxy().query(FIND,KrqdtAppEmployWorktype.class)
				.setParameter("companyID", companyID).setParameter("employmentCode", employmentCode).setParameter("appType", appType).getList(c -> convertToDomain(c));
	}
	
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode, int appType){
		return this.queryProxy().query(FIND_EMPLOYMENT_SET, KrqstAppEmploymentSet.class)
				.setParameter("companyId", companyId)
				.setParameter("employmentCode", employmentCode)
				.setParameter("appType", appType)
				.getList(c -> toDomain(c));
				
	}
	
	private AppEmployWorkType convertToDomain(KrqdtAppEmployWorktype entity){
		return AppEmployWorkType.createSimpleFromJavaType(entity.getKrqdtAppEmployWorktypePK().getCid(),
				entity.getKrqdtAppEmployWorktypePK().getEmploymentCode(),
				entity.getKrqdtAppEmployWorktypePK().getAppType(),
				entity.getKrqdtAppEmployWorktypePK().getHolidayOrPauseType(),
				entity.getKrqdtAppEmployWorktypePK().getWorkTypeCode());
	}
	
	private AppEmploymentSetting toDomain(KrqstAppEmploymentSet entity) {
		boolean holidayTypeUseFlg = false;
		if(Optional.ofNullable(entity.getHolidayTypeUseFlg()) != null || entity.getHolidayTypeUseFlg() == 0) {
			holidayTypeUseFlg = false;
		}else {
			holidayTypeUseFlg = true;
		}
		List<AppEmployWorkType> lstAppEmployWorkType = entity.krqdtAppEmployWorktype.stream()
				.map(x -> AppEmployWorkType.createSimpleFromJavaType(x.getKrqdtAppEmployWorktypePK().getCid(),
						x.getKrqdtAppEmployWorktypePK().getEmploymentCode(),
						x.getKrqdtAppEmployWorktypePK().getAppType(),
						x.getKrqdtAppEmployWorktypePK().getHolidayOrPauseType(),
						x.getKrqdtAppEmployWorktypePK().getWorkTypeCode())
				).collect(Collectors.toList());
		return new AppEmploymentSetting(entity.getKrqstAppEmploymentSetPK().getCid(),
				entity.getKrqstAppEmploymentSetPK().getEmploymentCode(),
				EnumAdaptor.valueOf(entity.getKrqstAppEmploymentSetPK().getAppType(), ApplicationType.class),
				entity.getKrqstAppEmploymentSetPK().getHolidayOrPauseType(),
				holidayTypeUseFlg,
				entity.getDisplayFlag() == 0 ? false: true,
				lstAppEmployWorkType);
	}
}
