package nts.uk.ctx.exio.infra.repository.exi.extcategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.ctx.exio.dom.exi.extcategory.AlphaUseFlg;
import nts.uk.ctx.exio.dom.exi.extcategory.ExiDecimalUnit;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalHistoryContiFlg;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
import nts.uk.ctx.exio.infra.entity.exi.extcategory.OiomtExAcpCategory;
import nts.uk.ctx.exio.infra.entity.exi.extcategory.OiomtExAcpCategoryItem;
import nts.uk.shr.com.enumcommon.NotUseAtr;
@Stateless
public class JpaExternalAcceptCategoryRepository extends JpaRepository implements ExternalAcceptCategoryRepository{
	private static final String SELECT_ATSYS = "SELET c FROM OiomtExAcpCategory c "
			+ " WHERE c.atSysFlg = :useFlg"
			+ " ORDER BY c.categoryId";
	
	private static final String SELECT_PERSYS = "SELET c FROM OiomtExAcpCategory c "
			+ " WHERE c.persSysFlg = :useFlg"
			+ " ORDER BY c.categoryId";
	
	private static final String SELECT_SALARYSYS = "SELET c FROM OiomtExAcpCategory c "
			+ " WHERE c.salarySysFlg = :useFlg"
			+ " ORDER BY c.categoryId";
	
	private static final String SELECT_OFFICESYS = "SELET c FROM OiomtExAcpCategory c "
			+ " WHERE c.officeSysFlg = :useFlg"
			+ " ORDER BY c.categoryId";
	@Override
	public Optional<ExternalAcceptCategory> getByCategoryId(int categoryId) {
		String sql = "SELECT c FROM OiomtExAcpCategory c WHERE c.categoryId = :categoryId";
		Optional<ExternalAcceptCategory> result = this.queryProxy().query(sql, OiomtExAcpCategory.class)
				.setParameter("categoryId", categoryId)
				.getSingle(x -> toDomain(x));
		return result;
	}

	private ExternalAcceptCategory toDomain(OiomtExAcpCategory entity) {
		ExternalAcceptCategory domain = new ExternalAcceptCategory(entity.categoryId,
				entity.categoryName,
				EnumAdaptor.valueOf(entity.atSysFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.persSysFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.salarySysFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.officeSysFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.insertFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.deleteFlg, NotUseAtr.class),
				lstToDomainAcceptItem(entity.acpCategoryItem));
		return domain;
	}

	private List<ExternalAcceptCategoryItem> lstToDomainAcceptItem(List<OiomtExAcpCategoryItem> acpCategoryItem) {
		if(acpCategoryItem.isEmpty()) return new ArrayList<>();
		List<ExternalAcceptCategoryItem> lstResult = acpCategoryItem.stream().map(x -> 
				new ExternalAcceptCategoryItem(x.getPk().categoryId,
					x.getPk().itemNo,
					x.getItemName(),
					x.getTableName(),
					x.getColumnName(),
					EnumAdaptor.valueOf(x.getDataType(), DataType.class),
					Optional.ofNullable(x.getAlphaUseFlg() == null ? null : EnumAdaptor.valueOf(x.getAlphaUseFlg(), AlphaUseFlg.class)),
					EnumAdaptor.valueOf(x.getPrimatyKeyFlg(), NotUseAtr.class),
					x.getPrimitiveName(),
					Optional.ofNullable(x.getDecimalDigit()),
					Optional.ofNullable(x.getDecimalUnit() == null ? null : EnumAdaptor.valueOf(x.getDecimalUnit(), ExiDecimalUnit.class)),
					EnumAdaptor.valueOf(x.getRequiredFlg(), NotUseAtr.class),
					Optional.ofNullable(x.getNumberRangeStart()),
					Optional.ofNullable(x.getNumberRangeEnd()),
					Optional.ofNullable(x.getNumberRangeStart2()),
					Optional.ofNullable(x.getNumberRangeEnd2()),
					x.getSpecialFlg(),
					Optional.ofNullable(x.getRequiredNumber()),
					EnumAdaptor.valueOf(x.getDisplayFlg(), NotUseAtr.class),
					Optional.ofNullable(x.getHistoryFlg() == null ? null : EnumAdaptor.valueOf(x.getHistoryFlg(), NotUseAtr.class)),
					Optional.ofNullable(x.getHistoryContiFlg() == null ? null : EnumAdaptor.valueOf(x.getHistoryContiFlg(), ExternalHistoryContiFlg.class))))
				.collect(Collectors.toList());
		return lstResult;
	}

	@Override
	public List<ExternalAcceptCategory> getBySystem(SystemType systemType, NotUseAtr useAtr) {
		String sql = "";
		switch (systemType) {
			case ATTENDANCE_SYSTEM:
				sql = SELECT_ATSYS;
				break;
			case PERSON_SYSTEM:
				sql = SELECT_PERSYS;
				break;
			case PAYROLL_SYSTEM:
				sql = SELECT_SALARYSYS;
				break;
			case OFFICE_HELPER:
				sql = SELECT_OFFICESYS;
				break;
				default:
					break;
		}
		List<ExternalAcceptCategory>  lstResult = this.queryProxy().query(sql, OiomtExAcpCategory.class)
				.setParameter("useFlg", useAtr.value)
				.getList(x -> toDomain(x));
		return lstResult;
	}

}
