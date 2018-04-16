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
import nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting.KrqdtAppEmployWorktypePK;
import nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting.KrqstAppEmploymentSet;
import nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting.KrqstAppEmploymentSetPK;

@Stateless
public class JpaAppEmploymentSettingRepository extends JpaRepository implements AppEmploymentSettingRepository {
	private static final String FINDER_ALL ="SELECT e FROM KrqdtAppEmployWorktype e";
	private static final String FINDER_ALL_EMPLOYMENT_SET ="SELECT e FROM KrqstAppEmploymentSet e";
	private static final String FIND_EMPLOYMENT_SET = "SELECT c FROM KrqstAppEmploymentSet c "
			+ "WHERE c.krqstAppEmploymentSetPK.cid = :companyId "
			+ "AND c.krqstAppEmploymentSetPK.employmentCode = :employmentCode AND c.krqstAppEmploymentSetPK.appType = :appType";
			
	private static final String FIND_EMPLOYMENT_BY_COMPANYID = FINDER_ALL_EMPLOYMENT_SET + " WHERE e.krqstAppEmploymentSetPK.cid = :companyId";
	private static final String FIND_EMPLOYMENT_BY_EMPLOYMENTCD = FIND_EMPLOYMENT_BY_COMPANYID + " AND e.krqstAppEmploymentSetPK.employmentCode = :employmentCode";
	
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
	
