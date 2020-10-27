package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.functioncontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.FunctionControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncCond;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncControl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.functioncontrol.KscmtFunctionConpCondition;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.functioncontrol.KscmtFunctionConpConditionPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.functioncontrol.KscstScheFuncControl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.functioncontrol.KscstScheFuncControlPK;

@Stateless
public class JpaScheFuncControlRepository extends JpaRepository implements FunctionControlRepository {	
	/**
	 * Get Schedule Function Control data
	 */
	@Override
	public Optional<ScheFuncControl> getScheFuncControl(String companyId) {
		KscstScheFuncControlPK primaryKey = new KscstScheFuncControlPK(companyId);

		return this.queryProxy().find(primaryKey, KscstScheFuncControl.class).map(x -> toDomain(x));
	}
	
	/**
	 * Add Schedule Function Control data
	 */
	@Override
	public void addScheFuncControl(ScheFuncControl scheFuncControl) {
		this.commandProxy().insert(convertToDbType(scheFuncControl));
	}

	/**
	 * Convert to DB type
	 * 
	 * @param scheFuncControl
	 * @return
	 */
	private KscstScheFuncControl convertToDbType(ScheFuncControl scheFuncControl) {
		KscstScheFuncControl kscstScheFuncControl = new KscstScheFuncControl();
		KscstScheFuncControlPK primaryKey = new KscstScheFuncControlPK(scheFuncControl.getCompanyId());
		
		List<KscmtFunctionConpCondition> conditions = scheFuncControl.getScheFuncCond().stream().map(x -> {
			KscmtFunctionConpConditionPK key = new KscmtFunctionConpConditionPK(scheFuncControl.getCompanyId(), x.getConditionNo());
			return new KscmtFunctionConpCondition(key);
		}).collect(Collectors.toList());
		
		kscstScheFuncControl.kscstScheFuncControlPK = primaryKey;
		kscstScheFuncControl.alarmCheckUseCls = scheFuncControl.getAlarmCheckUseCls().value;
		kscstScheFuncControl.confirmedCls = scheFuncControl.getConfirmedCls().value;
		kscstScheFuncControl.publicCls = scheFuncControl.getPublicCls().value;
		kscstScheFuncControl.outputCls = scheFuncControl.getOutputCls().value;
		kscstScheFuncControl.workDormitionCls = scheFuncControl.getWorkDormitionCls().value;
		kscstScheFuncControl.teamCls = scheFuncControl.getTeamCls().value;
		kscstScheFuncControl.rankCls = scheFuncControl.getRankCls().value;
		kscstScheFuncControl.startDateInWeek = scheFuncControl.getStartDateInWeek().value;
		kscstScheFuncControl.shortNameDisp = scheFuncControl.getShortNameDisp().value;
		kscstScheFuncControl.timeDisp = scheFuncControl.getTimeDisp().value;
		kscstScheFuncControl.symbolDisp = scheFuncControl.getSymbolDisp().value;
		kscstScheFuncControl.twentyEightDaysCycle = scheFuncControl.getTwentyEightDaysCycle().value;
		kscstScheFuncControl.lastDayDisp = scheFuncControl.getLastDayDisp().value;
		kscstScheFuncControl.individualDisp = scheFuncControl.getIndividualDisp().value;
		kscstScheFuncControl.dispByDate = scheFuncControl.getDispByDate().value;
		kscstScheFuncControl.indicationByShift = scheFuncControl.getIndicationByShift().value;
		kscstScheFuncControl.regularWork = scheFuncControl.getRegularWork().value;
		kscstScheFuncControl.fluidWork = scheFuncControl.getFluidWork().value;
		kscstScheFuncControl.workingForFlex = scheFuncControl.getWorkingForFlex().value;
		kscstScheFuncControl.overtimeWork = scheFuncControl.getOvertimeWork().value;
		kscstScheFuncControl.normalCreation = scheFuncControl.getNormalCreation().value;
		kscstScheFuncControl.simulationCls = scheFuncControl.getSimulationCls().value;
		kscstScheFuncControl.captureUsageCls = scheFuncControl.getCaptureUsageCls().value;
		kscstScheFuncControl.completedFuncCls = scheFuncControl.getCompletedFuncCls().value;
		kscstScheFuncControl.howToComplete = scheFuncControl.getHowToComplete().value;
		kscstScheFuncControl.alarmCheckCls = scheFuncControl.getAlarmCheckCls().value;
		kscstScheFuncControl.executionMethod = scheFuncControl.getExecutionMethod().value;
		kscstScheFuncControl.handleRepairAtr = scheFuncControl.getHandleRepairAtr().value;
		kscstScheFuncControl.confirm = scheFuncControl.getConfirm().value;
		kscstScheFuncControl.searchMethod = scheFuncControl.getSearchMethod().value;
		kscstScheFuncControl.searchMethodDispCls = scheFuncControl.getSearchMethodDispCls().value;		
		kscstScheFuncControl.scheFuncConditions = conditions;
		
		return kscstScheFuncControl;
	}

