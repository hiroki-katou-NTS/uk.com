package nts.uk.ctx.pereg.infra.repository.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoSelectionItem;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtSelectionItem;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtSelectionItemPK;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class JpaPerInfoSelectionItemRepository extends JpaRepository implements IPerInfoSelectionItemRepository {

	private static final String SELECT_ALL = "SELECT si FROM PpemtSelectionItem si";
	private static final String SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE = "SELECT DISTINCT si FROM PpemtSelectionItem si INNER JOIN PpemtHistorySelection hs ON si.selectionItemPk.selectionItemId = hs.selectionItemId  WHERE si.contractCd = :contractCd AND hs.histidPK.histId IS NOT NULL "
			+ " ORDER BY si.selectionItemName ";

	private static final String SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE_AND_CID = "SELECT DISTINCT si FROM PpemtSelectionItem si INNER JOIN PpemtHistorySelection hs ON si.selectionItemPk.selectionItemId = hs.selectionItemId  WHERE si.contractCd = :contractCd AND hs.histidPK.histId IS NOT NULL AND hs.companyId=:companyId "
			+ " ORDER BY si.selectionItemName ";
	private static final String SELECT_All_SELECTION_ITEM_NAME = SELECT_ALL
			+ " WHERE si.selectionItemName = :selectionItemName";
	private static final String SELECT_ALL_BY_PERSON_TYPE = "SELECT si FROM PpemtSelectionItem si"
			+ " WHERE si.selectionItemClsAtr =:selectionItemClsAtr AND si.contractCd = :contractCode" 
			+ " ORDER BY si.selectionItemName ";
	private static final String SELECT_BY_HIST_ID = SELECT_ALL
			+ " INNER JOIN PpemtHistorySelection hs ON si.selectionItemPk.selectionItemId = hs.selectionItemId WHERE hs.histidPK.histId=:histId";

	@Override
	public void add(PerInfoSelectionItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	private void updateEntity(PerInfoSelectionItem domain, PpemtSelectionItem entity) {
		if (domain.getSelectionItemName() != null) {
			entity.setSelectionItemName(domain.getSelectionItemName().v());
		}

		if (domain.getIntegrationCode() != null) {
			entity.setIntegrationCd(domain.getIntegrationCode().v());
		}

		if (domain.getMemo() != null) {
			entity.setMemo(domain.getMemo().v());
		}
	}

	@Override
	public void update(PerInfoSelectionItem domain) {
		Optional<PpemtSelectionItem> existSelItem = this.queryProxy()
				.find(new PpemtSelectionItemPK(domain.getSelectionItemId()), PpemtSelectionItem.class);
		if (!existSelItem.isPresent()) {
			throw new RuntimeException("invalid PpemtSelectionItem!");
		}
		updateEntity(domain, existSelItem.get());

		this.commandProxy().update(existSelItem.get());
	}

	@Override
	public void remove(String selectionItemId) {
		PpemtSelectionItemPK pk = new PpemtSelectionItemPK(selectionItemId);
		this.commandProxy().remove(PpemtSelectionItem.class, pk);
	}

	@Override
	public List<PerInfoSelectionItem> getAllSelectionItemByContractCdAndCID(String contractCd, String companyId) {

		return this.queryProxy().query(SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE_AND_CID, PpemtSelectionItem.class)
				.setParameter("contractCd", contractCd).setParameter("companyId", companyId).getList(c -> toDomain(c));

	}

	@Override
	public List<PerInfoSelectionItem> getAllSelectionItemByContractCd(String contractCd) {
		return this.queryProxy().query(SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE, PpemtSelectionItem.class)
				.setParameter("contractCd", contractCd).getList(c -> toDomain(c));

	}

	private PerInfoSelectionItem toDomain(PpemtSelectionItem entity) {
		return PerInfoSelectionItem.createFromJavaType(entity.selectionItemPk.selectionItemId, entity.selectionItemName,
				entity.memo, entity.selectionItemClsAtr, entity.contractCd, entity.integrationCd, entity.selectionCd,
				entity.characterTypeAtr, entity.selectionName, entity.selectionExtCd);
	}

	// check selectionItemId
	@Override
	public Optional<PerInfoSelectionItem> getSelectionItemBySelectionItemId(String selectionItemId) {
		PpemtSelectionItemPK pk = new PpemtSelectionItemPK(selectionItemId);
		return this.queryProxy().find(pk, PpemtSelectionItem.class).map(c -> toDomain(c));
	}

	// get selectionItem By Name
	@Override
	public Optional<PerInfoSelectionItem> getSelectionItemByName(String selectionItemName) {
		return this.queryProxy().query(SELECT_All_SELECTION_ITEM_NAME, PpemtSelectionItem.class)
				.setParameter("selectionItemName", selectionItemName).getSingle(c -> toDomain(c));
	}

	private static PpemtSelectionItem toEntity(PerInfoSelectionItem domain) {
		PpemtSelectionItemPK key = new PpemtSelectionItemPK(domain.getSelectionItemId());
		return new PpemtSelectionItem(key, domain.getSelectionItemName().v(), domain.getContractCode(),
				domain.getIntegrationCode().v(), domain.getSelectionItemClassification().value,
				domain.getFormatSelection().getSelectionCode().v(), domain.getFormatSelection().getSelectionName().v(),
				domain.getFormatSelection().getSelectionExternalCode().v(),
				domain.getFormatSelection().getSelectionCodeCharacter().value, domain.getMemo().v());

	}

	// Lanlt
	@Override
	public List<PerInfoSelectionItem> getAllSelection(int selectionItemClsAtr, String contractCode) {
		return this.queryProxy().query(SELECT_ALL_BY_PERSON_TYPE, PpemtSelectionItem.class)
				.setParameter("selectionItemClsAtr", selectionItemClsAtr)
				.setParameter("contractCode", contractCode)
				.getList(c -> toDomain(c));
	}
	// Lanlt

	@Override
	public Optional<PerInfoSelectionItem> getSelectionItemByHistId(String histId) {
		return this.queryProxy().query(SELECT_BY_HIST_ID, PpemtSelectionItem.class).setParameter("histId", histId)
				.getSingle().map(c -> toDomain(c));

	}

}
