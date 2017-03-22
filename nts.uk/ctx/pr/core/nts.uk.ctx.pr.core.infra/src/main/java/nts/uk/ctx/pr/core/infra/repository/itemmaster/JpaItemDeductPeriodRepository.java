package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriod;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeductPeriod;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeductPeriodPK;

@Stateless
public class JpaItemDeductPeriodRepository extends JpaRepository implements ItemDeductPeriodRepository {

	@Override
	public Optional<ItemDeductPeriod> find(String companyCode, String itemCode) {
		QcamtItemDeductPeriodPK key = new QcamtItemDeductPeriodPK(companyCode, itemCode);
		return this.queryProxy().find(key, QcamtItemDeductPeriod.class).map(x -> toDomain(x));

	}

	private ItemDeductPeriod toDomain(QcamtItemDeductPeriod entity) {
		val domain = ItemDeductPeriod.createFromJavaType(entity.qcamtItemDeductPeriodPK.itemCd, entity.periodAtr,
				entity.strY, entity.endY, entity.cycleAtr, entity.cycle01Atr, entity.cycle02Atr, entity.cycle03Atr,
				entity.cycle04Atr, entity.cycle05Atr, entity.cycle06Atr, entity.cycle07Atr, entity.cycle08Atr,
				entity.cycle09Atr, entity.cycle10Atr, entity.cycle11Atr, entity.cycle12Atr);
		return domain;

	}
}
