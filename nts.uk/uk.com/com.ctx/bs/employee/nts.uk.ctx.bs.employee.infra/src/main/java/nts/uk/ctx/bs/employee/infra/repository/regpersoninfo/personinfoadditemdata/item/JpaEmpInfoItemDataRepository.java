package nts.uk.ctx.bs.employee.infra.repository.regpersoninfo.personinfoadditemdata.item;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;

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

	@Override
	public List<EmpInfoItemData> getAllInfoItem(String categoryCd, String companyId, String employeeId) {
		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_CTD_CODE_QUERY_STRING, Object[].class)
				.setParameter("categoryCd", categoryCd).setParameter("companyId", companyId).getList(c -> toDomain(c));
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
		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_RECODE_ID_QUERY_STRING, Object[].class)
				.setParameter("recordId", recordId).getList(c -> toDomain(c));
	}

	@Override
	public void addItemData(EmpInfoItemData infoItemData) {
		// TODO Auto-generated method stub

	}
}