	private static final String DELETE_WORKTYPE_SET = "DELETE FROM KrqdtAppEmployWorktype c "
			+ "WHERE c.krqdtAppEmployWorktypePK.cid =:companyId "
			+ "AND c.krqdtAppEmployWorktypePK.employmentCode =:employmentCode "
			+ "AND c.krqdtAppEmployWorktypePK.appType =:appType "
			+ "AND c.krqdtAppEmployWorktypePK.holidayOrPauseType =:holidayOrPauseType ";
	private static final String DELETE_WORKTYPE_SET_BY_CODE = "DELETE FROM KrqdtAppEmployWorktype c "
			+ "WHERE c.krqdtAppEmployWorktypePK.cid =:companyId "
			+ "AND c.krqdtAppEmployWorktypePK.employmentCode =:employmentCode ";
	private static final String DELETE_EMPLOYMENT_SET = "DELETE FROM KrqstAppEmploymentSet c "
			+ "WHERE c.krqstAppEmploymentSetPK.cid =:companyId "
			+ "AND c.krqstAppEmploymentSetPK.employmentCode =:employmentCode ";
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
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId){
		return this.queryProxy().query(FIND_EMPLOYMENT_BY_COMPANYID, KrqstAppEmploymentSet.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
				
	}
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode){
		return this.queryProxy().query(FIND_EMPLOYMENT_BY_EMPLOYMENTCD, KrqstAppEmploymentSet.class)
				.setParameter("companyId", companyId)
				.setParameter("employmentCode", employmentCode)
				.getList(c -> toDomain(c));
				
	}
	/**
	 * Insert list employment setting
	 */
	public void insert(List<AppEmploymentSetting> employmentList){
		commandProxy().insertAll(employmentList.stream()
				.map(item -> toEntity(item))
				.collect(Collectors.toList()));
	}
	
	public void update(List<AppEmploymentSetting> employmentList){
		//Update workType code
		updateWorkType(employmentList);
		//Update employment setting
		List<KrqstAppEmploymentSet> updateLst = employmentList.stream()
				.map(item -> {
					Optional<KrqstAppEmploymentSet> updateItemOp = queryProxy().find(new KrqstAppEmploymentSetPK(
																			item.getCompanyID(), 
																			item.getEmploymentCode(), 
																			item.getAppType().value, 
																			item.getHolidayOrPauseType()), KrqstAppEmploymentSet.class);
					if (updateItemOp.isPresent()) {
						KrqstAppEmploymentSet updateItem = updateItemOp.get();
						Integer holidayTypeUseFlg = null;
						if (item.getHolidayTypeUseFlg() != null) {
							holidayTypeUseFlg = item.getHolidayTypeUseFlg().booleanValue() ? 1: 0;
						}
						//updateItem.setVersion(item.getVersion());
						updateItem.setHolidayTypeUseFlg(item.getHolidayOrPauseType());
						updateItem.setDisplayFlag(item.isDisplayFlag() == true ? 1 : 0);
						updateItem.setHolidayTypeUseFlg(holidayTypeUseFlg);						
						return updateItem;
					}
					return toEntity(item);			
				})
				.collect(Collectors.toList());
		commandProxy().updateAll(updateLst);
	}
	public void remove(String companyId, String employmentCode){		
		/*List<KrqstAppEmploymentSet> deleteList = this.queryProxy().query(FIND_EMPLOYMENT_BY_EMPLOYMENTCD, KrqstAppEmploymentSet.class)
				.setParameter("companyId", companyId)
				.setParameter("employmentCode", employmentCode)
				.getList();
		if (!CollectionUtil.isEmpty(deleteList)) {
			this.commandProxy().removeAll(deleteList);
		}*/		
		//Delete Employment
		this.getEntityManager().createQuery(DELETE_EMPLOYMENT_SET).setParameter("companyId", companyId)
		.setParameter("employmentCode", employmentCode)
		.executeUpdate();
		//Delete work type
		this.getEntityManager().createQuery(DELETE_WORKTYPE_SET_BY_CODE).setParameter("companyId", companyId)
		.setParameter("employmentCode", employmentCode)
		.executeUpdate();
		this.getEntityManager().flush();
	}
	private void updateWorkType(List<AppEmploymentSetting> employmentList){
		for (AppEmploymentSetting appEmploymentSetting : employmentList) {
			//Delete all workType
			deleteWorkType(appEmploymentSetting.getCompanyID(), appEmploymentSetting.getEmploymentCode(), 
					appEmploymentSetting.getAppType().value, appEmploymentSetting.getHolidayOrPauseType());
			//Insert new workType
			List<KrqdtAppEmployWorktype> listEntities = appEmploymentSetting.getLstWorkType().stream()
					.map(item -> {return new KrqdtAppEmployWorktype(
							new KrqdtAppEmployWorktypePK(item.getCompanyID(), 
									item.getEmploymentCode(),
									item.getAppType().value,
									item.getHolidayOrPauseType(),
									item.getWorkTypeCode()),
							null);})
					.collect(Collectors.toList());
			this.commandProxy().insertAll(listEntities);			
		}		
	}
	private void deleteWorkType(String companyId, String employmentCode, int appType, int holidayOrPauseType){
		this.getEntityManager().createQuery(DELETE_WORKTYPE_SET).setParameter("companyId", companyId)
		.setParameter("employmentCode", employmentCode)
		.setParameter("appType", appType)
		.setParameter("holidayOrPauseType", holidayOrPauseType)
		.executeUpdate();
		this.getEntityManager().flush();
	}
	/**
	 * Convert employment setting domain to entity object
	 * @param domain
	 * @return
	 */
	private static KrqstAppEmploymentSet toEntity(AppEmploymentSetting domain){
		Integer holidayTypeUseFlg = null;
		if (domain.getHolidayTypeUseFlg() != null) {
			holidayTypeUseFlg = domain.getHolidayTypeUseFlg().booleanValue() ? 1: 0;
		}
		return new KrqstAppEmploymentSet(domain.getVersion(),
				new KrqstAppEmploymentSetPK(domain.getCompanyID(), 
						domain.getEmploymentCode(), 
						domain.getAppType().value, 
						domain.getHolidayOrPauseType()), 
				holidayTypeUseFlg,
				domain.isDisplayFlag() == true ? 1 : 0, 
				domain.getLstWorkType()
				.stream()
				.map(item -> {return new KrqdtAppEmployWorktype(
						new KrqdtAppEmployWorktypePK(item.getCompanyID(), 
								item.getEmploymentCode(),
								item.getAppType().value,
								item.getHolidayOrPauseType(),
								item.getWorkTypeCode()),
						null);})
				.collect(Collectors.toList()));
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
		//if(Optional.ofNullable(entity.getHolidayTypeUseFlg()) != null || entity.getHolidayTypeUseFlg() == 0) {
		if(entity.getHolidayTypeUseFlg() == null || entity.getHolidayTypeUseFlg() == 0) {
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
