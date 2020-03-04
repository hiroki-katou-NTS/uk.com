package nts.uk.ctx.hr.notice.infra.repository.report;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItem;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItemRepository;
import nts.uk.ctx.hr.notice.infra.entity.report.JhnRptLtItem;
import nts.uk.ctx.hr.notice.infra.entity.report.JhnRptLtItemPk;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaRegisterPersonalReportItemRepository extends JpaRepository implements RegisterPersonalReportItemRepository{
	
	private static final String SEL_ALL_ITEM_BY_CID_RPTLAYOUT_ID = "SELECT c FROM JhnRptLtItem c WHERE c.jhnRptLtItemPk.cid =:cid AND c.jhnRptLtItemPk.rptLayouId =:rptLayouId order by c.displayOrder ";
	private static final String REMOVE_ALL_BY_LAYOUT_ID = "DELETE FROM JhnRptLtItem c WHERE c.jhnRptLtItemPk.rptLayouId =:rptLayouId AND c.jhnRptLtItemPk.cid =:cid";
	@Override
	public List<RegisterPersonalReportItem> getAllItemBy(String cid, int rptLayoutId) {
		return this.queryProxy().query(SEL_ALL_ITEM_BY_CID_RPTLAYOUT_ID, JhnRptLtItem.class)
				.setParameter("cid", cid)
				.setParameter("rptLayouId", rptLayoutId)
				.getList(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public void insertAll(List<RegisterPersonalReportItem> domain) {
		this.commandProxy().insertAll(domain.stream().map(c -> {
			return toEntity(c);
		}).collect(Collectors.toList()));
	}
	
	private RegisterPersonalReportItem createDomainFromEntity(JhnRptLtItem entity) {
		return  new RegisterPersonalReportItem(
				entity.jhnRptLtItemPk.cid, entity.jhnRptLtItemPk.rptLayouId,
				entity.rptLayouCd, entity.rptLayouName, 
				entity.layoutItemType, 
				entity.jhnRptLtItemPk.categoryCd, entity.categoryName, 
				entity.contractCd, entity.fixedAtr,
				entity.jhnRptLtItemPk.itemCd, entity.itemName,
				entity.disOrder, Optional.ofNullable(entity.abolitionAtr == 1? true: false),
				"", entity.reflectId,
				entity.categoryId, entity.itemId, entity.displayOrder);
	}
	
	private JhnRptLtItem toEntity(RegisterPersonalReportItem domain) {
		
		JhnRptLtItemPk pk = new JhnRptLtItemPk (domain.getCompanyId(), domain.getPReportClsId(),
				domain.getCategoryCd(), domain.getItemCd());
		//domain.getLayoutOrder(), domain.getDisplayOrder()domain.getCategoryCd(), domain.getItemCd(),
		return new JhnRptLtItem(pk, 
				domain.getPReportCode(), domain.getPReportName(), 
				domain.getDisplayOrder(),
				domain.getItemType(), AppContexts.user().contractCode(),
				domain.getCategoryId(),domain.getCategoryName(), 
				domain.getItemId(), domain.getItemName(),
				domain.isFixedAtr(),
				domain.getIsAbolition().isPresent() == true ? (domain.getIsAbolition().get() == true ? 1 : 0) : 0,
				domain.getReflectionId(),
				domain.getLayoutOrder());
	}

	@Override
	public void updateAll(List<RegisterPersonalReportItem> domain) {
		this.commandProxy().updateAll(domain.stream().map(c -> {
			return toEntity(c);
		}).collect(Collectors.toList()));
		
	}

	@Override
	public void removeAllByLayoutId(String cid, int rptLayoutId) {
		// remove all classifications when update or override layout
		this.getEntityManager().createQuery(REMOVE_ALL_BY_LAYOUT_ID, JhnRptLtItem.class)
		.setParameter("rptLayouId", rptLayoutId)
		.setParameter("cid", cid)
		.executeUpdate();
	}
	
/*
 * // remove all classifications when update or override layout
		getEntityManager().createQuery(REMOVE_ALL_BY_LAYOUT_ID).setParameter("layoutId", layoutId).executeUpdate();
 * 
 */

}
