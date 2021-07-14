package nts.uk.ctx.exio.infra.repository.input.revise;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItemRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.CodeConvertDetail;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.infra.entity.input.revise.XimmtReviseItem;
import nts.uk.ctx.exio.infra.entity.input.revise.type.codeconvert.XimmtCodeConvert;
import nts.uk.ctx.exio.infra.entity.input.revise.type.codeconvert.XimmtCodeConvertDetail;

@Stateless
public class JpaReviseItemRepository extends JpaRepository implements ReviseItemRepository {

	@Override
	public Optional<ReviseItem> get(String companyId, ExternalImportCode settingCode, int importItemNumber) {
		
		String sql 	= " select f "
					+ " from XimmtReviseItem f"
					+ " where f.pk.companyId =:companyID "
					+ " and f.pk.settingCode =:settingCD "
					+ " and f.pk.itemNo =:importItemNO ";
		
		val entitiesOpt = this.queryProxy().query(sql, XimmtReviseItem.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode.toString())
				.setParameter("importItemNO", importItemNumber)
				.getSingle();
		
		if(!entitiesOpt.isPresent()) {
			return Optional.empty();
		}
		
		val codeConvert = getCodeConvert(companyId, settingCode, importItemNumber);
		
		return Optional.of(entitiesOpt.get().toDomain(codeConvert));
	}
	
	private Optional<ExternalImportCodeConvert> getCodeConvert(String companyId, ExternalImportCode settingCode, int importItemNumber){
		
		String sql = " select f "
				+ " from XimmtCodeConvert f"
				+ " where f.pk.companyId =:companyID "
				+ " and f.pk.settingCode =:convertCd "
				+ " and f.pk.itemNo =:itemNO ";
		
		val entities = this.queryProxy().query(sql, XimmtCodeConvert.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode)
				.setParameter("itemNO", importItemNumber)
				.getSingle();
		if(!entities.isPresent()) {
			return Optional.empty();
		}
		val details = getCodeConvertDetails(companyId, settingCode, importItemNumber);
		return Optional.of(entities.get().toDomain(details));
		
	}
	
	private List<CodeConvertDetail> getCodeConvertDetails(String companyId, ExternalImportCode settingCode, int importItemNumber){
		
		String sql = " select f "
				+ " from XimmtCodeConvertDetail f"
				+ " where f.pk.companyId =:companyID "
				+ " and f.pk.settingCode =:convertCd "
				+ " and f.pk.itemNo =:itemNO ";
		
		return this.queryProxy().query(sql, XimmtCodeConvertDetail.class)
				.setParameter("companyID", companyId)
				.setParameter("settingCD", settingCode)
				.setParameter("itemNO", importItemNumber)
				.getList(rec -> rec.toDomain());
	}
}