	/**
	 * Update Schedule Function Control data
	 */
	@Override
	public void updateScheFuncControl(ScheFuncControl scheFuncControl) {
		KscstScheFuncControlPK primaryKey = new KscstScheFuncControlPK(scheFuncControl.getCompanyId());		
		KscstScheFuncControl kscstScheFuncControl = this.queryProxy().find(primaryKey, KscstScheFuncControl.class).get();
		
		kscstScheFuncControl.alarmCheckUseCls = scheFuncControl.getAlarmCheckUseCls().value;
		kscstScheFuncControl.confirmedCls = scheFuncControl.getConfirmedCls().value;
		kscstScheFuncControl.publicCls = scheFuncControl.getPublicCls().value;
		kscstScheFuncControl.outputCls = scheFuncControl.getOutputCls().value;
		kscstScheFuncControl.workDormitionCls = scheFuncControl.getWorkDormitionCls().value;
		kscstScheFuncControl.teamCls = scheFuncControl.getTeamCls().value;
		kscstScheFuncControl.rankCls = scheFuncControl.getRankCls().value;
		kscstScheFuncControl.startDateInWeek = scheFuncControl.getStartDateInWeek().value;
		kscstScheFuncControl.shortNameDisp = scheFuncControl.getShortNameDisp().value;
		kscstScheFuncControl.timeDisp = scheFuncControl.getTimeDisp().value;
		kscstScheFuncControl.symbolDisp = scheFuncControl.getSymbolDisp().value;
		kscstScheFuncControl.twentyEightDaysCycle = scheFuncControl.getTwentyEightDaysCycle().value;
		kscstScheFuncControl.lastDayDisp = scheFuncControl.getLastDayDisp().value;
		kscstScheFuncControl.individualDisp = scheFuncControl.getIndividualDisp().value;
		kscstScheFuncControl.dispByDate = scheFuncControl.getDispByDate().value;
		kscstScheFuncControl.indicationByShift = scheFuncControl.getIndicationByShift().value;
		kscstScheFuncControl.regularWork = scheFuncControl.getRegularWork().value;
		kscstScheFuncControl.fluidWork = scheFuncControl.getFluidWork().value;
		kscstScheFuncControl.workingForFlex = scheFuncControl.getWorkingForFlex().value;
		kscstScheFuncControl.overtimeWork = scheFuncControl.getOvertimeWork().value;
		kscstScheFuncControl.normalCreation = scheFuncControl.getNormalCreation().value;
		kscstScheFuncControl.simulationCls = scheFuncControl.getSimulationCls().value;
		kscstScheFuncControl.captureUsageCls = scheFuncControl.getCaptureUsageCls().value;
		kscstScheFuncControl.completedFuncCls = scheFuncControl.getCompletedFuncCls().value;
		kscstScheFuncControl.howToComplete = scheFuncControl.getHowToComplete().value;
		kscstScheFuncControl.alarmCheckCls = scheFuncControl.getAlarmCheckCls().value;
		kscstScheFuncControl.executionMethod = scheFuncControl.getExecutionMethod().value;
		kscstScheFuncControl.handleRepairAtr = scheFuncControl.getHandleRepairAtr().value;
		kscstScheFuncControl.confirm = scheFuncControl.getConfirm().value;
		kscstScheFuncControl.searchMethod = scheFuncControl.getSearchMethod().value;
		kscstScheFuncControl.searchMethodDispCls = scheFuncControl.getSearchMethodDispCls().value;
		
		List<KscmtFunctionConpCondition> conditions = scheFuncControl.getScheFuncCond().stream().map(x -> {
			KscmtFunctionConpConditionPK key = new KscmtFunctionConpConditionPK(scheFuncControl.getCompanyId(), x.getConditionNo());
			return new KscmtFunctionConpCondition(key);
		}).collect(Collectors.toList());
		
		kscstScheFuncControl.scheFuncConditions = conditions;
		
		this.commandProxy().update(kscstScheFuncControl);
	}
	
	/**
	 * To domain
	 * @param entity
	 * @return
	 */
	private static ScheFuncControl toDomain(KscstScheFuncControl entity) {
		if (entity == null) {
			return null;
		}

		List<ScheFuncCond> lst = new ArrayList<>();
		for (KscmtFunctionConpCondition obj : entity.scheFuncConditions) {
			lst.add(toDomainScheFuncCond(obj));
		}
		
		ScheFuncControl domain = ScheFuncControl.createFromJavaType(entity.kscstScheFuncControlPK.companyId,
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
	private static ScheFuncCond toDomainScheFuncCond(KscmtFunctionConpCondition entity) {
		ScheFuncCond domain = ScheFuncCond.createFromJavaType(entity.kscmtFunctionConpConditionPK.companyId,
				entity.kscmtFunctionConpConditionPK.conditionNo);
		
		return domain;
	}
}
