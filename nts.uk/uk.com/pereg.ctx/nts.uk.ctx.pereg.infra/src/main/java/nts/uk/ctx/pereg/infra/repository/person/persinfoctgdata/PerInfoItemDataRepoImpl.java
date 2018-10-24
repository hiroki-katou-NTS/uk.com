/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.persinfoctgdata;

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
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem;
import nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata.PpemtPerInfoItemData;
import nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata.PpemtPerInfoItemDataPK;
import nts.uk.shr.pereg.app.ItemValueType;

/**
 * @author danpv
 *
 */
@Stateless
public class PerInfoItemDataRepoImpl extends JpaRepository implements PerInfoItemDataRepository {

	private static final String SELECT_ALL_INFO_ITEM_NO_WHERE = "SELECT id,pi.requiredAtr,pi.itemName,pi.itemCd,ic.pInfoCtgId,pc.categoryCd FROM PpemtPerInfoItemData id"
			+ " INNER JOIN PpemtPerInfoItem pi"
			+ " ON id.primaryKey.perInfoDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtgData ic" + " ON id.primaryKey.recordId = ic.recordId"
			+ " INNER JOIN PpemtPerInfoCtg pc" + " ON ic.pInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId";

	private static final String GET_BY_RID = "SELECT itemData, itemInfo, infoCtg FROM PpemtPerInfoItemData itemData"
			+ " INNER JOIN PpemtPerInfoItem itemInfo ON itemData.primaryKey.perInfoDefId = itemInfo.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg infoCtg ON itemInfo.perInfoCtgId = infoCtg.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " where itemData.primaryKey.recordId = :recordId";
	
	private static final String GET_BY_ITEM_DEF_ID_AND_RECORD_ID = "SELECT itemData, itemInfo, infoCtg FROM PpemtPerInfoItemData itemData"
			+ " INNER JOIN PpemtPerInfoItem itemInfo ON itemData.primaryKey.perInfoDefId = itemInfo.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg infoCtg ON itemInfo.perInfoCtgId = infoCtg.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " where itemData.primaryKey.perInfoDefId = :perInfoDefId and itemData.primaryKey.recordId = :recordId";

	private static final String SELECT_ALL_INFO_ITEM_BY_CTGID_AND_PID = "SELECT id,pi.requiredAtr,pi.itemName,pi.itemCd,ic.pInfoCtgId,pc.categoryCd FROM PpemtPerInfoItemData id"
			+ " INNER JOIN PpemtPerInfoItem pi"
			+ " ON id.primaryKey.perInfoDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtgData ic" + " ON id.primaryKey.recordId = ic.recordId"
			+ " INNER JOIN PpemtPerInfoCtg pc" + " ON ic.pInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE ic.pInfoCtgId = :ctgid AND ic.pId = :pid";
	
	private static final String SEL_ALL_ITEM_BY_CTG_IDS = "SELECT id.primaryKey.perInfoDefId"
			+ " FROM PpemtPerInfoItemData id"
			+ " INNER JOIN PpemtPerInfoItem pi ON id.primaryKey.perInfoDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg pc ON pi.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoItemCm pm ON pi.itemCd = pm.ppemtPerInfoItemCmPK.itemCd AND pc.categoryCd = pm.ppemtPerInfoItemCmPK.categoryCd"
			+ " WHERE pm.ppemtPerInfoItemCmPK.itemCd =:itemCd"
			+ " AND pi.perInfoCtgId IN :perInfoCtgId";
	
	private static final String GET_ITEM_DATA_WITH_RECORD_IDS = "SELECT id FROM PpemtPerInfoItemData id "
			+ "WHERE id.primaryKey.perInfoDefId = :itemDefId AND id.primaryKey.recordId IN :recordIds";
	

	private PersonInfoItemData toDomain(Object[] entity) {

		int dataStateType = entity[4] != null ? Integer.valueOf(entity[3].toString()) : 0;

		BigDecimal intValue = new BigDecimal(entity[6] != null ? Integer.valueOf(entity[6].toString()) : null);

		GeneralDate dateValue = GeneralDate.fromString(String.valueOf(entity[7].toString()), "yyyy-MM-dd");

		int isRequired = Integer.parseInt(entity[8] != null ? entity[9].toString() : "0");

		return PersonInfoItemData.createFromJavaType(entity[10].toString(), entity[0].toString(), entity[1].toString(),
				entity[11].toString(), entity[12].toString(), entity[9].toString(), isRequired, dataStateType,
				entity[5].toString(), intValue, dateValue);
	}
	
