package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItem;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemPK;

@RequestScoped
public class JpaItemMasterRepository extends JpaRepository implements ItemMasterRepository {
	private final String SEL = "SELECT c FROM QcamtItem c";
	private final String SEL_3 = SEL + " WHERE c.qcamtItemPK.ccd = :companyCode AND c.avePayAtr = :avePayAtr";
	private final String SEL_3_1 = SEL + " WHERE c.qcamtItemPK.ccd = :companyCode AND c.qcamtItemPK.ctgAtr = :ctgAtr";
	private final String SEL_10 = "";
	
	@Override
	public Optional<ItemMaster> find(String companyCode, int categoryAtr, String itemCode) {
		// SEL_2
		return this.queryProxy().find(new QcamtItemPK(companyCode, categoryAtr, itemCode), QcamtItem.class)
				.map(x -> toDomain(x));
	}

	@Override
	public List<ItemMaster> findAll(String companyCode, int avePayAtr) {
		return this.queryProxy().query(SEL_3, QcamtItem.class)
				.setParameter("companyCode", companyCode)
				.setParameter("avePayAtr", avePayAtr)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<ItemMaster> findAllByCategory(String companyCode, int categoryAtr) {
		return this.queryProxy().query(SEL_3_1, QcamtItem.class)
				.setParameter("companyCode", companyCode)
				.setParameter("ctgAtr", categoryAtr)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<ItemMaster> findAll(String companyCode, int categoryAtr, String itemCode, int fixAtr) {
		return this.queryProxy().query(SEL_10, QcamtItem.class)
				.setParameter("companyCode", companyCode)
				.setParameter("itemCode", itemCode)
				.setParameter("fixAtr", fixAtr)
				.getList(c -> toDomain(c));
	}

	/**
	 * Convert to domain
	 * @param entity
	 * @return
	 */
	private ItemMaster toDomain(QcamtItem entity) {
		val domain = ItemMaster.createFromJavaType(
				entity.qcamtItemPK.ccd,
				entity.qcamtItemPK.itemCd, 
				entity.itemName, 
				entity.itemAbName,
				entity.itemAbNameE, 
				entity.itemAbNameO, 
				entity.qcamtItemPK.ctgAtr, 
				entity.fixAtr, 
				entity.dispSet,
				entity.uniteCd, 
				entity.zeroDispSet, 
				entity.itemDispAtr);
		
		return domain;
	}
}
