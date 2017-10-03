package repository.person.setting.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.setting.selection.BpemtSelectionItem;
import entity.person.setting.selection.BpemtSelectionItemPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;

@Stateless
public class JpaPerInfoSelectionItemRepository extends JpaRepository implements IPerInfoSelectionItemRepository {

	private static final String SELECT_ALL = "SELECT si FROM BpemtSelectionItem si";
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
		BpemtSelectionItemPK pk = new BpemtSelectionItemPK(selectionItemId);
		this.commandProxy().remove(BpemtSelectionItem.class, pk);
	}

	@Override
	public List<PerInfoSelectionItem> getAllPerInfoSelectionItem(String contractCd) {
		return this.queryProxy().query(SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE, BpemtSelectionItem.class)
				.setParameter("contractCd", contractCd).getList(c -> toDomain(c));
	}

	private PerInfoSelectionItem toDomain(BpemtSelectionItem entity) {
		return PerInfoSelectionItem.createFromJavaType(entity.selectionItemPk.selectionItemId, entity.selectionItemName,
				entity.memo, entity.selectionItemClsAtr, entity.contractCd, entity.integrationCd, entity.selectionCd,
				entity.characterTypeAtr, entity.selectionName, entity.selectionExtCd);
	}

	// check selectionItemId
	@Override
	public Optional<PerInfoSelectionItem> getPerInfoSelectionItem(String selectionItemId) {
		BpemtSelectionItemPK pk = new BpemtSelectionItemPK(selectionItemId);
		return this.queryProxy().find(pk, BpemtSelectionItem.class).map(c -> toDomain(c));
	}

	// check selectionItemName
	@Override
	public Optional<PerInfoSelectionItem> checkItemName(String selectionItemName) {
		return this.queryProxy().query(SELECT_All_SELECTION_ITEM_NAME, BpemtSelectionItem.class)
				.setParameter("selectionItemName", selectionItemName).getSingle(c -> toDomain(c));
	}

	// check SelectionItemClassification
	@Override
	public Optional<PerInfoSelectionItem> checkItemClassification(String selectionItemClassification) {
		return this.queryProxy().query(SELECT_All_SELECTION_ITEM_CLASSIFI, BpemtSelectionItem.class)
				.setParameter("selectionItemClassification", selectionItemClassification).getSingle(c -> toDomain(c));

	}

	@Override
	public boolean checkExist(String selectionItemId) {
		// TODO Auto-generated method stub
		return false;
	}

	private static BpemtSelectionItem toEntity(PerInfoSelectionItem domain) {
		BpemtSelectionItemPK key = new BpemtSelectionItemPK(domain.getSelectionItemId());
		return new BpemtSelectionItem(key, domain.getSelectionItemName().v(), domain.getContractCode(),
				domain.getIntegrationCode().v(), domain.getSelectionItemClassification().value,
				domain.getFormatSelection().getSelectionCode().v(), domain.getFormatSelection().getSelectionName().v(),
				domain.getFormatSelection().getSelectionExternalCode().v(),
				domain.getFormatSelection().getSelectionCodeCharacter().value, domain.getMemo().v());

	}

}
