package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriod;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriodRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemSalaryPeriod;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemSalaryPeriodPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaItemSalaryPeriodRepository extends JpaRepository implements ItemSalaryPeriodRepository {

	@Override
	public Optional<ItemSalaryPeriod> find( String itemCode) {
		String companyCode = AppContexts.user().companyCode();
		QcamtItemSalaryPeriodPK key = new QcamtItemSalaryPeriodPK(companyCode, itemCode);
		return this.queryProxy().find(key, QcamtItemSalaryPeriod.class).map(x -> toDomain(x));
	}

	private ItemSalaryPeriod toDomain(QcamtItemSalaryPeriod entity) {
		val domain = ItemSalaryPeriod.createFromJavaType(entity.qcamtItemSalaryPeriodPK.itemCd, entity.periodAtr,
				entity.strY, entity.endY, entity.cycleAtr, entity.cycle01Atr, entity.cycle02Atr, entity.cycle03Atr,
				entity.cycle04Atr, entity.cycle05Atr, entity.cycle06Atr, entity.cycle07Atr, entity.cycle08Atr,
				entity.cycle09Atr, entity.cycle10Atr, entity.cycle11Atr, entity.cycle12Atr);
		return domain;

	}

	@Override
	public void add(ItemSalaryPeriod domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(ItemSalaryPeriod domain) {
		this.commandProxy().update(toEntity(domain));

	}

	@Override
	public void delete(String itemCd) {
		String companyCode = AppContexts.user().companyCode();
		QcamtItemSalaryPeriodPK key = new QcamtItemSalaryPeriodPK(companyCode, itemCd);
		this.commandProxy().remove(QcamtItemSalaryPeriod.class, key);

	}

	private QcamtItemSalaryPeriod toEntity(ItemSalaryPeriod domain) {
		String campanyCode = AppContexts.user().companyCode();
		QcamtItemSalaryPeriodPK key = new QcamtItemSalaryPeriodPK(campanyCode, domain.getItemCd().v());
		return new QcamtItemSalaryPeriod(key, domain.getPeriodAtr().value, domain.getStrY().v(), domain.getEndY().v(),
				domain.getCycleAtr().value, domain.getCycle01Atr().value, domain.getCycle02Atr().value,
				domain.getCycle03Atr().value, domain.getCycle04Atr().value, domain.getCycle05Atr().value,
				domain.getCycle06Atr().value, domain.getCycle07Atr().value, domain.getCycle08Atr().value,
				domain.getCycle09Atr().value, domain.getCycle10Atr().value, domain.getCycle11Atr().value,
				domain.getCycle12Atr().value);
	}

}
