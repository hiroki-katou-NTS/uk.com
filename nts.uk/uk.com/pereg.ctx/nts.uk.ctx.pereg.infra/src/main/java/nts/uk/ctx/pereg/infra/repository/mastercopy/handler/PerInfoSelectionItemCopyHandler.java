/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtSelectionHist;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtSelectionHistPK;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtSelectionDef;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelectionItemSort;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelectionItemSortPK;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelectionItem;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelectionItemPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PerInfoSelectionItemCopyHandler.
 */
public class PerInfoSelectionItemCopyHandler extends DataCopyHandler {

	/** The Constant QUERY_HISTORY_ITEM_BY_CONTRACTCD. */
	private static final String QUERY_HISTORY_ITEM_BY_CONTRACTCD = "SELECT l FROM PpemtSelectionDef l WHERE l.contractCd = :contractCd";
	
	/** The Constant QUERY_HISTORY_SELECTION. */
	private static final String QUERY_HISTORY_SELECTION = "SELECT p FROM PpemtSelectionHist p WHERE p.companyId = :companyId AND p.selectionItemId IN :selectionItemIds";
	
	/** The Constant QUERY_SELECTION. */
	private static final String QUERY_SELECTION = "SELECT s FROM PpemtSelectionItem s WHERE s.histId = :histId";
	
	/** The Constant QUERY_SELECTION_ORDER. */
	private static final String QUERY_SELECTION_ORDER = "SELECT o FROM PpemtSelectionItemSort o WHERE o.histId = :histId";
	
	/** The Constant DELETE_SELECTION. */
	private static final String DELETE_SELECTION = "DELETE FROM PpemtSelectionItem s WHERE s.histId IN :histIds";
	
	/** The Constant DELETE_SELECTION_ORDER. */
	private static final String DELETE_SELECTION_ORDER = "DELETE FROM PpemtSelectionItemSort o WHERE o.histId IN :histIds";

