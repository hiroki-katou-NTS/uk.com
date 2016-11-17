package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QcamtItem;

@RequestScoped
public class JpaItemMasterRepository extends JpaRepository implements ItemMasterRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM QcamtItem c";
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE + " WHERE c.qcamtItemPK.ccd = :companyCode"
			+ " AND c.qcamtItemPK.ctgAtr = :categoryType";
	private final String SELECT_DETAIL = SELECT_ALL_DETAILS + " AND c.qcamtItemPK.itemCd = :itemCode";

	/**
	 * find all item master by company code, category type
	 */
	@Override
	public List<ItemMaster> getAllItemMaster(String companyCode, int categoryType) {
		return this.queryProxy().query(SELECT_ALL_DETAILS, QcamtItem.class).setParameter("companyCode", companyCode)
				.setParameter("categoryType", categoryType).getList(c -> toDomain(c));
	}

	private static ItemMaster toDomain(QcamtItem entity) {
		val domain = ItemMaster.createSimpleFromJavaType(entity.qcamtItemPK.ccd, entity.qcamtItemPK.itemCd,
				entity.qcamtItemPK.ctgAtr, entity.itemName);
		entity.toDomain(domain);
		return domain;
	}

	@Override
	/**
	 * find item by company code, category type, item code
	 */
	public Optional<ItemMaster> getItemMaster(String companyCode, int categoryType, String itemCode) {
		return this.queryProxy().query(SELECT_DETAIL, QcamtItem.class).setParameter("companyCode", companyCode)
				.setParameter("categoryType", categoryType).setParameter("itemCd", itemCode)
				.getSingle(c -> toDomain(c));
	}

}
