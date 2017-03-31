package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeductBd;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeductBdPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaItemDeductBDRepository extends JpaRepository implements ItemDeductBDRepository {
	private final String SEL = "SELECT c FROM QcamtItemDeductBd c";
	private final String SEL_1 = SEL
			+ " WHERE c.qcamtItemDeductBdPK.ccd = :companyCode AND c.qcamtItemDeductBdPK.itemCd = :itemCode";

	@Override
	public List<ItemDeductBD> findAll(String companyCode, String itemCode) {
		return this.queryProxy().query(SEL_1, QcamtItemDeductBd.class).setParameter("companyCode", companyCode)
				.setParameter("itemCode", itemCode).getList(c -> toDomain(c));

	}

	private ItemDeductBD toDomain(QcamtItemDeductBd entity) {
		val domain = ItemDeductBD.createFromJavaType(entity.qcamtItemDeductBdPK.itemCd,
				entity.qcamtItemDeductBdPK.itemBreakdownCd, entity.itemBreakdownName, entity.itemBreakdownAbName,
				entity.uniteCd, entity.zeroDispSet, entity.itemDispAtr, entity.errRangeLowAtr, entity.errRangeLow,
				entity.errRangeHighAtr, entity.errRangeHigh, entity.alRangeLowAtr, entity.alRangeLow,
				entity.alRangeHighAtr, entity.alRangeHigh);
		return domain;
	}

	@Override
	public Optional<ItemDeductBD> find(String itemCd, String itemBreakdownCd) {
		String companyCode = AppContexts.user().companyCode();
		QcamtItemDeductBdPK pk = new QcamtItemDeductBdPK(companyCode, itemCd, itemBreakdownCd);
		return this.queryProxy().find(pk, QcamtItemDeductBd.class).map(x -> toDomain(x));
	}

	@Override
	public void add(ItemDeductBD itemDeductBD) {
		this.commandProxy().insert(toEntity(itemDeductBD));
	}

	private QcamtItemDeductBd toEntity(ItemDeductBD domain) {
		String campanyCode = AppContexts.user().companyCode();
		QcamtItemDeductBdPK pk = new QcamtItemDeductBdPK(campanyCode, domain.getItemCd().v(),
				domain.getItemBreakdownCd().v());
		return new QcamtItemDeductBd(pk, domain.getItemBreakdownName().v(), domain.getItemBreakdownAbName().v(),
				domain.getUniteCd().v(), domain.getZeroDispSet().value, domain.getItemDispAtr().value,
				domain.getErrRangeLowAtr().value, domain.getErrRangeLow().v(), domain.getErrRangeHighAtr().value,
				domain.getErrRangeHigh().v(), domain.getAlRangeLowAtr().value, domain.getAlRangeLow().v(),
				domain.getAlRangeHighAtr().value, domain.getAlRangeHigh().v());
	}

	@Override
	public void delete(String itemCd, String itemBreakdownCd) {
		String companyCode = AppContexts.user().companyCode();
		QcamtItemDeductBdPK pk = new QcamtItemDeductBdPK(companyCode, itemCd, itemBreakdownCd);
		this.commandProxy().remove(QcamtItemDeductBd.class, pk);

	}

	@Override
	public void update(ItemDeductBD itemDeductBD) {
		this.commandProxy().update(toEntity(itemDeductBD));

	}

}
