package nts.uk.ctx.exio.infra.repository.exi.extcategory;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.AlphaUseFlg;
import nts.uk.ctx.exio.dom.exi.extcategory.ExiDecimalUnit;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalHistoryContiFlg;
import nts.uk.ctx.exio.dom.exi.extcategory.OiomtExAcpCategoryItemRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialExternalItem;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
import nts.uk.ctx.exio.infra.entity.exi.extcategory.OiomtExAcpCategoryItem;
import nts.uk.shr.com.enumcommon.NotUseAtr;
@Stateless
public class JpaOiomtExAcpCategoryItemRepository extends JpaRepository implements OiomtExAcpCategoryItemRepository{

	@Override
	public List<ExternalAcceptCategoryItem> getByCategory(int categoryId) {
		String sql = "SELECT c FROM OiomtExAcpCategoryItem c"
				+ " WHERE c.pk.categoryId = :categoryId"
				+ " ORDER BY c.pk.itemNo";
		List<ExternalAcceptCategoryItem> lstItem = this.queryProxy().query(sql, OiomtExAcpCategoryItem.class)
				.setParameter("categoryId", categoryId)
				.getList(x -> toDomain(x));
		return lstItem;
	}
	private ExternalAcceptCategoryItem toDomain(OiomtExAcpCategoryItem entity) {
		ExternalAcceptCategoryItem lstResult = 
				new ExternalAcceptCategoryItem(entity.getPk().categoryId,
					entity.getPk().itemNo,
					entity.getItemName(),
					entity.getTableName(),
					entity.getColumnName(),
					EnumAdaptor.valueOf(entity.getDataType(), DataType.class),
					Optional.ofNullable(entity.getAlphaUseFlg() == null ? null : EnumAdaptor.valueOf(entity.getAlphaUseFlg(), AlphaUseFlg.class)),
					EnumAdaptor.valueOf(entity.getPrimatyKeyFlg(), NotUseAtr.class),
					Optional.ofNullable(entity.getPrimitiveName()),
					Optional.ofNullable(entity.getDecimalDigit()),
					Optional.ofNullable(entity.getDecimalUnit() == null ? null : EnumAdaptor.valueOf(entity.getDecimalUnit(), ExiDecimalUnit.class)),
					EnumAdaptor.valueOf(entity.getRequiredFlg(), NotUseAtr.class),
					Optional.ofNullable(entity.getNumberRangeStart()),
					Optional.ofNullable(entity.getNumberRangeEnd()),
					Optional.ofNullable(entity.getNumberRangeStart2()),
					Optional.ofNullable(entity.getNumberRangeEnd2()),
					EnumAdaptor.valueOf(entity.getSpecialFlg(), SpecialExternalItem.class),
					Optional.ofNullable(entity.getRequiredNumber()),
					EnumAdaptor.valueOf(entity.getDisplayFlg(), NotUseAtr.class),
					Optional.ofNullable(entity.getHistoryFlg() == null ? null : EnumAdaptor.valueOf(entity.getHistoryFlg(), NotUseAtr.class)),
					Optional.ofNullable(entity.getHistoryContiFlg() == null ? null : EnumAdaptor.valueOf(entity.getHistoryContiFlg(), ExternalHistoryContiFlg.class)));
		return lstResult;
	}
}
