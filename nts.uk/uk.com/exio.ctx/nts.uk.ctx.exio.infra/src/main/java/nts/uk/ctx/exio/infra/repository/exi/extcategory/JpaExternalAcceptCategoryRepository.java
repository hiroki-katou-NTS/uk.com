package nts.uk.ctx.exio.infra.repository.exi.extcategory;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryRepository;
import nts.uk.ctx.exio.dom.exi.extcategory.OiomtExAcpCategoryItemRepository;
import nts.uk.ctx.exio.infra.entity.exi.extcategory.OiomtExAcpCategory;
import nts.uk.shr.com.enumcommon.NotUseAtr;
@Stateless
public class JpaExternalAcceptCategoryRepository extends JpaRepository implements ExternalAcceptCategoryRepository{
	@Inject
	private OiomtExAcpCategoryItemRepository itemReposi;
	private static final String SELECT_ATSYS = "SELECT c FROM OiomtExAcpCategory c "
			+ " WHERE c.atSysFlg = :useFlg"
			+ " ORDER BY c.categoryId";
	
	private static final String SELECT_PERSYS = "SELECT c FROM OiomtExAcpCategory c "
			+ " WHERE c.persSysFlg = :useFlg"
			+ " ORDER BY c.categoryId";
	
	private static final String SELECT_SALARYSYS = "SELECT c FROM OiomtExAcpCategory c "
			+ " WHERE c.salarySysFlg = :useFlg"
			+ " ORDER BY c.categoryId";
	
	private static final String SELECT_OFFICESYS = "SELECT c FROM OiomtExAcpCategory c "
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
		List<ExternalAcceptCategoryItem> lstItem = itemReposi.getByCategory(entity.categoryId);
		
		ExternalAcceptCategory domain = new ExternalAcceptCategory(entity.categoryId,
				entity.categoryName,
				EnumAdaptor.valueOf(entity.atSysFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.persSysFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.salarySysFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.officeSysFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.insertFlg, NotUseAtr.class),
				EnumAdaptor.valueOf(entity.deleteFlg, NotUseAtr.class),
				lstItem);
		return domain;
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
