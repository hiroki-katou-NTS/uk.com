package nts.uk.ctx.pereg.infra.repository.person.setting.selectionitem.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelItemOrder;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelItemOrderPK;

/**
 * 
 * @author tuannv
 *
 */

@Stateless
public class JpaSelectionItemOrderRepository extends JpaRepository implements SelectionItemOrderRepository {

	private static final String SELECT_ALL = "SELECT si FROM PpemtSelItemOrder si";
	
	private static final String SELECT_ALL_HISTORY_ID = SELECT_ALL + " WHERE si.histId = :histId"
			+ " ORDER BY si.dispOrder ASC";
	
	private static final String SELECT_BY_HISTORY_ID_LIST = SELECT_ALL + " WHERE si.histId IN :histIdList"
			+ " ORDER BY si.histId, si.dispOrder ASC";
	
	private static final String SELECT_ALL_IN_SELECTION_ITEM_ID = SELECT_ALL
			+ " INNER JOIN PpemtHistorySelection his ON si.histId = his.histidPK.histId"
			+ " WHERE his.selectionItemId = :selectionItemId";
	
	private static final String SELECT_ALL_SELECTION_ID = SELECT_ALL
			+ " WHERE si.selectionIdPK.selectionId = :selectionId";

	@Override
	public void add(SelectionItemOrder selectionItemOrder) {
		this.commandProxy().insert(toEntity(selectionItemOrder));

	}
	
	@Override
	public void addAll(List<SelectionItemOrder> selectionItemOrders) {
		List<PpemtSelItemOrder> entities = selectionItemOrders.stream().map(x -> toEntity(x))
				.collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}

	@Override
	public void remove(String selectionId) {
		PpemtSelItemOrderPK pk = new PpemtSelItemOrderPK(selectionId);
		this.commandProxy().remove(PpemtSelItemOrder.class, pk);

	}
	
	@Override
	public void removeInSelectionItemId(String selectionItemId) {
		List<PpemtSelItemOrder> orderList = this.queryProxy()
				.query(SELECT_ALL_IN_SELECTION_ITEM_ID, PpemtSelItemOrder.class)
				.setParameter("selectionItemId", selectionItemId).getList();
		this.commandProxy().removeAll(orderList);
	}

	// Domain:
	private SelectionItemOrder toDomain(PpemtSelItemOrder entity) {
		return SelectionItemOrder.selectionItemOrder(entity.selectionIdPK.selectionId, entity.histId, entity.dispOrder,
				entity.initSelection);

	}

	// to Entity:
	private static PpemtSelItemOrder toEntity(SelectionItemOrder domain) {
		PpemtSelItemOrderPK key = new PpemtSelItemOrderPK(domain.getSelectionID());
		return new PpemtSelItemOrder(key, domain.getHistId(), domain.getDisporder().v(),
				domain.getInitSelection().value);
	}

	@Override
	public List<SelectionItemOrder> getAllOrderSelectionByHistId(String histId) {
		return this.queryProxy().query(SELECT_ALL_HISTORY_ID, PpemtSelItemOrder.class).setParameter("histId", histId)
				.getList(c -> toDomain(c));
	}
	
	@Override
	public Map<String, List<SelectionItemOrder>> getByHistIdList(List<String> histIdList) {
		List<SelectionItemOrder> seletionOrders = this.queryProxy()
				.query(SELECT_BY_HISTORY_ID_LIST, PpemtSelItemOrder.class).setParameter("histIdList", histIdList)
				.getList(c -> toDomain(c));
		return seletionOrders.stream().collect(Collectors.groupingBy(SelectionItemOrder::getHistId));
	}

	@Override
	public List<SelectionItemOrder> getAllOrderBySelectionId(String selectionId) {
		return this.queryProxy().query(SELECT_ALL_SELECTION_ID, PpemtSelItemOrder.class)
				.setParameter("selectionId", selectionId).getList(c -> toDomain(c));
	}

	// hoatt
	/**
	 * update List Selection Item Order
	 * 
	 * @param lstSelOrder
	 */
	@Override
	public void updateListSelOrder(List<SelectionItemOrder> lstSelOrder) {
		List<PpemtSelItemOrder> lstEntity = new ArrayList<>();
		for (SelectionItemOrder selItemOrder : lstSelOrder) {
			PpemtSelItemOrder selOrderUi = toEntity(selItemOrder);
			PpemtSelItemOrder selOrder = this.queryProxy()
					.find(new PpemtSelItemOrderPK(selItemOrder.getSelectionID()), PpemtSelItemOrder.class).get();
			selOrder.setDispOrder(selOrderUi.dispOrder);
			selOrder.setInitSelection(selOrderUi.initSelection);
			lstEntity.add(selOrder);
		}
		this.commandProxy().updateAll(lstEntity);
	}
}
