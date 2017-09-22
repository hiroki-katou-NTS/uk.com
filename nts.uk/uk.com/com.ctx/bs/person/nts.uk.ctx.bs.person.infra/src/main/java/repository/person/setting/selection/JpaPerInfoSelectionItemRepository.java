package repository.person.setting.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.setting.selection.BpsmtSelectionItem;
import entity.person.setting.selection.BpsmtSelectionItemPK;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;

@Stateless
public class JpaPerInfoSelectionItemRepository extends JpaRepository implements IPerInfoSelectionItemRepository {

	private static final String SELECT_ALL = "SELECT si FROM BpsmtSelectionItem si";

	private static final String SELECT_ALL_PERSON_INFO_SELECTION_ITEMS_BY_CONTRACTCODE_QUERY = SELECT_ALL
			+ " WHERE si.contractCd = :contractCd";

	@Override
	public void add(PerInfoSelectionItem domain) {

		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(PerInfoSelectionItem domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(PerInfoSelectionItem domain) {
		BpsmtSelectionItemPK pk = new BpsmtSelectionItemPK(toEntity(domain).selectionItemPk.selectionItemId);
		this.commandProxy().remove(BpsmtSelectionItem.class, pk);
	}

	@Override
	public List<PerInfoSelectionItem> getAllPerInfoSelectionItem(String contractCd) {
		return this.queryProxy()
				.query(SELECT_ALL_PERSON_INFO_SELECTION_ITEMS_BY_CONTRACTCODE_QUERY, BpsmtSelectionItem.class)
				.setParameter("contractCd", contractCd).getList(c -> toDomain(c));
	}

	private PerInfoSelectionItem toDomain(BpsmtSelectionItem entity) {

		return PerInfoSelectionItem.createFromJavaType(entity.selectionItemPk.selectionItemId, entity.selectionItemName,
				entity.memo, entity.selectionItemClsAtr, entity.contractCd, entity.integrationCd, entity.selectionCd,
				entity.characterTypeAtr, entity.selectionName, entity.selectionExtCd);

	}

	@Override
	public Optional<PerInfoSelectionItem> getPerInfoSelectionItem(String selectionItemId) {
		BpsmtSelectionItemPK pk = new BpsmtSelectionItemPK(selectionItemId);
		return this.queryProxy().find(pk, BpsmtSelectionItem.class).map(c -> toDomain(c));
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
