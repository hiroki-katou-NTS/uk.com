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
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KscmtSpecDateWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KsmmtWpSpecDateSetPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaWorkplaceSpecificDateRepository extends JpaRepository implements WorkplaceSpecificDateRepository {

	private static final String SELECT_NO_WHERE = "SELECT s FROM KscmtSpecDateWkp s";

	private static final String GET_BY_DATE = SELECT_NO_WHERE 
			+ " WHERE s.ksmmtWpSpecDateSetPK.workplaceId = :workplaceId"
			+ " AND s.ksmmtWpSpecDateSetPK.specificDate = :specificDate";

	// get List With Name of Specific
	private static final String GET_BY_USE_WITH_NAME = "SELECT p.name,p.useAtr, s FROM KscmtSpecDateWkp s"
			+ " LEFT JOIN KscmtSpecDateItem p ON s.ksmmtWpSpecDateSetPK.specificDateItemNo = p.ksmstSpecificDateItemPK.itemNo "
			+ " WHERE s.ksmmtWpSpecDateSetPK.workplaceId = :workplaceId"
			+ " AND p.ksmstSpecificDateItemPK.companyId = :companyID"
			+ " AND s.ksmmtWpSpecDateSetPK.specificDate >= :startYm"
			+ " AND s.ksmmtWpSpecDateSetPK.specificDate <= :endYm";
	
	//Delete by Month 
	
	private static final String DELETE_BY_YEAR_MONTH = "DELETE from KscmtSpecDateWkp c "
			+ " WHERE c.ksmmtWpSpecDateSetPK.workplaceId = :workplaceId"
			+ " AND c.ksmmtWpSpecDateSetPK.specificDate >= :startYm"
			+ " AND c.ksmmtWpSpecDateSetPK.specificDate <= :endYm";
	
	private static final String DELETE_BY_DATE = "DELETE FROM KscmtSpecDateWkp c"
			+ " WHERE c.ksmmtWpSpecDateSetPK.workplaceId = :workplaceId"
			+ " AND c.ksmmtWpSpecDateSetPK.specificDate = :specificDate";

	// No with name
	private static WorkplaceSpecificDateItem toDomain(KscmtSpecDateWkp entity) {
		
	/**TODO dev fix
	WorkplaceSpecificDateItem domain = WorkplaceSpecificDateItem.createFromJavaType(
				entity.ksmmtWpSpecDateSetPK.workplaceId, entity.ksmmtWpSpecDateSetPK.specificDate,
				entity.ksmmtWpSpecDateSetPK.specificDateItemNo, "");
				**/
		return null;
	}

	// With NAME
	private static WorkplaceSpecificDateItem toDomainWithName(Object[] object) {
		String specificDateItemName = (String) object[0];
		KscmtSpecDateWkp entity = (KscmtSpecDateWkp) object[2];
		/** TODO dev fix
		WorkplaceSpecificDateItem domain = WorkplaceSpecificDateItem.createFromJavaType(
				entity.ksmmtWpSpecDateSetPK.workplaceId, 
				entity.ksmmtWpSpecDateSetPK.specificDate,
				entity.ksmmtWpSpecDateSetPK.specificDateItemNo, 
				specificDateItemName);
		 **/
		return null;
	}
	
	//No with Name 
	private static KscmtSpecDateWkp toEntity(WorkplaceSpecificDateItem domain){
		val entity = new KscmtSpecDateWkp();
		/** TODO dev fix
		entity.ksmmtWpSpecDateSetPK = new KsmmtWpSpecDateSetPK(
				domain.getWorkplaceId(),
				domain.getSpecificDate(),
				domain.getSpecificDateItemNo().v());
		**/		
		return entity;
	}

	@Override
	public void insert(String companyId, WorkplaceSpecificDateItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String companyId, WorkplaceSpecificDateItem domain) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deleteByYmd(String workplaceId, GeneralDate ymd) {
		
		this.getEntityManager().createQuery(DELETE_BY_DATE)
		.setParameter("workplaceId", workplaceId)
		.setParameter("specificDate", ymd)
		.executeUpdate();
		
	}
	
	@Override
	public void delete(String workplaceId, DatePeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<WorkplaceSpecificDateItem> get(String workplaceId, GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkplaceSpecificDateItem> getList(String workplaceId, DatePeriod period) {
		// TODO Auto-generated method stub
		return null;
	}

}
