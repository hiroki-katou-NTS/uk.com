package repository.person.setting.selectionitem.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.setting.selectionitem.PpemtHistorySelection;
import entity.person.setting.selectionitem.selection.PpemtSelection;
import entity.person.setting.selectionitem.selection.PpemtSelectionPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionRepository;

/**
 * 
 * @author tuannv
 *
 */

@Stateless
public class JpaSelectionRepository extends JpaRepository implements SelectionRepository {

	private static final String SELECT_ALL = "SELECT si FROM PpemtSelection si";
	private static final String SELECT_ALL_HISTORY_ID = SELECT_ALL + " WHERE si.histId = :histId";
	private static final String SELECT_ALL_SELECTION_CD = SELECT_ALL
			+ " WHERE si.selectionCd = :selectionCd AND si.histId = :histId";

	private static final String SELECT_ALL_HISTORY_IDfdsfdsfsd = SELECT_ALL + " WHERE si.histId = :histId";

	@Override
	public void add(Selection selection) {
		this.commandProxy().insert(toEntity(selection));

	}

	@Override
	public void update(Selection selection) {
		this.commandProxy().update(toEntity(selection));

	}

	@Override
	public void remove(String selectionId) {
		PpemtSelectionPK pk = new PpemtSelectionPK(selectionId);
		this.commandProxy().remove(PpemtSelection.class, pk);

	}

	// Domain:
	private Selection toDomain(PpemtSelection entity) {
		return Selection.createFromSelection(entity.selectionId.selectionId, entity.histId, entity.selectionCd,
				entity.selectionName, entity.externalCd, entity.memo);

	}

	@Override
	public List<Selection> getAllHistorySelection(String histId) {
		return this.queryProxy().query(SELECT_ALL_HISTORY_ID, PpemtSelection.class).setParameter("histId", histId)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<Selection> getHistSelection(String histId) {
		return this.queryProxy().query(SELECT_ALL_HISTORY_ID, PpemtSelection.class).setParameter("histId", histId)
				.getSingle(c -> toDomain(c));
	}

	// to Entity:
	private static PpemtSelection toEntity(Selection domain) {
		PpemtSelectionPK key = new PpemtSelectionPK(domain.getSelectionID());
		return new PpemtSelection(key, domain.getHistId(), domain.getSelectionCD().v(), domain.getSelectionName().v(),
				domain.getExternalCD().v(), domain.getMemoSelection().v());

	}

	// check by selectionCD:
	@Override
	public Optional<Selection> getCheckBySelectionCD(String selectionCd) {

		return this.queryProxy().query(SELECT_ALL_SELECTION_CD, PpemtSelection.class)
				.setParameter("selectionCd", selectionCd).getSingle(c -> toDomain(c));
	}

	@Override
	public List<Selection> geSelectionList(String selectionCd, String histId) {
		return queryProxy().query(SELECT_ALL_SELECTION_CD, PpemtSelection.class)
				.setParameter("selectionCd", selectionCd).setParameter("histId", histId).getList(c -> toDomain(c));

	}

	@Override
	public List<String> getAllHist(String histId) {
		return queryProxy().query(SELECT_ALL_HISTORY_ID, String.class).setParameter("histId", histId).getList();

	}

}
