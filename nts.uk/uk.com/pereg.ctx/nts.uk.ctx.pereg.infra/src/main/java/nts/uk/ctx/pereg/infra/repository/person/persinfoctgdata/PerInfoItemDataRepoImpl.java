/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.persinfoctgdata;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.DataStateType;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtItem;
import nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata.PpemtPerDataItem;
import nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata.PpemtPerDataItemPK;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValueType;

/**
 * @author danpv
 *
 */
@Stateless
public class PerInfoItemDataRepoImpl extends JpaRepository implements PerInfoItemDataRepository {

//	private static final String SELECT_ALL_INFO_ITEM_NO_WHERE = "SELECT id,pi.requiredAtr,pi.itemName,pi.itemCd,ic.pInfoCtgId,pc.categoryCd FROM PpemtPerDataItem id"
//			+ " INNER JOIN PpemtItem pi"
//			+ " ON id.primaryKey.perInfoDefId = pi.ppemtItemPK.perInfoItemDefId"
//			+ " INNER JOIN PpemtCtgData ic" + " ON id.primaryKey.recordId = ic.recordId"
//			+ " INNER JOIN PpemtCtg pc" + " ON ic.pInfoCtgId = pc.ppemtCtgPK.perInfoCtgId";

	private static final String GET_BY_RID = "SELECT itemData, itemInfo, infoCtg FROM PpemtPerDataItem itemData"
			+ " INNER JOIN PpemtItem itemInfo ON itemData.primaryKey.perInfoDefId = itemInfo.ppemtItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtCtg infoCtg ON itemInfo.perInfoCtgId = infoCtg.ppemtCtgPK.perInfoCtgId"
			+ " where itemData.primaryKey.recordId = :recordId";
	
	private static final String GET_BY_RIDS = "SELECT itemData, itemInfo, infoCtg FROM PpemtPerDataItem itemData"
			+ " INNER JOIN PpemtItem itemInfo ON itemData.primaryKey.perInfoDefId = itemInfo.ppemtItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtCtg infoCtg ON itemInfo.perInfoCtgId = infoCtg.ppemtCtgPK.perInfoCtgId"
			+ " where itemData.primaryKey.recordId IN :recordId";
	
	private static final String GET_BY_ITEM_DEF_ID_AND_RECORD_ID = "SELECT itemData, itemInfo, infoCtg FROM PpemtPerDataItem itemData"
			+ " INNER JOIN PpemtItem itemInfo ON itemData.primaryKey.perInfoDefId = itemInfo.ppemtItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtCtg infoCtg ON itemInfo.perInfoCtgId = infoCtg.ppemtCtgPK.perInfoCtgId"
			+ " where itemData.primaryKey.perInfoDefId = :perInfoDefId and itemData.primaryKey.recordId = :recordId";

	private static final String SELECT_ALL_INFO_ITEM_BY_CTGID_AND_PID = "SELECT id,pi.requiredAtr,pi.itemName,pi.itemCd,ic.pInfoCtgId,pc.categoryCd FROM PpemtPerDataItem id"
			+ " INNER JOIN PpemtItem pi"
			+ " ON id.primaryKey.perInfoDefId = pi.ppemtItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtCtgData ic" + " ON id.primaryKey.recordId = ic.recordId"
			+ " INNER JOIN PpemtCtg pc" + " ON ic.pInfoCtgId = pc.ppemtCtgPK.perInfoCtgId"
			+ " WHERE ic.pInfoCtgId = :ctgid AND ic.pId = :pid";
	
	private static final String SEL_ALL_ITEM_BY_CTG_IDS = "SELECT id.primaryKey.perInfoDefId"
			+ " FROM PpemtPerDataItem id"
			+ " INNER JOIN PpemtItem pi ON id.primaryKey.perInfoDefId = pi.ppemtItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtCtg pc ON pi.perInfoCtgId = pc.ppemtCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtItemCommon pm ON pi.itemCd = pm.ppemtItemCommonPK.itemCd AND pc.categoryCd = pm.ppemtItemCommonPK.categoryCd"
			+ " WHERE pm.ppemtItemCommonPK.itemCd =:itemCd"
			+ " AND pi.perInfoCtgId IN :perInfoCtgId";
	
