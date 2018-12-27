package nts.uk.ctx.exio.infra.repository.exo.cdconvert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.cdconvert.OiomtCdConvertDetail;
import nts.uk.ctx.exio.infra.entity.exo.cdconvert.OiomtCdConvertDetailPk;
import nts.uk.ctx.exio.infra.entity.exo.cdconvert.OiomtOutputCodeConvert;
import nts.uk.ctx.exio.infra.entity.exo.cdconvert.OiomtOutputCodeConvertPk;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetail;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvert;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaOutputCodeConvertRepository extends JpaRepository implements OutputCodeConvertRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtOutputCodeConvert f";
	private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE f.outputCodeConvertPk.cid = :cid";
	private static final String SELECT_BY_ID = SELECT_BY_CID + " AND f.outputCodeConvertPk.convertCd = :convertCode";

	@Override
	public List<OutputCodeConvert> getAllOutputCodeConvert() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtOutputCodeConvert.class)
				.getList(item -> toDomain(item));
	}
	
	
	@Override
	public List<OutputCodeConvert> getOutputCodeConvertByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtOutputCodeConvert.class).setParameter("cid", cid)
				.getList(item -> toDomain(item));
	}

	@Override
	public Optional<OutputCodeConvert> getOutputCodeConvertById(String cid, String convertCode) {
		return this.queryProxy().query(SELECT_BY_ID, OiomtOutputCodeConvert.class)
				.setParameter("cid", cid).setParameter("convertCode", convertCode)
				.getSingle(item -> toDomain(item));
	}

	@Override
	public void add(OutputCodeConvert domain) {
		Optional<OutputCodeConvert> duplicateDomain 
			= getOutputCodeConvertById(domain.getCid(), domain.getConvertCode().toString());
		if(duplicateDomain.isPresent()) throw new BusinessException("Msg_3");
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(OutputCodeConvert domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(String cid, String convertCd) {
		this.commandProxy().remove(OiomtOutputCodeConvert.class, new OiomtOutputCodeConvertPk(cid, convertCd));
	}

	private static OutputCodeConvert toDomain(OiomtOutputCodeConvert entity) {
		return new OutputCodeConvert(entity.outputCodeConvertPk.convertCd, entity.convertName,
				entity.outputCodeConvertPk.cid, entity.acceptWithoutSetting,
				entity.listOiomtCdConvertDetail.stream().map(itemDetail -> {
					return new CdConvertDetail(itemDetail.cdConvertDetailPk.cid, itemDetail.cdConvertDetailPk.convertCd,
							itemDetail.outputItem, itemDetail.systemCd, itemDetail.cdConvertDetailPk.lineNumber);
				}).collect(Collectors.toList()));
	}

	private OiomtOutputCodeConvert toEntity(OutputCodeConvert domain) {
		return new OiomtOutputCodeConvert(new OiomtOutputCodeConvertPk(domain.getCid(), domain.getConvertCode().v()),
				domain.getConvertName().v(), domain.getAcceptWithoutSetting().value,
				domain.getListCdConvertDetails().stream().map(itemDetail -> {
					return new OiomtCdConvertDetail(
							new OiomtCdConvertDetailPk(itemDetail.getConvertCd().v(), itemDetail.getLineNumber(), itemDetail.getCid()),
							itemDetail.getOutputItem().isPresent() ? itemDetail.getOutputItem().get().v() : null,
							itemDetail.getSystemCd().v(),null);
				}).collect(Collectors.toList()));
	}
}
