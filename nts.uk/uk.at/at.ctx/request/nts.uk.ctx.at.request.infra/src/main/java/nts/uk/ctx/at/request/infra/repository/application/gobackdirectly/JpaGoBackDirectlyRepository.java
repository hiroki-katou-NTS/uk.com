package nts.uk.ctx.at.request.infra.repository.application.gobackdirectly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectly;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectlyPK;

@Stateless
public class JpaGoBackDirectlyRepository extends JpaRepository implements GoBackDirectlyRepository {

	public static final String SELECT_NO_WHERE = "SELECT c FROM KrqdtGoBackDirectly c";

	public static final String SELECT_WITH_APP_ID = SELECT_NO_WHERE 
			+ " WHERE c.krqdtGoBackDirectlyPK.companyID =:companyID"
			+ " AND c.krqdtGoBackDirectlyPK.appID =:appID ";
	public static final String FIND_BY_LIST_APPID = SELECT_NO_WHERE 
			+ " WHERE c.krqdtGoBackDirectlyPK.companyID = :companyID"
			+ " AND c.krqdtGoBackDirectlyPK.appID IN :lstAppID ";

	/**
	 * @param krqdtGoBackDirectly
	 * @return
	 */
	private GoBackDirectly toDomain(KrqdtGoBackDirectly entity) {
		GoBackDirectly goBackDirectly = new GoBackDirectly(entity.krqdtGoBackDirectlyPK.companyID, 
				entity.krqdtGoBackDirectlyPK.appID,
				entity.workTypeCD, 
				entity.siftCD,
				entity.workChangeAtr,
				entity.goWorkAtr1,
				entity.backHomeAtr1,
				entity.workTimeStart1,
				entity.workTimeEnd1, 
				entity.workLocationCd1,
				entity.goWorkAtr2,
				entity.backHomeAtr2,
				entity.workTimeStart2,
				entity.workTimeEnd2, 
				entity.workLocationCd2);
		goBackDirectly.setVersion(entity.version);
		return goBackDirectly;
	}

	/**
	 * @param domain
	 * @return
	 */
	private KrqdtGoBackDirectly toEntity(GoBackDirectly domain){
		val entity = new KrqdtGoBackDirectly();
		entity.krqdtGoBackDirectlyPK = new KrqdtGoBackDirectlyPK();
		entity.krqdtGoBackDirectlyPK.companyID = domain.getCompanyID();
		entity.krqdtGoBackDirectlyPK.appID  = domain.getAppID();
		entity.version = domain.getVersion();
		entity.workTypeCD = domain.getWorkTypeCD().map(x -> x.v()).orElse(null);
		entity.siftCD = domain.getSiftCD().map(x -> x.v()).orElse(null);
		entity.workChangeAtr = domain.getWorkChangeAtr().map(x -> x.value).orElse(null);
		entity.workTimeStart1 = domain.getWorkTimeStart1().map(x -> x.v()).orElse(null);
		entity.workTimeEnd1 = domain.getWorkTimeEnd1().map(x -> x.v()).orElse(null);
		entity.goWorkAtr1 = domain.getGoWorkAtr1().value;
		entity.backHomeAtr1  = domain.getBackHomeAtr1().value;
		entity.workLocationCd1 = domain.getWorkLocationCD1().map(x -> x).orElse(null);
		entity.workTimeStart2 = domain.getWorkTimeStart2().map(x -> x.v()).orElse(null);
		entity.workTimeEnd2 = domain.getWorkTimeEnd2().map(x -> x.v()).orElse(null);
		entity.goWorkAtr2 = domain.getGoWorkAtr2().map(x -> x.value).orElse(null);
		entity.backHomeAtr2  = domain.getBackHomeAtr2().map(x -> x.value).orElse(null);
		entity.workLocationCd2 = domain.getWorkLocationCD2().map(x -> x).orElse(null);
		return entity;
	}

	@Override
	public Optional<GoBackDirectly> findByApplicationID(String companyID, String appID) {
		Optional<GoBackDirectly> item =  this.queryProxy().query(SELECT_WITH_APP_ID, KrqdtGoBackDirectly.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.getSingle(c -> toDomain(c));
		if(!item.isPresent()) {
			return Optional.empty();
		}
		return item;
	}
	/**
	 * 
	 */
	@Override
	public void update(GoBackDirectly goBackDirectly) {
		//get current Entity
		Optional<KrqdtGoBackDirectly> goBack = this.queryProxy().find(new KrqdtGoBackDirectlyPK(goBackDirectly.getCompanyID(),goBackDirectly.getAppID()), KrqdtGoBackDirectly.class);
		if(goBack.isPresent()) {
			KrqdtGoBackDirectly currentEntity = goBack.get();
			currentEntity.setGoWorkAtr1(goBackDirectly.getGoWorkAtr1().value);
			currentEntity.setBackHomeAtr1(goBackDirectly.getBackHomeAtr1().value);
			currentEntity.setWorkTimeStart1(goBackDirectly.getWorkTimeStart1().map(x -> x.v()).orElse(null));
			currentEntity.setWorkTimeEnd1(goBackDirectly.getWorkTimeEnd1().map(x -> x.v()).orElse(null));
			currentEntity.setGoWorkAtr2(goBackDirectly.getGoWorkAtr2().map(x -> x.value).orElse(null));
			currentEntity.setBackHomeAtr2(goBackDirectly.getBackHomeAtr2().map(x -> x.value).orElse(null));
			currentEntity.setWorkChangeAtr(goBackDirectly.getWorkChangeAtr().map(x -> x.value).orElse(null));
			currentEntity.setWorkTimeStart2(goBackDirectly.getWorkTimeStart2().map(x -> x.v()).orElse(null));
			currentEntity.setWorkTimeEnd2(goBackDirectly.getWorkTimeEnd2().map(x -> x.v()).orElse(null));
			currentEntity.setWorkLocationCd1(goBackDirectly.getWorkLocationCD1().map(x -> x).orElse(null));
			currentEntity.setWorkLocationCd2(goBackDirectly.getWorkLocationCD2().map(x -> x).orElse(null));
			if(goBackDirectly.getWorkChangeAtr().map(x -> x).orElse(UseAtr.NOTUSE) == UseAtr.USE) {
				currentEntity.setWorkTypeCD(goBackDirectly.getWorkTypeCD().map(x -> x.v()).orElse(null));
				currentEntity.setSiftCD(goBackDirectly.getSiftCD().map(x -> x.v()).orElse(null));
			}
			this.commandProxy().update(currentEntity);
			
		}
		
	}
	/**
	 * 
	 */
	@Override
	public void insert(GoBackDirectly goBackDirectly) {
		this.commandProxy().insert(toEntity(goBackDirectly));
	}
	/**
	 * 
	 */
	@Override
	public void delete(String companyID, String appID) {
		this.commandProxy().remove(KrqdtGoBackDirectly.class, new KrqdtGoBackDirectlyPK(companyID, appID));
	}
	/**
	 * @author hoatt
	 * get List Application Go Back
	 * @param companyID
	 * @param appID
	 * @return
	 */
	@Override
	public List<GoBackDirectly> getListAppGoBack(String companyID, List<String> lstAppID) {
		if(lstAppID.isEmpty()){
			return new ArrayList<>();
		}
		return this.queryProxy().query(FIND_BY_LIST_APPID, KrqdtGoBackDirectly.class)
				.setParameter("companyID", companyID)
				.setParameter("lstAppID", lstAppID)
                .getList(item -> toDomain(item));
	}

}