	private static final String GET_ITEM_DATA_WITH_RECORD_IDS = "SELECT id FROM PpemtPerDataItem id "
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
		PpemtPerDataItem itemData = (PpemtPerDataItem) data[0];
		PpemtItem itemInfo = (PpemtItem) data[1];
		PpemtCtg infoCtg = (PpemtCtg) data[2];
		return PersonInfoItemData.createFromJavaType(itemInfo.itemCd, itemInfo.ppemtItemPK.perInfoItemDefId,
				itemData.primaryKey.recordId, infoCtg.ppemtCtgPK.perInfoCtgId, infoCtg.categoryCd,
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
	private PpemtPerDataItem toEntity(PersonInfoItemData domain) {

		PpemtPerDataItemPK key = new PpemtPerDataItemPK(domain.getRecordId(), domain.getPerInfoItemDefId());

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
		default:
			break;
		}
		return new PpemtPerDataItem(key, domain.getDataState().getDataStateType().value, stringValue, intValue,
				dateValue);
	}

	private void updateEntity(PersonInfoItemData domain, PpemtPerDataItem entity) {
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
		default:
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
		PpemtPerDataItemPK key = new PpemtPerDataItemPK(domain.getRecordId(), domain.getPerInfoItemDefId());
		Optional<PpemtPerDataItem> existItem = this.queryProxy().find(key, PpemtPerDataItem.class);
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
		PpemtPerDataItemPK key = new PpemtPerDataItemPK(domain.getRecordId(), domain.getPerInfoItemDefId());
		Optional<PpemtPerDataItem> existItem = this.queryProxy().find(key, PpemtPerDataItem.class);
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
		PpemtPerDataItemPK key = new PpemtPerDataItemPK(domain.getRecordId(), domain.getPerInfoItemDefId());
		this.commandProxy().remove(PpemtPerDataItem.class, key);

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
		List<PpemtPerDataItem> entities = new ArrayList<>();
		CollectionUtil.split(recordIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy()
				.query(GET_ITEM_DATA_WITH_RECORD_IDS, PpemtPerDataItem.class)
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

	@Override
	public List<PersonInfoItemData> getAllInfoItemByRecordId(List<String> recordIds) {
		if (recordIds.isEmpty()) {
			return new ArrayList<>();
		}

		List<Object[]> entities = new ArrayList<>();
		CollectionUtil.split(recordIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy().query(GET_BY_RIDS, Object[].class)
					.setParameter("recordId", subList).getList());
		});
		
		return entities.stream()
				.map(ent -> toDomainNew(ent))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<PersonInfoItemData> getAllInfoItemByRecordIdsAndItemIds(List<String> itemIds, List<String> recordIds) {
		List<PersonInfoItemData> result = new ArrayList<>();
		CollectionUtil.split(recordIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT RECORD_ID, PER_INFO_DEF_ID FROM PPEMT_PER_DATA_ITEM WHERE PER_INFO_DEF_ID IN ("
					+ NtsStatement.In.createParamsString(itemIds) + ")" + " AND RECORD_ID IN ( "
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				for (int i = 0 ; i < itemIds.size(); i++) {
					stmt.setString( 1 + i, itemIds.get(i));
				}
				for (int i = 0 ; i < subList.size(); i++) {
					stmt.setString( itemIds.size() + 1 + i, subList.get(i));
				}

				new NtsResultSet(stmt.executeQuery()).forEach(rec -> {
					PersonInfoItemData perItemData = new PersonInfoItemData();
					perItemData.setRecordId(rec.getString("RECORD_ID"));
					perItemData.setPerInfoItemDefId(rec.getString("PER_INFO_DEF_ID"));
					result.add(perItemData);
				});
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
		});
		return result;
	}

	@Override
	public void addAll(List<PersonInfoItemData> domains) {
		String INS_SQL = "INSERT INTO PPEMT_PER_DATA_ITEM ( INS_DATE , INS_CCD , INS_SCD , INS_PG , "
				+ "  UPD_DATE ,  UPD_CCD,  UPD_SCD , UPD_PG ,"
				+ "  RECORD_ID, PER_INFO_DEF_ID, SAVE_DATA_ATR, STRING_VAL , INT_VAL , DATE_VAL) VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ "  UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL, RECORD_ID_VAL, PER_INFO_DEF_ID_VAL, SAVE_DATA_ATR_VAL, STRING_VAL_VAL, INT_VAL_VAL, DATE_VAL_VAL)";
    	GeneralDateTime insertTime = GeneralDateTime.now();
    	String insCcd = AppContexts.user().companyCode();
    	String insScd = AppContexts.user().employeeCode();
    	String insPg = AppContexts.programId();
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg =  insPg;
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c ->{
			String sql = INS_SQL;
			sql = sql.replace("INS_DATE_VAL", "'" + insertTime +"'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd +"'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd +"'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg +"'");
			
			sql = sql.replace("UPD_DATE_VAL", "'" + insertTime +"'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("RECORD_ID_VAL", "'" + c.getRecordId() +"'");
			sql = sql.replace("PER_INFO_DEF_ID_VAL", "'" + c.getPerInfoItemDefId() +"'");
			
			sql = sql.replace("SAVE_DATA_ATR_VAL", ""+c.getDataState().getDataStateType().value +"");
			
			if(c.getDataState().getDataStateType() == DataStateType.String) {
				sql = sql.replace("STRING_VAL_VAL", c.getDataState().getStringValue() == null ? "null": "'" + c.getDataState().getStringValue() +"'");
			}else {
				sql = sql.replace("STRING_VAL_VAL", "null");
			}
			
			if(c.getDataState().getDataStateType() == DataStateType.Numeric) {
				sql = sql.replace("INT_VAL_VAL",  c.getDataState().getNumberValue() == null? "null": "" + c.getDataState().getNumberValue() +"");
			}else {
				sql = sql.replace("INT_VAL_VAL",  "null");
			}
			
			if(c.getDataState().getDataStateType() == DataStateType.Date) {
				sql = sql.replace("DATE_VAL_VAL", c.getDataState().getDateValue() == null? "null": "'" + c.getDataState().getDateValue() +"'");
			}else {
				sql = sql.replace("DATE_VAL_VAL", "null");
			}
			
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
	}

	@Override
	public void updateAll(List<PersonInfoItemData> domains) {
		String UP_SQL = "UPDATE PPEMT_PER_DATA_ITEM"
				+ " SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " RECORD_ID = RECORD_ID_VAL, PER_INFO_DEF_ID = PER_INFO_DEF_ID_VAL, SAVE_DATA_ATR = SAVE_DATA_ATR_VAL,"
				+ " STRING_VAL = STRING_VAL_VAL, INT_VAL = INT_VAL_VAL, DATE_VAL = DATE_VAL_VAL "
				+ " WHERE  RECORD_ID = RECORD_ID_VAL AND  PER_INFO_DEF_ID = PER_INFO_DEF_ID_VAL; ";
    	GeneralDateTime insertTime = GeneralDateTime.now();
		String updCcd = AppContexts.user().companyCode();
		String updScd = AppContexts.user().employeeCode();
		String updPg =  AppContexts.programId();
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c ->{
			String sql = UP_SQL;
			
			sql = sql.replace("UPD_DATE_VAL", "'" + insertTime +"'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("RECORD_ID_VAL", "'" + c.getRecordId() +"'");
			sql = sql.replace("PER_INFO_DEF_ID_VAL", "'" + c.getPerInfoItemDefId()+"'");
			sql = sql.replace("SAVE_DATA_ATR_VAL", "" + c.getDataState().getDataStateType().value +"");
			
			if(c.getDataState().getDataStateType() == DataStateType.String) {
				sql = sql.replace("STRING_VAL_VAL", c.getDataState().getStringValue() == null ? "null": "'" + c.getDataState().getStringValue() +"'");
			}else {
				sql = sql.replace("STRING_VAL_VAL", "null");
			}
			
			if(c.getDataState().getDataStateType() == DataStateType.Numeric) {
				sql = sql.replace("INT_VAL_VAL",  c.getDataState().getNumberValue() == null? "null": "" + c.getDataState().getNumberValue() +"");
			}else {
				sql = sql.replace("INT_VAL_VAL",  "null");
			}
			
			if(c.getDataState().getDataStateType() == DataStateType.Date) {
				sql = sql.replace("DATE_VAL_VAL", c.getDataState().getDateValue() == null? "null": "'" + c.getDataState().getDateValue() +"'");
			}else {
				sql = sql.replace("DATE_VAL_VAL", "null");
			}
			
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}
}
