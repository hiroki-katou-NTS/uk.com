package nts.uk.ctx.at.request.infra.repository.setting.employment.appemploymentsetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.eclipse.persistence.internal.xr.CollectionResult;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.holidayshipment.BreakOutType;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
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
	
//	public List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode, int appType){
//		return this.queryProxy().query(FIND_EMPLOYMENT_SET, KrqstAppEmploymentSet.class)
//				.setParameter("companyId", companyId)
//				.setParameter("employmentCode", employmentCode)
//				.setParameter("appType", appType)
//				.getList(c -> toDomain(c));
//				
//	}
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode, int appType){
		List<KrqstAppEmploymentSet> list = this.queryProxy().query(FIND_EMPLOYMENT_SET, KrqstAppEmploymentSet.class)
				.setParameter("companyId", companyId)
				.setParameter("employmentCode", employmentCode)
				.setParameter("appType", appType)
				.getList();
		if(!list.isEmpty()) {			
			List<AppEmploymentSetting> listReturn = toDomain(list, companyId);
			return listReturn;
		}
		return null;
				
	}
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId){
		
		List<KrqstAppEmploymentSet> list = this.queryProxy().query(FIND_EMPLOYMENT_BY_COMPANYID, KrqstAppEmploymentSet.class)
				.setParameter("companyId", companyId)
				.getList();
		List<AppEmploymentSetting> listReturn = toDomain(list, companyId);
		return listReturn;
				
	}
//	public List<AppEmploymentSetting_New> getEmploymentSettingRef(String companyId){
//		List<KrqstAppEmploymentSet> list = this.queryProxy().query(FIND_EMPLOYMENT_BY_COMPANYID, KrqstAppEmploymentSet.class)
//				.setParameter("companyId", companyId)
//				.getList();
//		List<AppEmploymentSetting_New> listReturn = toDomain(list, companyId);
//		return listReturn;
//	}
//	public List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode){
//		return this.queryProxy().query(FIND_EMPLOYMENT_BY_EMPLOYMENTCD, KrqstAppEmploymentSet.class)
//				.setParameter("companyId", companyId)
//				.setParameter("employmentCode", employmentCode)
//				.getList(c -> toDomain(c));
//				
//	}
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode){
		List<KrqstAppEmploymentSet> list = this.queryProxy().query(FIND_EMPLOYMENT_BY_EMPLOYMENTCD, KrqstAppEmploymentSet.class)
				.setParameter("companyId", companyId)
				.setParameter("employmentCode", employmentCode)
				.getList();
		if(!list.isEmpty()) {			
			List<AppEmploymentSetting> listReturn = toDomain(list, companyId);
			return listReturn;
		}
		 return null;
				
	}
	/**
	 * Insert list employment setting
	 */
//	public void insert(List<AppEmploymentSetting> employmentList){
//		commandProxy().insertAll(employmentList.stream()
//				.map(item -> toEntity(item))
//				.collect(Collectors.toList()));
//	}
	public void insert(AppEmploymentSetting domain){
		List<KrqstAppEmploymentSet> updateLst;
		if(!CollectionUtil.isEmpty(domain.getListWTOAH())) {
			updateLst = domain.getListWTOAH().stream().map( x ->{
				
				return new KrqstAppEmploymentSet(domain.getVersion(), new KrqstAppEmploymentSetPK(domain.getCompanyID(),
						domain.getEmploymentCode(), 
						x.getAppType().value,
						x.getAppType().value == 1 ? x.getHolidayAppType().get().value : x.getAppType().value == 10 ? x.getSwingOutAtr().get().value : 9),
						x.getHolidayTypeUseFlg().get() ? 1 : 0, x.getWorkTypeSetDisplayFlg() ? 1 : 0,
						CollectionUtil.isEmpty(x.getWorkTypeList()) ? null : x.getWorkTypeList().stream().map(y -> new KrqdtAppEmployWorktype(new KrqdtAppEmployWorktypePK(domain.getCompanyID(), domain.getEmploymentCode(),
								 x.getAppType().value, x.getHolidayAppType().isPresent() ? x.getHolidayAppType().get().value : x.getSwingOutAtr().isPresent() ? x.getSwingOutAtr().get().value : 9, y), null)).collect(Collectors.toList())  		
						);
			}).collect(Collectors.toList());
			if(!CollectionUtil.isEmpty(updateLst)) {
				commandProxy().insertAll(updateLst);
			}
		}
			
	}
	
