package repository.person.setting.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.setting.selection.BpsmtHistorySelection;
import entity.person.setting.selection.BpsmtHistorySelectionPK;
import entity.person.setting.selection.BpsmtSelectionItem;
import entity.person.setting.selection.BpsmtSelectionItemPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;

@Stateless
public class JpaPerInfoSelectionItemRepository extends JpaRepository implements IPerInfoSelectionItemRepository {

	private static final String SELECT_ALL = "SELECT si FROM BpsmtSelectionItem si";
	private static final String SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE = SELECT_ALL
			+ " WHERE si.contractCd = :contractCd";
	private static final String SELECT_All_SELECTION_ITEM_NAME = SELECT_ALL
			+ " WHERE si.selectionItemName = :selectionItemName";
	private static final String SELECT_All_SELECTION_ITEM_CLASSIFI = SELECT_ALL
			+ " WHERE si.selectionItemClassification = :selectionItemClassification";

	@Override
	public void add(PerInfoSelectionItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PerInfoSelectionItem domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(String selectionItemId) {
		BpsmtSelectionItemPK pk = new BpsmtSelectionItemPK(selectionItemId);
		this.commandProxy().remove(BpsmtSelectionItem.class, pk);
	}

	@Override
	public List<PerInfoSelectionItem> getAllPerInfoSelectionItem(String contractCd) {
		return this.queryProxy().query(SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE, BpsmtSelectionItem.class)
				.setParameter("contractCd", contractCd).getList(c -> toDomain(c));
	}

	private PerInfoSelectionItem toDomain(BpsmtSelectionItem entity) {
		return PerInfoSelectionItem.createFromJavaType(entity.selectionItemPk.selectionItemId, entity.selectionItemName,
				entity.memo, entity.selectionItemClsAtr, entity.contractCd, entity.integrationCd, entity.selectionCd,
				entity.characterTypeAtr, entity.selectionName, entity.selectionExtCd);
	}

	// check selectionItemId
	@Override
	public Optional<PerInfoSelectionItem> getPerInfoSelectionItem(String selectionItemId) {
		BpsmtSelectionItemPK pk = new BpsmtSelectionItemPK(selectionItemId);
		return this.queryProxy().find(pk, BpsmtSelectionItem.class).map(c -> toDomain(c));
	}

	// check selectionItemName
	@Override
	public Optional<PerInfoSelectionItem> checkItemName(String selectionItemName) {
		return this.queryProxy().query(SELECT_All_SELECTION_ITEM_NAME, BpsmtSelectionItem.class)
				.setParameter("selectionItemName", selectionItemName).getSingle(c -> toDomain(c));
	}

	// check SelectionItemClassification
	@Override
	public Optional<PerInfoSelectionItem> checkItemClassification(String selectionItemClassification) {
		return this.queryProxy().query(SELECT_All_SELECTION_ITEM_CLASSIFI, BpsmtSelectionItem.class)
				.setParameter("selectionItemClassification", selectionItemClassification).getSingle(c -> toDomain(c));

	}

	@Override
	public boolean checkExist(String selectionItemId) {
		// TODO Auto-generated method stub
		return false;
	}

	private static BpsmtSelectionItem toEntity(PerInfoSelectionItem domain) {
		BpsmtSelectionItemPK key = new BpsmtSelectionItemPK(domain.getSelectionItemId());
		return new BpsmtSelectionItem(key, domain.getSelectionItemName().v(), domain.getContractCode(),
				domain.getIntegrationCode().v(), domain.getSelectionItemClassification().value,
				domain.getFormatSelection().getSelectionCode().v(), domain.getFormatSelection().getSelectionName().v(),
				domain.getFormatSelection().getSelectionExternalCode().v(),
				domain.getFormatSelection().getSelectionCodeCharacter().value, domain.getMemo().v());

	}

}
