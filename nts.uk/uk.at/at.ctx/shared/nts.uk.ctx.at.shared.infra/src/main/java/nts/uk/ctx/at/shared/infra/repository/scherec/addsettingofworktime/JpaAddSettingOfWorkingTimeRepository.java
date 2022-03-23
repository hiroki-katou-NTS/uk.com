package nts.uk.ctx.at.shared.infra.repository.scherec.addsettingofworktime;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfPremiumTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TreatDeductTimeForCalcWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TreatLateEarlyTimeSetUnit;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TreatVacationTimeForCalcPremium;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TreatVacationTimeForCalcWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TreatLateEarlyTime;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddInclude;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddIncludePK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddPremium;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddWorktime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * リポジトリ実装：労働時間の加算設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaAddSettingOfWorkingTimeRepository extends JpaRepository implements AddSettingOfWorkingTimeRepository {

	@Override
	public Optional<AddSettingOfWorkingTime> findByCIDAndLabor(String companyId, int laborSystemAtr) {
		Optional<KsrmtCalcCAddInclude> includeWorktime = this.queryProxy().find(
				new KsrmtCalcCAddIncludePK(companyId, laborSystemAtr, 0), KsrmtCalcCAddInclude.class);
		if (includeWorktime.isPresent()) {
			Optional<KsrmtCalcCAddInclude> includePremium = this.queryProxy().find(
					new KsrmtCalcCAddIncludePK(companyId, laborSystemAtr, 1), KsrmtCalcCAddInclude.class);
			Optional<KsrmtCalcCAddWorktime> worktime = this.queryProxy().find(
					new KsrmtCalcCAddPK(companyId, laborSystemAtr), KsrmtCalcCAddWorktime.class);
			Optional<KsrmtCalcCAddPremium> premium = this.queryProxy().find(
					new KsrmtCalcCAddPK(companyId, laborSystemAtr), KsrmtCalcCAddPremium.class);
			return Optional.of(toDomain(includeWorktime.get(), includePremium, worktime, premium));
		}
		return Optional.empty();
	}

	/**
	 * ドメイン：労働時間の加算設定に変換する
	 * @param includeWorktime 労働時間の加算設定(含める要素を指定)(就業時間用)
	 * @param includePremium 労働時間の加算設定(含める要素を指定)(割増時間用)
	 * @param worktime 労働時間の加算設定(就業時間の加算設定)
	 * @param premium 労働時間の加算設定(割増時間の加算設定)
	 * @return 労働時間の加算設定
	 */
	public static AddSettingOfWorkingTime toDomain(
			KsrmtCalcCAddInclude includeWorktime,
			Optional<KsrmtCalcCAddInclude> includePremium,
			Optional<KsrmtCalcCAddWorktime> worktime,
			Optional<KsrmtCalcCAddPremium> premium){
		
		int useAtr = 0;
		Integer wrkExcessOfFixDefo = null;
		Integer wrkExcessOfFlex = null;
		Integer wrkWithinMonth = null;
		Integer wrkMinusAbsence = null;
		int prmCalcActualOpe = 0;
		Integer prmCalcIncludeCare = null;
		Integer prmIncludeLate = null;
		Integer prmIncludeLateByApp = null;
		Integer prmEnableSetPerWorkHour = null;
		Integer prmCalcIncludeInterval = null;
		Integer prmAddition = null;
		Integer prmExcessOfFixDefo = null;
		Integer prmExcessOfFlex = null;
		
		// 労働時間の加算設定(含める要素を指定)から取得する
		if (includePremium.isPresent()){
			// 割増設定がある時、割増計算方法を設定する
			useAtr = 1;			// 割増計算方法を設定する="する"
			prmCalcActualOpe = includePremium.get().isActualOnly;
			prmCalcIncludeCare = includePremium.get().isCare;
			prmIncludeLate = includePremium.get().isLate;
			prmIncludeLateByApp = includePremium.get().isLateDeleteApp;
			prmEnableSetPerWorkHour = includePremium.get().lateSettingUnit;
			prmCalcIncludeInterval = includePremium.get().isIntervalTime;
			prmAddition = includePremium.get().isVacation;
		}
		else{
			// 割増設定がない時、就業時間の設定を割増時間に写す
			prmCalcActualOpe = includeWorktime.isActualOnly;
			prmCalcIncludeCare = includeWorktime.isCare;
			prmIncludeLate = includeWorktime.isLate;
			prmIncludeLateByApp = includeWorktime.isLateDeleteApp;
			prmEnableSetPerWorkHour = includeWorktime.lateSettingUnit;
			prmCalcIncludeInterval = includeWorktime.isIntervalTime;
			prmAddition = includeWorktime.isVacation;
		}
		// 労働時間の加算設定(就業時間の加算設定)から取得する
		if (worktime.isPresent()){
			wrkExcessOfFixDefo = worktime.get().fixCalcOverPredtime;
			wrkExcessOfFlex = worktime.get().fleCalcShortPredtime;
			wrkWithinMonth = worktime.get().fleCalcInLegal;
			wrkMinusAbsence = worktime.get().fleCalcDeductPredtimeAbsence;
		}
		// 労働時間の加算設定(割増時間の加算設定)から取得する
		if (premium.isPresent()){
			prmExcessOfFixDefo = premium.get().fixCalcOverPredtime;
			prmExcessOfFlex = premium.get().fleCalcOverPredtime;
		}
		// 割増時間の加算設定の属性値を作成する
		Optional<TreatDeductTimeForCalcWorkTime> prmTreatDeduct = Optional.empty();
		Optional<TreatVacationTimeForCalcPremium> prmTreatVacation = Optional.empty();
		if (useAtr == 1){
			prmTreatDeduct = Optional.of(TreatDeductTimeForCalcWorkTime.createFromJavaType(
					prmCalcIncludeCare,
					TreatLateEarlyTimeSetUnit.createFromJavaType(
							prmIncludeLate,
							prmIncludeLateByApp,
							prmEnableSetPerWorkHour),
					prmCalcIncludeInterval));
			prmTreatVacation = Optional.of(new TreatVacationTimeForCalcPremium(
					prmAddition,
					prmExcessOfFixDefo,
					prmExcessOfFlex));
		}
		// 労働時間の加算設定を返す
		return new AddSettingOfWorkingTime(
				AddSettingOfPremiumTime.createFromJavaType(
						prmCalcActualOpe,
						prmTreatDeduct,
						prmTreatVacation),
				AddSettingOfWorkTime.createFromJavaType(
						includeWorktime.isActualOnly,
						Optional.of(TreatDeductTimeForCalcWorkTime.createFromJavaType(
								includeWorktime.isCare,
								TreatLateEarlyTimeSetUnit.createFromJavaType(
										includeWorktime.isLate,
										includeWorktime.isLateDeleteApp,
										includeWorktime.lateSettingUnit),
								includeWorktime.isIntervalTime)),
						Optional.of(new TreatVacationTimeForCalcWorkTime(
								includeWorktime.isVacation,
								wrkExcessOfFixDefo,
								wrkWithinMonth,
								wrkExcessOfFlex,
								wrkMinusAbsence))),
				NotUseAtr.valueOf(useAtr));
	}

	/**
	 * エンティティ：労働時間の加算設定(含める要素を指定)に変換する(就業時間用)
	 * @param companyId 会社ID
	 * @param laborSystemAtr 労働制
	 * @param domain 労働時間の加算設定
	 * @return 労働時間の加算設定(含める要素を指定)
	 */
	public static KsrmtCalcCAddInclude toEntityIncludeWorktime(
			String companyId,
			int laborSystemAtr,
			AddSettingOfWorkingTime domain){
		
		KsrmtCalcCAddInclude entity = new KsrmtCalcCAddInclude();
		KsrmtCalcCAddIncludePK key = new KsrmtCalcCAddIncludePK(companyId, laborSystemAtr, 0);
		entity.pk = key;
		updateEntityIncludeWorktime(entity, domain);
		return entity;
	}

	/**
	 * エンティティ：労働時間の加算設定(含める要素を指定)を更新する(就業時間用)
	 * @param entity 労働時間の加算設定(含める要素を指定)
	 * @param domain 労働時間の加算設定
	 */
	public static void updateEntityIncludeWorktime(
			KsrmtCalcCAddInclude entity,
			AddSettingOfWorkingTime domain){
		
		AddSettingOfWorkTime addSet = domain.getAddSetOfWorkTime();
		entity.isActualOnly = addSet.getCalculateActualOperation().isCalclationByActualTime() ? 0 : 1;
		entity.isVacation = null;
		entity.isCare = null;
		entity.lateSettingUnit = null;
		entity.isLate = null;
		entity.isLateDeleteApp = null;
		entity.isIntervalTime = null;
		if (addSet.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcWorkTime treatVacation = addSet.getTreatVacation().get();
			entity.isVacation = treatVacation.getAddition().value;
		}
		if (addSet.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSet.getTreatDeduct().get();
			entity.isCare = treatDeduct.getCalculateIncludCareTime().value;
			entity.lateSettingUnit = treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() ? 1 : 0;
			TreatLateEarlyTime treatLate = treatDeduct.getTreatLateEarlyTimeSet().getTreatSet();
			entity.isLate = treatLate.isInclude() ? 1 : 0;
			entity.isLateDeleteApp = treatLate.isIncludeByApp() ? 1 : 0;
			entity.isIntervalTime = treatDeduct.getCalculateIncludIntervalExemptionTime().value;
		}
	}
	
	/**
	 * エンティティ：労働時間の加算設定(含める要素を指定)に変換する(割増時間用)
	 * @param companyId 会社ID
	 * @param laborSystemAtr 労働制
	 * @param domain 労働時間の加算設定
	 * @return 労働時間の加算設定(含める要素を指定)
	 */
	public static Optional<KsrmtCalcCAddInclude> toEntityIncludePremium(
			String companyId,
			int laborSystemAtr,
			AddSettingOfWorkingTime domain){
		
		if (domain.getUseAtr().isNotUse()) return Optional.empty();
		
		KsrmtCalcCAddInclude entity = new KsrmtCalcCAddInclude();
		KsrmtCalcCAddIncludePK key = new KsrmtCalcCAddIncludePK(companyId, laborSystemAtr, 1);
		entity.pk = key;
		updateEntityIncludePremium(entity, domain);
		return Optional.of(entity);
	}

	/**
	 * エンティティ：労働時間の加算設定(含める要素を指定)を更新する(割増時間用)
	 * @param entity 労働時間の加算設定(含める要素を指定)
	 * @param domain 労働時間の加算設定
	 */
	public static void updateEntityIncludePremium(
			KsrmtCalcCAddInclude entity,
			AddSettingOfWorkingTime domain){
		
		AddSettingOfPremiumTime addSet = domain.getAddSetOfPremium();
		entity.isActualOnly = addSet.getCalculateActualOperation().isCalclationByActualTime() ? 0 : 1;
		entity.isVacation = null;
		entity.isCare = null;
		entity.lateSettingUnit = null;
		entity.isLate = null;
		entity.isLateDeleteApp = null;
		entity.isIntervalTime = null;
		if (addSet.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcPremium treatVacation = addSet.getTreatVacation().get();
			entity.isVacation = treatVacation.getAddition().value;
		}
		if (addSet.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSet.getTreatDeduct().get();
			entity.isCare = treatDeduct.getCalculateIncludCareTime().value;
			entity.lateSettingUnit = treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() ? 1 : 0;
			TreatLateEarlyTime treatLate = treatDeduct.getTreatLateEarlyTimeSet().getTreatSet();
			entity.isLate = treatLate.isInclude() ? 1 : 0;
			entity.isLateDeleteApp = treatLate.isIncludeByApp() ? 1 : 0;
			entity.isIntervalTime = treatDeduct.getCalculateIncludIntervalExemptionTime().value;
		}
	}
	
	/**
	 * エンティティ：労働時間の加算設定(就業時間の加算設定)に変換する
	 * @param companyId 会社ID
	 * @param laborSystemAtr 労働制
	 * @param domain 労働時間の加算設定
	 * @return 労働時間の加算設定(就業時間の加算設定)
	 */
	public static KsrmtCalcCAddWorktime toEntityWorktime(
			String companyId,
			int laborSystemAtr,
			AddSettingOfWorkingTime domain){
		
		KsrmtCalcCAddWorktime entity = new KsrmtCalcCAddWorktime();
		KsrmtCalcCAddPK key = new KsrmtCalcCAddPK(companyId, laborSystemAtr);
		entity.pk = key;
		updateEntityWorktime(entity, domain);
		return entity;
	}
	
	/**
	 * エンティティ：労働時間の加算設定(就業時間の加算設定)を更新する
	 * @param entity 労働時間の加算設定(就業時間の加算設定)
	 * @param domain 労働時間の加算設定
	 */
	public static void updateEntityWorktime(
			KsrmtCalcCAddWorktime entity,
			AddSettingOfWorkingTime domain){
		
		entity.fixCalcOverPredtime = null;
		entity.fleCalcInLegal = null;
		entity.fleCalcShortPredtime = null;
		entity.fleCalcDeductPredtimeAbsence = null;
		if (domain.getAddSetOfWorkTime().getTreatVacation().isPresent()){
			TreatVacationTimeForCalcWorkTime treatVacation = domain.getAddSetOfWorkTime().getTreatVacation().get();
			if (treatVacation.getDeformationExceedsPredeterminedValue().isPresent()){
				entity.fixCalcOverPredtime = treatVacation.getDeformationExceedsPredeterminedValue().get().value; 
			}
			if (treatVacation.getAdditionWithinMonthlyStatutory().isPresent()){
				entity.fleCalcInLegal = treatVacation.getAdditionWithinMonthlyStatutory().get().value;
			}
			if (treatVacation.getPredeterminedDeficiencyOfFlex().isPresent()){
				entity.fleCalcShortPredtime = treatVacation.getPredeterminedDeficiencyOfFlex().get().value;
			}
			if (treatVacation.getMinusAbsenceTime().isPresent()){
				entity.fleCalcDeductPredtimeAbsence = treatVacation.getMinusAbsenceTime().get().value;
			}
		}
	}

	/**
	 * エンティティ：労働時間の加算設定(割増時間の加算設定)に変換する
	 * @param companyId 会社ID
	 * @param laborSystemAtr 労働制
	 * @param domain 労働時間の加算設定
	 * @return 労働時間の加算設定(割増時間の加算設定)
	 */
	public static KsrmtCalcCAddPremium toEntityPremium(
			String companyId,
			int laborSystemAtr,
			AddSettingOfWorkingTime domain){
		
		KsrmtCalcCAddPremium entity = new KsrmtCalcCAddPremium();
		KsrmtCalcCAddPK key = new KsrmtCalcCAddPK(companyId, laborSystemAtr);
		entity.pk = key;
		updateEntityPremium(entity, domain);
		return entity;
	}
	
	/**
	 * エンティティ：労働時間の加算設定(割増時間の加算設定)を更新する
	 * @param entity 労働時間の加算設定(割増時間の加算設定)
	 * @param domain 労働時間の加算設定
	 */
	public static void updateEntityPremium(
			KsrmtCalcCAddPremium entity,
			AddSettingOfWorkingTime domain){
		
		entity.fixCalcOverPredtime = null;
		entity.fleCalcOverPredtime = null;
		if (domain.getAddSetOfPremium().getTreatVacation().isPresent()){
			TreatVacationTimeForCalcPremium treatVacation = domain.getAddSetOfPremium().getTreatVacation().get();
			if (treatVacation.getDeformationExceedsPredeterminedValue().isPresent()){
				entity.fixCalcOverPredtime = treatVacation.getDeformationExceedsPredeterminedValue().get().value; 
			}
			if (treatVacation.getPredeterminedExcessTimeOfFlex().isPresent()){
				entity.fleCalcOverPredtime = treatVacation.getPredeterminedExcessTimeOfFlex().get().value;
			}
		}
	}
}