	private PersonInfoItemData toDomainNew(Object[] data){
		PpemtPerInfoItemData itemData = (PpemtPerInfoItemData) data[0];
		PpemtPerInfoItem itemInfo = (PpemtPerInfoItem) data[1];
		PpemtPerInfoCtg infoCtg = (PpemtPerInfoCtg) data[2];
		return PersonInfoItemData.createFromJavaType(itemInfo.itemCd, itemInfo.ppemtPerInfoItemPK.perInfoItemDefId,
				itemData.primaryKey.recordId, infoCtg.ppemtPerInfoCtgPK.perInfoCtgId, infoCtg.categoryCd,
				itemInfo.itemName, itemInfo.requiredAtr, itemData.saveDataAtr, itemData.stringVal, itemData.intVal,
				itemData.dateVal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.
	 * PerInfoItemDataRepository#getAllInfoItem(java.lang.String)
	 */
	@Override
	public List<PersonInfoItemData> getAllInfoItem(String categoryCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonInfoItemData> getAllInfoItemByRecordId(String recordId) {

		List<Object[]> datas = this.queryProxy().query(GET_BY_RID).setParameter("recordId", recordId).getList();

		if (datas == null){
			return new ArrayList<>();
		}
			
		return datas.stream().map(data -> toDomainNew(data)).collect(Collectors.toList());
		
	}

	// sonnlb code start
	private PpemtPerInfoItemData toEntity(PersonInfoItemData domain) {

		PpemtPerInfoItemDataPK key = new PpemtPerInfoItemDataPK(domain.getRecordId(), domain.getPerInfoItemDefId());

		String stringValue = null;
		BigDecimal intValue = null;
		GeneralDate dateValue = null;
		switch(EnumAdaptor.valueOf(domain.getDataState().getDataStateType().value, ItemValueType.class)){
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
		return new PpemtPerInfoItemData(key, domain.getDataState().getDataStateType().value, stringValue, intValue,
				dateValue);
	}

	private void updateEntity(PersonInfoItemData domain, PpemtPerInfoItemData entity) {
		entity.saveDataAtr = domain.getDataState().getDataStateType().value;
		switch(EnumAdaptor.valueOf(entity.saveDataAtr, ItemValueType.class)){
		case STRING:
		case SELECTION:
			entity.stringVal = domain.getDataState().getStringValue();
			break;
		case NUMERIC:
		case TIME:
		case TIMEPOINT:
			entity.intVal = domain.getDataState().getNumberValue();
			break;
		case DATE:
			entity.dateVal = domain.getDataState().getDateValue();
			break;
		}
	}

	/**
	 * Add item data
	 * 
	 * @param domain
	 */
	@Override
	public void addItemData(PersonInfoItemData domain) {
		this.commandProxy().insert(toEntity(domain));

	}
	
	@Override
	public void registerItemData(PersonInfoItemData domain) {
		PpemtPerInfoItemDataPK key = new PpemtPerInfoItemDataPK(domain.getRecordId(), domain.getPerInfoItemDefId());
		Optional<PpemtPerInfoItemData> existItem = this.queryProxy().find(key, PpemtPerInfoItemData.class);
		if (!existItem.isPresent()) {
			addItemData(domain);
		} else {
			// Update entity
			updateEntity(domain, existItem.get());
			// Update table
			this.commandProxy().update(existItem.get());
		}
	}
	

	@Override
	public void updateItemData(PersonInfoItemData domain) {
		PpemtPerInfoItemDataPK key = new PpemtPerInfoItemDataPK(domain.getRecordId(), domain.getPerInfoItemDefId());
		Optional<PpemtPerInfoItemData> existItem = this.queryProxy().find(key, PpemtPerInfoItemData.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("invalid PersonInfoItemData");
		}
		// Update entity
		updateEntity(domain, existItem.get());
		// Update table
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteItemData(PersonInfoItemData domain) {
		PpemtPerInfoItemDataPK key = new PpemtPerInfoItemDataPK(domain.getRecordId(), domain.getPerInfoItemDefId());
		this.commandProxy().remove(PpemtPerInfoItemData.class, key);

	}

	@Override
	public List<PersonInfoItemData> getAllInfoItemByPidCtgId(String ctgId, String pid) {

		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_CTGID_AND_PID, Object[].class)
				.setParameter("ctgid", ctgId).setParameter("pid", pid).getList(c -> toDomain(c));
	}

	@Override
	public Optional<PersonInfoItemData> getPerInfoItemDataByItemDefIdAndRecordId(String perInfoDefId, String recordId) {
		return this.queryProxy().query(GET_BY_ITEM_DEF_ID_AND_RECORD_ID)
				.setParameter("recordId", recordId)
				.setParameter("perInfoDefId", perInfoDefId).getSingle(data -> toDomainNew(data));
	}
	
	@Override
	public List<PersonInfoItemData> getItemData(String itemDefId, List<String> recordIds) {
		if (recordIds.isEmpty()) {
			return new ArrayList<>();
		}
		List<PpemtPerInfoItemData> entities = new ArrayList<>();
		CollectionUtil.split(recordIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy()
				.query(GET_ITEM_DATA_WITH_RECORD_IDS, PpemtPerInfoItemData.class)
				.setParameter("itemDefId", itemDefId)
				.setParameter("recordIds", subList).getList());
		});
		return entities.stream().map(ent -> PersonInfoItemData.createFromJavaType(itemDefId, ent.primaryKey.recordId,
				ent.saveDataAtr, ent.stringVal, ent.intVal, ent.dateVal)).collect(Collectors.toList());
		
		
	}

	@Override
	public boolean hasItemData(List<String> ctgId, String itemCd) {
		List<Object[]> itemLst = new ArrayList<>();
		CollectionUtil.split(ctgId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			itemLst.addAll(this.queryProxy().query(SEL_ALL_ITEM_BY_CTG_IDS,  Object[].class)
				.setParameter("perInfoCtgId", subList)
				.setParameter("itemCd", itemCd)
				.getList());
		});
		return itemLst.size() > 0;
	}

}