//	public void update(List<AppEmploymentSetting> employmentList){
//		//Update workType code
//		updateWorkType(employmentList);
//		//Update employment setting
//		List<KrqstAppEmploymentSet> updateLst = employmentList.stream()
//				.map(item -> {
//					Optional<KrqstAppEmploymentSet> updateItemOp = queryProxy().find(new KrqstAppEmploymentSetPK(
//																			item.getCompanyID(), 
//																			item.getEmploymentCode(), 
//																			item.getAppType().value, 
//																			item.getHolidayOrPauseType()), KrqstAppEmploymentSet.class);
//					if (updateItemOp.isPresent()) {
//						KrqstAppEmploymentSet updateItem = updateItemOp.get();
//						Integer holidayTypeUseFlg = null;
//						if (item.getHolidayTypeUseFlg() != null) {
//							holidayTypeUseFlg = item.getHolidayTypeUseFlg().booleanValue() ? 1: 0;
//						}
//						//updateItem.setVersion(item.getVersion());
//						updateItem.setHolidayTypeUseFlg(item.getHolidayOrPauseType());
//						updateItem.setDisplayFlag(item.isDisplayFlag() == true ? 1 : 0);
//						updateItem.setHolidayTypeUseFlg(holidayTypeUseFlg);						
//						return updateItem;
//					}
//					return toEntity(item);			
//				})
//				.collect(Collectors.toList());
//		commandProxy().updateAll(updateLst);
//	}
	public void update(AppEmploymentSetting domain) {
		updateWorkType(domain);
		List<KrqstAppEmploymentSet> updateLst;
		if(!CollectionUtil.isEmpty(domain.getListWTOAH())) {
			updateLst = domain.getListWTOAH().stream().map( x ->{
				Optional<KrqstAppEmploymentSet> updateItemOp = queryProxy().find(new KrqstAppEmploymentSetPK(
						domain.getCompanyID(), 
						domain.getEmploymentCode(), 
						x.getAppType().value, 
						x.getAppType().value == 1 ? x.getHolidayAppType().get().value : x.getAppType().value == 10 ? x.getSwingOutAtr().get().value : 9 
						), KrqstAppEmploymentSet.class);
				if(updateItemOp.isPresent()) {
					KrqstAppEmploymentSet updateItem = updateItemOp.get();
					Integer holidayTypeUseFlg = null;
					if (x.getHolidayTypeUseFlg() != null) {
						holidayTypeUseFlg = x.getHolidayTypeUseFlg().get() ? 1: 0;
					}
					//updateItem.setVersion(item.getVersion());
					updateItem.setHolidayTypeUseFlg(x.getHolidayTypeUseFlg().get() ? 1 : 0);
					updateItem.setDisplayFlag(x.getWorkTypeSetDisplayFlg() ? 1 : 0);
					updateItem.setHolidayTypeUseFlg(holidayTypeUseFlg);						
					return updateItem;
				}
				return new KrqstAppEmploymentSet(domain.getVersion(), new KrqstAppEmploymentSetPK(domain.getCompanyID(),
						domain.getEmploymentCode(), 
						x.getAppType().value,
						x.getAppType().value == 1 ? x.getHolidayAppType().get().value : x.getAppType().value == 10 ? x.getSwingOutAtr().get().value : 9),
						x.getHolidayTypeUseFlg().get() ? 1 : 0, x.getWorkTypeSetDisplayFlg() ? 1 : 0,new ArrayList<>());
			}).collect(Collectors.toList());
			if(!CollectionUtil.isEmpty(updateLst)) {
				commandProxy().updateAll(updateLst);				
			} 
		}
	}
	public void updateWorkType(AppEmploymentSetting domain) {
		List<KrqdtAppEmployWorktype> list = new ArrayList<>();
		if (!CollectionUtil.isEmpty(domain.getListWTOAH())) {
			domain.getListWTOAH().stream().map(x -> {
				
				if(!CollectionUtil.isEmpty(x.getWorkTypeList())){
					deleteWorkType(domain.getCompanyID(), domain.getEmploymentCode(), 
							x.getAppType().value, x.getSwingOutAtr().isPresent() ? x.getSwingOutAtr().get().value : x.getHolidayAppType().isPresent() ? x.getHolidayAppType().get().value : 9);
					return x.getWorkTypeList().stream().map( y-> new KrqdtAppEmployWorktype(new KrqdtAppEmployWorktypePK(
							domain.getCompanyID(),
							domain.getEmploymentCode(),
							x.getAppType().value,
							x.getAppType().value == 1 ? x.getHolidayAppType().get().value : x.getAppType().value == 10 ? x.getSwingOutAtr().get().value : 9 ,
							y), null)).collect(Collectors.toList());
				}
				return null;
			}).filter(x -> x != null).forEach(a -> list.addAll(a));
			
		}
		this.commandProxy().insertAll(list);
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
//	private void updateWorkType(List<AppEmploymentSetting> employmentList){
//		for (AppEmploymentSetting appEmploymentSetting : employmentList) {
//			//Delete all workType
//			deleteWorkType(appEmploymentSetting.getCompanyID(), appEmploymentSetting.getEmploymentCode(), 
//					appEmploymentSetting.getAppType().value, appEmploymentSetting.getHolidayOrPauseType());
//			//Insert new workType
//			List<KrqdtAppEmployWorktype> listEntities = appEmploymentSetting.getLstWorkType().stream()
//					.map(item -> {return new KrqdtAppEmployWorktype(
//							new KrqdtAppEmployWorktypePK(item.getCompanyID(), 
//									item.getEmploymentCode(),
//									item.getAppType().value,
//									item.getHolidayOrPauseType(),
//									item.getWorkTypeCode()),
//							null);})
//					.collect(Collectors.toList());
//			this.commandProxy().insertAll(listEntities);			
//		}		
//	}
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
//	private static KrqstAppEmploymentSet toEntity(AppEmploymentSetting domain){
//		Integer holidayTypeUseFlg = null;
//		if (domain.getHolidayTypeUseFlg() != null) {
//			holidayTypeUseFlg = domain.getHolidayTypeUseFlg().booleanValue() ? 1: 0;
//		}
//		return new KrqstAppEmploymentSet(domain.getVersion(),
//				new KrqstAppEmploymentSetPK(domain.getCompanyID(), 
//						domain.getEmploymentCode(), 
//						domain.getAppType().value, 
//						domain.getHolidayOrPauseType()), 
//				holidayTypeUseFlg,
//				domain.isDisplayFlag() == true ? 1 : 0, 
//				domain.getLstWorkType()
//				.stream()
//				.map(item -> {return new KrqdtAppEmployWorktype(
//						new KrqdtAppEmployWorktypePK(item.getCompanyID(), 
//								item.getEmploymentCode(),
//								item.getAppType().value,
//								item.getHolidayOrPauseType(),
//								item.getWorkTypeCode()),
//						null);})
//				.collect(Collectors.toList()));
//	}
//	
//	private static KrqstAppEmploymentSet toEntityNew(AppEmploymentSetting domain){
//		return null;
//	}
	
	private AppEmployWorkType convertToDomain(KrqdtAppEmployWorktype entity){
		return AppEmployWorkType.createSimpleFromJavaType(entity.getKrqdtAppEmployWorktypePK().getCid(),
				entity.getKrqdtAppEmployWorktypePK().getEmploymentCode(),
				entity.getKrqdtAppEmployWorktypePK().getAppType(),
				entity.getKrqdtAppEmployWorktypePK().getHolidayOrPauseType(),
				entity.getKrqdtAppEmployWorktypePK().getWorkTypeCode());
	}
	
	// remove
//	private AppEmploymentSetting toDomain(KrqstAppEmploymentSet entity) {
//		
//	
//		boolean holidayTypeUseFlg = false;
//		//if(Optional.ofNullable(entity.getHolidayTypeUseFlg()) != null || entity.getHolidayTypeUseFlg() == 0) {
//		if(entity.getHolidayTypeUseFlg() == null || entity.getHolidayTypeUseFlg() == 0) {
//			holidayTypeUseFlg = false;
//		}else {
//			holidayTypeUseFlg = true;
//		}
//		List<AppEmployWorkType> lstAppEmployWorkType = entity.krqdtAppEmployWorktype.stream()
//				.map(x -> AppEmployWorkType.createSimpleFromJavaType(x.getKrqdtAppEmployWorktypePK().getCid(),
//						x.getKrqdtAppEmployWorktypePK().getEmploymentCode(),
//						x.getKrqdtAppEmployWorktypePK().getAppType(),
//						x.getKrqdtAppEmployWorktypePK().getHolidayOrPauseType(),
//						x.getKrqdtAppEmployWorktypePK().getWorkTypeCode())
//				).collect(Collectors.toList());
//		
//		
//		return new AppEmploymentSetting(entity.getKrqstAppEmploymentSetPK().getCid(),
//				entity.getKrqstAppEmploymentSetPK().getEmploymentCode(),
//				EnumAdaptor.valueOf(entity.getKrqstAppEmploymentSetPK().getAppType(), ApplicationType.class),
//				entity.getKrqstAppEmploymentSetPK().getHolidayOrPauseType(),
//				holidayTypeUseFlg,
//				entity.getDisplayFlag() == 0 ? false: true,
//				lstAppEmployWorkType);
//	}
	private List<AppEmploymentSetting> toDomain(List<KrqstAppEmploymentSet> list, String companyId){
		Map<String, Map<String,List<WorkTypeObjAppHoliday>>> maps = new HashMap();
		list.stream().forEach(x -> {
			String cid = x.getKrqstAppEmploymentSetPK().getCid();
			String empCode = x.getKrqstAppEmploymentSetPK().getEmploymentCode();
			WorkTypeObjAppHoliday i = new WorkTypeObjAppHoliday();
			i.setAppType(EnumAdaptor.valueOf(x.getKrqstAppEmploymentSetPK().getAppType(), ApplicationType.class));
			if(x.getKrqstAppEmploymentSetPK().getAppType() == 1) {
				i.setHolidayAppType(Optional.of(EnumAdaptor.valueOf(x.getKrqstAppEmploymentSetPK().getHolidayOrPauseType(), HdAppType.class)));				
			}else {
				i.setHolidayAppType(Optional.ofNullable(null));
			}
			i.setHolidayTypeUseFlg(Optional.of((x.getHolidayTypeUseFlg() == null || x.getHolidayTypeUseFlg() == 0) ? false : true));
			if(x.getKrqstAppEmploymentSetPK().getAppType() == 10) {
				i.setSwingOutAtr(Optional.of(EnumAdaptor.valueOf(x.getKrqstAppEmploymentSetPK().getHolidayOrPauseType(), BreakOutType.class)));			
			}else {
				i.setSwingOutAtr(Optional.ofNullable(null));
			}
			i.setWorkTypeSetDisplayFlg(x.getDisplayFlag() == 1);
			if(!x.getKrqdtAppEmployWorktype().isEmpty()) {	
				i.setWorkTypeList(x.getKrqdtAppEmployWorktype().stream().map(y->y.krqdtAppEmployWorktypePK.getWorkTypeCode()).collect(Collectors.toList()));
				
			}
			 
			if(maps.containsKey(cid)) {
				if(maps.get(cid).containsKey(empCode)){
					maps.get(cid).get(empCode).add(i);
				}else {
					List<WorkTypeObjAppHoliday> list1 = new ArrayList<>();
					list1.add(i);
					maps.get(cid).put(empCode, list1);
				}
			}else {
				List<WorkTypeObjAppHoliday> list1 = new ArrayList<>();
				list1.add(i);
				Map<String,List<WorkTypeObjAppHoliday>> map = new HashMap();
				map.put(empCode, list1);
				maps.put(cid, map);
			}
		});
		Map<String,List<WorkTypeObjAppHoliday>> map2 = maps.get(companyId);
		List<AppEmploymentSetting> listReturn = new ArrayList<>();
		for (Map.Entry<String, List<WorkTypeObjAppHoliday>> entry : map2.entrySet()) {
		    if(entry != null) {
		    	AppEmploymentSetting item = new AppEmploymentSetting(companyId, entry.getKey(), entry.getValue());
			    listReturn.add(item);
		    }
		}
		return listReturn;
	}
}
