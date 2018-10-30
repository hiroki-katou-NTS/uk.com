package nts.uk.ctx.pereg.infra.repository.person.additemdata.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.infra.entity.person.additemdata.item.PpemtEmpInfoItemData;
import nts.uk.ctx.pereg.infra.entity.person.additemdata.item.PpemtEmpInfoItemDataPk;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItemCm;
import nts.uk.shr.pereg.app.ItemValueType;

@Stateless
public class JpaEmpInfoItemDataRepository extends JpaRepository implements EmpInfoItemDataRepository {

	private static final String SELECT_ALL_INFO_ITEM_NO_WHERE = "SELECT id,pi.requiredAtr,pi.itemName,pi.itemCd,pc.ppemtPerInfoCtgPK.perInfoCtgId,pc.categoryCd,pm.itemType,pm.selectionItemRefType,pm.selectionItemRefCode"
			+ " FROM PpemtEmpInfoItemData id"
			+ " INNER JOIN PpemtPerInfoItem pi ON id.ppemtEmpInfoItemDataPk.perInfoDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg pc ON id.ppemtEmpInfoItemDataPk.recordId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm pm ON pi.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd"
			+ " INNER JOIN PpemtEmpInfoCtgData ic ON id.ppemtEmpInfoItemDataPk.recordId = ic.recordId";

	public static final String SELECT_ALL_INFO_ITEM_BY_CTD_CODE_QUERY_STRING = SELECT_ALL_INFO_ITEM_NO_WHERE
			+ " WHERE pi.abolitionAtr=0 AND pc.categoryCd = :categoryCd AND pc.cid = :companyId AND ic.employeeId= :employeeId";

	private static final String SELECT_ALL_INFO_ITEM_NO_WHERE_2 = "SELECT id, pi, pc ,pm FROM PpemtEmpInfoItemData id"
			+ " INNER JOIN PpemtPerInfoItem pi ON id.ppemtEmpInfoItemDataPk.perInfoDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg pc ON pi.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm pm ON pi.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd";
	private static final String SELECT_ALL_INFO_ITEM_BY_RECODE_ID_QUERY_STRING = SELECT_ALL_INFO_ITEM_NO_WHERE_2
			+ " WHERE id.ppemtEmpInfoItemDataPk.recordId = :recordId";

	private static final String SELECT_ALL_INFO_ITEM_BY_ITEMDEF_ID_AND_RECODE_ID = SELECT_ALL_INFO_ITEM_NO_WHERE_2
			+ " WHERE id.ppemtEmpInfoItemDataPk.perInfoDefId = :perInfoDefId AND id.ppemtEmpInfoItemDataPk.recordId = :recordId";
	
	

	private static final String SELECT_ALL_INFO_ITEM_BY_CTGID_AND_SID = SELECT_ALL_INFO_ITEM_NO_WHERE
			+ " WHERE ic.personInfoCtgId = :ctgid AND ic.employeeId = :sid";
	
	
	private static final String DELETE_ITEM_DATA = "DELETE FROM PpemtEmpInfoItemData WHERE ppemtEmpInfoItemDataPk.recordId = :recordId";
	
	private static final String SELECT_ITEM_DATA_OF_RECORD_ID_LIST = "SELECT id FROM PpemtEmpInfoItemData id "
			+ "WHERE id.ppemtEmpInfoItemDataPk.perInfoDefId = :itemId AND id.ppemtEmpInfoItemDataPk.recordId IN :recordIds";

	public static final String SELECT_ALL_INFO_ITEM_BY_ALL_CID_QUERY_STRING = "SELECT id.ppemtEmpInfoItemDataPk.perInfoDefId"
			+ " FROM PpemtEmpInfoItemData id"
			+ " INNER JOIN PpemtPerInfoItem pi ON id.ppemtEmpInfoItemDataPk.perInfoDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg pc ON pi.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm pm ON pi.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd"
			+ " WHERE pm.ppemtPerInfoItemCmPK.itemCd =:itemCd"
			+ " AND pi.perInfoCtgId IN :perInfoCtgId";

