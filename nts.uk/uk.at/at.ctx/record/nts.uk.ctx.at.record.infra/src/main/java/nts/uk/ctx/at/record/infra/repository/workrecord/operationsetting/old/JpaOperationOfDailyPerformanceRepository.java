/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DisplayRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FunctionalRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.OpOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.OperationOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnit;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcstDailyRecOpe;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcstDailyRecOpeDisp;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcstDailyRecOpeFun;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaOperationOfDailyPerformanceRepository extends JpaRepository
		implements OpOfDailyPerformance {

	@Override
	public OperationOfDailyPerformance find(CompanyId companyId) {
		Optional<KrcstDailyRecOpeDisp> krcstDailyRecOpeDispOpt = this.queryProxy().find(companyId.toString(),
				KrcstDailyRecOpeDisp.class);
		Optional<KrcstDailyRecOpeFun> krcstDailyRecOpeFunOpt = this.queryProxy().find(companyId.toString(),
				KrcstDailyRecOpeFun.class);
		Optional<KrcstDailyRecOpe> krcstDailyRecOpeOpt = this.queryProxy().find(companyId.toString(),
				KrcstDailyRecOpe.class);

		DisplayRestriction displayRestriction = null;
		if (krcstDailyRecOpeDispOpt.isPresent()) {
			KrcstDailyRecOpeDisp dispEnt = krcstDailyRecOpeDispOpt.get();
			displayRestriction = new DisplayRestriction(dispEnt.yearHdDispResAtr, dispEnt.yearHdDispResCheck,
					dispEnt.savingYearHdDispResAtr, dispEnt.savingYearHdDispResCheck, dispEnt.comHdDispResAtr,
					dispEnt.comHdDispResCheck, dispEnt.subHdDispResAtr, dispEnt.subHdDispResCheck);
		}

		FunctionalRestriction functionalRestriction = null;
		if (krcstDailyRecOpeFunOpt.isPresent()) {
			KrcstDailyRecOpeFun funcEnt = krcstDailyRecOpeFunOpt.get();
			functionalRestriction = new FunctionalRestriction(funcEnt.registerTotalTimeCheerAtr,
					funcEnt.completedDisOneMonthAtr, funcEnt.useWorkDetailAtr, funcEnt.registerActualExceedAtr,
					funcEnt.confirmSubmitAppAtr, funcEnt.useInitialValueSetAtr, funcEnt.startAppScreenAtr,
					funcEnt.disConfirmMessageAtr, funcEnt.confirmBySupervisorAtr, funcEnt.supervisorConfirmWhenError,
					funcEnt.confirmByYourselfAtr, funcEnt.yourselfConfirmWhenError);
		}

		if (krcstDailyRecOpeOpt.isPresent()) {
			KrcstDailyRecOpe krcstDailyRecOpe = krcstDailyRecOpeOpt.get();
			return new OperationOfDailyPerformance(companyId, functionalRestriction, displayRestriction,
					EnumAdaptor.valueOf(krcstDailyRecOpe.settingUnit, SettingUnit.class),
					krcstDailyRecOpe.comment);
		} else {
			return new OperationOfDailyPerformance(companyId, functionalRestriction, displayRestriction, null, null);
		}

	}

	@Override
	public void register(OperationOfDailyPerformance domain) {
		Optional<KrcstDailyRecOpe> krcstDailyRecOpeOpt = this.queryProxy().find(domain.getCompanyId().toString(),
				KrcstDailyRecOpe.class);
		Optional<KrcstDailyRecOpeDisp> krcstDailyRecOpeDispOpt = this.queryProxy()
				.find(domain.getCompanyId().toString(), KrcstDailyRecOpeDisp.class);
		Optional<KrcstDailyRecOpeFun> krcstDailyRecOpeFunOpt = this.queryProxy().find(domain.getCompanyId().toString(),
				KrcstDailyRecOpeFun.class);

		if (domain.getSettingUnit() != null && domain.getComment() != null) {
			if (krcstDailyRecOpeOpt.isPresent()) {
				// update
				KrcstDailyRecOpe entity = krcstDailyRecOpeOpt.get();
				updateKrcstDailyRecOpeEntity(entity, domain);
				this.commandProxy().update(entity);
			} else {
				// insert
				KrcstDailyRecOpe entity = new KrcstDailyRecOpe();
				updateKrcstDailyRecOpeEntity(entity, domain);
				this.commandProxy().insert(entity);
			}
		}

		if (domain.getDisplayRestriction() != null) {
			if (krcstDailyRecOpeDispOpt.isPresent()) {
				// update
				KrcstDailyRecOpeDisp entity = krcstDailyRecOpeDispOpt.get();
				updateKrcstDailyRecOpeDisp(entity, domain);
				this.commandProxy().update(entity);
			} else {
				// insert
				KrcstDailyRecOpeDisp entity = new KrcstDailyRecOpeDisp();
				updateKrcstDailyRecOpeDisp(entity, domain);
				this.commandProxy().insert(entity);
			}
		}

		if (domain.getFunctionalRestriction() != null) {
			if (krcstDailyRecOpeFunOpt.isPresent()) {
				// update
				KrcstDailyRecOpeFun entity = krcstDailyRecOpeFunOpt.get();
				updateKrcstDailyRecOpeFun(entity, domain);
				this.commandProxy().update(entity);
			} else {
				// insert
				KrcstDailyRecOpeFun entity = new KrcstDailyRecOpeFun();
				updateKrcstDailyRecOpeFun(entity, domain);
				this.commandProxy().insert(entity);
			}
		}

	}

	private void updateKrcstDailyRecOpeEntity(KrcstDailyRecOpe entity, OperationOfDailyPerformance domain) {
		entity.settingUnit = domain.getSettingUnit().value;
		entity.comment = domain.getComment().toString();
	}

	private void updateKrcstDailyRecOpeDisp(KrcstDailyRecOpeDisp entity, OperationOfDailyPerformance domain) {

		entity.cid = domain.getCompanyId().v();
		DisplayRestriction dispRest = domain.getDisplayRestriction();

		entity.savingYearHdDispResCheck = booleanToBigDecimal(dispRest.getSavingYear().isRemainingNumberCheck());
		entity.savingYearHdDispResAtr = booleanToBigDecimal(dispRest.getSavingYear().isDisplayAtr());

		entity.comHdDispResCheck = booleanToBigDecimal(dispRest.getCompensatory().isRemainingNumberCheck());
		entity.comHdDispResAtr = booleanToBigDecimal(dispRest.getCompensatory().isDisplayAtr());

		entity.subHdDispResCheck = booleanToBigDecimal(dispRest.getSubstitution().isRemainingNumberCheck());
		entity.subHdDispResAtr = booleanToBigDecimal(dispRest.getSubstitution().isDisplayAtr());

		entity.yearHdDispResCheck = booleanToBigDecimal(dispRest.getYear().isRemainingNumberCheck());
		entity.yearHdDispResAtr = booleanToBigDecimal(dispRest.getYear().isDisplayAtr());
	}

	private void updateKrcstDailyRecOpeFun(KrcstDailyRecOpeFun entity, OperationOfDailyPerformance domain) {
		entity.cid = domain.getCompanyId().v();
		FunctionalRestriction funcRest = domain.getFunctionalRestriction();
		entity.registerTotalTimeCheerAtr = booleanToBigDecimal(funcRest.getRegisteredTotalTimeCheer());
		entity.completedDisOneMonthAtr = booleanToBigDecimal(funcRest.getCompleteDisplayOneMonth());
		entity.disConfirmMessageAtr = booleanToBigDecimal(funcRest.getDisplayConfirmMessage());
		entity.useInitialValueSetAtr = booleanToBigDecimal(funcRest.getUseInitialValueSet());
		entity.useWorkDetailAtr = booleanToBigDecimal(funcRest.getUseWorkDetail());
		entity.registerActualExceedAtr = booleanToBigDecimal(funcRest.getRegisterActualExceed());
		entity.startAppScreenAtr = booleanToBigDecimal(funcRest.getStartAppScreen());
		entity.confirmSubmitAppAtr = booleanToBigDecimal(funcRest.getConfirmSubmitApp());
		entity.confirmByYourselfAtr = booleanToBigDecimal(funcRest.getUseConfirmByYourself());
		entity.initialValueSettingAtr = booleanToBigDecimal(funcRest.getUseInitialValueSet());
		entity.confirmBySupervisorAtr = booleanToBigDecimal(funcRest.getUseSupervisorConfirm());
		entity.yourselfConfirmWhenError = funcRest.getYourselfConfirmError().value;
		entity.supervisorConfirmWhenError = funcRest.getSupervisorConfirmError().value;
	}

	private int booleanToBigDecimal(boolean value) {
		if (value) {
			return 1;
		}
		return 0;

	}
}
