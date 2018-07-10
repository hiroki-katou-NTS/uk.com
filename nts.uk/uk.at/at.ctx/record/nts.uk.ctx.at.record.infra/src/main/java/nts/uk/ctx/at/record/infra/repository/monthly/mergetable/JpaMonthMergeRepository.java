package nts.uk.ctx.at.record.infra.repository.monthly.mergetable;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.mergetable.AnyItemMerge;
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
	public List<ObjectMergeAll> find(MonthMergeKey key) {
		// TODO Auto-generated method stub
		return null;
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
		entity.toEntityExcessOutsideWorkOfMonthly(monthMerge.getExcessOutsideWorkOfMonthlyMerge());
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

		entity.toEntityAnyItemOfMonthly1(domain.getAnyItemOfMonthly1());
		entity.toEntityAnyItemOfMonthly2(domain.getAnyItemOfMonthly2());
		entity.toEntityAnyItemOfMonthly3(domain.getAnyItemOfMonthly3());
		entity.toEntityAnyItemOfMonthly4(domain.getAnyItemOfMonthly4());
		entity.toEntityAnyItemOfMonthly5(domain.getAnyItemOfMonthly5());
		entity.toEntityAnyItemOfMonthly6(domain.getAnyItemOfMonthly6());
		entity.toEntityAnyItemOfMonthly7(domain.getAnyItemOfMonthly7());
		entity.toEntityAnyItemOfMonthly8(domain.getAnyItemOfMonthly8());
		entity.toEntityAnyItemOfMonthly9(domain.getAnyItemOfMonthly9());
		entity.toEntityAnyItemOfMonthly10(domain.getAnyItemOfMonthly10());
		entity.toEntityAnyItemOfMonthly11(domain.getAnyItemOfMonthly11());
		entity.toEntityAnyItemOfMonthly12(domain.getAnyItemOfMonthly12());
		entity.toEntityAnyItemOfMonthly13(domain.getAnyItemOfMonthly13());
		entity.toEntityAnyItemOfMonthly14(domain.getAnyItemOfMonthly14());
		entity.toEntityAnyItemOfMonthly15(domain.getAnyItemOfMonthly15());
		entity.toEntityAnyItemOfMonthly16(domain.getAnyItemOfMonthly16());
		entity.toEntityAnyItemOfMonthly17(domain.getAnyItemOfMonthly17());
		entity.toEntityAnyItemOfMonthly18(domain.getAnyItemOfMonthly18());
		entity.toEntityAnyItemOfMonthly19(domain.getAnyItemOfMonthly19());
		entity.toEntityAnyItemOfMonthly20(domain.getAnyItemOfMonthly20());
		entity.toEntityAnyItemOfMonthly21(domain.getAnyItemOfMonthly21());
		entity.toEntityAnyItemOfMonthly22(domain.getAnyItemOfMonthly22());
		entity.toEntityAnyItemOfMonthly23(domain.getAnyItemOfMonthly23());
		entity.toEntityAnyItemOfMonthly24(domain.getAnyItemOfMonthly24());
		entity.toEntityAnyItemOfMonthly25(domain.getAnyItemOfMonthly25());
		entity.toEntityAnyItemOfMonthly26(domain.getAnyItemOfMonthly26());
		entity.toEntityAnyItemOfMonthly27(domain.getAnyItemOfMonthly27());
		entity.toEntityAnyItemOfMonthly28(domain.getAnyItemOfMonthly28());
		entity.toEntityAnyItemOfMonthly29(domain.getAnyItemOfMonthly29());
		entity.toEntityAnyItemOfMonthly30(domain.getAnyItemOfMonthly30());
		entity.toEntityAnyItemOfMonthly31(domain.getAnyItemOfMonthly31());
		entity.toEntityAnyItemOfMonthly32(domain.getAnyItemOfMonthly32());
		entity.toEntityAnyItemOfMonthly33(domain.getAnyItemOfMonthly33());
		entity.toEntityAnyItemOfMonthly34(domain.getAnyItemOfMonthly34());
		entity.toEntityAnyItemOfMonthly35(domain.getAnyItemOfMonthly35());
		entity.toEntityAnyItemOfMonthly36(domain.getAnyItemOfMonthly36());
		entity.toEntityAnyItemOfMonthly37(domain.getAnyItemOfMonthly37());
		entity.toEntityAnyItemOfMonthly38(domain.getAnyItemOfMonthly38());
		entity.toEntityAnyItemOfMonthly39(domain.getAnyItemOfMonthly39());
		entity.toEntityAnyItemOfMonthly40(domain.getAnyItemOfMonthly40());
		entity.toEntityAnyItemOfMonthly41(domain.getAnyItemOfMonthly41());
		entity.toEntityAnyItemOfMonthly42(domain.getAnyItemOfMonthly42());
		entity.toEntityAnyItemOfMonthly43(domain.getAnyItemOfMonthly43());
		entity.toEntityAnyItemOfMonthly44(domain.getAnyItemOfMonthly44());
		entity.toEntityAnyItemOfMonthly45(domain.getAnyItemOfMonthly45());
		entity.toEntityAnyItemOfMonthly46(domain.getAnyItemOfMonthly46());
		entity.toEntityAnyItemOfMonthly47(domain.getAnyItemOfMonthly47());
		entity.toEntityAnyItemOfMonthly48(domain.getAnyItemOfMonthly48());
		entity.toEntityAnyItemOfMonthly49(domain.getAnyItemOfMonthly49());
		entity.toEntityAnyItemOfMonthly50(domain.getAnyItemOfMonthly50());
		entity.toEntityAnyItemOfMonthly51(domain.getAnyItemOfMonthly51());
		entity.toEntityAnyItemOfMonthly52(domain.getAnyItemOfMonthly52());
		entity.toEntityAnyItemOfMonthly53(domain.getAnyItemOfMonthly53());
		entity.toEntityAnyItemOfMonthly54(domain.getAnyItemOfMonthly54());
		entity.toEntityAnyItemOfMonthly55(domain.getAnyItemOfMonthly55());
		entity.toEntityAnyItemOfMonthly56(domain.getAnyItemOfMonthly56());
		entity.toEntityAnyItemOfMonthly57(domain.getAnyItemOfMonthly57());
		entity.toEntityAnyItemOfMonthly58(domain.getAnyItemOfMonthly58());
		entity.toEntityAnyItemOfMonthly59(domain.getAnyItemOfMonthly59());
		entity.toEntityAnyItemOfMonthly60(domain.getAnyItemOfMonthly60());
		entity.toEntityAnyItemOfMonthly61(domain.getAnyItemOfMonthly61());
		entity.toEntityAnyItemOfMonthly62(domain.getAnyItemOfMonthly62());
		entity.toEntityAnyItemOfMonthly63(domain.getAnyItemOfMonthly63());
		entity.toEntityAnyItemOfMonthly64(domain.getAnyItemOfMonthly64());
		entity.toEntityAnyItemOfMonthly65(domain.getAnyItemOfMonthly65());
		entity.toEntityAnyItemOfMonthly66(domain.getAnyItemOfMonthly66());
		entity.toEntityAnyItemOfMonthly67(domain.getAnyItemOfMonthly67());
		entity.toEntityAnyItemOfMonthly68(domain.getAnyItemOfMonthly68());
		entity.toEntityAnyItemOfMonthly69(domain.getAnyItemOfMonthly69());
		entity.toEntityAnyItemOfMonthly70(domain.getAnyItemOfMonthly70());
		entity.toEntityAnyItemOfMonthly71(domain.getAnyItemOfMonthly71());
		entity.toEntityAnyItemOfMonthly72(domain.getAnyItemOfMonthly72());
		entity.toEntityAnyItemOfMonthly73(domain.getAnyItemOfMonthly73());
		entity.toEntityAnyItemOfMonthly74(domain.getAnyItemOfMonthly74());
		entity.toEntityAnyItemOfMonthly75(domain.getAnyItemOfMonthly75());
		entity.toEntityAnyItemOfMonthly76(domain.getAnyItemOfMonthly76());
		entity.toEntityAnyItemOfMonthly77(domain.getAnyItemOfMonthly77());
		entity.toEntityAnyItemOfMonthly78(domain.getAnyItemOfMonthly78());
		entity.toEntityAnyItemOfMonthly79(domain.getAnyItemOfMonthly79());
		entity.toEntityAnyItemOfMonthly80(domain.getAnyItemOfMonthly80());
		entity.toEntityAnyItemOfMonthly81(domain.getAnyItemOfMonthly81());
		entity.toEntityAnyItemOfMonthly82(domain.getAnyItemOfMonthly82());
		entity.toEntityAnyItemOfMonthly83(domain.getAnyItemOfMonthly83());
		entity.toEntityAnyItemOfMonthly84(domain.getAnyItemOfMonthly84());
		entity.toEntityAnyItemOfMonthly85(domain.getAnyItemOfMonthly85());
		entity.toEntityAnyItemOfMonthly86(domain.getAnyItemOfMonthly86());
		entity.toEntityAnyItemOfMonthly87(domain.getAnyItemOfMonthly87());
		entity.toEntityAnyItemOfMonthly88(domain.getAnyItemOfMonthly88());
		entity.toEntityAnyItemOfMonthly89(domain.getAnyItemOfMonthly89());
		entity.toEntityAnyItemOfMonthly90(domain.getAnyItemOfMonthly90());
		entity.toEntityAnyItemOfMonthly91(domain.getAnyItemOfMonthly91());
		entity.toEntityAnyItemOfMonthly92(domain.getAnyItemOfMonthly92());
		entity.toEntityAnyItemOfMonthly93(domain.getAnyItemOfMonthly93());
		entity.toEntityAnyItemOfMonthly94(domain.getAnyItemOfMonthly94());
		entity.toEntityAnyItemOfMonthly95(domain.getAnyItemOfMonthly95());
		entity.toEntityAnyItemOfMonthly96(domain.getAnyItemOfMonthly96());
		entity.toEntityAnyItemOfMonthly97(domain.getAnyItemOfMonthly97());
		entity.toEntityAnyItemOfMonthly98(domain.getAnyItemOfMonthly98());
		entity.toEntityAnyItemOfMonthly99(domain.getAnyItemOfMonthly99());
		entity.toEntityAnyItemOfMonthly100(domain.getAnyItemOfMonthly100());
		entity.toEntityAnyItemOfMonthly101(domain.getAnyItemOfMonthly101());
		entity.toEntityAnyItemOfMonthly102(domain.getAnyItemOfMonthly102());
		entity.toEntityAnyItemOfMonthly103(domain.getAnyItemOfMonthly103());
		entity.toEntityAnyItemOfMonthly104(domain.getAnyItemOfMonthly104());
		entity.toEntityAnyItemOfMonthly105(domain.getAnyItemOfMonthly105());
		entity.toEntityAnyItemOfMonthly106(domain.getAnyItemOfMonthly106());
		entity.toEntityAnyItemOfMonthly107(domain.getAnyItemOfMonthly107());
		entity.toEntityAnyItemOfMonthly108(domain.getAnyItemOfMonthly108());
		entity.toEntityAnyItemOfMonthly109(domain.getAnyItemOfMonthly109());
		entity.toEntityAnyItemOfMonthly110(domain.getAnyItemOfMonthly110());
		entity.toEntityAnyItemOfMonthly111(domain.getAnyItemOfMonthly111());
		entity.toEntityAnyItemOfMonthly112(domain.getAnyItemOfMonthly112());
		entity.toEntityAnyItemOfMonthly113(domain.getAnyItemOfMonthly113());
		entity.toEntityAnyItemOfMonthly114(domain.getAnyItemOfMonthly114());
		entity.toEntityAnyItemOfMonthly115(domain.getAnyItemOfMonthly115());
		entity.toEntityAnyItemOfMonthly116(domain.getAnyItemOfMonthly116());
		entity.toEntityAnyItemOfMonthly117(domain.getAnyItemOfMonthly117());
		entity.toEntityAnyItemOfMonthly118(domain.getAnyItemOfMonthly118());
		entity.toEntityAnyItemOfMonthly119(domain.getAnyItemOfMonthly119());
		entity.toEntityAnyItemOfMonthly120(domain.getAnyItemOfMonthly120());
		entity.toEntityAnyItemOfMonthly121(domain.getAnyItemOfMonthly121());
		entity.toEntityAnyItemOfMonthly122(domain.getAnyItemOfMonthly122());
		entity.toEntityAnyItemOfMonthly123(domain.getAnyItemOfMonthly123());
		entity.toEntityAnyItemOfMonthly124(domain.getAnyItemOfMonthly124());
		entity.toEntityAnyItemOfMonthly125(domain.getAnyItemOfMonthly125());
		entity.toEntityAnyItemOfMonthly126(domain.getAnyItemOfMonthly126());
		entity.toEntityAnyItemOfMonthly127(domain.getAnyItemOfMonthly127());
		entity.toEntityAnyItemOfMonthly128(domain.getAnyItemOfMonthly128());
		entity.toEntityAnyItemOfMonthly129(domain.getAnyItemOfMonthly129());
		entity.toEntityAnyItemOfMonthly130(domain.getAnyItemOfMonthly130());
		entity.toEntityAnyItemOfMonthly131(domain.getAnyItemOfMonthly131());
		entity.toEntityAnyItemOfMonthly132(domain.getAnyItemOfMonthly132());
		entity.toEntityAnyItemOfMonthly133(domain.getAnyItemOfMonthly133());
		entity.toEntityAnyItemOfMonthly134(domain.getAnyItemOfMonthly134());
		entity.toEntityAnyItemOfMonthly135(domain.getAnyItemOfMonthly135());
		entity.toEntityAnyItemOfMonthly136(domain.getAnyItemOfMonthly136());
		entity.toEntityAnyItemOfMonthly137(domain.getAnyItemOfMonthly137());
		entity.toEntityAnyItemOfMonthly138(domain.getAnyItemOfMonthly138());
		entity.toEntityAnyItemOfMonthly139(domain.getAnyItemOfMonthly139());
		entity.toEntityAnyItemOfMonthly140(domain.getAnyItemOfMonthly140());
		entity.toEntityAnyItemOfMonthly141(domain.getAnyItemOfMonthly141());
		entity.toEntityAnyItemOfMonthly142(domain.getAnyItemOfMonthly142());
		entity.toEntityAnyItemOfMonthly143(domain.getAnyItemOfMonthly143());
		entity.toEntityAnyItemOfMonthly144(domain.getAnyItemOfMonthly144());
		entity.toEntityAnyItemOfMonthly145(domain.getAnyItemOfMonthly145());
		entity.toEntityAnyItemOfMonthly146(domain.getAnyItemOfMonthly146());
		entity.toEntityAnyItemOfMonthly147(domain.getAnyItemOfMonthly147());
		entity.toEntityAnyItemOfMonthly148(domain.getAnyItemOfMonthly148());
		entity.toEntityAnyItemOfMonthly149(domain.getAnyItemOfMonthly149());
		entity.toEntityAnyItemOfMonthly150(domain.getAnyItemOfMonthly150());
		entity.toEntityAnyItemOfMonthly151(domain.getAnyItemOfMonthly151());
		entity.toEntityAnyItemOfMonthly152(domain.getAnyItemOfMonthly152());
		entity.toEntityAnyItemOfMonthly153(domain.getAnyItemOfMonthly153());
		entity.toEntityAnyItemOfMonthly154(domain.getAnyItemOfMonthly154());
		entity.toEntityAnyItemOfMonthly155(domain.getAnyItemOfMonthly155());
		entity.toEntityAnyItemOfMonthly156(domain.getAnyItemOfMonthly156());
		entity.toEntityAnyItemOfMonthly157(domain.getAnyItemOfMonthly157());
		entity.toEntityAnyItemOfMonthly158(domain.getAnyItemOfMonthly158());
		entity.toEntityAnyItemOfMonthly159(domain.getAnyItemOfMonthly159());
		entity.toEntityAnyItemOfMonthly160(domain.getAnyItemOfMonthly160());
		entity.toEntityAnyItemOfMonthly161(domain.getAnyItemOfMonthly161());
		entity.toEntityAnyItemOfMonthly162(domain.getAnyItemOfMonthly162());
		entity.toEntityAnyItemOfMonthly163(domain.getAnyItemOfMonthly163());
		entity.toEntityAnyItemOfMonthly164(domain.getAnyItemOfMonthly164());
		entity.toEntityAnyItemOfMonthly165(domain.getAnyItemOfMonthly165());
		entity.toEntityAnyItemOfMonthly166(domain.getAnyItemOfMonthly166());
		entity.toEntityAnyItemOfMonthly167(domain.getAnyItemOfMonthly167());
		entity.toEntityAnyItemOfMonthly168(domain.getAnyItemOfMonthly168());
		entity.toEntityAnyItemOfMonthly169(domain.getAnyItemOfMonthly169());
		entity.toEntityAnyItemOfMonthly170(domain.getAnyItemOfMonthly170());
		entity.toEntityAnyItemOfMonthly171(domain.getAnyItemOfMonthly171());
		entity.toEntityAnyItemOfMonthly172(domain.getAnyItemOfMonthly172());
		entity.toEntityAnyItemOfMonthly173(domain.getAnyItemOfMonthly173());
		entity.toEntityAnyItemOfMonthly174(domain.getAnyItemOfMonthly174());
		entity.toEntityAnyItemOfMonthly175(domain.getAnyItemOfMonthly175());
		entity.toEntityAnyItemOfMonthly176(domain.getAnyItemOfMonthly176());
		entity.toEntityAnyItemOfMonthly177(domain.getAnyItemOfMonthly177());
		entity.toEntityAnyItemOfMonthly178(domain.getAnyItemOfMonthly178());
		entity.toEntityAnyItemOfMonthly179(domain.getAnyItemOfMonthly179());
		entity.toEntityAnyItemOfMonthly180(domain.getAnyItemOfMonthly180());
		entity.toEntityAnyItemOfMonthly181(domain.getAnyItemOfMonthly181());
		entity.toEntityAnyItemOfMonthly182(domain.getAnyItemOfMonthly182());
		entity.toEntityAnyItemOfMonthly183(domain.getAnyItemOfMonthly183());
		entity.toEntityAnyItemOfMonthly184(domain.getAnyItemOfMonthly184());
		entity.toEntityAnyItemOfMonthly185(domain.getAnyItemOfMonthly185());
		entity.toEntityAnyItemOfMonthly186(domain.getAnyItemOfMonthly186());
		entity.toEntityAnyItemOfMonthly187(domain.getAnyItemOfMonthly187());
		entity.toEntityAnyItemOfMonthly188(domain.getAnyItemOfMonthly188());
		entity.toEntityAnyItemOfMonthly189(domain.getAnyItemOfMonthly189());
		entity.toEntityAnyItemOfMonthly190(domain.getAnyItemOfMonthly190());
		entity.toEntityAnyItemOfMonthly191(domain.getAnyItemOfMonthly191());
		entity.toEntityAnyItemOfMonthly192(domain.getAnyItemOfMonthly192());
		entity.toEntityAnyItemOfMonthly193(domain.getAnyItemOfMonthly193());
		entity.toEntityAnyItemOfMonthly194(domain.getAnyItemOfMonthly194());
		entity.toEntityAnyItemOfMonthly195(domain.getAnyItemOfMonthly195());
		entity.toEntityAnyItemOfMonthly196(domain.getAnyItemOfMonthly196());
		entity.toEntityAnyItemOfMonthly197(domain.getAnyItemOfMonthly197());
		entity.toEntityAnyItemOfMonthly198(domain.getAnyItemOfMonthly198());
		entity.toEntityAnyItemOfMonthly199(domain.getAnyItemOfMonthly199());
		entity.toEntityAnyItemOfMonthly200(domain.getAnyItemOfMonthly200());
	}
	
	
	private void toDomainMonthMerge(MonthMerge monthMerge, KrcdtMonMerge entity, boolean isUpdate) {
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
		entity.toEntityExcessOutsideWorkOfMonthly(monthMerge.getExcessOutsideWorkOfMonthlyMerge());
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

	private void toDomainRemainMerge(RemainMerge domain, KrcdtMonRemainMerge entity, boolean isUpdate) {
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

	private void toDomainAnyItemOfMonthly(AnyItemMerge domain, KrcdtMonAnyItemValueMerge entity, boolean isUpdate) {
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

		entity.toEntityAnyItemOfMonthly1(domain.getAnyItemOfMonthly1());
		entity.toEntityAnyItemOfMonthly2(domain.getAnyItemOfMonthly2());
		entity.toEntityAnyItemOfMonthly3(domain.getAnyItemOfMonthly3());
		entity.toEntityAnyItemOfMonthly4(domain.getAnyItemOfMonthly4());
		entity.toEntityAnyItemOfMonthly5(domain.getAnyItemOfMonthly5());
		entity.toEntityAnyItemOfMonthly6(domain.getAnyItemOfMonthly6());
		entity.toEntityAnyItemOfMonthly7(domain.getAnyItemOfMonthly7());
		entity.toEntityAnyItemOfMonthly8(domain.getAnyItemOfMonthly8());
		entity.toEntityAnyItemOfMonthly9(domain.getAnyItemOfMonthly9());
		entity.toEntityAnyItemOfMonthly10(domain.getAnyItemOfMonthly10());
		entity.toEntityAnyItemOfMonthly11(domain.getAnyItemOfMonthly11());
		entity.toEntityAnyItemOfMonthly12(domain.getAnyItemOfMonthly12());
		entity.toEntityAnyItemOfMonthly13(domain.getAnyItemOfMonthly13());
		entity.toEntityAnyItemOfMonthly14(domain.getAnyItemOfMonthly14());
		entity.toEntityAnyItemOfMonthly15(domain.getAnyItemOfMonthly15());
		entity.toEntityAnyItemOfMonthly16(domain.getAnyItemOfMonthly16());
		entity.toEntityAnyItemOfMonthly17(domain.getAnyItemOfMonthly17());
		entity.toEntityAnyItemOfMonthly18(domain.getAnyItemOfMonthly18());
		entity.toEntityAnyItemOfMonthly19(domain.getAnyItemOfMonthly19());
		entity.toEntityAnyItemOfMonthly20(domain.getAnyItemOfMonthly20());
		entity.toEntityAnyItemOfMonthly21(domain.getAnyItemOfMonthly21());
		entity.toEntityAnyItemOfMonthly22(domain.getAnyItemOfMonthly22());
		entity.toEntityAnyItemOfMonthly23(domain.getAnyItemOfMonthly23());
		entity.toEntityAnyItemOfMonthly24(domain.getAnyItemOfMonthly24());
		entity.toEntityAnyItemOfMonthly25(domain.getAnyItemOfMonthly25());
		entity.toEntityAnyItemOfMonthly26(domain.getAnyItemOfMonthly26());
		entity.toEntityAnyItemOfMonthly27(domain.getAnyItemOfMonthly27());
		entity.toEntityAnyItemOfMonthly28(domain.getAnyItemOfMonthly28());
		entity.toEntityAnyItemOfMonthly29(domain.getAnyItemOfMonthly29());
		entity.toEntityAnyItemOfMonthly30(domain.getAnyItemOfMonthly30());
		entity.toEntityAnyItemOfMonthly31(domain.getAnyItemOfMonthly31());
		entity.toEntityAnyItemOfMonthly32(domain.getAnyItemOfMonthly32());
		entity.toEntityAnyItemOfMonthly33(domain.getAnyItemOfMonthly33());
		entity.toEntityAnyItemOfMonthly34(domain.getAnyItemOfMonthly34());
		entity.toEntityAnyItemOfMonthly35(domain.getAnyItemOfMonthly35());
		entity.toEntityAnyItemOfMonthly36(domain.getAnyItemOfMonthly36());
		entity.toEntityAnyItemOfMonthly37(domain.getAnyItemOfMonthly37());
		entity.toEntityAnyItemOfMonthly38(domain.getAnyItemOfMonthly38());
		entity.toEntityAnyItemOfMonthly39(domain.getAnyItemOfMonthly39());
		entity.toEntityAnyItemOfMonthly40(domain.getAnyItemOfMonthly40());
		entity.toEntityAnyItemOfMonthly41(domain.getAnyItemOfMonthly41());
		entity.toEntityAnyItemOfMonthly42(domain.getAnyItemOfMonthly42());
		entity.toEntityAnyItemOfMonthly43(domain.getAnyItemOfMonthly43());
		entity.toEntityAnyItemOfMonthly44(domain.getAnyItemOfMonthly44());
		entity.toEntityAnyItemOfMonthly45(domain.getAnyItemOfMonthly45());
		entity.toEntityAnyItemOfMonthly46(domain.getAnyItemOfMonthly46());
		entity.toEntityAnyItemOfMonthly47(domain.getAnyItemOfMonthly47());
		entity.toEntityAnyItemOfMonthly48(domain.getAnyItemOfMonthly48());
		entity.toEntityAnyItemOfMonthly49(domain.getAnyItemOfMonthly49());
		entity.toEntityAnyItemOfMonthly50(domain.getAnyItemOfMonthly50());
		entity.toEntityAnyItemOfMonthly51(domain.getAnyItemOfMonthly51());
		entity.toEntityAnyItemOfMonthly52(domain.getAnyItemOfMonthly52());
		entity.toEntityAnyItemOfMonthly53(domain.getAnyItemOfMonthly53());
		entity.toEntityAnyItemOfMonthly54(domain.getAnyItemOfMonthly54());
		entity.toEntityAnyItemOfMonthly55(domain.getAnyItemOfMonthly55());
		entity.toEntityAnyItemOfMonthly56(domain.getAnyItemOfMonthly56());
		entity.toEntityAnyItemOfMonthly57(domain.getAnyItemOfMonthly57());
		entity.toEntityAnyItemOfMonthly58(domain.getAnyItemOfMonthly58());
		entity.toEntityAnyItemOfMonthly59(domain.getAnyItemOfMonthly59());
		entity.toEntityAnyItemOfMonthly60(domain.getAnyItemOfMonthly60());
		entity.toEntityAnyItemOfMonthly61(domain.getAnyItemOfMonthly61());
		entity.toEntityAnyItemOfMonthly62(domain.getAnyItemOfMonthly62());
		entity.toEntityAnyItemOfMonthly63(domain.getAnyItemOfMonthly63());
		entity.toEntityAnyItemOfMonthly64(domain.getAnyItemOfMonthly64());
		entity.toEntityAnyItemOfMonthly65(domain.getAnyItemOfMonthly65());
		entity.toEntityAnyItemOfMonthly66(domain.getAnyItemOfMonthly66());
		entity.toEntityAnyItemOfMonthly67(domain.getAnyItemOfMonthly67());
		entity.toEntityAnyItemOfMonthly68(domain.getAnyItemOfMonthly68());
		entity.toEntityAnyItemOfMonthly69(domain.getAnyItemOfMonthly69());
		entity.toEntityAnyItemOfMonthly70(domain.getAnyItemOfMonthly70());
		entity.toEntityAnyItemOfMonthly71(domain.getAnyItemOfMonthly71());
		entity.toEntityAnyItemOfMonthly72(domain.getAnyItemOfMonthly72());
		entity.toEntityAnyItemOfMonthly73(domain.getAnyItemOfMonthly73());
		entity.toEntityAnyItemOfMonthly74(domain.getAnyItemOfMonthly74());
		entity.toEntityAnyItemOfMonthly75(domain.getAnyItemOfMonthly75());
		entity.toEntityAnyItemOfMonthly76(domain.getAnyItemOfMonthly76());
		entity.toEntityAnyItemOfMonthly77(domain.getAnyItemOfMonthly77());
		entity.toEntityAnyItemOfMonthly78(domain.getAnyItemOfMonthly78());
		entity.toEntityAnyItemOfMonthly79(domain.getAnyItemOfMonthly79());
		entity.toEntityAnyItemOfMonthly80(domain.getAnyItemOfMonthly80());
		entity.toEntityAnyItemOfMonthly81(domain.getAnyItemOfMonthly81());
		entity.toEntityAnyItemOfMonthly82(domain.getAnyItemOfMonthly82());
		entity.toEntityAnyItemOfMonthly83(domain.getAnyItemOfMonthly83());
		entity.toEntityAnyItemOfMonthly84(domain.getAnyItemOfMonthly84());
		entity.toEntityAnyItemOfMonthly85(domain.getAnyItemOfMonthly85());
		entity.toEntityAnyItemOfMonthly86(domain.getAnyItemOfMonthly86());
		entity.toEntityAnyItemOfMonthly87(domain.getAnyItemOfMonthly87());
		entity.toEntityAnyItemOfMonthly88(domain.getAnyItemOfMonthly88());
		entity.toEntityAnyItemOfMonthly89(domain.getAnyItemOfMonthly89());
		entity.toEntityAnyItemOfMonthly90(domain.getAnyItemOfMonthly90());
		entity.toEntityAnyItemOfMonthly91(domain.getAnyItemOfMonthly91());
		entity.toEntityAnyItemOfMonthly92(domain.getAnyItemOfMonthly92());
		entity.toEntityAnyItemOfMonthly93(domain.getAnyItemOfMonthly93());
		entity.toEntityAnyItemOfMonthly94(domain.getAnyItemOfMonthly94());
		entity.toEntityAnyItemOfMonthly95(domain.getAnyItemOfMonthly95());
		entity.toEntityAnyItemOfMonthly96(domain.getAnyItemOfMonthly96());
		entity.toEntityAnyItemOfMonthly97(domain.getAnyItemOfMonthly97());
		entity.toEntityAnyItemOfMonthly98(domain.getAnyItemOfMonthly98());
		entity.toEntityAnyItemOfMonthly99(domain.getAnyItemOfMonthly99());
		entity.toEntityAnyItemOfMonthly100(domain.getAnyItemOfMonthly100());
		entity.toEntityAnyItemOfMonthly101(domain.getAnyItemOfMonthly101());
		entity.toEntityAnyItemOfMonthly102(domain.getAnyItemOfMonthly102());
		entity.toEntityAnyItemOfMonthly103(domain.getAnyItemOfMonthly103());
		entity.toEntityAnyItemOfMonthly104(domain.getAnyItemOfMonthly104());
		entity.toEntityAnyItemOfMonthly105(domain.getAnyItemOfMonthly105());
		entity.toEntityAnyItemOfMonthly106(domain.getAnyItemOfMonthly106());
		entity.toEntityAnyItemOfMonthly107(domain.getAnyItemOfMonthly107());
		entity.toEntityAnyItemOfMonthly108(domain.getAnyItemOfMonthly108());
		entity.toEntityAnyItemOfMonthly109(domain.getAnyItemOfMonthly109());
		entity.toEntityAnyItemOfMonthly110(domain.getAnyItemOfMonthly110());
		entity.toEntityAnyItemOfMonthly111(domain.getAnyItemOfMonthly111());
		entity.toEntityAnyItemOfMonthly112(domain.getAnyItemOfMonthly112());
		entity.toEntityAnyItemOfMonthly113(domain.getAnyItemOfMonthly113());
		entity.toEntityAnyItemOfMonthly114(domain.getAnyItemOfMonthly114());
		entity.toEntityAnyItemOfMonthly115(domain.getAnyItemOfMonthly115());
		entity.toEntityAnyItemOfMonthly116(domain.getAnyItemOfMonthly116());
		entity.toEntityAnyItemOfMonthly117(domain.getAnyItemOfMonthly117());
		entity.toEntityAnyItemOfMonthly118(domain.getAnyItemOfMonthly118());
		entity.toEntityAnyItemOfMonthly119(domain.getAnyItemOfMonthly119());
		entity.toEntityAnyItemOfMonthly120(domain.getAnyItemOfMonthly120());
		entity.toEntityAnyItemOfMonthly121(domain.getAnyItemOfMonthly121());
		entity.toEntityAnyItemOfMonthly122(domain.getAnyItemOfMonthly122());
		entity.toEntityAnyItemOfMonthly123(domain.getAnyItemOfMonthly123());
		entity.toEntityAnyItemOfMonthly124(domain.getAnyItemOfMonthly124());
		entity.toEntityAnyItemOfMonthly125(domain.getAnyItemOfMonthly125());
		entity.toEntityAnyItemOfMonthly126(domain.getAnyItemOfMonthly126());
		entity.toEntityAnyItemOfMonthly127(domain.getAnyItemOfMonthly127());
		entity.toEntityAnyItemOfMonthly128(domain.getAnyItemOfMonthly128());
		entity.toEntityAnyItemOfMonthly129(domain.getAnyItemOfMonthly129());
		entity.toEntityAnyItemOfMonthly130(domain.getAnyItemOfMonthly130());
		entity.toEntityAnyItemOfMonthly131(domain.getAnyItemOfMonthly131());
		entity.toEntityAnyItemOfMonthly132(domain.getAnyItemOfMonthly132());
		entity.toEntityAnyItemOfMonthly133(domain.getAnyItemOfMonthly133());
		entity.toEntityAnyItemOfMonthly134(domain.getAnyItemOfMonthly134());
		entity.toEntityAnyItemOfMonthly135(domain.getAnyItemOfMonthly135());
		entity.toEntityAnyItemOfMonthly136(domain.getAnyItemOfMonthly136());
		entity.toEntityAnyItemOfMonthly137(domain.getAnyItemOfMonthly137());
		entity.toEntityAnyItemOfMonthly138(domain.getAnyItemOfMonthly138());
		entity.toEntityAnyItemOfMonthly139(domain.getAnyItemOfMonthly139());
		entity.toEntityAnyItemOfMonthly140(domain.getAnyItemOfMonthly140());
		entity.toEntityAnyItemOfMonthly141(domain.getAnyItemOfMonthly141());
		entity.toEntityAnyItemOfMonthly142(domain.getAnyItemOfMonthly142());
		entity.toEntityAnyItemOfMonthly143(domain.getAnyItemOfMonthly143());
		entity.toEntityAnyItemOfMonthly144(domain.getAnyItemOfMonthly144());
		entity.toEntityAnyItemOfMonthly145(domain.getAnyItemOfMonthly145());
		entity.toEntityAnyItemOfMonthly146(domain.getAnyItemOfMonthly146());
		entity.toEntityAnyItemOfMonthly147(domain.getAnyItemOfMonthly147());
		entity.toEntityAnyItemOfMonthly148(domain.getAnyItemOfMonthly148());
		entity.toEntityAnyItemOfMonthly149(domain.getAnyItemOfMonthly149());
		entity.toEntityAnyItemOfMonthly150(domain.getAnyItemOfMonthly150());
		entity.toEntityAnyItemOfMonthly151(domain.getAnyItemOfMonthly151());
		entity.toEntityAnyItemOfMonthly152(domain.getAnyItemOfMonthly152());
		entity.toEntityAnyItemOfMonthly153(domain.getAnyItemOfMonthly153());
		entity.toEntityAnyItemOfMonthly154(domain.getAnyItemOfMonthly154());
		entity.toEntityAnyItemOfMonthly155(domain.getAnyItemOfMonthly155());
		entity.toEntityAnyItemOfMonthly156(domain.getAnyItemOfMonthly156());
		entity.toEntityAnyItemOfMonthly157(domain.getAnyItemOfMonthly157());
		entity.toEntityAnyItemOfMonthly158(domain.getAnyItemOfMonthly158());
		entity.toEntityAnyItemOfMonthly159(domain.getAnyItemOfMonthly159());
		entity.toEntityAnyItemOfMonthly160(domain.getAnyItemOfMonthly160());
		entity.toEntityAnyItemOfMonthly161(domain.getAnyItemOfMonthly161());
		entity.toEntityAnyItemOfMonthly162(domain.getAnyItemOfMonthly162());
		entity.toEntityAnyItemOfMonthly163(domain.getAnyItemOfMonthly163());
		entity.toEntityAnyItemOfMonthly164(domain.getAnyItemOfMonthly164());
		entity.toEntityAnyItemOfMonthly165(domain.getAnyItemOfMonthly165());
		entity.toEntityAnyItemOfMonthly166(domain.getAnyItemOfMonthly166());
		entity.toEntityAnyItemOfMonthly167(domain.getAnyItemOfMonthly167());
		entity.toEntityAnyItemOfMonthly168(domain.getAnyItemOfMonthly168());
		entity.toEntityAnyItemOfMonthly169(domain.getAnyItemOfMonthly169());
		entity.toEntityAnyItemOfMonthly170(domain.getAnyItemOfMonthly170());
		entity.toEntityAnyItemOfMonthly171(domain.getAnyItemOfMonthly171());
		entity.toEntityAnyItemOfMonthly172(domain.getAnyItemOfMonthly172());
		entity.toEntityAnyItemOfMonthly173(domain.getAnyItemOfMonthly173());
		entity.toEntityAnyItemOfMonthly174(domain.getAnyItemOfMonthly174());
		entity.toEntityAnyItemOfMonthly175(domain.getAnyItemOfMonthly175());
		entity.toEntityAnyItemOfMonthly176(domain.getAnyItemOfMonthly176());
		entity.toEntityAnyItemOfMonthly177(domain.getAnyItemOfMonthly177());
		entity.toEntityAnyItemOfMonthly178(domain.getAnyItemOfMonthly178());
		entity.toEntityAnyItemOfMonthly179(domain.getAnyItemOfMonthly179());
		entity.toEntityAnyItemOfMonthly180(domain.getAnyItemOfMonthly180());
		entity.toEntityAnyItemOfMonthly181(domain.getAnyItemOfMonthly181());
		entity.toEntityAnyItemOfMonthly182(domain.getAnyItemOfMonthly182());
		entity.toEntityAnyItemOfMonthly183(domain.getAnyItemOfMonthly183());
		entity.toEntityAnyItemOfMonthly184(domain.getAnyItemOfMonthly184());
		entity.toEntityAnyItemOfMonthly185(domain.getAnyItemOfMonthly185());
		entity.toEntityAnyItemOfMonthly186(domain.getAnyItemOfMonthly186());
		entity.toEntityAnyItemOfMonthly187(domain.getAnyItemOfMonthly187());
		entity.toEntityAnyItemOfMonthly188(domain.getAnyItemOfMonthly188());
		entity.toEntityAnyItemOfMonthly189(domain.getAnyItemOfMonthly189());
		entity.toEntityAnyItemOfMonthly190(domain.getAnyItemOfMonthly190());
		entity.toEntityAnyItemOfMonthly191(domain.getAnyItemOfMonthly191());
		entity.toEntityAnyItemOfMonthly192(domain.getAnyItemOfMonthly192());
		entity.toEntityAnyItemOfMonthly193(domain.getAnyItemOfMonthly193());
		entity.toEntityAnyItemOfMonthly194(domain.getAnyItemOfMonthly194());
		entity.toEntityAnyItemOfMonthly195(domain.getAnyItemOfMonthly195());
		entity.toEntityAnyItemOfMonthly196(domain.getAnyItemOfMonthly196());
		entity.toEntityAnyItemOfMonthly197(domain.getAnyItemOfMonthly197());
		entity.toEntityAnyItemOfMonthly198(domain.getAnyItemOfMonthly198());
		entity.toEntityAnyItemOfMonthly199(domain.getAnyItemOfMonthly199());
		entity.toEntityAnyItemOfMonthly200(domain.getAnyItemOfMonthly200());
	}





}
