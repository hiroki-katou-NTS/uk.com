package nts.uk.ctx.pereg.infra.repository.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.export.PersonSelectionItemExportData;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.PerInfoSelectionItem;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.SelectionItemReportData;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtSelectionItem;
import nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.PpemtSelectionItemPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class JpaPerInfoSelectionItemRepository extends JpaRepository implements IPerInfoSelectionItemRepository {

	private static final String SELECT_ALL = "SELECT si FROM PpemtSelectionItem si";
	private static final String SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE = "SELECT si FROM PpemtSelectionItem si WHERE si.contractCd = :contractCd"
			+ " ORDER BY si.selectionItemName";

	private static final String SELECT_ALL_SELECTION_ITEM_BY_CONTRACTCODE_AND_CID = "SELECT DISTINCT si FROM PpemtSelectionItem si INNER JOIN PpemtHistorySelection hs ON si.selectionItemPk.selectionItemId = hs.selectionItemId  WHERE si.contractCd = :contractCd AND hs.histidPK.histId IS NOT NULL AND hs.companyId=:companyId "
			+ " ORDER BY si.selectionItemName ";
	private static final String SELECT_SELECTION_ITEM_NAME = SELECT_ALL
			+ " WHERE si.contractCd = :contractCode AND si.selectionItemName = :selectionItemName";

	private static final String SELECT_SELECTION_ITEM_NAME1 = SELECT_ALL
			+ " WHERE si.contractCd = :contractCode AND si.selectionItemName = :selectionItemName"
			+ " AND si.selectionItemPk.selectionItemId <> :selectionItemId";

	private static final String SELECT_ALL_BY_PERSON_TYPE = "SELECT si FROM PpemtSelectionItem si"
			+ " WHERE si.contractCd = :contractCode" + " ORDER BY si.selectionItemName ";
	private static final String SELECT_BY_HIST_ID = SELECT_ALL
			+ " INNER JOIN PpemtHistorySelection hs ON si.selectionItemPk.selectionItemId = hs.selectionItemId WHERE hs.histidPK.histId=:histId";

	private static final String PERSON_SELECT_ALL = "SELECT si.selectionItemName, hs.startDate, hs.endDate, so.initSelection, ss.selectionCd, ss.selectionName, ss.externalCd, ss.memo FROM PpemtSelectionItem si "
			+ "LEFT JOIN PpemtHistorySelection hs ON si.selectionItemPk.selectionItemId = hs.selectionItemId "
			+ "LEFT JOIN PpemtSelItemOrder so ON hs.histidPK.histId = so.histId "
			+ "LEFT JOIN PpemtSelection ss ON so.histId = ss.histId AND so.selectionIdPK.selectionId = ss.selectionId.selectionId "
			+ "WHERE si.contractCd =:contractCd AND hs.companyId =:companyId";

	@Override
	public void add(PerInfoSelectionItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PerInfoSelectionItem domain) {
		Optional<PpemtSelectionItem> existSelItem = this.queryProxy()
				.find(new PpemtSelectionItemPK(domain.getSelectionItemId()), PpemtSelectionItem.class);

		if (!existSelItem.isPresent()) {
			throw new RuntimeException("invalid PpemtSelectionItem!");
		}

		PpemtSelectionItem entity = existSelItem.get();

		updateEntity(domain, entity);

		this.commandProxy().update(entity);
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
		return PerInfoSelectionItem.createFromJavaType(entity.contractCd, entity.selectionItemPk.selectionItemId,
				entity.selectionItemName, entity.characterTypeAtr, entity.codeLength, entity.nameLength,
				entity.extCodeLength, entity.integrationCd, entity.memo);
	}

	// check selectionItemId
	@Override
	public Optional<PerInfoSelectionItem> getSelectionItemBySelectionItemId(String selectionItemId) {
		PpemtSelectionItemPK pk = new PpemtSelectionItemPK(selectionItemId);
		return this.queryProxy().find(pk, PpemtSelectionItem.class).map(c -> toDomain(c));
	}

	@Override
	public Optional<PerInfoSelectionItem> getSelectionItemByName(String contractCode, String selectionItemName,
			String selectionItemId) {
		if (selectionItemId != null) {
			return this.queryProxy().query(SELECT_SELECTION_ITEM_NAME1, PpemtSelectionItem.class)
					.setParameter("selectionItemName", selectionItemName).setParameter("contractCode", contractCode)
					.setParameter("selectionItemId", selectionItemId).getSingle(c -> toDomain(c));
		} else {
			return this.queryProxy().query(SELECT_SELECTION_ITEM_NAME, PpemtSelectionItem.class)
					.setParameter("selectionItemName", selectionItemName).setParameter("contractCode", contractCode)
					.getSingle(c -> toDomain(c));
		}

	}

	private PpemtSelectionItem toEntity(PerInfoSelectionItem domain) {
		PpemtSelectionItemPK key = new PpemtSelectionItemPK(domain.getSelectionItemId());
		PpemtSelectionItem entity = new PpemtSelectionItem();

		entity.selectionItemPk = key;
		entity.contractCd = domain.getContractCode();
		entity.selectionItemName = domain.getSelectionItemName().v();

		entity.characterTypeAtr = domain.getFormatSelection().getCharacterType().value;
		entity.codeLength = domain.getFormatSelection().getCodeLength().v();
		entity.nameLength = domain.getFormatSelection().getNameLength().v();
		entity.extCodeLength = domain.getFormatSelection().getExternalCodeLength().v();

		entity.integrationCd = domain.getIntegrationCode().isPresent() ? domain.getIntegrationCode().get().v() : null;
		entity.memo = domain.getMemo().isPresent() ? domain.getMemo().get().v() : null;

		return entity;
	}

	private void updateEntity(PerInfoSelectionItem domain, PpemtSelectionItem entity) {
		entity.selectionItemName = domain.getSelectionItemName().v();
		entity.integrationCd = domain.getIntegrationCode().isPresent() ? domain.getIntegrationCode().get().v() : null;
		entity.memo = domain.getMemo().isPresent() ? domain.getMemo().get().v() : null;
	}

	// Lanlt
	@Override
	public List<PerInfoSelectionItem> getAllSelection(String contractCode) {
		return this.queryProxy().query(SELECT_ALL_BY_PERSON_TYPE, PpemtSelectionItem.class)
				.setParameter("contractCode", contractCode).getList(c -> toDomain(c));
	}
	// Lanlt

	@Override
	public Optional<PerInfoSelectionItem> getSelectionItemByHistId(String histId) {
		return this.queryProxy().query(SELECT_BY_HIST_ID, PpemtSelectionItem.class).setParameter("histId", histId)
				.getSingle().map(c -> toDomain(c));

	}

	@Override
	public List<PersonSelectionItemExportData> findAllSelection(String contractCd) {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(PERSON_SELECT_ALL, Object[].class).setParameter("contractCd", contractCd).setParameter("companyId", companyId)
				.getList(x -> toReportData(x));
	}
	
	@Override
	public List<SelectionItemReportData> findByContractCd(String contractCd) {
		return this.queryProxy().query(SELECT_ALL_BY_PERSON_TYPE, PpemtSelectionItem.class)
				.setParameter("contractCode", contractCd)
				.getList(c -> toReportData(c));
	}
	
	private SelectionItemReportData toReportData (PpemtSelectionItem entity) {
		return new SelectionItemReportData(entity.getSelectionItemName(), entity.getCharacterTypeAtr(), entity.getCodeLength(), entity.getNameLength(), entity.getExtCodeLength(), entity.getIntegrationCd(), entity.getMemo());
	}

	private PersonSelectionItemExportData toReportData(Object[] entity) {
		// TODO Auto-generated method stub
		return new PersonSelectionItemExportData(
					entity[0] == null ? null :entity[0].toString(), 
					entity[1] == null ? null : (GeneralDate) entity[1], 
					entity[2] == null ? null :(GeneralDate) entity[2],
					entity[3] == null ? null : (Integer) entity[3],
					entity[4] == null ? null : entity[4].toString(),
					entity[5] == null ? null : entity[5].toString(),
					entity[6] == null ? null : entity[6].toString(),
					entity[7] == null ? null : entity[7].toString()
				);
	}

}
