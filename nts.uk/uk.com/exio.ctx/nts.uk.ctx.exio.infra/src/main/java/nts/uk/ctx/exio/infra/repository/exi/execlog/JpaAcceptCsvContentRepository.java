package nts.uk.ctx.exio.infra.repository.exi.execlog;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.execlog.AcceptCsvContent;
import nts.uk.ctx.exio.dom.exi.execlog.AcceptCsvContentRepository;
import nts.uk.ctx.exio.dom.exi.execlog.CsvAcceptMode;
import nts.uk.ctx.exio.dom.exi.execlog.DeleteCondFlg;
import nts.uk.ctx.exio.infra.entity.exi.execlog.OiomtExAcpCsvContent;
import nts.uk.ctx.exio.infra.entity.exi.execlog.OiomtExAcpCsvContentPk;

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
		AcceptCsvContent domain = new AcceptCsvContent(x.getPk().getCid(),
				x.getPk().getAsynTaskId(),
				x.getPk().getLineNumber(),
				x.getPk().getItemNo(),
				EnumAdaptor.valueOf(x.getDeleteCondFlg(), DeleteCondFlg.class),
				EnumAdaptor.valueOf(x.getAcceptMode(), CsvAcceptMode.class),
				x.getProcessDate(),
				x.getTableName(),
				x.getColumnName(),
				x.getItemValue());
		return domain;
	}

}
