package repository.person.setting.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.setting.selection.BpemtHistorySelection;
import entity.person.setting.selection.BpemtHistorySelectionPK;
import entity.person.setting.selection.BpemtSelectionItem;
import entity.person.setting.selection.BpemtSelectionItemPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelectionRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;

@Stateless
public class JpaPerInfoHistorySelectionRepository extends JpaRepository implements PerInfoHistorySelectionRepository {

	private static final String SELECT_ALL = "SELECT si FROM BpemtHistorySelection si";
	private static final String SELECT_ALL_HISTORY_SELECTION = SELECT_ALL
			+ " WHERE si.selectionItemId = :selectionItemId";
	
	
	@Override
	public void add(PerInfoHistorySelection domain) {
		this.commandProxy().insert(toHistEntity(domain));

	}

	@Override
	public void remove(String histId) {
		BpemtHistorySelectionPK pk = new BpemtHistorySelectionPK(histId);
		this.commandProxy().remove(BpemtHistorySelection.class, pk);

	}

	private PerInfoHistorySelection toDomain(BpemtHistorySelection entity) {
		return PerInfoHistorySelection.historySelection(entity.histidPK.histidPK, entity.selectionItemId,
				entity.companyCode, entity.endDate, entity.startDate);
	}
	
	@Override
	public List<PerInfoHistorySelection> historySelection(String selectionItemId) {
		return this.queryProxy().query(SELECT_ALL_HISTORY_SELECTION, BpemtHistorySelection.class)
				.setParameter("selectionItemId", selectionItemId).getList(c -> toDomain(c));
	}
	
	public Optional<PerInfoHistorySelection> getHistorySelectionItem(String histId) {
		BpemtHistorySelectionPK pkHistorySelection = new BpemtHistorySelectionPK(histId);
		return this.queryProxy().find(pkHistorySelection, BpemtHistorySelection.class).map(c -> toDomain(c));
	}

	private static BpemtHistorySelection toHistEntity(PerInfoHistorySelection domain) {
		BpemtHistorySelectionPK key = new BpemtHistorySelectionPK(domain.getHistId());
		return new BpemtHistorySelection(key, domain.getSelectionItemId(), domain.getCompanyCode(), domain.getEndDate(),
				domain.getStartDate());
	}
}
