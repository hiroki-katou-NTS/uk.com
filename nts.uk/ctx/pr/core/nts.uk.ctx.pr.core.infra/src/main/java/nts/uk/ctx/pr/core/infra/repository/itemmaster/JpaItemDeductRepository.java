package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeduct;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeduct;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemDeductPK;

@RequestScoped
public class JpaItemDeductRepository extends JpaRepository implements ItemDeductRespository {

	@Override
	public Optional<ItemDeduct> find(String companyCode, String itemCode) {
		return this.queryProxy().find(new QcamtItemDeductPK(companyCode, itemCode), QcamtItemDeduct.class)
				.map(c -> toDomain(c));
	}

	private ItemDeduct toDomain(QcamtItemDeduct entity) {

		val domain = ItemDeduct.createFromJavaType(entity.qcamtItemDeductPK.ccd, entity.qcamtItemDeductPK.itemCd,
				entity.deductAtr, entity.errRangeLowAtr, entity.errRangeLow, entity.errRangeHighAtr,
				entity.errRangeHigh, entity.alRangeLowAtr, entity.alRangeLow, entity.alRangeHighAtr, entity.alRangeHigh,
				entity.memo);
		// TODO Auto-generated method stub
		return domain;
	}

	@Override
	public void add(ItemDeduct itemDeduct) {
		this.commandProxy().insert(toEntity(itemDeduct));
	}

	private QcamtItemDeduct toEntity(ItemDeduct domain) {
		// TODO Auto-generated method stub
		return new QcamtItemDeduct(new QcamtItemDeductPK(domain.getCcd().v(), domain.getItemCd().v()),
				domain.getDeductAtr().value, domain.getErrRangeLowAtr().value, domain.getErrRangeLow().v(),
				domain.getErrRangeHighAtr().value, domain.getErrRangeHigh().v(), domain.getAlRangeLowAtr().value,
				domain.getAlRangeLow().v(), domain.getAlRangeHighAtr().value, domain.getAlRangeHigh().v(),
				domain.getMemo().v());
	}

	@Override
	public void delete(String companyCode, String itemCode) {
		this.commandProxy().remove(QcamtItemDeduct.class, new QcamtItemDeductPK(companyCode, itemCode));
	}
}
