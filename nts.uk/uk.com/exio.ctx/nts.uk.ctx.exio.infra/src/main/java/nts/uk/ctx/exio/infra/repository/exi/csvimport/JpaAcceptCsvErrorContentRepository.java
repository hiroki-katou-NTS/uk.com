package nts.uk.ctx.exio.infra.repository.exi.csvimport;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.csvimport.AcceptCsvErrorContent;
import nts.uk.ctx.exio.dom.exi.csvimport.AcceptCsvErrorContentRepository;
import nts.uk.ctx.exio.infra.entity.exi.csvimport.OiomtExAcpCsvError;
import nts.uk.ctx.exio.infra.entity.exi.csvimport.OiomtExAcpCsvErrorPk;

@Stateless
public class JpaAcceptCsvErrorContentRepository extends JpaRepository implements AcceptCsvErrorContentRepository {

	private static final String SELECT_BY_ASYNTASKID = "SELECT c FROM OiomtExAcpCsvError "
			+ " WHERE c.pk.cid = :cid "
			+ " AND c.pk.asynTaskId = :asynTaskId";
	@Override
	public void add(List<AcceptCsvErrorContent> lstContent) {
		this.commandProxy().insertAll(lstContent.stream().map(x-> toEntity(x)).collect(Collectors.toList()));
	}

	private OiomtExAcpCsvError toEntity(AcceptCsvErrorContent domain) {
		OiomtExAcpCsvErrorPk pk = new OiomtExAcpCsvErrorPk(domain.getCid(),
				domain.getAsynTaskId(),
				domain.getLineNumber(),
				domain.getItemNo());
		OiomtExAcpCsvError entity = new OiomtExAcpCsvError(pk,
				domain.getItemName(),
				domain.getItemValue(),
				domain.getErrorContent());
		return entity;
	}

	@Override
	public void delete(String cid, String asynTaskId) {
		List<OiomtExAcpCsvError> lstResult = this.queryProxy().query(SELECT_BY_ASYNTASKID, OiomtExAcpCsvError.class)
				.setParameter("cid", cid)
				.setParameter("asynTaskId", asynTaskId)
				.getList();
		
		this.commandProxy().removeAll(lstResult);
	}

	@Override
	public List<AcceptCsvErrorContent> getByAsynTaskId(String cid, String asynTaskId) {
		List<AcceptCsvErrorContent> lstResult = this.queryProxy().query(SELECT_BY_ASYNTASKID, OiomtExAcpCsvError.class)
				.setParameter("cid", cid)
				.setParameter("asynTaskId", asynTaskId)
				.getList(x -> toDomain(x));
		return lstResult;
	}

	private AcceptCsvErrorContent toDomain(OiomtExAcpCsvError entity)
	{
		AcceptCsvErrorContent result = new AcceptCsvErrorContent(entity.pk.getCid(),
				entity.pk.getAsynTaskId(),
				entity.pk.getLineNumber(),
				entity.pk.getItemNo(),
				entity.itemName,
				entity.itemValue,
				entity.errorContent);
		return result;
	}

}
