/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSetRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddInclude;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddIncludePK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddPremium;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddWorktime;
import nts.uk.ctx.at.shared.infra.repository.scherec.addsettingofworktime.JpaAddSettingOfWorkingTimeRepository;

import javax.ejb.Stateless;

/**
 * リポジトリ実装：変形労働勤務の加算設定
 */
@Stateless
public class JpaWorkDeformedLaborAdditionSetRepository extends JpaRepository implements WorkDeformedLaborAdditionSetRepository{

	@Override
	public Optional<WorkDeformedLaborAdditionSet> findByCid(String companyId) {
		Optional<KsrmtCalcCAddInclude> includeWorktime = this.queryProxy().find(
				new KsrmtCalcCAddIncludePK(companyId, WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR, false), KsrmtCalcCAddInclude.class);
		if (includeWorktime.isPresent()) {
			Optional<KsrmtCalcCAddInclude> includePremium = this.queryProxy().find(
					new KsrmtCalcCAddIncludePK(companyId, WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR, true), KsrmtCalcCAddInclude.class);
			Optional<KsrmtCalcCAddWorktime> worktime = this.queryProxy().find(
					new KsrmtCalcCAddPK(companyId, WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR), KsrmtCalcCAddWorktime.class);
			Optional<KsrmtCalcCAddPremium> premium = this.queryProxy().find(
					new KsrmtCalcCAddPK(companyId, WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR), KsrmtCalcCAddPremium.class);
			return Optional.of(toDomain(includeWorktime.get(), includePremium, worktime, premium));
		}
		return Optional.empty();
	}
	
	public static WorkDeformedLaborAdditionSet toDomain(
			KsrmtCalcCAddInclude includeWorktime,
			Optional<KsrmtCalcCAddInclude> includePremium,
			Optional<KsrmtCalcCAddWorktime> worktime,
			Optional<KsrmtCalcCAddPremium> premium){
		
		return new WorkDeformedLaborAdditionSet(
				includeWorktime.pk.cid,
				JpaAddSettingOfWorkingTimeRepository.toDomain(includeWorktime, includePremium, worktime, premium));
	}
}
