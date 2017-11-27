package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChange;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChangePk;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
@Transactional
public class JpaAppWorkChangeRepository extends JpaRepository implements IAppWorkChangeRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrqdtAppWorkChange f";
    
    private static final String SELECT_BY_KEY_STRING = String.join(SELECT_ALL_QUERY_STRING, " WHERE f.appWorkChangePk.cid =:companyID AND f.appWorkChangePk.appId =:appId ");

    @Override
    public List<AppWorkChange> getAllAppWorkChange(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrqdtAppWorkChange.class)
                .getList(item -> toDomain(item));
    }
    
    @Override
    public Optional<AppWorkChange> getAppworkChangeById(String cid, String appId){
    	return this.queryProxy().query(SELECT_BY_KEY_STRING, KrqdtAppWorkChange.class)
				.setParameter("companyID", cid)
				.setParameter("appId", appId)
				.getSingle(c -> toDomain(c));
    }
    
    @Override
    public void add(AppWorkChange domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(AppWorkChange domain){
        this.commandProxy().update(toEntity(domain));
    }
    @Override
    public void remove(AppWorkChange domain){
        this.commandProxy().remove(toEntity(domain));
    }
    
    @Override
    public void remove(String cid, String appId){
        this.commandProxy().remove(KrqdtAppWorkChangePk.class, new KrqdtAppWorkChangePk(cid, appId));
    }
    
	private static AppWorkChange toDomain(KrqdtAppWorkChange entity) {
		return AppWorkChange.createFromJavaType(entity.cid, entity.appId, entity.workTypeCd, entity.workTimeCd,
				entity.excludeHolidayAtr, entity.workChangeAtr, entity.goWorkAtr1, entity.backHomeAtr1,
				entity.breakTimeStart1, entity.breakTimeEnd1, entity.workTimeStart1, entity.workTimeEnd1,
				entity.workTimeStart2, entity.workTimeEnd2, entity.goWorkAtr2, entity.backHomeAtr2);
	}

	private KrqdtAppWorkChange toEntity(AppWorkChange domain) {
		return new KrqdtAppWorkChange(new KrqdtAppWorkChangePk(), domain.getCid(), domain.getAppId(),
				domain.getWorkTypeCd(), domain.getWorkTimeCd(), domain.getExcludeHolidayAtr(),
				domain.getWorkChangeAtr(), domain.getGoWorkAtr1(), domain.getBackHomeAtr1(),
				domain.getBreakTimeStart1(), domain.getBreakTimeEnd1(), domain.getWorkTimeStart1(),
				domain.getWorkTimeEnd1(), domain.getWorkTimeStart2(), domain.getWorkTimeEnd2(), domain.getGoWorkAtr2(),
				domain.getBackHomeAtr2());
	}

}
