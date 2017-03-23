package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeductBd;

@RequestScoped
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

}
