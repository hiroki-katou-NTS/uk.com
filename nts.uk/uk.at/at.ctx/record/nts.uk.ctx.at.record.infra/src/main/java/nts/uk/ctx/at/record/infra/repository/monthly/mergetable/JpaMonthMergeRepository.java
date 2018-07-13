package nts.uk.ctx.at.record.infra.repository.monthly.mergetable;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.mergetable.AnyItemMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.AnyItemOfMonthlyMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeRepository;
import nts.uk.ctx.at.record.dom.monthly.mergetable.ObjectMergeAll;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMerge;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonAnyItemValueMerge;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonMerge;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonMergePk;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonRemainMerge;

@Stateless
public class JpaMonthMergeRepository extends JpaRepository implements MonthMergeRepository {

	@Override
	public ObjectMergeAll find(MonthMergeKey key) {
		ObjectMergeAll mergeAll = new ObjectMergeAll();
		Optional<KrcdtMonMerge> entityMonthMerge = this.queryProxy().find(new KrcdtMonMergePk(
				key.getEmployeeId(), 
				key.getYearMonth().v(), 
				key.getClosureId().value, 
				key.getClosureDate().getClosureDay().v(), 
				key.getClosureDate().getLastDayOfMonth() == true ? 1: 0),KrcdtMonMerge.class);
		
		
		Optional<KrcdtMonRemainMerge> entityRemainMerge = this.queryProxy().find(new KrcdtMonMergePk(
				key.getEmployeeId(), 
				key.getYearMonth().v(), 
				key.getClosureId().value, 
				key.getClosureDate().getClosureDay().v(), 
				key.getClosureDate().getLastDayOfMonth() == true ? 1: 0),KrcdtMonRemainMerge.class);
		
		Optional<KrcdtMonAnyItemValueMerge> entityMonAnyItemValueMerge = this.queryProxy().find(new KrcdtMonMergePk(
				key.getEmployeeId(), 
				key.getYearMonth().v(), 
				key.getClosureId().value, 
				key.getClosureDate().getClosureDay().v(), 
				key.getClosureDate().getLastDayOfMonth() == true ? 1: 0),KrcdtMonAnyItemValueMerge.class);
		if(entityMonthMerge.isPresent()) {
			mergeAll.setMonthMerge(toDomainMonthMerge(entityMonthMerge.get()));
		}
		
		if(entityRemainMerge.isPresent()) {
			mergeAll.setRemainMerge(toDomainRemainMerge(entityRemainMerge.get()));
		}
		
		if(entityMonAnyItemValueMerge.isPresent()) {
			mergeAll.setAnyItemMerge(toDomainAnyItemOfMonthly(entityMonAnyItemValueMerge.get()));
		}
		
		
		return mergeAll;
	}
	
	@Override
	public void insert(ObjectMergeAll objMergeAll) {

		KrcdtMonMerge entityMonthMerge = new KrcdtMonMerge();
		this.toEntityMonthMerge(objMergeAll.getMonthMerge(), entityMonthMerge, false);

		KrcdtMonAnyItemValueMerge entityAnyItemValue = new KrcdtMonAnyItemValueMerge();
		this.toEntityAnyItemOfMonthly(objMergeAll.getAnyItemMerge(), entityAnyItemValue, false);

		KrcdtMonRemainMerge entityRemain = new KrcdtMonRemainMerge();
		this.toEntityRemainMerge(objMergeAll.getRemainMerge(), entityRemain, false);

		this.commandProxy().insert(entityMonthMerge);
		this.commandProxy().insert(entityAnyItemValue);
		this.commandProxy().insert(entityRemain);
	}


	@Override
	public void update(ObjectMergeAll objMergeAll) {
		KrcdtMonMerge entityMonthMerge = new KrcdtMonMerge();
		this.toEntityMonthMerge(objMergeAll.getMonthMerge(), entityMonthMerge, true);

		KrcdtMonAnyItemValueMerge entityAnyItemValue = new KrcdtMonAnyItemValueMerge();
		this.toEntityAnyItemOfMonthly(objMergeAll.getAnyItemMerge(), entityAnyItemValue, true);

		KrcdtMonRemainMerge entityRemain = new KrcdtMonRemainMerge();
		this.toEntityRemainMerge(objMergeAll.getRemainMerge(), entityRemain, true);

		this.commandProxy().update(entityMonthMerge);
		this.commandProxy().update(entityAnyItemValue);
		this.commandProxy().update(entityRemain);

	}

