package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.function.control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.function.control.FunctionControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.function.control.ScheFuncCond;
import nts.uk.ctx.at.schedule.dom.schedule.setting.function.control.ScheFuncControl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.function.control.KsfstScheFuncCondition;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.function.control.KsfstScheFuncConditionPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.function.control.KsfstScheFuncControl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.function.control.KsfstScheFuncControlPK;

@Stateless
public class JpaScheFuncControlRepository extends JpaRepository implements FunctionControlRepository {	
	/**
	 * Get Schedule Function Control data
	 */
	@Override
	public ScheFuncControl getScheFuncControl(String companyId) {
		KsfstScheFuncControlPK primaryKey = new KsfstScheFuncControlPK(companyId);

		return this.queryProxy().find(primaryKey, KsfstScheFuncControl.class).map(x -> toDomain(x)).get();
	}

	/**
	 * Update Schedule Function Control data
	 */
	@Override
	public void updateScheFuncControl(ScheFuncControl scheFuncControl) {
		KsfstScheFuncControlPK primaryKey = new KsfstScheFuncControlPK(scheFuncControl.getCompanyId());		
		KsfstScheFuncControl ksfstScheFuncControl = this.queryProxy().find(primaryKey, KsfstScheFuncControl.class).get();
		
		ksfstScheFuncControl.alarmCheckUseCls = scheFuncControl.getAlarmCheckUseCls().value;
		ksfstScheFuncControl.confirmedCls = scheFuncControl.getConfirmedCls().value;
		ksfstScheFuncControl.publicCls = scheFuncControl.getPublicCls().value;
		ksfstScheFuncControl.outputCls = scheFuncControl.getOutputCls().value;
		ksfstScheFuncControl.workDormitionCls = scheFuncControl.getWorkDormitionCls().value;
		ksfstScheFuncControl.teamCls = scheFuncControl.getTeamCls().value;
		ksfstScheFuncControl.rankCls = scheFuncControl.getRankCls().value;
		ksfstScheFuncControl.startDateInWeek = scheFuncControl.getStartDateInWeek().value;
		ksfstScheFuncControl.shortNameDisp = scheFuncControl.getShortNameDisp().value;
		ksfstScheFuncControl.timeDisp = scheFuncControl.getTimeDisp().value;
		ksfstScheFuncControl.symbolDisp = scheFuncControl.getSymbolDisp().value;
		ksfstScheFuncControl.twentyEightDaysCycle = scheFuncControl.getTwentyEightDaysCycle().value;
		ksfstScheFuncControl.lastDayDisp = scheFuncControl.getLastDayDisp().value;
		ksfstScheFuncControl.individualDisp = scheFuncControl.getIndividualDisp().value;
		ksfstScheFuncControl.dispByDate = scheFuncControl.getDispByDate().value;
		ksfstScheFuncControl.indicationByShift = scheFuncControl.getIndicationByShift().value;
		ksfstScheFuncControl.regularWork = scheFuncControl.getRegularWork().value;
		ksfstScheFuncControl.fluidWork = scheFuncControl.getFluidWork().value;
		ksfstScheFuncControl.workingForFlex = scheFuncControl.getWorkingForFlex().value;
		ksfstScheFuncControl.overtimeWork = scheFuncControl.getOvertimeWork().value;
		ksfstScheFuncControl.normalCreation = scheFuncControl.getNormalCreation().value;
		ksfstScheFuncControl.simulationCls = scheFuncControl.getSimulationCls().value;
		ksfstScheFuncControl.captureUsageCls = scheFuncControl.getCaptureUsageCls().value;
		ksfstScheFuncControl.completedFuncCls = scheFuncControl.getCompletedFuncCls().value;
		ksfstScheFuncControl.howToComplete = scheFuncControl.getHowToComplete().value;
		ksfstScheFuncControl.alarmCheckCls = scheFuncControl.getAlarmCheckCls().value;
		ksfstScheFuncControl.executionMethod = scheFuncControl.getExecutionMethod().value;
		ksfstScheFuncControl.handleRepairAtr = scheFuncControl.getHandleRepairAtr().value;
		ksfstScheFuncControl.confirm = scheFuncControl.getConfirm().value;
		ksfstScheFuncControl.searchMethod = scheFuncControl.getSearchMethod().value;
		ksfstScheFuncControl.searchMethodDispCls = scheFuncControl.getSearchMethodDispCls().value;
		
		List<KsfstScheFuncCondition> conditions = scheFuncControl.getScheFuncCond().stream().map(x -> {
			KsfstScheFuncConditionPK key = new KsfstScheFuncConditionPK(scheFuncControl.getCompanyId(), x.getConditionNo());
			return new KsfstScheFuncCondition(key);
		}).collect(Collectors.toList());
		
		ksfstScheFuncControl.scheFuncConditions = conditions;
		
		this.commandProxy().update(ksfstScheFuncControl);
	}
	
	/**
	 * To domain
	 * @param entity
	 * @return
	 */
	private static ScheFuncControl toDomain(KsfstScheFuncControl entity) {
		if (entity == null) {
			return null;
		}

		List<ScheFuncCond> lst = new ArrayList<>();
		for (KsfstScheFuncCondition obj : entity.scheFuncConditions) {
			lst.add(toDomainScheFuncCond(obj));
		}
		
		ScheFuncControl domain = ScheFuncControl.createFromJavaType(entity.ksfstScheFuncControlPK.companyId,
				entity.alarmCheckUseCls, entity.confirmedCls, entity.publicCls,
				entity.outputCls, entity.workDormitionCls, entity.teamCls, entity.rankCls, entity.startDateInWeek,
				entity.shortNameDisp, entity.timeDisp, entity.symbolDisp, entity.twentyEightDaysCycle, entity.lastDayDisp,
				entity.individualDisp, entity.dispByDate, entity.indicationByShift, entity.regularWork, entity.fluidWork,
				entity.workingForFlex, entity.overtimeWork, entity.normalCreation, entity.simulationCls,
				entity.captureUsageCls, entity.completedFuncCls, entity.howToComplete, entity.alarmCheckCls,
				entity.executionMethod, entity.handleRepairAtr, entity.confirm, entity.searchMethod,
				entity.searchMethodDispCls, lst);
		
		return domain;
	}
	
	/**
	 * To domain for conditions
	 * @param entity
	 * @return
	 */
	private static ScheFuncCond toDomainScheFuncCond(KsfstScheFuncCondition entity) {
		ScheFuncCond domain = ScheFuncCond.createFromJavaType(entity.ksfstScheFuncConditionPK.companyId,
				entity.ksfstScheFuncConditionPK.conditionNo);
		
		return domain;
	}
}
