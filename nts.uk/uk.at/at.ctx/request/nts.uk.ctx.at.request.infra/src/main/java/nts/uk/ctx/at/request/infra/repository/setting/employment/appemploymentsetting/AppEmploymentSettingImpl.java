package nts.uk.ctx.at.request.infra.repository.setting.employment.appemploymentsetting;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting.KrqdtAppEmployWorktype;

@Stateless
public class AppEmploymentSettingImpl extends JpaRepository implements AppEmploymentSettingRepository {
	private static final String FINDER_ALL ="SELECT e FROM KrqdtAppEmployWorktype e";
	
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
	private AppEmployWorkType convertToDomain(KrqdtAppEmployWorktype entity){
		return AppEmployWorkType.createSimpleFromJavaType(entity.getKrqdtAppEmployWorktypePK().getCid(),
				entity.getKrqdtAppEmployWorktypePK().getEmploymentCode(),
				entity.getKrqdtAppEmployWorktypePK().getAppType(),
				entity.getKrqdtAppEmployWorktypePK().getHolidayOrPauseType(),
				entity.getKrqdtAppEmployWorktypePK().getWorkTypeCode());
	}
}
