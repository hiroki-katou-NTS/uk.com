package nts.uk.ctx.exio.infra.repository.exi.codeconvert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvertRepository;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtExAcCdConv;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtExAcCdConvPk;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtExAcCdConvDtl;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtExAcCdConvDtlPk;

@Stateless
public class JpaAcceptCdConvertRepository extends JpaRepository implements AcceptCdConvertRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExAcCdConv f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.acceptCdConvertPk.cid =:cid AND  f.acceptCdConvertPk.convertCd =:convertCd ";
	private static final String SELECT_BY_COMPANY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.acceptCdConvertPk.cid =:cid";

	@Override
	public List<AcceptCdConvert> getAllAcceptCdConvert() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExAcCdConv.class)
				.getList(item -> toDomain(item));
	}

	@Override
	public List<AcceptCdConvert> getAcceptCdConvertByCompanyId(String cid) {
		return this.queryProxy().query(SELECT_BY_COMPANY_STRING, OiomtExAcCdConv.class)
				.setParameter("cid", cid)
				.getList(item -> toDomain(item));
	}

	@Override
	public Optional<AcceptCdConvert> getAcceptCdConvertById(String cid, String convertCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExAcCdConv.class)
				.setParameter("cid", cid)
				.setParameter("convertCd", convertCd)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public void add(AcceptCdConvert domain) {
		Optional<AcceptCdConvert> duplicateDomain = getAcceptCdConvertById(domain.getCid(), domain.getConvertCd().toString());
		if(duplicateDomain.isPresent()) throw new BusinessException("Msg_3");
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(AcceptCdConvert domain) {		
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(String cid, String convertCd) {
		this.commandProxy().remove(OiomtExAcCdConv.class, new OiomtExAcCdConvPk(cid, convertCd));
	}

	private static AcceptCdConvert toDomain(OiomtExAcCdConv entity) {
		return new AcceptCdConvert(entity.acceptCdConvertPk.cid,
				entity.acceptCdConvertPk.convertCd, entity.convertName, entity.acceptWithoutSetting,
				entity.oiomtExAcCdConvDtl.stream().map(itemDetail -> {
					return new CdConvertDetails(itemDetail.cdConvertDetailsPk.cid,
							itemDetail.cdConvertDetailsPk.convertCd, itemDetail.cdConvertDetailsPk.lineNumber,
							itemDetail.outputItem, itemDetail.systemCd);

				}).collect(Collectors.toList()));
	}

	private OiomtExAcCdConv toEntity(AcceptCdConvert domain) {
		return new OiomtExAcCdConv(
				new OiomtExAcCdConvPk(domain.getCid(), domain.getConvertCd().v()), domain.getConvertName().v(),
				domain.getAcceptWithoutSetting().value, domain.getListConvertDetails().stream().map(itemDetail -> {
					return new OiomtExAcCdConvDtl(
							new OiomtExAcCdConvDtlPk(itemDetail.getCid(), itemDetail.getConvertCd(),
									itemDetail.getLineNumber()),
							itemDetail.getOutputItem().v(), itemDetail.getSystemCd().v(), null);
				}).collect(Collectors.toList()));
	}

}
