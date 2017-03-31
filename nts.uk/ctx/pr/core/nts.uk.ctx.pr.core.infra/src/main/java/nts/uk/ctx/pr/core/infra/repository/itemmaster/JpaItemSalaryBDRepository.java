package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemSalaryBd;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemSalaryBdPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaItemSalaryBDRepository extends JpaRepository implements ItemSalaryBDRepository {

	private final String SEL = "SELECT c FROM QcamtItemSalaryBd c";
	private final String SEL_1 = SEL
			+ " WHERE c.qcamtItemSalaryBdPK.ccd = :companyCode AND c.qcamtItemSalaryBdPK.itemCd = :itemCode";

	@Override
	public List<ItemSalaryBD> findAll(String companyCode, String itemCode) {
		return this.queryProxy().query(SEL_1, QcamtItemSalaryBd.class).setParameter("companyCode", companyCode)
				.setParameter("itemCode", itemCode).getList(c -> toDomain(c));

	}

	private ItemSalaryBD toDomain(QcamtItemSalaryBd entity) {

		val domain = ItemSalaryBD.createFromJavaType(entity.qcamtItemSalaryBdPK.itemCd,
				entity.qcamtItemSalaryBdPK.itemBreakdownCd, entity.itemBreakdownName, entity.itemBreakdownAbName,
				entity.uniteCd, entity.zeroDispSet, entity.itemDispAtr, entity.errRangeLowAtr, entity.errRangeLow,
				entity.errRangeHighAtr, entity.errRangeHigh, entity.alRangeLowAtr, entity.alRangeLow,
				entity.alRangeHighAtr, entity.alRangeHigh);
		return domain;

	}

	@Override
	public void add(ItemSalaryBD itemSalaryBD) {
		this.commandProxy().insert(toEntity(itemSalaryBD));

	}

	private QcamtItemSalaryBd toEntity(ItemSalaryBD domain) {
		String campanyCode = AppContexts.user().companyCode();
		QcamtItemSalaryBdPK pk = new QcamtItemSalaryBdPK(campanyCode, domain.getItemCd().v(),
				domain.getItemBreakdownCd().v());
		return new QcamtItemSalaryBd(pk, domain.getItemBreakdownName().v(), domain.getItemBreakdownAbName().v(),
				domain.getUniteCd().v(), domain.getZeroDispSet().value, domain.getItemDispAtr().value,
				domain.getErrRangeLowAtr().value, domain.getErrRangeLow().v(), domain.getErrRangeHighAtr().value,
				domain.getErrRangeHigh().v(), domain.getAlRangeLowAtr().value, domain.getAlRangeLow().v(),
				domain.getAlRangeHighAtr().value, domain.getAlRangeHigh().v());
	}

	@Override
	public void delete(String itemCd, String itemBreakdownCd) {
		String companyCode = AppContexts.user().companyCode();
		QcamtItemSalaryBdPK pk = new QcamtItemSalaryBdPK(companyCode, itemCd, itemBreakdownCd);
		this.commandProxy().remove(QcamtItemSalaryBd.class, pk);

	}

	@Override
	public Optional<ItemSalaryBD> find(String itemCd, String itemBreakdownCd) {
		String companyCode = AppContexts.user().companyCode();
		QcamtItemSalaryBdPK pk = new QcamtItemSalaryBdPK(companyCode, itemCd, itemBreakdownCd);
		return this.queryProxy().find(pk, QcamtItemSalaryBd.class).map(x -> toDomain(x));
	}

	@Override
	public void update(ItemSalaryBD domain) {
		this.commandProxy().update(toEntity(domain));

	}

}
