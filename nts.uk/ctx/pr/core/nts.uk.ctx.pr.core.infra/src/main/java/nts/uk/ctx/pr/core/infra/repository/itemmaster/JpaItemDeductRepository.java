package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeduct;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeduct;

@RequestScoped
public class JpaItemDeductRepository extends JpaRepository implements ItemDeductRespository {
	private final String SEL = "SELECT c FROM QcamtItemDeduct c";
	private final String SEL_1 = SEL
			+ " WHERE c.qcamtItemDeductPK.ccd = :companyCode AND c.qcamtItemDeductPK.itemCd = :itemCd";

	@Override
	public Optional<ItemDeduct> find(String companyCode, String itemCode) {
		return this.queryProxy().query(SEL_1, QcamtItemDeduct.class).setParameter("companyCode", companyCode)
				.setParameter("itemCd", itemCode).getSingle(c -> toDomain(c));
	}

	private ItemDeduct toDomain(QcamtItemDeduct entity) {

		val domain = ItemDeduct.createFromJavaType(entity.qcamtItemDeductPK.ccd, entity.qcamtItemDeductPK.itemCd,
				entity.deductAtr, entity.errRangeLowAtr, entity.errRangeLow, entity.errRangeHighAtr,
				entity.errRangeHigh, entity.alRangeLowAtr, entity.alRangeLow, entity.alRangeHighAtr, entity.alRangeHigh,
				entity.memo);
		// TODO Auto-generated method stub
		return domain;
	}
}
