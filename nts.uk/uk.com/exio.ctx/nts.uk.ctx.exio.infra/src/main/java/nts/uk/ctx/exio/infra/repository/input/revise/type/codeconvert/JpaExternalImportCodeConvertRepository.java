package nts.uk.ctx.exio.infra.repository.input.revise.type.codeconvert;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertDetail;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.ExternalImportCodeConvertRepository;
import nts.uk.ctx.exio.infra.entity.input.revise.type.codeconvert.XimmtCodeConvert;

@Stateless
public class JpaExternalImportCodeConvertRepository extends JpaRepository implements ExternalImportCodeConvertRepository{

	@Override
	public Optional<ExternalImportCodeConvert> get(String cid, CodeConvertCode convertCd) {
		
		String sql = " select f "
				+ " from XimmtCodeConvert f"
				+ " where f.pk.companyId =:cid AND  f.pk.code =:convertCd ";
		
		return this.queryProxy().query(sql, XimmtCodeConvert.class)
				.setParameter("cid", cid)
				.setParameter("convertCd", convertCd.toString())
				.getSingle(c -> toDomain(c));
	}
	
	private ExternalImportCodeConvert toDomain(XimmtCodeConvert entity) {
		return new ExternalImportCodeConvert(
				entity.getPk().getCompanyId(),
				entity.getPk().getCode(), 
				entity.getName(), 
				BooleanUtils.toBoolean(entity.getImportWithoutSetting()),
				entity.ximmtCodeConvertDetail.stream().map(itemDetail -> {
					return new CodeConvertDetail(
							itemDetail.getPk().getCompanyId(),
							itemDetail.getPk().getConvertCode(), 
							itemDetail.getPk().getTargetCode(), 
							itemDetail.getSystemCode());
				}).collect(Collectors.toList()));
	}
}
