/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSetRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddInclude;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddIncludePK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddPremium;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddWorktime;
import nts.uk.ctx.at.shared.infra.repository.scherec.addsettingofworktime.JpaAddSettingOfWorkingTimeRepository;

import javax.ejb.Stateless;

/**
 * リポジトリ実装：通常勤務の加算設定
 */
@Stateless
public class JpaWorkRegularAdditionSetRepository extends JpaRepository implements WorkRegularAdditionSetRepository{

	@Override
	public Optional<WorkRegularAdditionSet> findByCID(String companyId) {
		Optional<KsrmtCalcCAddInclude> includeWorktime = this.queryProxy().find(
				new KsrmtCalcCAddIncludePK(companyId, WorkRegularAdditionSet.LABOR_SYSTEM_ATR, 0), KsrmtCalcCAddInclude.class);
		if (includeWorktime.isPresent()) {
			Optional<KsrmtCalcCAddInclude> includePremium = this.queryProxy().find(
					new KsrmtCalcCAddIncludePK(companyId, WorkRegularAdditionSet.LABOR_SYSTEM_ATR, 1), KsrmtCalcCAddInclude.class);
			Optional<KsrmtCalcCAddWorktime> worktime = this.queryProxy().find(
					new KsrmtCalcCAddPK(companyId, WorkRegularAdditionSet.LABOR_SYSTEM_ATR), KsrmtCalcCAddWorktime.class);
			Optional<KsrmtCalcCAddPremium> premium = this.queryProxy().find(
					new KsrmtCalcCAddPK(companyId, WorkRegularAdditionSet.LABOR_SYSTEM_ATR), KsrmtCalcCAddPremium.class);
			return Optional.of(toDomain(includeWorktime.get(), includePremium, worktime, premium));
		}
		return Optional.empty();
	}
	
	public static WorkRegularAdditionSet toDomain(
			KsrmtCalcCAddInclude includeWorktime,
			Optional<KsrmtCalcCAddInclude> includePremium,
			Optional<KsrmtCalcCAddWorktime> worktime,
			Optional<KsrmtCalcCAddPremium> premium){
		
		return new WorkRegularAdditionSet(
				includeWorktime.pk.cid,
				JpaAddSettingOfWorkingTimeRepository.toDomain(includeWorktime, includePremium, worktime, premium));
	}
}
