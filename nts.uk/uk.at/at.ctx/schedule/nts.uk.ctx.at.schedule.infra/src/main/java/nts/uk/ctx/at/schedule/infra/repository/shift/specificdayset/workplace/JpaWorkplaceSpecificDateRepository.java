package nts.uk.ctx.at.schedule.infra.repository.shift.specificdayset.workplace;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KsmmtWpSpecDateSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KsmmtWpSpecDateSetPK;
@Stateless
public class JpaWorkplaceSpecificDateRepository extends JpaRepository implements WorkplaceSpecificDateRepository{
	private static final String SELECT_BY_DATE = "SELECT c FROM KsmmtWpSpecDateSet c "
			+ " WHERE c.ksmmtWpSpecDateSetPK.workplaceId =: workplaceId"
			+ " AND c.ksmmtWpSpecDateSetPK.specificDate =: specificDate";
	
	private static WorkplaceSpecificDateItem toDomain(KsmmtWpSpecDateSet entity){
		val domain = WorkplaceSpecificDateItem.createFromJavaType(entity.ksmmtWpSpecDateSetPK.workplaceId,
				entity.ksmmtWpSpecDateSetPK.specificDate,
				entity.ksmmtWpSpecDateSetPK.specificDateItemNo);
		return domain;
	}
	private static KsmmtWpSpecDateSet toEntity(WorkplaceSpecificDateItem domain){
		val entity = new KsmmtWpSpecDateSet();
		entity.ksmmtWpSpecDateSetPK = new KsmmtWpSpecDateSetPK();
		entity.ksmmtWpSpecDateSetPK.workplaceId = domain.getWorkplaceId();
		entity.ksmmtWpSpecDateSetPK.specificDate = domain.getSpecificDate().v();
		entity.ksmmtWpSpecDateSetPK.specificDateItemNo = domain.getSpecificDateItemNo().v();
		return entity;
	}
	/**
	 * get workplace Spec By Date
	 * @param workplaceId
	 * @param specificDate
	 * @return
	 */
	@Override
	public List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, int specificDate) {
		return this.queryProxy().query(SELECT_BY_DATE, KsmmtWpSpecDateSet.class)
				.setParameter("workplaceId", workplaceId)
				.setParameter("specificDate", specificDate)
				.getList(c->toDomain(c));
	}
	/**
	 * add WorkplaceSpec
	 * @param lstWorkplaceSpecificDate
	 */
	@Override
	public void addWorkplaceSpec(List<WorkplaceSpecificDateItem> lstWorkplaceSpecificDate) {
		List<KsmmtWpSpecDateSet> lstEntity = new ArrayList<>();
		for (WorkplaceSpecificDateItem workplaceSpecificDateItem : lstWorkplaceSpecificDate) {
			KsmmtWpSpecDateSet entity = toEntity(workplaceSpecificDateItem);
			lstEntity.add(entity);
		}
		this.commandProxy().insertAll(lstEntity);
	}
	/**
	 * delete WorkplaceSpec
	 * @param workplaceId
	 * @param specificDate
	 */
	@Override
	public void deleteWorkplaceSpec(String workplaceId, int specificDate) {
		List<KsmmtWpSpecDateSet> lstEntity = new ArrayList<>();
		List<WorkplaceSpecificDateItem> lstWorkplaceSpecificDate = this.getWorkplaceSpecByDate(workplaceId, specificDate);
		for (WorkplaceSpecificDateItem workplaceSpecificDateItem : lstWorkplaceSpecificDate) {
			KsmmtWpSpecDateSet entity = toEntity(workplaceSpecificDateItem);
			lstEntity.add(entity);
		}
		this.commandProxy().removeAll(lstEntity);
	}

}
