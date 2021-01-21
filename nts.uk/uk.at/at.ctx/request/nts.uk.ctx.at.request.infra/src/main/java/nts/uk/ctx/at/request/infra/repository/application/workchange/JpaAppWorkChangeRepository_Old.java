package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.logging.log4j.util.Strings;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChange_Old;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChangePk_Old;

@Stateless
public class JpaAppWorkChangeRepository_Old extends JpaRepository implements IAppWorkChangeRepository
{

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrqdtAppWorkChange_Old f";
    
    private static final String SELECT_BY_KEY_STRING =SELECT_ALL_QUERY_STRING + " WHERE f.appWorkChangePk.cid =:companyID AND f.appWorkChangePk.appId =:appId ";
    
    private static final String FIND_BY_LIST_APPID = "SELECT a FROM KrqdtAppWorkChange_Old a"
    		+ " WHERE a.appWorkChangePk.cid = :companyID"
    		+ " AND a.appWorkChangePk.appId IN :lstAppId";

    @Override
    public List<AppWorkChange_Old> getAllAppWorkChange(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrqdtAppWorkChange_Old.class)
                .getList(item -> toDomain(item));
    }
    
    @Override
    public Optional<AppWorkChange_Old> getAppworkChangeById(String cid, String appId){
    	return this.queryProxy().query(SELECT_BY_KEY_STRING, KrqdtAppWorkChange_Old.class)
				.setParameter("companyID", cid)
				.setParameter("appId", appId)
				.getSingle(c -> toDomain(c));
    }
    
    @Override
    public void add(AppWorkChange_Old domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(AppWorkChange_Old domain){
    	KrqdtAppWorkChange_Old newAppWC = toEntity(domain);
    	KrqdtAppWorkChange_Old updateWorkChange = this.queryProxy().find(newAppWC.appWorkChangePk, KrqdtAppWorkChange_Old.class).get();
    	if (null == updateWorkChange) {
			return;
		}
    	//updateWorkChange.version = newAppWC.version;
		updateWorkChange.workTypeCd = newAppWC.workTypeCd;
		updateWorkChange.workTimeCd = newAppWC.workTimeCd;
		updateWorkChange.excludeHolidayAtr = newAppWC.excludeHolidayAtr;
		updateWorkChange.workChangeAtr = newAppWC.workChangeAtr;
		updateWorkChange.goWorkAtr1 = newAppWC.goWorkAtr1;
		updateWorkChange.backHomeAtr1 = newAppWC.backHomeAtr1;
		updateWorkChange.goWorkAtr2 = newAppWC.goWorkAtr2;
		updateWorkChange.backHomeAtr2 = newAppWC.backHomeAtr2;
		updateWorkChange.workTimeStart1 = newAppWC.workTimeStart1;
		updateWorkChange.workTimeEnd1 = newAppWC.workTimeEnd1;
		updateWorkChange.workTimeStart2 = newAppWC.workTimeStart2;
		updateWorkChange.workTimeEnd2 = newAppWC.workTimeEnd2;
		updateWorkChange.breakTimeStart1 = newAppWC.breakTimeStart1;
		updateWorkChange.breakTimeEnd1 = newAppWC.breakTimeEnd1;
		
		this.commandProxy().update(updateWorkChange);
    }
    
    @Override
    public void delete(String cid, String appId){
        this.commandProxy().remove(KrqdtAppWorkChange_Old.class, new KrqdtAppWorkChangePk_Old(cid, appId));
    }
    
	private static AppWorkChange_Old toDomain(KrqdtAppWorkChange_Old entity) {
		AppWorkChange_Old appWorkChange = AppWorkChange_Old.createFromJavaType(entity.appWorkChangePk.cid,
				entity.appWorkChangePk.appId, entity.workTypeCd, entity.workTimeCd, entity.excludeHolidayAtr,
				entity.workChangeAtr, entity.goWorkAtr1, entity.backHomeAtr1, entity.breakTimeStart1,
				entity.breakTimeEnd1, entity.workTimeStart1, entity.workTimeEnd1, entity.workTimeStart2,
				entity.workTimeEnd2, entity.goWorkAtr2, entity.backHomeAtr2);
		//appWorkChange.setVersion(entity.version);

		return appWorkChange;
	}

	private KrqdtAppWorkChange_Old toEntity(AppWorkChange_Old domain) {
		return new KrqdtAppWorkChange_Old(domain.getVersion(), new KrqdtAppWorkChangePk_Old(domain.getCid(), domain.getAppId()),
				Strings.isBlank(domain.getWorkTypeCd()) ? null : domain.getWorkTypeCd(), 
				Strings.isBlank(domain.getWorkTimeCd()) ? null : domain.getWorkTimeCd(), 
				domain.getExcludeHolidayAtr(),
				domain.getWorkChangeAtr(), domain.getGoWorkAtr1(), domain.getBackHomeAtr1(),
				domain.getBreakTimeStart1(), domain.getBreakTimeEnd1(), domain.getWorkTimeStart1(),
				domain.getWorkTimeEnd1(), domain.getWorkTimeStart2(), domain.getWorkTimeEnd2(), domain.getGoWorkAtr2(),
				domain.getBackHomeAtr2());
	}
	/**
     * @author hoatt
     * get list application work change by list appID
     * @param companyID
     * @param lstAppId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<AppWorkChange_Old> getListAppWorkChangeByID(String companyID, List<String> lstAppId) {
		if(lstAppId.isEmpty()){
			return new ArrayList<>();
		}
		List<AppWorkChange_Old> resultList = new ArrayList<>();
		CollectionUtil.split(lstAppId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND_BY_LIST_APPID, KrqdtAppWorkChange_Old.class)
								  .setParameter("companyID", companyID)
								  .setParameter("lstAppId", subList)
								  .getList(item -> toDomain(item)));
		});
		return resultList;
	}

}
