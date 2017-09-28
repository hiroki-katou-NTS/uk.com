package repository.person.setting.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.setting.selection.BpsmtHistorySelection;
import entity.person.setting.selection.BpsmtHistorySelectionPK;
import entity.person.setting.selection.BpsmtSelectionItem;
import entity.person.setting.selection.BpsmtSelectionItemPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelectionRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;

@Stateless
public class JpaPerInfoHistorySelectionRepository extends JpaRepository implements PerInfoHistorySelectionRepository {

	private static final String SELECT_ALL = "SELECT si FROM BpsmtHistorySelection si";
	private static final String SELECT_ALL_HISTORY_SELECTION = SELECT_ALL
			+ " WHERE si.selectionItemId = :selectionItemId";
	
	
	@Override
	public void add(PerInfoHistorySelection domain) {
		this.commandProxy().insert(toHistEntity(domain));

	}

	@Override
	public void remove(String histId) {
		BpsmtHistorySelectionPK pk = new BpsmtHistorySelectionPK(histId);
		this.commandProxy().remove(BpsmtHistorySelection.class, pk);

	}

	private PerInfoHistorySelection toDomain(BpsmtHistorySelection entity) {
		return PerInfoHistorySelection.historySelection(entity.histidPK.histidPK, entity.selectionItemId,
				entity.companyCode, entity.endDate, entity.startDate);
	}
	
	@Override
	public List<PerInfoHistorySelection> historySelection(String selectionItemId) {
		return this.queryProxy().query(SELECT_ALL_HISTORY_SELECTION, BpsmtHistorySelection.class)
				.setParameter("selectionItemId", selectionItemId).getList(c -> toDomain(c));
	}
	
	// check histId
	public Optional<PerInfoHistorySelection> getHistorySelectionItem(String histId) {
		BpsmtHistorySelectionPK pkHistorySelection = new BpsmtHistorySelectionPK(histId);
		return this.queryProxy().find(pkHistorySelection, BpsmtHistorySelection.class).map(c -> toDomain(c));
	}

	private static BpsmtHistorySelection toHistEntity(PerInfoHistorySelection domain) {
		BpsmtHistorySelectionPK key = new BpsmtHistorySelectionPK(domain.getHistId());
		return new BpsmtHistorySelection(key, domain.getSelectionItemId(), domain.getCompanyCode(), domain.getEndDate(),
				domain.getStartDate());
	}
}
