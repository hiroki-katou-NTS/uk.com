package nts.uk.ctx.exio.infra.repository.exi.csvimport;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.condset.DeleteExistDataMethod;
import nts.uk.ctx.exio.dom.exi.csvimport.AcceptCsvContent;
import nts.uk.ctx.exio.dom.exi.csvimport.AcceptCsvContentRepository;
import nts.uk.ctx.exio.infra.entity.exi.csvimport.OiomtExAcpCsvContent;
import nts.uk.ctx.exio.infra.entity.exi.csvimport.OiomtExAcpCsvContentPk;

public class JpaAcceptCsvContentRepository extends JpaRepository implements AcceptCsvContentRepository {
	private static final String SELECT_BY_ASYNTASKID = "SELECT c FROM OiomtExAcpCsvContent c "
			+ " WHERE c.pk.cid = :cid "
			+ " AND c.pk.asynTaskId = :asynTaskId";
	@Override
	public void removeAll(String cid, String asynTaskId) {
		List<OiomtExAcpCsvContent> lstContent = this.queryProxy().query(SELECT_BY_ASYNTASKID, OiomtExAcpCsvContent.class)
				.setParameter("cid", cid)
				.setParameter("asynTaskId", asynTaskId)
				.getList();
		this.commandProxy().removeAll(lstContent);
	}

	@Override
	public void addAll(List<AcceptCsvContent> lstContent) {
		this.commandProxy().insertAll(lstContent.stream().map(x-> toEntity(x)).collect(Collectors.toList()));
		
	}

	private OiomtExAcpCsvContent toEntity(AcceptCsvContent x) {
		OiomtExAcpCsvContentPk pk = new OiomtExAcpCsvContentPk(x.getCid(),
				x.getAsynTaskId(),
				x.getLineNumber(),
				x.getItemNo());
		OiomtExAcpCsvContent entity = new OiomtExAcpCsvContent(pk,
				x.getDeleteCondFlg().value,
				x.getAcceptMode().value,
				x.getProcessDate(),
				x.getTableName(),
				x.getColumnName(),
				x.getItemValue());
		return entity;
	}

	@Override
	public List<AcceptCsvContent> getAll(String cid, String asynTaskId) {
		List<AcceptCsvContent> lstResult = this.queryProxy().query(SELECT_BY_ASYNTASKID, OiomtExAcpCsvContent.class)
				.setParameter("cid", cid)
				.setParameter("asynTaskId", asynTaskId)
				.getList(x -> toDomain(x));
		return lstResult;
	}

	private AcceptCsvContent toDomain(OiomtExAcpCsvContent x) {
		AcceptCsvContent domain = new AcceptCsvContent(x.pk.getCid(),
				x.pk.getAsynTaskId(),
				x.pk.getLineNumber(),
				x.pk.getItemNo(),
				EnumAdaptor.valueOf(x.deleteCondFlg, DeleteExistDataMethod.class),
				EnumAdaptor.valueOf(x.acceptMode, AcceptMode.class),
				x.processDate,
				x.tableName,
				x.columnName,
				x.itemValue);
		return domain;
	}

}
