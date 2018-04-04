package nts.uk.ctx.at.request.infra.repository.setting.company.vacationapplicationsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.vacationapplicationsetting.KrqstHdAppSet;
import nts.uk.ctx.at.request.infra.entity.setting.company.vacationapplicationsetting.KrqstHdAppSetPK;
import nts.uk.shr.com.context.AppContexts;
/**
 * the hd app set repository
 * @author yennth
 *
 */
@Stateless
public class JpaHdAppSetRepository extends JpaRepository implements HdAppSetRepository{
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private HdAppSet toDomain(KrqstHdAppSet entity){
		HdAppSet domain = HdAppSet.createFromJavaType(entity.krqstHdAppSetPK.companyId, 
				entity.use60h, entity.obstacleName, 
				entity.regisShortLostHd, entity.hdName, entity.regisLackPubHd, entity.changeWrkHour,
				entity.ckuperLimit, entity.actualDisp, entity.wrkHours, entity.pridigCheck, 
				entity.yearHdName, entity.regisNumYear, entity.furikyuName, entity.regisInsuff, 
				entity.useGener, entity.useYear, entity.timeDigest, entity.absenteeism, 
				entity.concheckOutLegal, entity.specialVaca, entity.concheckDateRelease, 
				entity.appDateContra, entity.yearResig, entity.regisShortReser, entity.hdType, entity.displayUnselect);
		return domain;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqstHdAppSet toEntity(HdAppSet domain){
		val entity = new KrqstHdAppSet();
		entity.krqstHdAppSetPK = new KrqstHdAppSetPK(domain.getCompanyId());
		entity.absenteeism = domain.getAbsenteeism() == null ? null : domain.getAbsenteeism().v();
		entity.actualDisp = domain.getActualDisp().value;
		entity.appDateContra = domain.getAppDateContra().value;
		entity.changeWrkHour = domain.getChangeWrkHour().value;
		entity.ckuperLimit = domain.getCkuperLimit().value;
		entity.concheckDateRelease = domain.getConcheckDateRelease().value;
		entity.concheckOutLegal = domain.getConcheckOutLegal().value;
		entity.furikyuName = domain.getFurikyuName() == null ? null : domain.getFurikyuName().v();
		entity.hdName = domain.getHdName() == null ? null : domain.getHdName().v();
		entity.obstacleName = domain.getObstacleName() == null ? null : domain.getObstacleName().v();
		entity.pridigCheck = domain.getPridigCheck().value;
		entity.regisInsuff = domain.getRegisInsuff().value;
		entity.regisLackPubHd = domain.getRegisLackPubHd().value;
		entity.regisNumYear = domain.getRegisNumYear().value;
		entity.regisShortLostHd = domain.getRegisShortLostHd().value;
		entity.regisShortReser = domain.getRegisShortReser().value;
		entity.specialVaca = domain.getSpecialVaca() == null ? null : domain.getSpecialVaca().v();
		entity.timeDigest = domain.getTimeDigest() == null ? null : domain.getTimeDigest().v();
		entity.use60h = domain.getUse60h().value;
		entity.useGener = domain.getUseGener().value;
		entity.useYear = domain.getUseYear().value;
		entity.wrkHours = domain.getWrkHours().value;
		entity.yearHdName = domain.getYearHdName() == null ? null : domain.getYearHdName().v();
		entity.yearResig = domain.getYearResig() == null ? null : domain.getYearResig().v();
		return entity;
	}
	
	/**
	 * get hd app set by companyId and hd app type
	 * @author yennth
	 */
	@Override
	public Optional<HdAppSet> getAll() {
		val pk = new KrqstHdAppSetPK(AppContexts.user().companyId());
		return this.queryProxy().find(pk, KrqstHdAppSet.class).map(x -> toDomain(x));
	}
	/**
	 * update hd app set
	 * @author yennth
	 */
	@Override
	public void update(HdAppSet hdAppSet) {
		KrqstHdAppSet entity = toEntity(hdAppSet);
		KrqstHdAppSet oldEntity = this.queryProxy().find(entity.krqstHdAppSetPK, KrqstHdAppSet.class).get();
		oldEntity.absenteeism = entity.absenteeism;
		oldEntity.actualDisp = entity.actualDisp;
		oldEntity.appDateContra = entity.appDateContra;
		oldEntity.changeWrkHour = entity.changeWrkHour;
		oldEntity.ckuperLimit = entity.ckuperLimit;
		oldEntity.concheckDateRelease = entity.concheckDateRelease;
		oldEntity.concheckOutLegal = entity.concheckOutLegal;
		oldEntity.furikyuName = entity.furikyuName;
		oldEntity.hdName = entity.hdName;
		oldEntity.obstacleName = entity.obstacleName;
		oldEntity.pridigCheck = entity.pridigCheck;
		oldEntity.regisInsuff = entity.regisInsuff;
		oldEntity.regisLackPubHd = entity.regisLackPubHd;
		oldEntity.regisNumYear = entity.regisNumYear;
		oldEntity.regisShortLostHd = entity.regisShortLostHd;
		oldEntity.regisShortReser = entity.regisShortReser;
		oldEntity.specialVaca = entity.specialVaca;
		oldEntity.timeDigest = entity.timeDigest;
		oldEntity.use60h = entity.use60h;
		oldEntity.useGener = entity.useGener;
		oldEntity.useYear = entity.useYear;
		oldEntity.wrkHours = entity.wrkHours;
		oldEntity.yearHdName = entity.yearHdName;
		oldEntity.yearResig = entity.yearResig;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert hd app set
	 * @author yennth
	 */
	@Override
	public void insert(HdAppSet hdAppSet) {
		KrqstHdAppSet entity = toEntity(hdAppSet);
		this.commandProxy().insert(entity);
	}
}
