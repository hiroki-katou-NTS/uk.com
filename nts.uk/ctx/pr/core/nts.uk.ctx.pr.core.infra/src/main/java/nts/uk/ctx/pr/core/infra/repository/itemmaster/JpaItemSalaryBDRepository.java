package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemSalaryBd;

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

}
