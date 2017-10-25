package nts.uk.ctx.bs.employee.infra.repository.regpersoninfo.personinfoadditemdata.item;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;

@Stateless
public class JPAEmpInfoItemDataRepository extends JpaRepository implements EmpInfoItemDataRepository {

	public final String SELECT_ALL_INFO_ITEM_BY_CTD_CODE_QUERY_STRING = "SELECT id,pi.requiredAtr,pi.itemName FROM PpemtEmpInfoItemData id"
			+ " INNER JOIN PpemtPerInfoItem pi"
			+ " ON id.ppemtEmpInfoItemDataPk.perInfoDefId = pi.ppemtPerInfoItemPK.perInfoItemDefId"
			+ " INNER JOIN PpemtPerInfoCtg pc" + " ON id.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " WHERE pi.abolitionAtr=0 AND pc.categoryCd = :categoryCd";

	@Override
	public List<EmpInfoItemData> getAllInfoItem(String categoryCd) {
		return this.queryProxy().query(SELECT_ALL_INFO_ITEM_BY_CTD_CODE_QUERY_STRING, Object[].class)
				.setParameter("categoryCd", categoryCd).getList(c -> toDomain(c));
	}

	private EmpInfoItemData toDomain(Object[] entity) {

		int dataStateType = entity[3] != null ? Integer.valueOf(entity[3].toString()) : 0;

		BigDecimal intValue = new BigDecimal(entity[5] != null ? Integer.valueOf(entity[5].toString()) : null);

		GeneralDate dateValue = GeneralDate.fromString(String.valueOf(entity[6].toString()), "yyyy-MM-dd");

		int isRequired = Integer.parseInt(entity[8] != null ? entity[8].toString() : "0");

		return EmpInfoItemData.createFromJavaType(entity[0].toString(), entity[1].toString(), entity[2].toString(),
				entity[7].toString(), isRequired, dataStateType, entity[4].toString(), intValue, dateValue);
	}

}
