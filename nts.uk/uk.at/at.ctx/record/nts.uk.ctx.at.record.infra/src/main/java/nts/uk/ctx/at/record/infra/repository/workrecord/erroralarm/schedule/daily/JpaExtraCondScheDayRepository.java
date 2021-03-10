package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.daily;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtraCondScheDayRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtractionCondScheduleDay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyCon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheAnyCondDay;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheAnyCondDayPk;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheFixCondDay;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheFixCondDayPk;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaExtraCondScheDayRepository extends JpaRepository implements ExtraCondScheDayRepository {
	private static final String SELECT_BASIC = "SELECT a FROM KscdtScheAnyCondDay a";
	private static final String BY_CONTRACT_COMPANY = " WHERE a.pk.cid = :companyId AND a.contractCd = :contractCode";
	private static final String BY_ERAL_CHECK_ID = " AND a.pk.cid = :companyId AND a.contractCd = :contractCode AND a.pk.checkId = :eralCheckIds";
	
    @Override
    public List<ExtractionCondScheduleDay> getAll(String cid) {
        List<KscdtScheAnyCondDay> entities = new ArrayList<>();

        return null;
    }
    
    @Override
	public List<ExtractionCondScheduleDay> getScheAnyCondDay(String contractCode, String companyId) {
    	List<KscdtScheAnyCondDay> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY, KscdtScheAnyCondDay.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.getList();
//        return entities.stream().map(item -> item.toDomain()).collect(Collectors.toList());
    	return new ArrayList<>();
	}
    
    @Override
	public List<ExtractionCondScheduleDay> getScheAnyCondDay(String contractCode, String companyId, String eralCheckIds) {
    	List<KscdtScheAnyCondDay> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID, KscdtScheAnyCondDay.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", eralCheckIds)
				.getList();
        return entities.stream()
        		.map(item -> ExtractionCondScheduleDay.create(item.pk.checkId, item.pk.sortBy, item.useAtr, item.condName, item.message))
        		.collect(Collectors.toList());
	}

	@Override
	public void add(String contractCode, String companyId, ExtractionCondScheduleDay domain) {
		this.commandProxy().insert(fromDomain(contractCode, companyId, domain));
	}

	@Override
	public void update(String contractCode, String companyId, ExtractionCondScheduleDay domain) {
		KscdtScheAnyCondDayPk pk = new KscdtScheAnyCondDayPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		Optional<KscdtScheAnyCondDay> entityOpt = this.queryProxy().find(pk, KscdtScheAnyCondDay.class);
		KscdtScheAnyCondDay entity = null;
		
		if (entityOpt.isPresent()) {
			entity = entityOpt.get();
		} else {
			entity = new KscdtScheAnyCondDay();
			entity.pk = pk;
		}
		
		entity.useAtr = domain.isUse();
		entity.condName = domain.getName().v();
		if (domain.getErrorAlarmMessage() != null) {
			entity.message = domain.getErrorAlarmMessage().get().v();
		}
		//TODO
		
		if (!entityOpt.isPresent()) {
			this.commandProxy().insert(entity);
		} else {
			this.commandProxy().update(entity);;
		}
	}
	
	private KscdtScheAnyCondDay fromDomain(String contractCode, String companyId, ExtractionCondScheduleDay domain) {
		KscdtScheAnyCondDayPk pk = new KscdtScheAnyCondDayPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		KscdtScheAnyCondDay entity = new KscdtScheAnyCondDay(pk, domain.getName().v(), domain.isUse(), 0, 0, 0, 0, 0, "");
		entity.setContractCd(contractCode);
		if (domain.getErrorAlarmMessage() != null) {
			entity.message = domain.getErrorAlarmMessage().get().v();
		}
		return entity;
	}

	@Override
	public void delete(String contractCode, String companyId, List<String> erAlCheckIds) {
		List<KscdtScheAnyCondDay> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID, KscdtScheAnyCondDay.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", erAlCheckIds)
				.getList();
		this.commandProxy().removeAll(entities);
	}
}
