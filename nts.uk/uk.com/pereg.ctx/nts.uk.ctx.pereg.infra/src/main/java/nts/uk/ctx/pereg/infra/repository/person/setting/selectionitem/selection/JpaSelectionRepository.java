package nts.uk.ctx.pereg.infra.repository.person.setting.selectionitem.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelectionItem;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection.PpemtSelectionItemPK;

/**
 * 
 * @author tuannv
 *
 */

@Stateless
public class JpaSelectionRepository extends JpaRepository implements SelectionRepository {

	private static final String SELECT_ALL = "SELECT si FROM PpemtSelectionItem si";
	
	private static final String SELECT_ALL_HISTORY_ID = SELECT_ALL + " WHERE si.histId = :histId";
	
	private static final String SELECT_ALL_HISTORY_ID_LIST = SELECT_ALL + " WHERE si.histId IN :histIdList";
	
	private static final String SELECT_ALL_IN_SELECTION_ITEM_ID = SELECT_ALL 
			+ " INNER JOIN PpemtSelectionHist his ON si.histId = his.histidPK.histId"
			+ " WHERE his.selectionItemId = :selectionItemId";
	
	private static final String SELECT_ALL_SELECTION_CD = SELECT_ALL
			+ " WHERE si.selectionCd = :selectionCd AND si.histId = :histId";
	
	private static final String SELECT_ALL_SELECTION_BY_SELECTIONID = SELECT_ALL
			+ " WHERE si.selectionId = :selectionId";
	
	// selection for company
	private static final String SEL_ALL_BY_SEL_ID_PERSON_TYPE_BY_CID = " SELECT se , item.selectionItemName FROM PpemtSelectionDef item"
			+ " INNER JOIN PpemtSelectionHist his ON item.selectionItemPk.selectionItemId = his.selectionItemId" 
			+ " INNER JOIN PpemtSelectionItem se ON his.histidPK.histId = se.histId" 
			+ " INNER JOIN PpemtSelectionItemSort order ON his.histidPK.histId = order.histId"
			+ " AND se.selectionId.selectionId = order.selectionIdPK.selectionId " 
			+ " WHERE his.startDate <= :baseDate AND his.endDate >= :baseDate " 
			+ " AND item.selectionItemPk.selectionItemId =:selectionItemId"
			+ " AND his.companyId =:companyId"
			+ " ORDER BY order.dispOrder";
	
	private static final String SEL_ALL_BY_SEL_ID = " SELECT se FROM PpemtSelectionDef  item"
			+ " INNER JOIN PpemtSelectionHist his "
			+ " ON item.selectionItemPk.selectionItemId = his.selectionItemId" + " INNER JOIN PpemtSelectionItem se"
			+ " ON his.histidPK.histId = se.histId" + " INNER JOIN PpemtSelectionItemSort order"
			+ " ON his.histidPK.histId = order.histId "
			+ " AND se.selectionId.selectionId = order.selectionIdPK.selectionId " + " WHERE his.startDate <= :baseDate"
			+ " AND his.endDate >= :baseDate " + " AND item.selectionItemPk.selectionItemId =:selectionItemId"
			+ " ORDER BY order.dispOrder";
	// Lanlt

	@Override
	public void add(Selection selection) {
		this.commandProxy().insert(toEntity(selection));

	}
	
	@Override
	public void addAll(List<Selection> selectionList) {
		List<PpemtSelectionItem> entities = selectionList.stream().map(domain -> toEntity(domain))
				.collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}

