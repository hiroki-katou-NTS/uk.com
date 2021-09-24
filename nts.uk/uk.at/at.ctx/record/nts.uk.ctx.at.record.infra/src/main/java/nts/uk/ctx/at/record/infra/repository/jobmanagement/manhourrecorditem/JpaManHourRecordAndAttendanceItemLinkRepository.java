package nts.uk.ctx.at.record.infra.repository.jobmanagement.manhourrecorditem;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordAndAttendanceItemLink;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordAndAttendanceItemLinkRepository;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.manhourrecorditem.KrcmtManHrItem;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.manhourrecorditem.KrcmtManHrItemLink;

/**
 * 
 * @author tutt
 *
 */
public class JpaManHourRecordAndAttendanceItemLinkRepository extends JpaRepository
		implements ManHourRecordAndAttendanceItemLinkRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT l FROM KrcmtManHrItemLink l";
	private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE l.pk.cId = :cId";
	private static final String SELECT_BY_CID_AND_ITEM = SELECT_BY_CID + " AND l.pk.manHrItemId IN :items";
	private static final String SELECT_BY_CID_AND_ATT_ITEM = SELECT_BY_CID + " AND l.pk.attItemId = :attItem";

	@Override
	public void insert(ManHourRecordAndAttendanceItemLink link) {
		this.commandProxy().insert(new KrcmtManHrItemLink(link));
	}

	@Override
	public List<ManHourRecordAndAttendanceItemLink> get(String cId) {
		return this.queryProxy().query(SELECT_BY_CID, KrcmtManHrItemLink.class).getList(item -> item.toDomain());
	}

	@Override
	public List<ManHourRecordAndAttendanceItemLink> get(String cId, List<Integer> items) {
		return this.queryProxy().query(SELECT_BY_CID_AND_ITEM, KrcmtManHrItemLink.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ManHourRecordAndAttendanceItemLink> get(String cId, int attItem) {
		return this.queryProxy().query(SELECT_BY_CID_AND_ATT_ITEM, KrcmtManHrItemLink.class)
				.getSingle(item -> item.toDomain());
	}

}
