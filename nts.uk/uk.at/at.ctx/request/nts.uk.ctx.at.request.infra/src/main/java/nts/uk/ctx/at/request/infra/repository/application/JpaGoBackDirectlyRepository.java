package nts.uk.ctx.at.request.infra.repository.application;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkTimeGoBack;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectly;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectlyPK;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
public class JpaGoBackDirectlyRepository extends JpaRepository implements GoBackDirectlyRepository {

	public final String SELECT_NO_WHERE = "SELECT c FROM KrqdtGoBackDirectly c";

	public final String SELECT_WITH_APP_ID = SELECT_NO_WHERE 
			+ " WHERE c.krqdtGoBackDirectlyPK.companyID =:companyID"
			+ " AND c.krqdtGoBackDirectlyPK.appID =:appID ";

	/**
	 * @param krqdtGoBackDirectly
	 * @return
	 */
	private GoBackDirectly toDomain(KrqdtGoBackDirectly entity) {
		return new GoBackDirectly(entity.krqdtGoBackDirectlyPK.companyID, 
				entity.krqdtGoBackDirectlyPK.appID,
				new WorkTypeCode(entity.workTypeCD), 
				new SiftCode(entity.siftCD),
				EnumAdaptor.valueOf(entity.workChangeAtr, UseAtr.class),
				EnumAdaptor.valueOf(entity.goWorkAtr1, UseAtr.class),
				EnumAdaptor.valueOf(entity.backHomeAtr1, UseAtr.class),
				new WorkTimeGoBack(entity.workTimeStart1),
				new WorkTimeGoBack(entity.workTimeEnd1), 
				entity.workLocationCd1,
				EnumAdaptor.valueOf(entity.goWorkAtr2, UseAtr.class),
				EnumAdaptor.valueOf(entity.backHomeAtr2, UseAtr.class),
				new WorkTimeGoBack(entity.workTimeStart2),
				new WorkTimeGoBack(entity.workTimeEnd2), 
				entity.workLocationCd2);
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
		entity.workTypeCD = domain.getWorkTypeCD().v();
		entity.siftCD = domain.getSiftCd().v();
		entity.workChangeAtr = domain.getWorkChangeAtr().value;
		entity.workTimeStart1 = domain.getWorkTimeStart1().v();
		entity.workTimeEnd1 = domain.getWorkTimeEnd1().v();
		entity.goWorkAtr1 = domain.getGoWorkAtr1().value;
		entity.backHomeAtr1  = domain.getBackHomeAtr1().value;
		entity.workLocationCd1 = domain.getWorkLocationCD1();
		entity.workTimeStart2 = domain.getWorkTimeStart2().v();
		entity.workTimeEnd2 = domain.getWorkTimeEnd2().v();
		entity.goWorkAtr2 = domain.getGoWorkAtr2().value;
		entity.backHomeAtr2  = domain.getBackHomeAtr2().value;
		entity.workLocationCd2 = domain.getWorkLocationCD2();
		return entity;
	}

	@Override
	public Optional<GoBackDirectly> findByApplicationID(String companyID, String appID) {
		return this.queryProxy().query(SELECT_WITH_APP_ID, KrqdtGoBackDirectly.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public void update(GoBackDirectly goBackDirectly) {
		//get current Entity
		KrqdtGoBackDirectly currentEntity = this.queryProxy()
				.find(new KrqdtGoBackDirectlyPK(goBackDirectly.getCompanyID(),goBackDirectly.getAppID()), KrqdtGoBackDirectly.class)
				.get(); 
		currentEntity.setGoWorkAtr1(goBackDirectly.getGoWorkAtr1().value);
		currentEntity.setBackHomeAtr1(goBackDirectly.getBackHomeAtr1().value);
		currentEntity.setWorkTimeStart1(goBackDirectly.getWorkTimeStart1().v());
		currentEntity.setWorkTimeEnd1(goBackDirectly.getWorkTimeEnd1().v());
		currentEntity.setGoWorkAtr2(goBackDirectly.getGoWorkAtr2().value);
		currentEntity.setBackHomeAtr2(goBackDirectly.getBackHomeAtr2().value);
		currentEntity.setWorkChangeAtr(goBackDirectly.getWorkChangeAtr().value);
		currentEntity.setWorkTimeStart2(goBackDirectly.getWorkTimeStart2().v());
		currentEntity.setWorkTimeEnd2(goBackDirectly.getWorkTimeEnd2().v());
		
		if(goBackDirectly.getWorkChangeAtr() == UseAtr.USE) {
			currentEntity.setWorkTypeCD(goBackDirectly.getWorkTypeCD().v());
			currentEntity.setSiftCD(goBackDirectly.getSiftCd().v());
		}
		this.commandProxy().update(goBackDirectly);
	}

	@Override
	public void insert(GoBackDirectly goBackDirectly) {
		this.commandProxy().insert(goBackDirectly);
	}

	@Override
	public void delete(GoBackDirectly goBackDirectly) {
		this.commandProxy()
			.remove(KrqdtGoBackDirectly.class, new KrqdtGoBackDirectlyPK(goBackDirectly.getCompanyID(),goBackDirectly.getAppID()));
	}

}
