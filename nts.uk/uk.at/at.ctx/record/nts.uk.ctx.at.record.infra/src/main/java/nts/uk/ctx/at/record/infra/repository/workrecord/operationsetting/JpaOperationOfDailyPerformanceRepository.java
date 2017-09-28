/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DisplayRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FunctionalRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.OperationOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.OperationOfDailyPerformanceRepoInterface;
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
		implements OperationOfDailyPerformanceRepoInterface {

	@Override
	public OperationOfDailyPerformance findOperationOfDailyPerformance(CompanyId companyId) {
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
					EnumAdaptor.valueOf(krcstDailyRecOpe.settingUnit.intValue(), SettingUnit.class),
					krcstDailyRecOpe.comment);
		} else {
			return new OperationOfDailyPerformance(companyId, functionalRestriction, displayRestriction, null, null);
		}

	}

	@Override
	public void registerOperationOfDailyPerformance(OperationOfDailyPerformance domain) {
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
				updateKrcstDailyRecOpeDisp(entity, domain.getDisplayRestriction());
				this.commandProxy().update(entity);
			} else {
				// insert
				KrcstDailyRecOpeDisp entity = new KrcstDailyRecOpeDisp();
				updateKrcstDailyRecOpeDisp(entity, domain.getDisplayRestriction());
				this.commandProxy().insert(entity);
			}
		}

		if (domain.getFunctionalRestriction() != null) {
			if (krcstDailyRecOpeFunOpt.isPresent()) {
				// update
				KrcstDailyRecOpeFun entity = krcstDailyRecOpeFunOpt.get();
				updateKrcstDailyRecOpeFun(entity, domain.getFunctionalRestriction());
				this.commandProxy().update(entity);
			} else {
				// insert
				KrcstDailyRecOpeFun entity = new KrcstDailyRecOpeFun();
				updateKrcstDailyRecOpeFun(entity, domain.getFunctionalRestriction());
				this.commandProxy().insert(entity);
			}
		}

	}

	private void updateKrcstDailyRecOpeEntity(KrcstDailyRecOpe entity, OperationOfDailyPerformance domain) {
		entity.settingUnit = new BigDecimal(domain.getSettingUnit().value);
		entity.comment = domain.getComment().toString();
	}

	private void updateKrcstDailyRecOpeDisp(KrcstDailyRecOpeDisp entity, DisplayRestriction domain) {
		entity.savingYearHdDispResCheck = booleanToBigDecimal(domain.getSavingYear().isRemainingNumberCheck());
		entity.savingYearHdDispResAtr = booleanToBigDecimal(domain.getSavingYear().isDisplayAtr());

		entity.comHdDispResCheck = booleanToBigDecimal(domain.getCompensatory().isRemainingNumberCheck());
		entity.comHdDispResAtr = booleanToBigDecimal(domain.getCompensatory().isDisplayAtr());

		entity.subHdDispResCheck = booleanToBigDecimal(domain.getSubstitution().isRemainingNumberCheck());
		entity.subHdDispResAtr = booleanToBigDecimal(domain.getSubstitution().isDisplayAtr());

		entity.yearHdDispResCheck = booleanToBigDecimal(domain.getYear().isRemainingNumberCheck());
		entity.yearHdDispResAtr = booleanToBigDecimal(domain.getYear().isDisplayAtr());
	}

	private void updateKrcstDailyRecOpeFun(KrcstDailyRecOpeFun entity, FunctionalRestriction domain) {
		entity.registerTotalTimeCheerAtr = booleanToBigDecimal(domain.getRegisteredTotalTimeCheer());
		entity.completedDisOneMonthAtr = booleanToBigDecimal(domain.getCompleteDisplayOneMonth());
		entity.disConfirmMessageAtr = booleanToBigDecimal(domain.getDisplayConfirmMessage());
		entity.useInitialValueSetAtr = booleanToBigDecimal(domain.getUseInitialValueSet());
		entity.useWorkDetailAtr = booleanToBigDecimal(domain.getUseWorkDetail());
		entity.registerActualExceedAtr = booleanToBigDecimal(domain.getRegisterActualExceed());
		entity.startAppScreenAtr = booleanToBigDecimal(domain.getStartAppScreen());
		entity.confirmSubmitAppAtr = booleanToBigDecimal(domain.getConfirmSubmitApp());
		entity.confirmByYourselfAtr = booleanToBigDecimal(domain.getUseConfirmByYourself());
		entity.initialValueSettingAtr = booleanToBigDecimal(domain.getUseInitialValueSet());
		entity.confirmBySupervisorAtr = booleanToBigDecimal(domain.getUseSupervisorConfirm());
		entity.yourselfConfirmWhenError = new BigDecimal(domain.getYourselfConfirmError().value);
		entity.supervisorConfirmWhenError = new BigDecimal(domain.getSupervisorConfirmError().value);
	}

	private BigDecimal booleanToBigDecimal(boolean value) {
		if (value) {
			return new BigDecimal(1);
		}
		return new BigDecimal(0);

	}
}