	@Override
	public void update(Selection selection) {
		PpemtSelectionItem newEntity = toEntity(selection);
		PpemtSelectionItem updateEntity = this.queryProxy().find(newEntity.selectionId, PpemtSelectionItem.class).get();
		updateEntity.selectionName = newEntity.selectionName;
		updateEntity.externalCd = newEntity.externalCd;
		updateEntity.histId = newEntity.histId;
		updateEntity.selectionCd = newEntity.selectionCd;
		updateEntity.memo = newEntity.memo;
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void remove(String selectionId) {
		PpemtSelectionItemPK pk = new PpemtSelectionItemPK(selectionId);
		this.commandProxy().remove(PpemtSelectionItem.class, pk);
	}
	
	@Override
	public void removeAll(List<String> selectionIds) {
		if (selectionIds.isEmpty()) {
			return;
		}
		List<PpemtSelectionItemPK> keys = selectionIds.stream().map(x -> new PpemtSelectionItemPK(x))
				.collect(Collectors.toList());
		this.commandProxy().removeAll(PpemtSelectionItem.class, keys);
	}
	
	@Override
	public void removeInSelectionItemId(String selectionItemId) {
		List<PpemtSelectionItem> selectionList = this.queryProxy()
				.query(SELECT_ALL_IN_SELECTION_ITEM_ID, PpemtSelectionItem.class)
				.setParameter("selectionItemId", selectionItemId).getList();
		this.commandProxy().removeAll(selectionList);
	}

	// Domain:
	private Selection toDomain(PpemtSelectionItem entity) {
		return Selection.createFromSelection(entity.selectionId.selectionId, entity.histId, entity.selectionCd,
				entity.selectionName, entity.externalCd, entity.memo);

	}
	
	// Domain:
	private Selection toDomain(Object[] entity) {
		PpemtSelectionItem sel =  (PpemtSelectionItem) entity[0];
		Selection sel1 = Selection.createFromSelection(sel.selectionId.selectionId, sel.histId, sel.selectionCd,
				sel.selectionName, sel.externalCd, sel.memo, entity[1].toString());
		return sel1;

	}
	
	@Override
	public List<Selection> getAllSelectByHistId(String histId) {
		return this.queryProxy().query(SELECT_ALL_HISTORY_ID, PpemtSelectionItem.class).setParameter("histId", histId)
				.getList(c -> toDomain(c));
	}
	
	@Override
	public List<Selection> getByHistIdList(List<String> histIdList) {
		if (histIdList.isEmpty()) {
			return new ArrayList<>();
		}
		List<Selection> selections = new ArrayList<>(); 
		CollectionUtil.split(histIdList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			selections.addAll(this.queryProxy().query(SELECT_ALL_HISTORY_ID_LIST, PpemtSelectionItem.class)
				.setParameter("histIdList", subList).getList(c -> toDomain(c)));
		});
		return selections;
	}

	// to Entity:
	private static PpemtSelectionItem toEntity(Selection domain) {
		PpemtSelectionItemPK key = new PpemtSelectionItemPK(domain.getSelectionID());
		return new PpemtSelectionItem(key, domain.getHistId(), domain.getSelectionCD().v(), domain.getSelectionName().v(),
				domain.getExternalCD().v(), domain.getMemoSelection().v());

	}

	// check by selectionCD:
	@Override
	public Optional<Selection> getSelectionBySelectionCd(String selectionCd) {

		return this.queryProxy().query(SELECT_ALL_SELECTION_CD, PpemtSelectionItem.class)
				.setParameter("selectionCd", selectionCd).getSingle(c -> toDomain(c));
	}

	@Override
	public List<Selection> getAllSelectionBySelectionCdAndHistId(String selectionCd, String histId) {
		return queryProxy().query(SELECT_ALL_SELECTION_CD, PpemtSelectionItem.class)
				.setParameter("selectionCd", selectionCd).setParameter("histId", histId).getList(c -> toDomain(c));

	}

	@Override
	public List<String> getAllHistId(String histId) {
		return queryProxy().query(SELECT_ALL_HISTORY_ID, String.class).setParameter("histId", histId).getList();

	}

	@Override
	public List<Selection> getAllSelectionBySelectionID(String selectionId) {
		return this.queryProxy().query(SELECT_ALL_SELECTION_BY_SELECTIONID, PpemtSelectionItem.class)
				.setParameter("selectionId", selectionId).getList(c -> toDomain(c));
	}

	@Override
	public List<Selection> getAllSelectionByHistoryId(String selectionItemId, GeneralDate baseDate) {
		List<Selection> selectionLst = this.queryProxy().query(SEL_ALL_BY_SEL_ID, PpemtSelectionItem.class)
				.setParameter("selectionItemId", selectionItemId).setParameter("baseDate", baseDate)
				.getList(c -> toDomain(c));
		return selectionLst;
	}

	@Override
	public List<Selection> getAllSelectionByCompanyId(String companyId, String selectionItemId, GeneralDate baseDate) {
		List<Selection> selectionLst = this.queryProxy().query(SEL_ALL_BY_SEL_ID_PERSON_TYPE_BY_CID, Object[].class)
				.setParameter("selectionItemId", selectionItemId).setParameter("baseDate", baseDate)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
		return selectionLst;
	}

}
