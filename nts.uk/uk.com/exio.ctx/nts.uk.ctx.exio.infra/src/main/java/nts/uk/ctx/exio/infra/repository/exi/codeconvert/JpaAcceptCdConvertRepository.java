package nts.uk.ctx.exio.infra.repository.exi.codeconvert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertDetails;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.ExternalImportCodeConvertRepository;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtAcceptCdConvertPk;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtCdConvertDetailsPk;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtExAcCdConv;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtExAcCdConvDtl;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaAcceptCdConvertRepository extends JpaRepository implements ExternalImportCodeConvertRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExAcCdConv f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.acceptCdConvertPk.cid =:cid AND  f.acceptCdConvertPk.convertCd =:convertCd ";
	private static final String SELECT_BY_COMPANY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.acceptCdConvertPk.cid =:cid";

	@Override
	public List<ExternalImportCodeConvert> getAll() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExAcCdConv.class)
				.getList(item -> toDomain(item));
	}

	@Override
	public List<ExternalImportCodeConvert> get(String cid) {
		return this.queryProxy().query(SELECT_BY_COMPANY_STRING, OiomtExAcCdConv.class)
				.setParameter("cid", cid)
				.getList(item -> toDomain(item));
	}

	@Override
	public Optional<ExternalImportCodeConvert> get(String cid, String convertCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExAcCdConv.class)
				.setParameter("cid", cid)
				.setParameter("convertCd", convertCd)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public void add(ExternalImportCodeConvert domain) {
		Optional<ExternalImportCodeConvert> duplicateDomain = get(domain.getCompanyId(), domain.getConvertCode().toString());
		if(duplicateDomain.isPresent()) throw new BusinessException("Msg_3");
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(ExternalImportCodeConvert domain) {		
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(String cid, String convertCd) {
		this.commandProxy().remove(OiomtExAcCdConv.class, new OiomtAcceptCdConvertPk(cid, convertCd));
	}

	private static ExternalImportCodeConvert toDomain(OiomtExAcCdConv entity) {
		return new ExternalImportCodeConvert(
				entity.acceptCdConvertPk.cid,
				entity.acceptCdConvertPk.convertCd, 
				entity.convertName, 
				entity.acceptWithoutSetting,
				entity.oiomtCdConvertDetails.stream().map(itemDetail -> {
					return new CodeConvertDetails(itemDetail.cdConvertDetailsPk.cid,
							itemDetail.cdConvertDetailsPk.convertCd, itemDetail.cdConvertDetailsPk.lineNumber,
							itemDetail.outputItem, itemDetail.systemCd);
				}).collect(Collectors.toList()));
	}

	private OiomtExAcCdConv toEntity(ExternalImportCodeConvert domain) {
		String contractCd = AppContexts.user().contractCode();
		return new OiomtExAcCdConv(
				new OiomtAcceptCdConvertPk(domain.getCompanyId(), domain.getConvertCode().v()), 
				contractCd, domain.getConvertName().v(),
				domain.getAcceptWithoutSetting().value, domain.getListConvertDetails().stream().map(itemDetail -> {
					return new OiomtExAcCdConvDtl(
							new OiomtCdConvertDetailsPk(itemDetail.getCid(), itemDetail.getConvertCd(),
									itemDetail.getLineNumber()),
							contractCd,
							itemDetail.getOutputItem().v(), itemDetail.getSystemCd().v(), null);
				}).collect(Collectors.toList()));
	}

}