	@Override
	public List<EmpInfoItemData> getAllInfoItem(String categoryCd, String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_CTD_CODE_QUERY_STRING, Object[].class)
				.setParameter("categoryCd", categoryCd).setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId).getList(c -> toDomain(c));
	}

	private EmpInfoItemData toDomainNew(Object[] entity) {
		PpemtEmpInfoItemData itemData = (PpemtEmpInfoItemData) entity[0];
		PpemtPerInfoItem personInforItem = (PpemtPerInfoItem) entity[1];
		PpemtPerInfoCtg personInforCategory = (PpemtPerInfoCtg) entity[2];
		PpemtPerInfoItemCm perInfoItemCm = (PpemtPerInfoItemCm) entity[3];

		return EmpInfoItemData.createFromJavaType(personInforItem.itemCd,
				personInforItem.ppemtPerInfoItemPK.perInfoItemDefId, itemData.ppemtEmpInfoItemDataPk.recordId,
				personInforCategory.ppemtPerInfoCtgPK.perInfoCtgId, personInforCategory.categoryCd,
				personInforItem.itemName, personInforItem.requiredAtr, itemData.saveDataType, itemData.stringValue,
				itemData.intValue, itemData.dateValue,
				perInfoItemCm.dataType == null ? itemData.saveDataType : perInfoItemCm.dataType.intValue());

	}

	private EmpInfoItemData toDomain(Object[] entity) {
		int dataStateType = entity[4] != null ? Integer.valueOf(entity[3].toString()) : 0;

		BigDecimal intValue = new BigDecimal(entity[6] != null ? Integer.valueOf(entity[6].toString()) : null);

		GeneralDate dateValue = GeneralDate.fromString(String.valueOf(entity[7].toString()), "yyyy-MM-dd");

		int isRequired = Integer.parseInt(entity[8] != null ? entity[9].toString() : "0");

		int dataType = Integer.parseInt(entity[13] != null ? entity[13].toString() : "0");

		int selectionItemRefType = Integer.parseInt(entity[14] != null ? entity[14].toString() : "0");

		String selectionItemRefCd = entity[15] != null ? entity[15].toString() : "";

		EmpInfoItemData newInfoItem = EmpInfoItemData.createFromJavaType(entity[10].toString(), entity[0].toString(),
				entity[1].toString(), entity[11].toString(), entity[12].toString(), entity[9].toString(), isRequired,
				dataStateType, entity[5].toString(), intValue, dateValue, dataType);

		newInfoItem.setSelectionItemRefType(selectionItemRefType);

		newInfoItem.setSelectionItemRefCd(selectionItemRefCd);
		
		return newInfoItem;
	}

	@Override
	public List<EmpInfoItemData> getAllInfoItemByRecordId(String recordId) {
		List<Object[]> lstEntity = this.queryProxy()
				.query(SELECT_ALL_INFO_ITEM_BY_RECODE_ID_QUERY_STRING, Object[].class)
				.setParameter("recordId", recordId).getList();
		if (lstEntity == null)
			return new ArrayList<>();
		return lstEntity.stream().map(c -> toDomainNew(c)).collect(Collectors.toList());
	}

	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private PpemtEmpInfoItemData toEntiy(EmpInfoItemData domain) {
		PpemtEmpInfoItemDataPk key = new PpemtEmpInfoItemDataPk(domain.getPerInfoDefId(), domain.getRecordId());
		String stringValue = null;
		BigDecimal intValue = null;
		GeneralDate dateValue = null;
		switch (EnumAdaptor.valueOf(domain.getDataState().getDataStateType().value, ItemValueType.class)) {
		case STRING:
		case SELECTION:
			stringValue = domain.getDataState().getStringValue();
			break;
		case NUMERIC:
		case TIME:
		case TIMEPOINT:
			intValue = domain.getDataState().getNumberValue();
			break;
		case DATE:
			dateValue = domain.getDataState().getDateValue();
			break;
		}
		return new PpemtEmpInfoItemData(key, domain.getDataState().getDataStateType().value, stringValue, intValue,
				dateValue);
	}

	private void updateEntiy(EmpInfoItemData domain, PpemtEmpInfoItemData entity) {
		entity.saveDataType = domain.getDataState().getDataStateType().value;

		switch (EnumAdaptor.valueOf(entity.saveDataType, ItemValueType.class)) {
		case STRING:
		case SELECTION:
			entity.stringValue = domain.getDataState().getStringValue();
			break;
		case NUMERIC:
		case TIME:
		case TIMEPOINT:
			entity.intValue = domain.getDataState().getNumberValue();
			break;
		case DATE:
			entity.dateValue = domain.getDataState().getDateValue();
			break;
		}

	}

	@Override
	public void addItemData(EmpInfoItemData domain) {
		this.commandProxy().insert(toEntiy(domain));
	}
	
	@Override
	public void registerEmpInfoItemData(EmpInfoItemData domain) {
		// Get exist item
		PpemtEmpInfoItemDataPk key = new PpemtEmpInfoItemDataPk(domain.getPerInfoDefId(), domain.getRecordId());
		Optional<PpemtEmpInfoItemData> existItem = this.queryProxy().find(key, PpemtEmpInfoItemData.class);
		
		if (!existItem.isPresent()) {
			addItemData(domain);
		} else {
			updateEntiy(domain, existItem.get());
			// Update table
			this.commandProxy().update(existItem.get());
		}
	}

	@Override
	public void updateEmpInfoItemData(EmpInfoItemData domain) {
		// Get exist item
		PpemtEmpInfoItemDataPk key = new PpemtEmpInfoItemDataPk(domain.getPerInfoDefId(), domain.getRecordId());
		Optional<PpemtEmpInfoItemData> existItem = this.queryProxy().find(key, PpemtEmpInfoItemData.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("Invalid PpemtEmpInfoItemData");
		}
		updateEntiy(domain, existItem.get());
		// Update table
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteEmployInfoItemData(String recordId) {
		this.getEntityManager().createQuery(DELETE_ITEM_DATA).setParameter("recordId", recordId).executeUpdate();
		this.getEntityManager().flush();

	}

	@Override
	public List<EmpInfoItemData> getAllInfoItemBySidCtgId(String ctgId, String employeeId) {
		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_CTGID_AND_SID, Object[].class)
				.setParameter("ctgid", ctgId).setParameter("sid", employeeId).getList(c -> toDomain(c));
	}

	@Override
	public Optional<EmpInfoItemData> getInfoItemByItemDefIdAndRecordId(String itemDefId, String recordId) {
		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_ITEMDEF_ID_AND_RECODE_ID, Object[].class)
				.setParameter("recordId", recordId).setParameter("perInfoDefId", itemDefId)
				.getSingle(c -> toDomainNew(c));
	}
	
	@Override
	public List<EmpInfoItemData> getItemsData(String itemDefId, List<String> recordIds) {
		if (recordIds.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<PpemtEmpInfoItemData> entites = new ArrayList<>();
		CollectionUtil.split(recordIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entites.addAll(this.queryProxy()
				.query(SELECT_ITEM_DATA_OF_RECORD_ID_LIST, PpemtEmpInfoItemData.class).setParameter("itemId", itemDefId)
				.setParameter("recordIds", subList).getList());
		});
		
		return entites.stream()
				.map(ent -> EmpInfoItemData.createFromJavaType(ent.ppemtEmpInfoItemDataPk.perInfoDefId,
						ent.ppemtEmpInfoItemDataPk.recordId, ent.saveDataType, ent.stringValue, ent.intValue,
						ent.dateValue))
				.collect(Collectors.toList());
	}

	@Override
	public boolean hasItemData(String itemCd, List<String> perInfoCtgId) {
		List<Object[]> item = new ArrayList<>();
		CollectionUtil.split(perInfoCtgId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			item.addAll(this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_ALL_CID_QUERY_STRING, Object[].class)
			.setParameter("itemCd", itemCd)
			.setParameter("perInfoCtgId", subList)
			.getList());
		});
		return item.size() > 0;
	}
}
