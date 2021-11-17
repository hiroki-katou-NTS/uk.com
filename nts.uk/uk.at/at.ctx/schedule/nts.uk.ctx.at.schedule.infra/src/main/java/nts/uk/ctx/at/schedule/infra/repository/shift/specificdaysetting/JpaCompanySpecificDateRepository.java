package nts.uk.ctx.at.schedule.infra.repository.shift.specificdaysetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KscmtSpecDateCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KsmmtComSpecDateSetPK;

@Stateless
public class JpaCompanySpecificDateRepository extends JpaRepository implements CompanySpecificDateRepository {
	
	/**
	 * select a KscmtSpecDateCom ALL
	 */
	private static final String SELECT_NO_WHERE = "SELECT s FROM KscmtSpecDateCom s";
	
	/**
	 * select KscmtSpecDateCom by date
	 */
	private static final String GET_BY_DATE = SELECT_NO_WHERE 
			+ " WHERE s.ksmmtComSpecDateSetPK.companyId = :companyId"
			+ " AND s.ksmmtComSpecDateSetPK.specificDate = :specificDate";

	/**
	 * get List With Name of Specific
	 */
	private static final String GET_BY_USE_WITH_NAME = "SELECT p.name,p.useAtr, s FROM KscmtSpecDateCom s"
			+ " INNER JOIN KscmtSpecDateItem p ON p.ksmstSpecificDateItemPK.itemNo = s.ksmmtComSpecDateSetPK.specificDateItemNo AND p.ksmstSpecificDateItemPK.companyId = s.ksmmtComSpecDateSetPK.companyId "
			+ " WHERE s.ksmmtComSpecDateSetPK.companyId = :companyId"
			+ " AND s.ksmmtComSpecDateSetPK.specificDate >= :startYm"
			+ " AND s.ksmmtComSpecDateSetPK.specificDate <= :endYm";
	
	/**
	 *Delete by Month 
	 */
	private static final String DELETE_BY_YEAR_MONTH = "DELETE from KscmtSpecDateCom c "
			+ " WHERE c.ksmmtComSpecDateSetPK.companyId = :companyId"
			+ " AND c.ksmmtComSpecDateSetPK.specificDate >= :startYm"
			+ " AND c.ksmmtComSpecDateSetPK.specificDate <= :endYm";
	
	private static final String DELETE_BY_DATE = "DELETE FROM KscmtSpecDateCom c"
			+ " WHERE c.ksmmtComSpecDateSetPK.companyId = :companyId"
			+ " AND c.ksmmtComSpecDateSetPK.specificDate = :specificDate";

	/**
	 * convert entity to domain NO with Name
	 * @param entity
	 * @return
	 */
	private static CompanySpecificDateItem toDomain(KscmtSpecDateCom entity) {
		
		CompanySpecificDateItem domain = new CompanySpecificDateItem( entity.ksmmtComSpecDateSetPK.companyId
				, entity.ksmmtComSpecDateSetPK.specificDate
				, null//TODO dev fix
				);
		
		return domain;
	}

	/**
	 * convert entity to domain with Name
	 * @param object
	 * @return
	 */
	private static CompanySpecificDateItem toDomainWithName(Object[] object) {
		String specificDateItemName = (String) object[0];
		KscmtSpecDateCom entity = (KscmtSpecDateCom) object[2];
		CompanySpecificDateItem domain = new CompanySpecificDateItem( entity.ksmmtComSpecDateSetPK.companyId
				, entity.ksmmtComSpecDateSetPK.specificDate
				, null //TODO dev fix 
				);
		
		return domain;
	}
	
	/**
	 * convert domain to entity NO with name
	 * @param domain
	 * @return
	 */
	private static KscmtSpecDateCom toEntity(CompanySpecificDateItem domain){
		val entity = new KscmtSpecDateCom();
		entity.ksmmtComSpecDateSetPK = new KsmmtComSpecDateSetPK(
				domain.getCompanyId(),
				domain.getSpecificDate(),
				null//TODO dev fix
				);
		return entity;
	}
	@Override
	public void insert(CompanySpecificDateItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CompanySpecificDateItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId, GeneralDate ymd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId, DatePeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<CompanySpecificDateItem> get(String companyId, GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanySpecificDateItem> getList(String companyId, DatePeriod period) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
