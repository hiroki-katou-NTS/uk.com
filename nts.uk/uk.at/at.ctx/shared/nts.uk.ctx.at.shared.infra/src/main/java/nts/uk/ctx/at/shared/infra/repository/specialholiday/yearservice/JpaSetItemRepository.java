package nts.uk.ctx.at.shared.infra.repository.specialholiday.yearservice;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.YearServiceSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.repository.YearServiceSetRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceset.KshstYearServiceSet;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceset.KshstYearServiceSetPK;

@Stateless
public class JpaSetItemRepository extends JpaRepository implements YearServiceSetRepository{
	private final String SELECT_NO_WHERE = "SELECT c FROM KshstYearServiceSet c ";
	private final String SELECT_ITEM = SELECT_NO_WHERE + "WHERE c.kshstYearServiceSetPK.companyId = :companyId";
	/**
	 * change entity to domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static YearServiceSet toDomain(KshstYearServiceSet entity){
		YearServiceSet domain  = YearServiceSet.createFromJavaType(entity.kshstYearServiceSetPK.companyId,
																	entity.kshstYearServiceSetPK.specialHolidayCode,
																	entity.kshstYearServiceSetPK.yearServiceType,
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
	private static KshstYearServiceSet toEntity(YearServiceSet domain){
		val entity = new KshstYearServiceSet();
		entity.kshstYearServiceSetPK = new KshstYearServiceSetPK(domain.getCompanyId(),
																	domain.getSpecialHolidayCode(),
																	domain.getYearServiceType());
		entity.year = domain.getYear();
		entity.month = domain.getMonth();
		entity.date = domain.getDate();
		return entity;
	}
	/** 
	 * get all data
	 * author: Hoang Yen
	 */
	@Override
	public List<YearServiceSet> findAll(String companyId) {
		return this.queryProxy().query(SELECT_ITEM, KshstYearServiceSet.class).setParameter("companyId", companyId).getList(c->toDomain(c));
	}
	/**
	 * update a item to year service set
	 * author: Hoang Yen
	 */
	@Override
	public void update(YearServiceSet yearServiceSet) {
		KshstYearServiceSet entity = toEntity(yearServiceSet);
		KshstYearServiceSet oldEntity = this.queryProxy().find(entity.kshstYearServiceSetPK, KshstYearServiceSet.class).get();
		oldEntity.setYear(entity.year);
		oldEntity.setMonth(entity.month);
		oldEntity.setDate(entity.date);
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert a item to year service set
	 * author: Hoang Yen
	 */
	@Override
	public void insert(YearServiceSet yearServiceSet) {
		this.commandProxy().insert(toEntity(yearServiceSet));
	}
	/**
	 * find a item by key
	 * author: Hoang Yen
	 */
	@Override
	public Optional<YearServiceSet> find(String companyId, int specialHolidayCode, int yearServiceType) {
		return this.queryProxy().find(new KshstYearServiceSetPK(companyId, specialHolidayCode, yearServiceType), KshstYearServiceSet.class).map(c->toDomain(c));
	}
}
