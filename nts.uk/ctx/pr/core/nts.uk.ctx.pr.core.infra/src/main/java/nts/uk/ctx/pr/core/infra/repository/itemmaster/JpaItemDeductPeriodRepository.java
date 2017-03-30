package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriod;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeductPeriod;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeductPeriodPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaItemDeductPeriodRepository extends JpaRepository implements ItemDeductPeriodRepository {

	@Override
	public Optional<ItemDeductPeriod> find(String itemCode) {
		String companyCode = AppContexts.user().companyCode();
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

	@Override
	public void add(ItemDeductPeriod domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void delete(String itemCode) {
		String companyCode = AppContexts.user().companyCode();
		QcamtItemDeductPeriodPK key = new QcamtItemDeductPeriodPK(companyCode, itemCode);
		this.commandProxy().remove(QcamtItemDeductPeriod.class, key);

	}

	private QcamtItemDeductPeriod toEntity(ItemDeductPeriod domain) {
		String campanyCode = AppContexts.user().companyCode();
		QcamtItemDeductPeriodPK key = new QcamtItemDeductPeriodPK(campanyCode, domain.getItemCd().v());
		return new QcamtItemDeductPeriod(key, domain.getPeriodAtr().value, domain.getStrY().v(), domain.getEndY().v(),
				domain.getCycleAtr().value, domain.getCycle01Atr().value, domain.getCycle02Atr().value,
				domain.getCycle03Atr().value, domain.getCycle04Atr().value, domain.getCycle05Atr().value,
				domain.getCycle06Atr().value, domain.getCycle07Atr().value, domain.getCycle08Atr().value,
				domain.getCycle09Atr().value, domain.getCycle10Atr().value, domain.getCycle11Atr().value,
				domain.getCycle12Atr().value);
	}

	@Override
	public void update(ItemDeductPeriod domain) {
		this.commandProxy().update(toEntity(domain));

	}
}
