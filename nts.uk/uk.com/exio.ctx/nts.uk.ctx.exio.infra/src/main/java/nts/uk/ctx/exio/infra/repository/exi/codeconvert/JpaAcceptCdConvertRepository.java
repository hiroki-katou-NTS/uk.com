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
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtAcceptCdConvert;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtAcceptCdConvertPk;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtCdConvertDetails;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtCdConvertDetailsPk;

@Stateless
public class JpaAcceptCdConvertRepository extends JpaRepository implements AcceptCdConvertRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtAcceptCdConvert f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.acceptCdConvertPk.cid =:cid AND  f.acceptCdConvertPk.convertCd =:convertCd ";
	private static final String SELECT_BY_COMPANY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.acceptCdConvertPk.cid =:cid";

	@Override
	public List<AcceptCdConvert> getAllAcceptCdConvert() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtAcceptCdConvert.class)
				.getList(item -> toDomain(item));
	}

	@Override
	public List<AcceptCdConvert> getAcceptCdConvertByCompanyId(String cid) {
		return this.queryProxy().query(SELECT_BY_COMPANY_STRING, OiomtAcceptCdConvert.class)
				.setParameter("cid", cid)
				.getList(item -> toDomain(item));
	}

	@Override
	public Optional<AcceptCdConvert> getAcceptCdConvertById(String cid, String convertCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtAcceptCdConvert.class)
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
		this.commandProxy().remove(OiomtAcceptCdConvert.class, new OiomtAcceptCdConvertPk(cid, convertCd));
	}

	private static AcceptCdConvert toDomain(OiomtAcceptCdConvert entity) {
		return new AcceptCdConvert(entity.acceptCdConvertPk.cid,
				entity.acceptCdConvertPk.convertCd, entity.convertName, entity.acceptWithoutSetting,
				entity.oiomtCdConvertDetails.stream().map(itemDetail -> {
					return new CdConvertDetails(itemDetail.cdConvertDetailsPk.cid,
							itemDetail.cdConvertDetailsPk.convertCd, itemDetail.cdConvertDetailsPk.lineNumber,
							itemDetail.outputItem, itemDetail.systemCd);

				}).collect(Collectors.toList()));
	}

	private OiomtAcceptCdConvert toEntity(AcceptCdConvert domain) {
		return new OiomtAcceptCdConvert(
				new OiomtAcceptCdConvertPk(domain.getCid(), domain.getConvertCd().v()), domain.getConvertName().v(),
				domain.getAcceptWithoutSetting().value, domain.getListConvertDetails().stream().map(itemDetail -> {
					return new OiomtCdConvertDetails(
							new OiomtCdConvertDetailsPk(itemDetail.getCid(), itemDetail.getConvertCd(),
									itemDetail.getLineNumber()),
							itemDetail.getOutputItem().v(), itemDetail.getSystemCd().v(), null);
				}).collect(Collectors.toList()));
	}

}
