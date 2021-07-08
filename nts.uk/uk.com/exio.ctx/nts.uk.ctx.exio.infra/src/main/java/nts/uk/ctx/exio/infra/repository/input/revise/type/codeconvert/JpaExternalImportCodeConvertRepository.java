package nts.uk.ctx.exio.infra.repository.input.revise.type.codeconvert;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.CodeConvertDetail;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.ExternalImportCodeConvertRepository;
import nts.uk.ctx.exio.infra.entity.input.revise.type.codeconvert.XimmtCodeConvert;
import nts.uk.ctx.exio.infra.entity.input.revise.type.codeconvert.XimmtCodeConvertDetail;

@Stateless
public class JpaExternalImportCodeConvertRepository extends JpaRepository implements ExternalImportCodeConvertRepository{

	@Override
	public Optional<ExternalImportCodeConvert> get(String cid, CodeConvertCode convertCd) {
		
		String sql = " select f "
				+ " from XimmtCodeConvert f"
				+ " where f.pk.companyId =:cid "
				+ " and f.pk.code =:convertCd ";
		
		val entities = this.queryProxy().query(sql, XimmtCodeConvert.class)
				.setParameter("cid", cid)
				.setParameter("convertCd", convertCd.toString())
				.getSingle();
		if(entities.isPresent()) {
			return Optional.empty();
		}
		val details = getDetails(cid, convertCd);
		return Optional.of(toDomain(entities.get(), details));
	}
	
	private List<CodeConvertDetail> getDetails(String cid, CodeConvertCode convertCd){
		
		String sql = " select f "
				+ " from XimmtCodeConvertDetail f"
				+ " where f.pk.companyId =:cid "
				+ " and f.pk.convertCode =:convertCd ";
		
		return this.queryProxy().query(sql, XimmtCodeConvertDetail.class)
				.setParameter("cid", cid)
				.setParameter("convertCd", convertCd.toString())
				.getList(rec -> rec.toDomain());
	}
	
	private ExternalImportCodeConvert toDomain(XimmtCodeConvert entity, List<CodeConvertDetail> details) {
		return new ExternalImportCodeConvert(
				entity.getPk().getCompanyId(),
				entity.getPk().getCode(), 
				entity.getName(), 
				BooleanUtils.toBoolean(entity.getImportWithoutSetting()),
				details);
	}
}
