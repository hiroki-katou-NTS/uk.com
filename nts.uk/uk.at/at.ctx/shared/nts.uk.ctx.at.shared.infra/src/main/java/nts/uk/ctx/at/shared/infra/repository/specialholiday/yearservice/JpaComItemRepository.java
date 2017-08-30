package nts.uk.ctx.at.shared.infra.repository.specialholiday.yearservice;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.YearServiceCom;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.repository.YearServiceComRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearservicecom.KshstYearServiceCom;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearservicecom.KshstYearServiceComPK;

@Stateless
public class JpaComItemRepository extends JpaRepository implements YearServiceComRepository{
	private final String SELECT_NO_WHERE = "SELECT c FROM KshstYearServiceCom c ";
	private final String SELECT_ITEM = SELECT_NO_WHERE + "WHERE c.kshstYearServiceComPk.companyId = :companyId";
	/**
	 * change from entity to domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static YearServiceCom toDomain(KshstYearServiceCom entity){
		YearServiceCom domain = YearServiceCom.createFromJavaType(entity.kshstYearServiceComPK.companyId,
																	entity.kshstYearServiceComPK.specialHolidayCode,
																	entity.lengthServiceYearAtr);
		return domain;
	}
	/**
	 * change from domain to entity
	 * @param domain
	 * @return
	 */
	private static KshstYearServiceCom toEntity(YearServiceCom domain){
		val entity = new KshstYearServiceCom();
		entity.kshstYearServiceComPK = new KshstYearServiceComPK(domain.getCompanyId(), domain.getSpecialHolidayCode());
		entity.lengthServiceYearAtr = domain.getLengthServiceYearAtr();
		return entity;
	}
	/**
	 * get all data
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	@Override
	public List<YearServiceCom> findAll(String companyId) {
		return this.queryProxy().query(SELECT_ITEM, KshstYearServiceCom.class).setParameter("companyId", companyId).getList(c->toDomain(c));
	}
	/**
	 * update length Service Year Atr
	 * @param yearServiceCom
	 * author: Hoang Yen
	 */
	@Override
	public void update(YearServiceCom yearServiceCom) {
		KshstYearServiceCom entity = toEntity(yearServiceCom);
		KshstYearServiceCom oldEntity = this.queryProxy().find(entity.kshstYearServiceComPK, KshstYearServiceCom.class).get();
		oldEntity.setLengthServiceYearAtr(entity.lengthServiceYearAtr);
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert 1 item to year service com
	 * @param yearServiceCom
	 * author: Hoang Yen
	 */
	@Override
	public void insert(YearServiceCom yearServiceCom) {
		this.commandProxy().insert(toEntity(yearServiceCom));
	}
	/**
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 */
	@Override
	public Optional<YearServiceCom> find(String companyId, int specialHolidayCode) {
		return this.queryProxy().find(new KshstYearServiceComPK(companyId, specialHolidayCode), KshstYearServiceCom.class).map(c->toDomain(c));
	}
}