	/**
	 * Instantiates a new per info selection item copy handler.
	 *
	 * @param repo the repo
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public PerInfoSelectionItemCopyHandler(JpaRepository repo, int copyMethod, String companyId) {
		this.copyMethod = copyMethod;
		this.companyId = companyId;
		this.entityManager = repo.getEntityManager();
		this.commandProxy = repo.commandProxy();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public Map<String, String> doCopy() {
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
		
		return Collections.emptyMap();
	}

	/**
	 * Copy master data.
	 *
	 * @param sourceCid the source cid
	 * @param targetCid the target cid
	 * @param isReplace the is replace
	 */
	private void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
		// アルゴリズム「指定会社のく選択項目定義を全て削除する」を実行する
		// Lấy domain [PerInfoSelectionItem], Điều kiện :contractCode ＝ login
		// contractCodePPEMT_SELECTION_HIST
		List<PpemtSelectionDef> ppemtSelectionDef = this.entityManager
				.createQuery(QUERY_HISTORY_ITEM_BY_CONTRACTCD, PpemtSelectionDef.class)
				.setParameter("contractCd", AppContexts.user().contractCode()).getResultList();
		// Delete domain [HistorySelection], Điều kiện: companyID ＝Input．companyID, selectionItemID ＝ 「PerInfoSelectionItem」．ID đa lấy
		List<String> selectionItemIds = ppemtSelectionDef.stream().map(item -> item.selectionItemPk.selectionItemId)
				.collect(Collectors.toList());
		List<PpemtSelectionHist> ppemtSelectionHists = new ArrayList<>();
		CollectionUtil.split(selectionItemIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			ppemtSelectionHists.addAll(this.entityManager
				.createQuery(QUERY_HISTORY_SELECTION, PpemtSelectionHist.class).setParameter("companyId", targetCid)
				.setParameter("selectionItemIds", subList).getResultList());
		});
		List<String> histIds = ppemtSelectionHists.stream().map(item -> item.histidPK.histId)
				.collect(Collectors.toList());
		this.commandProxy.removeAll(ppemtSelectionHists);
		if (!histIds.isEmpty()) {
			CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				// Delete doman [Selection], ĐK: historyID ＝「HistorySelection」．history．historyID
				this.entityManager.createQuery(DELETE_SELECTION, PpemtSelectionItem.class)
					.setParameter("histIds", subList)
					.executeUpdate();
				
				// Delete domain [OrderSelectionAndDefaultValues], ĐK: historyID ＝「HistorySelection」．history．historyID
				this.entityManager.createQuery(DELETE_SELECTION_ORDER, PpemtSelectionItemSort.class)
					.setParameter("histIds", subList)
					.executeUpdate();
			});
		}

		// 指定会社の選択項目定義を全て取得する
		// Acquire domain model "Personal information selection item",Contractcode = Logged in contract code
		// Acquire the domain model [HistorySelection] by Company ID = Input.Company ID zero and Selection item ID = SelectionItem.selectionitemid
		List<PpemtSelectionHist> ppemtSelectionHistsZero = this.entityManager
				.createQuery(QUERY_HISTORY_SELECTION, PpemtSelectionHist.class).setParameter("companyId", sourceCid)
				.setParameter("selectionItemIds", selectionItemIds).getResultList();

		// (Set [SelectionHistory], [Seletion],[OrderSelectionAndDefaultValues])
		List<PpemtSelectionHist> newPpemtSelectionHists = new ArrayList<>();
		List<PpemtSelectionItem> newPpemtSelectionItems = new ArrayList<>();
		List<PpemtSelectionItemSort> newPpemtSelectionItemSorts = new ArrayList<>();

		ppemtSelectionHistsZero.stream().forEach(item -> {
			String newHistId = IdentifierUtil.randomUniqueId();
			List<PpemtSelectionItem> ppemtSelectionItemsZero = this.entityManager
					.createQuery(QUERY_SELECTION, PpemtSelectionItem.class).setParameter("histId", item.histidPK.histId)
					.getResultList();
			List<PpemtSelectionItemSort> ppemtSelectionItemSortsZero = this.entityManager
					.createQuery(QUERY_SELECTION_ORDER, PpemtSelectionItemSort.class)
					.setParameter("histId", item.histidPK.histId).getResultList();
			// set [SelectionHistory]
			newPpemtSelectionHists.add(new PpemtSelectionHist(new PpemtSelectionHistPK(newHistId),
					item.selectionItemId, targetCid, item.startDate, item.endDate));
			
			Map<String, String> mapSelectionId = new HashMap<>();
			
			// set [Seletion]
			newPpemtSelectionItems.addAll(ppemtSelectionItemsZero.stream().map(selectionItem -> {
				String selectionId = IdentifierUtil.randomUniqueId();
				mapSelectionId.put(selectionItem.selectionId.selectionId, selectionId);
				return new PpemtSelectionItem(new PpemtSelectionItemPK(selectionId), newHistId,
						selectionItem.selectionCd, selectionItem.selectionName,
						selectionItem.externalCd, selectionItem.memo);
			}).collect(Collectors.toList()));
			
			// set [OrderSelectionAndDefaultValues]
			newPpemtSelectionItemSorts.addAll(ppemtSelectionItemSortsZero.stream()
					.map(selOrderItem -> new PpemtSelectionItemSort(
							new PpemtSelectionItemSortPK(
									mapSelectionId.get(selOrderItem.selectionIdPK.selectionId)),
							newHistId, selOrderItem.dispOrder, selOrderItem.initSelection))
					.collect(Collectors.toList()));
		});

		this.commandProxy.insertAll(newPpemtSelectionHists);
		this.commandProxy.insertAll(newPpemtSelectionItems);
		this.commandProxy.insertAll(newPpemtSelectionItemSorts);
	}
}
