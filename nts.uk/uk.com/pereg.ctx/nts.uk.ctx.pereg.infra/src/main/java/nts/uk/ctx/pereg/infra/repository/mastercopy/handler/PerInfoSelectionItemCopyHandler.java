package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtHistorySelection;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtHistorySelectionPK;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtSelectionItem;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelItemOrder;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelItemOrderPK;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelection;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelectionPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author locph
 */
public class PerInfoSelectionItemCopyHandler extends DataCopyHandler {

	private static final String QUERY_HISTORY_ITEM_BY_CONTRACTCD = "SELECT l FROM PpemtSelectionItem l WHERE l.contractCd = :contractCd";
	private static final String QUERY_HISTORY_SELECTION = "SELECT p FROM PpemtHistorySelection p WHERE p.companyId = :companyId AND p.selectionItemId IN :selectionItemIds";
	private static final String QUERY_SELECTION = "SELECT s FROM PpemtSelection s WHERE s.histId = :histId";
	private static final String QUERY_SELECTION_ORDER = "SELECT o FROM PpemtSelItemOrder o WHERE o.histId = :histId";
	private static final String DELETE_SELECTION = "DELETE FROM PpemtSelection s WHERE s.histId IN :histIds";
	private static final String DELETE_SELECTION_ORDER = "DELETE FROM PpemtSelItemOrder o WHERE o.histId IN :histIds";

	public PerInfoSelectionItemCopyHandler(JpaRepository repo, int copyMethod, String companyId) {
		this.copyMethod = copyMethod;
		this.companyId = companyId;
		this.entityManager = repo.getEntityManager();
		this.commandProxy = repo.commandProxy();
	}

	@Override
	public void doCopy() {
		String sourceCid = AppContexts.user().zeroCompanyIdInContract();
		String targetCid = companyId;

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			copyMasterData(sourceCid, targetCid, false);
			break;
		case ADD_NEW:
			// Insert Data
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

	private void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
		// アルゴリズム「指定会社のく選択項目定義を全て削除する」を実行する
		// Lấy domain [PerInfoSelectionItem], Điều kiện :contractCode ＝ login
		// contractCodePPEMT_HISTORY_SELECTION
		List<PpemtSelectionItem> ppemtSelectionItem = this.entityManager
				.createQuery(QUERY_HISTORY_ITEM_BY_CONTRACTCD, PpemtSelectionItem.class)
				.setParameter("contractCd", AppContexts.user().contractCode()).getResultList();
		// Delete domain [HistorySelection], Điều kiện: companyID ＝Input．companyID, selectionItemID ＝ 「PerInfoSelectionItem」．ID đa lấy
		List<String> selectionItemIds = ppemtSelectionItem.stream().map(item -> item.selectionItemPk.selectionItemId)
				.collect(Collectors.toList());
		List<PpemtHistorySelection> ppemtHistorySelections = this.entityManager
				.createQuery(QUERY_HISTORY_SELECTION, PpemtHistorySelection.class).setParameter("companyId", targetCid)
				.setParameter("selectionItemIds", selectionItemIds).getResultList();
		List<String> histIds = ppemtHistorySelections.stream().map(item -> item.histidPK.histId)
				.collect(Collectors.toList());
		this.commandProxy.removeAll(ppemtHistorySelections);
		if (!histIds.isEmpty()) {
			// Delete doman [Selection], ĐK: historyID ＝「HistorySelection」．history．historyID
			this.entityManager.createQuery(DELETE_SELECTION, PpemtSelection.class).setParameter("histIds", histIds)
					.executeUpdate();
			// Delete domain [OrderSelectionAndDefaultValues], ĐK: historyID ＝「HistorySelection」．history．historyID
			this.entityManager.createQuery(DELETE_SELECTION_ORDER, PpemtSelItemOrder.class)
					.setParameter("histIds", histIds).executeUpdate();
		}

		// 指定会社の選択項目定義を全て取得する
		// Acquire domain model "Personal information selection item",Contractcode = Logged in contract code
		// Acquire the domain model [HistorySelection] by Company ID = Input.Company ID zero and Selection item ID = SelectionItem.selectionitemid
		List<PpemtHistorySelection> ppemtHistorySelectionsZero = this.entityManager
				.createQuery(QUERY_HISTORY_SELECTION, PpemtHistorySelection.class).setParameter("companyId", sourceCid)
				.setParameter("selectionItemIds", selectionItemIds).getResultList();

		// (Set [SelectionHistory], [Seletion],[OrderSelectionAndDefaultValues])
		List<PpemtHistorySelection> newPpemtHistorySelections = new ArrayList<>();
		List<PpemtSelection> newPpemtSelections = new ArrayList<>();
		List<PpemtSelItemOrder> newPpemtSelItemOrders = new ArrayList<>();

		ppemtHistorySelectionsZero.stream().forEach(item -> {
			String newHistId = IdentifierUtil.randomUniqueId();
			List<PpemtSelection> ppemtSelectionsZero = this.entityManager
					.createQuery(QUERY_SELECTION, PpemtSelection.class).setParameter("histId", item.histidPK.histId)
					.getResultList();
			List<PpemtSelItemOrder> ppemtSelItemOrdersZero = this.entityManager
					.createQuery(QUERY_SELECTION_ORDER, PpemtSelItemOrder.class)
					.setParameter("histId", item.histidPK.histId).getResultList();
			// set [SelectionHistory]
			newPpemtHistorySelections.add(new PpemtHistorySelection(new PpemtHistorySelectionPK(newHistId),
					item.selectionItemId, targetCid, item.startDate, item.endDate));
			// set [Seletion]
			newPpemtSelections.addAll(ppemtSelectionsZero.stream()
					.map(selectionItem -> new PpemtSelection(new PpemtSelectionPK(IdentifierUtil.randomUniqueId()),
							newHistId, selectionItem.selectionCd, selectionItem.selectionName, selectionItem.externalCd,
							selectionItem.memo))
					.collect(Collectors.toList()));
			// set [OrderSelectionAndDefaultValues]
			newPpemtSelItemOrders.addAll(ppemtSelItemOrdersZero.stream()
					.map(selOrderItem -> new PpemtSelItemOrder(new PpemtSelItemOrderPK(IdentifierUtil.randomUniqueId()),
							newHistId, selOrderItem.dispOrder, selOrderItem.initSelection))
					.collect(Collectors.toList()));
		});

		this.commandProxy.insertAll(newPpemtHistorySelections);
		this.commandProxy.insertAll(newPpemtSelections);
		this.commandProxy.insertAll(newPpemtSelItemOrders);
	}
}
