package nts.uk.ctx.bs.employee.infra.repository.regpersoninfo.personinfoadditemdata.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.infra.entity.regpersoninfo.personinfoadditemdata.item.PpemtEmpInfoItemData;
import nts.uk.ctx.bs.employee.infra.entity.regpersoninfo.personinfoadditemdata.item.PpemtEmpInfoItemDataPk;

@Stateless
public class JpaEmpInfoItemDataRepository extends JpaRepository implements EmpInfoItemDataRepository {

	private static final String SELECT_ALL_INFO_ITEM_NO_WHERE = "SELECT id,pi.requiredAtr,pi.itemName,pi.itemCd,ic.personInfoCtgId,pc.categoryCd FROM PpemtEmpInfoItemData id"
			+ " INNER JOIN PpemtPerInfoItem pi"
			+ " ON id.ppemtEmpInfoItemDataPk.perInfoDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtEmpInfoCtgData ic"
			+ " ON id.ppemtEmpInfoItemDataPk.recordId = ic.ppemtEmpInfoCtgDataPk.recordId"
			+ " INNER JOIN PpemtPerInfoCtg pc" + " ON ic.personInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId";

	public final String SELECT_ALL_INFO_ITEM_BY_CTD_CODE_QUERY_STRING = SELECT_ALL_INFO_ITEM_NO_WHERE
			+ " WHERE pi.abolitionAtr=0 AND pc.categoryCd = :categoryCd AND pc.cid = :companyId AND ic.employeeId= :employeeId";

	private static final String SELECT_ALL_INFO_ITEM_BY_RECODE_ID_QUERY_STRING = SELECT_ALL_INFO_ITEM_NO_WHERE
			+ " WHERE ic.ppemtEmpInfoCtgDataPk.recordId = :recordId";
	
	private static final String SELECT_ALL_INFO_ITEM_BY_CTGID_AND_SID = SELECT_ALL_INFO_ITEM_NO_WHERE
			+ " WHERE ic.personInfoCtgId = :ctgid AND ic.employeeId = :sid";

	@Override
	public List<EmpInfoItemData> getAllInfoItem(String categoryCd, String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_CTD_CODE_QUERY_STRING, Object[].class)
				.setParameter("categoryCd", categoryCd).setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId).getList(c -> toDomain(c));
	}

	private EmpInfoItemData toDomain(Object[] entity) {

		int dataStateType = entity[4] != null ? Integer.valueOf(entity[3].toString()) : 0;

		BigDecimal intValue = new BigDecimal(entity[6] != null ? Integer.valueOf(entity[6].toString()) : null);

		GeneralDate dateValue = GeneralDate.fromString(String.valueOf(entity[7].toString()), "yyyy-MM-dd");

		int isRequired = Integer.parseInt(entity[8] != null ? entity[9].toString() : "0");

		return EmpInfoItemData.createFromJavaType(entity[10].toString(), entity[0].toString(), entity[1].toString(),
				entity[11].toString(), entity[12].toString(), entity[9].toString(), isRequired, dataStateType,
				entity[5].toString(), intValue, dateValue);
	}

	@Override
	public List<EmpInfoItemData> getAllInfoItemByRecordId(String recordId) {
		List<EmpInfoItemData> lstObj =  this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_RECODE_ID_QUERY_STRING, Object[].class)
				.setParameter("recordId", recordId).getList(c -> toDomain(c));
		return lstObj == null ? new ArrayList<>() : lstObj;
	}

	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private PpemtEmpInfoItemData toEntiy(EmpInfoItemData domain) {
		PpemtEmpInfoItemDataPk key = new PpemtEmpInfoItemDataPk(domain.getPerInfoDefId(), domain.getRecordId());
		String stringValue = domain.getDataState().getStringValue();
		BigDecimal intValue = domain.getDataState().getNumberValue();
		GeneralDate dateValue = domain.getDataState().getDateValue();
		return new PpemtEmpInfoItemData(key, domain.getDataState().getDataStateType().value, stringValue, intValue,
				dateValue);
	}

	private void updateEntiy(EmpInfoItemData domain, PpemtEmpInfoItemData entity) {
		entity.stringValue = domain.getDataState().getStringValue();
		entity.intValue = domain.getDataState().getNumberValue();
		entity.dateValue = domain.getDataState().getDateValue();
		entity.saveDataType = domain.getDataState().getDataStateType().value;
	}

	@Override
	public void addItemData(EmpInfoItemData domain) {
		this.commandProxy().insert(toEntiy(domain));
	}

	@Override
	public void updateEmpInfoItemData(EmpInfoItemData domain) {
		// Get exist item
		PpemtEmpInfoItemDataPk key = new PpemtEmpInfoItemDataPk(domain.getPerInfoDefId(), domain.getRecordId());
		Optional<PpemtEmpInfoItemData> existItem = this.queryProxy().find(key, PpemtEmpInfoItemData.class);
		if (!existItem.isPresent()) {
			return;
		}
		updateEntiy(domain, existItem.get());
		// Update table
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteEmployInfoItemData(EmpInfoItemData domain) {
		PpemtEmpInfoItemDataPk key = new PpemtEmpInfoItemDataPk(domain.getPerInfoDefId(), domain.getRecordId());
		this.commandProxy().remove(PpemtEmpInfoItemData.class, key);

	}

	@Override
	public List<EmpInfoItemData> getAllInfoItemBySidCtgId(String ctgId, String employeeId) {
		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_CTGID_AND_SID, Object[].class)
				.setParameter("ctgid", ctgId)
				.setParameter("sid", employeeId)
				.getList(c -> toDomain(c));
	}
}
