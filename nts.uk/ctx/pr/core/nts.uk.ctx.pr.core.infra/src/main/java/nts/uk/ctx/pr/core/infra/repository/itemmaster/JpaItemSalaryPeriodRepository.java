package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriod;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriodRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemSalaryPeriod;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemSalaryPeriodPK;

@RequestScoped
public class JpaItemSalaryPeriodRepository extends JpaRepository implements ItemSalaryPeriodRepository {
	private final String SEL = "SELECT c FROM QcamtItemSalaryPeriod c";
	private final String SEL_1 = SEL
			+ " WHERE c.qcamtItemSalaryPeriodPK.ccd = :companyCode AND c.qcamtItemSalaryPeriodPK.itemCd = :itemCd";

	@Override
	public Optional<ItemSalaryPeriod> find(String companyCode, String itemCode) {
		QcamtItemSalaryPeriodPK key = new QcamtItemSalaryPeriodPK(companyCode, itemCode);
		return this.queryProxy().find(key, QcamtItemSalaryPeriod.class)
				.map(x -> toDomain(x));
	}

	private ItemSalaryPeriod toDomain(QcamtItemSalaryPeriod entity) {
		val domain = ItemSalaryPeriod.createFromJavaType(entity.qcamtItemSalaryPeriodPK.itemCd,entity.periodAtr, entity.strY, entity.endY, entity.cycleAtr,
				entity.cycle01Atr, entity.cycle02Atr, entity.cycle03Atr, entity.cycle04Atr, entity.cycle05Atr,
				entity.cycle06Atr, entity.cycle07Atr, entity.cycle08Atr, entity.cycle09Atr, entity.cycle10Atr,
				entity.cycle11Atr, entity.cycle12Atr);
		return domain;

	}

}
