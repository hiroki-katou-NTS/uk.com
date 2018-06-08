package nts.uk.ctx.at.shared.infra.repository.specialholiday.yearservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearservicecom.YearServiceCom;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.YearServiceSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.repository.YearServiceComRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearservicecom.KshstYearServiceCom;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearservicecom.KshstYearServiceComPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceset.KshstYearServiceSet;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceset.KshstYearServiceSetPK;

@Stateless
public class JpaSetItemRepository extends JpaRepository implements YearServiceComRepository{
	private static final String SELECT_NO_WHERE = "SELECT c FROM KshstYearServiceSet c ";
	private static final String SELECT_ITEM = SELECT_NO_WHERE + "WHERE c.kshstYearServiceSetPK.companyId = :companyId ";
	private static final String SELECT_YEAR = SELECT_ITEM + " AND c.year = :year";
	private static final String SELECT_ITEM_SPHC = SELECT_ITEM + " AND c.kshstYearServiceSetPK.specialHolidayCode = :specialHolidayCode";
	private static final String SELECT_NO_WHERE_COM = "SELECT c FROM KshstYearServiceCom c ";
	private static final String SELECT_ITEM_COM = SELECT_NO_WHERE_COM + "WHERE c.kshstYearServiceComPK.companyId = :companyId AND c.kshstYearServiceComPK.specialHolidayCode = :specialHolidayCode";
	/**
	 * change entity to domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static YearServiceSet toDomainSet(KshstYearServiceSet entity){
		YearServiceSet domain  = YearServiceSet.createFromJavaType(entity.kshstYearServiceSetPK.companyId,
																	entity.kshstYearServiceSetPK.specialHolidayCode,
																	entity.kshstYearServiceSetPK.yearServiceNo,
																	entity.year,
																	entity.month, 
																	entity.date);
		return domain;
	}
	/**
	 * change from domain to entity
	 * @param domain
	 * @return
	 */
	private static KshstYearServiceSet toEntitySet(YearServiceSet domain){
		val entity = new KshstYearServiceSet();
		entity.kshstYearServiceSetPK = new KshstYearServiceSetPK(domain.getCompanyId(),
																	domain.getSpecialHolidayCode(),
																	domain.getYearServiceNo());
		entity.year = domain.getYear();
		entity.month = domain.getMonth();
		entity.date = domain.getDate();
		return entity;
	}
	
	/**
	 * change from entity to domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static YearServiceCom toDomainCom(KshstYearServiceCom entity){
		List<KshstYearServiceSet> ls = entity.listYearServiceSet;
		List<YearServiceSet> domls = new ArrayList<>();
		for(KshstYearServiceSet item : ls){
			domls.add(toDomainSet(item));
		}
		YearServiceCom domain = YearServiceCom.createFromJavaType(entity.kshstYearServiceComPK.companyId,
																	entity.kshstYearServiceComPK.specialHolidayCode,
																	entity.lengthServiceYearAtr,
																	domls
																	);
		return domain;
	}
	/**
	 * change from domain to entity
	 * @param domain
	 * @return
	 */
	private static KshstYearServiceCom toEntityCom(YearServiceCom domain){
		val entity = new KshstYearServiceCom();
		entity.kshstYearServiceComPK = new KshstYearServiceComPK(domain.getCompanyId(), domain.getSpecialHolidayCode());
		entity.lengthServiceYearAtr = domain.getLengthServiceYearAtr();
		
		if (domain.getYearServiceSets() != null) {
			entity.listYearServiceSet =  domain.getYearServiceSets().stream()
					.map(x -> toEntitySet(x))
					.collect(Collectors.toList());
		}
		
		return entity;
	}

	/**
	 * update a item to year service set
	 * author: Hoang Yen
	 */
//	@Override
//	public void update(List<YearServiceSet> yearServiceSet) {
//		KshstYearServiceSet entity = toEntity(yearServiceSet);
//		KshstYearServiceSet oldEntity = this.queryProxy().find(entity.kshstYearServiceSetPK, KshstYearServiceSet.class).get();
//		oldEntity.setYear(entity.year);
//		oldEntity.setMonth(entity.month);
//		oldEntity.setDate(entity.date);
//		this.commandProxy().update(oldEntity);
//	}
	/** 
	 * get all data
	 * author: Hoang Yen
	 */
	@Override
	public List<YearServiceSet> findAllSet(String companyId, int specialHolidayCode) {
		return this.queryProxy().query(SELECT_ITEM_SPHC, KshstYearServiceSet.class).setParameter("companyId", companyId)
																					.setParameter("specialHolidayCode", specialHolidayCode)
																					.getList(c->toDomainSet(c));
	}
	@Override
	public void updateSet(List<YearServiceSet> yearServiceSetLs) {
		for(YearServiceSet item : yearServiceSetLs){
			KshstYearServiceSet entity = toEntitySet(item);
			KshstYearServiceSet oldEntity = this.queryProxy().find(entity.kshstYearServiceSetPK, KshstYearServiceSet.class).get();
			oldEntity.year = entity.year;
			oldEntity.month = entity.month;
			oldEntity.date = entity.date;
			this.commandProxy().update(oldEntity);
		}
	}
	@Override
	public void insertSet(List<YearServiceSet> yearServiceSetLs) {
		for(YearServiceSet item : yearServiceSetLs){
			KshstYearServiceSet entity = toEntitySet(item);
			this.commandProxy().insert(entity);
		}
	}
	@Override
	public Optional<YearServiceSet> findSet(String companyId, int specialHolidayCode, int yearServiceType) {
		return this.queryProxy().find(new KshstYearServiceSetPK(companyId, specialHolidayCode, yearServiceType), KshstYearServiceSet.class).map(c->toDomainSet(c));
	}
	@Override
	public List<YearServiceSet> findYearSet(String companyId, int year) {
		return this.queryProxy().query(SELECT_YEAR, KshstYearServiceSet.class)
				.setParameter("companyId", companyId)
				.setParameter("year", year)
				.getList(c->toDomainSet(c));
	}
	@Override
	public Optional<YearServiceCom> findAllCom(String companyId, int specialHolidayCode) {
		KshstYearServiceComPK key = new KshstYearServiceComPK(companyId, specialHolidayCode);
		return this.queryProxy().find(key, KshstYearServiceCom.class).map(c -> toDomainCom(c));
	}
	@Override
	public void updateCom(YearServiceCom yearServiceCom) {
		KshstYearServiceCom entity = toEntityCom(yearServiceCom);
		KshstYearServiceCom oldEntity = this.queryProxy().find(entity.kshstYearServiceComPK, KshstYearServiceCom.class).get();
		oldEntity.lengthServiceYearAtr = entity.lengthServiceYearAtr;
		if (yearServiceCom.getYearServiceSets() != null) {
			oldEntity.listYearServiceSet =  yearServiceCom.getYearServiceSets().stream()
					.map(x -> toEntitySet(x))
					.collect(Collectors.toList());
		}
		this.commandProxy().update(oldEntity);
	}
	@Override
	public void insertCom(YearServiceCom yearServiceCom) {
		this.commandProxy().insert(toEntityCom(yearServiceCom));
	}
	@Override
	public Optional<YearServiceCom> findCom(String companyId, int specialHolidayCode) {
		return this.queryProxy().find(new KshstYearServiceComPK(companyId, specialHolidayCode), KshstYearServiceCom.class).map(c->toDomainCom(c));
	}
	
}