	@Override
	public void delete(MonthMergeKey domainKey) {
		// 締め日付
		val closureDate = domainKey.getClosureDate();

		// キー
		val key = new KrcdtMonMergePk(domainKey.getEmployeeId(), 
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value, 
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		Optional<KrcdtMonMerge> monMerge = this.queryProxy().find(key, KrcdtMonMerge.class);
		Optional<KrcdtMonAnyItemValueMerge> anyMerge = this.queryProxy().find(key, KrcdtMonAnyItemValueMerge.class);
		Optional<KrcdtMonRemainMerge> remainMerge = this.queryProxy().find(key, KrcdtMonRemainMerge.class);
		if(monMerge.isPresent()) {
			this.commandProxy().remove(monMerge.get());
		}
		
		if(anyMerge.isPresent()) {
			this.commandProxy().remove(anyMerge.get());
		}
		
		if(remainMerge.isPresent()) {
			this.commandProxy().remove(remainMerge.get());
		}
	}
	
	private void toEntityMonthMerge(MonthMerge monthMerge, KrcdtMonMerge entity, boolean isUpdate) {
		// 締め日付
		val closureDate = monthMerge.getMonthMergeKey().getClosureDate();

		// キー
		val key = new KrcdtMonMergePk(monthMerge.getMonthMergeKey().getEmployeeId(),
				monthMerge.getMonthMergeKey().getYearMonth().v(), monthMerge.getMonthMergeKey().getClosureId().value,
				closureDate.getClosureDay().v(), (closureDate.getLastDayOfMonth() ? 1 : 0));
		if (isUpdate) {
			entity = this.getEntityManager().find(KrcdtMonMerge.class, key);
		} else {
			entity.krcdtMonMergePk = key;
		}
		entity.toEntityAbsenceDays(monthMerge.getAbsenceDaysMerge());
		entity.toEntityBonusPayTime(monthMerge.getBonusPayTimeMerge());
		entity.toEntityDivergenceTime(monthMerge.getDivergenceTimeMerge());
		entity.toEntityGoOut(monthMerge.getGoOutMerge());
		entity.toEntityHolidayWorkTime(monthMerge.getHolidayWorkTimeMerge());

		entity.toEntityOverTime(monthMerge.getOverTimeMerge());
		entity.toEntityPremiumTime(monthMerge.getPremiumTimeMerge());
		entity.toEntityExcessOutsideWorkOfMonthly(monthMerge.getExcessOutsideWorkOfMonthly());
		entity.toEntityExcessOutsideWorkMerge(monthMerge.getExcessOutsideWorkMerge());
		entity.toEntitySpecificDays(monthMerge.getSpecificDaysMerge());

		entity.toEntityTotalTimeSpentAtWork(monthMerge.getTotalTimeSpentAtWork());
		entity.toEntityTotalWorkingTime(monthMerge.getTotalWorkingTime());
		entity.toEntityAttendanceTimeOfMonthly(monthMerge.getAttendanceTimeOfMonthly());
		entity.toEntityFlexTimeOfMonthly(monthMerge.getFlexTimeOfMonthly());
		entity.toEntityHolidayWorkTimeOfMonthly(monthMerge.getHolidayWorkTimeOfMonthly());

		entity.toEntityLeaveOfMonthly(monthMerge.getLeaveOfMonthly());
		entity.toEntityMedicalTimeOfMonthly(monthMerge.getMedicalTimeOfMonthly());
		entity.toEntityOverTimeOfMonthly(monthMerge.getOverTimeOfMonthly());
		entity.toEntityRegAndIrreTimeOfMonth(monthMerge.getRegularAndIrregularTimeOfMonthly());
		entity.toEntityVacationUseTimeOfMonth(monthMerge.getVacationUseTimeOfMonthly());

		entity.toEntityVerticalTotalOfMonthly(monthMerge.getVerticalTotalOfMonthly());
		entity.toEntityAgreementTimeOfMonthly(monthMerge.getAgreementTimeOfMonthly());
		entity.toEntityAffiliationInfoOfMonthly(monthMerge.getAffiliationInfoOfMonthly());

	}

	private void toEntityRemainMerge(RemainMerge domain, KrcdtMonRemainMerge entity, boolean isUpdate) {
		// 締め日付
		val closureDate = domain.getMonthMergeKey().getClosureDate();

		// キー
		val key = new KrcdtMonMergePk(domain.getMonthMergeKey().getEmployeeId(),
				domain.getMonthMergeKey().getYearMonth().v(), domain.getMonthMergeKey().getClosureId().value,
				closureDate.getClosureDay().v(), (closureDate.getLastDayOfMonth() ? 1 : 0));
		if (isUpdate) {
			entity = this.getEntityManager().find(KrcdtMonRemainMerge.class, key);
		} else {
			entity.krcdtMonRemainPk = key;
		}
		entity.toEntityMonAnnleaRemain(domain.getAnnLeaRemNumEachMonth());
		entity.toEntityRsvLeaRemNumEachMonth(domain.getRsvLeaRemNumEachMonth());
		entity.toEntitySpeRemain(domain.getSpecialHolidayRemainDataMerge());
		entity.toEntityDayOffRemainDayAndTimes(domain.getMonthlyDayoffRemainData());
		entity.toEntityAbsenceLeaveRemainData(domain.getAbsenceLeaveRemainData());
	}

	private void toEntityAnyItemOfMonthly(AnyItemMerge domain, KrcdtMonAnyItemValueMerge entity, boolean isUpdate) {
		// 締め日付
		val closureDate = domain.getMonthMergeKey().getClosureDate();

		// キー
		val key = new KrcdtMonMergePk(domain.getMonthMergeKey().getEmployeeId(),
				domain.getMonthMergeKey().getYearMonth().v(), domain.getMonthMergeKey().getClosureId().value,
				closureDate.getClosureDay().v(), (closureDate.getLastDayOfMonth() ? 1 : 0));
		if (isUpdate) {
			entity = this.getEntityManager().find(KrcdtMonAnyItemValueMerge.class, key);
		} else {
			entity.krcdtMonAnyItemValuePk = key;
		}
		AnyItemOfMonthlyMerge anyItem = domain.getAnyItemOfMonthlyMerge();
		entity.toEntityAnyItemOfMonthly(anyItem);
	}
	
	
	private static MonthMerge toDomainMonthMerge(KrcdtMonMerge entity) {
		MonthMerge monthMerge = new MonthMerge();
		monthMerge.setMonthMergeKey(entity.toDomainKey());
		monthMerge.setAbsenceDaysMerge(entity.toDomainAbsenceDays());
		monthMerge.setBonusPayTimeMerge(entity.toDomainBonusPayTimeMerge());
		monthMerge.setDivergenceTimeMerge(entity.toDomainDivergenceTimeMerge());
		monthMerge.setGoOutMerge(entity.toDomainGoOut());
		monthMerge.setHolidayWorkTimeMerge(entity.toDomainHolidayWorkTimeMerge());
		monthMerge.setOverTimeMerge(entity.toDomainOverTimeMerge());
		monthMerge.setPremiumTimeMerge(entity.toDomainPremiumTimeMerge());
		monthMerge.setSpecificDaysMerge(entity.toDomainSpecificDaysMerge());
		monthMerge.setTotalTimeSpentAtWork(entity.toDomainTotalTimeSpentAtWork());
		monthMerge.setTotalWorkingTime(entity.toDomainTotalWorkingTime());
		monthMerge.setAttendanceTimeOfMonthly(entity.toDomainAttendanceTimeOfMonthly());
		monthMerge.setFlexTimeOfMonthly(entity.toDomainFlexTimeOfMonthly());
		monthMerge.setHolidayWorkTimeOfMonthly(entity.toDomainHolidayWorkTimeOfMonthly());
		monthMerge.setLeaveOfMonthly(entity.toDomainLeaveOfMonthly());
		monthMerge.setMedicalTimeOfMonthly(entity.toDomainMedicalTimeOfMonthly());
		monthMerge.setOverTimeOfMonthly(entity.toDomainOverTimeOfMonthly());
		monthMerge.setMedicalTimeOfMonthly(entity.toDomainMedicalTimeOfMonthly());
		monthMerge.setOverTimeOfMonthly(entity.toDomainOverTimeOfMonthly());
		monthMerge.setRegularAndIrregularTimeOfMonthly(entity.toDomainRegularAndIrregularTimeOfMonthly());
		monthMerge.setVacationUseTimeOfMonthly(entity.toDomainVacationUseTimeOfMonthly());
		monthMerge.setVerticalTotalOfMonthly(entity.toDomainVerticalTotalOfMonthly());
		monthMerge.setExcessOutsideWorkOfMonthly(entity.toDomainExcessOutsideWorkOfMonthly());
		monthMerge.setExcessOutsideWorkMerge(entity.toDomainExcessOutsideWork());
		monthMerge.setAgreementTimeOfMonthly(entity.toDomainAgreementTimeOfMonthly());
		monthMerge.setAffiliationInfoOfMonthly(entity.toDomainAffiliationInfoOfMonthly());
		return monthMerge;
	}

	private RemainMerge toDomainRemainMerge(KrcdtMonRemainMerge entity) {
		RemainMerge remainMerge = new RemainMerge();
		remainMerge.setMonthMergeKey(entity.toDomainKey());
		remainMerge.setAnnLeaRemNumEachMonth(entity.toDomainAnnLeaRemNumEachMonth());
		remainMerge.setRsvLeaRemNumEachMonth(entity.toDomainRsvLeaRemNumEachMonth());
		remainMerge.setSpecialHolidayRemainDataMerge(entity.toDomainSpecialHolidayRemainData());
		remainMerge.setMonthlyDayoffRemainData(entity.toDomainMonthlyDayoffRemainData());
		remainMerge.setAbsenceLeaveRemainData(entity.toDomainAbsenceLeaveRemainData());
		return remainMerge;
	}

	private AnyItemMerge toDomainAnyItemOfMonthly(KrcdtMonAnyItemValueMerge entity) {
		AnyItemMerge anyItemMerge = new AnyItemMerge();
		anyItemMerge.setMonthMergeKey(entity.toDomainKey());
		anyItemMerge.setAnyItemOfMonthlyMerge(entity.toDomainAnyItemOfMonthly());
		
		return anyItemMerge;
	}





}
