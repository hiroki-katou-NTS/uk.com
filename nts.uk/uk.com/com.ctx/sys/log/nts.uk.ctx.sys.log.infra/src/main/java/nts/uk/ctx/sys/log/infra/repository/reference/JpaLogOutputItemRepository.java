package nts.uk.ctx.sys.log.infra.repository.reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.log.dom.reference.LogOutputItem;
import nts.uk.ctx.sys.log.dom.reference.LogOutputItemRepository;
import nts.uk.ctx.sys.log.infra.entity.reference.SrcmtLogOutputItem;

/*
 * author: hiep.th
 */

@Stateless
public class JpaLogOutputItemRepository extends JpaRepository implements LogOutputItemRepository  {
	private static final String SELECT_ALL_QUERY_STRING = "SELECT s FROM SrcmtLogOutputItem s";
	private static final String SELECT_BY_RECORD_TYPE_STRING = SELECT_ALL_QUERY_STRING + " WHERE  s.srcdtLogOutputItemPK.recordType =:recordType ";
	private static final String SELECT_BY_RECORD_TYPE_ITEM_NO_STRING = SELECT_ALL_QUERY_STRING + " WHERE  s.srcdtLogOutputItemPK.recordType =:recordType AND s.srcdtLogOutputItemPK.itemNo =:itemNo ";
	private static final String SELECT_BY_RECORD_TYPE_ITEM_NO_LIST = SELECT_ALL_QUERY_STRING + " WHERE  s.srcdtLogOutputItemPK.recordType =:recordType AND s.srcdtLogOutputItemPK.itemNo IN :itemNos ";
	
	@Override
	public List<LogOutputItem> getByRecordType(int recordType) {
		return this.queryProxy().query(SELECT_BY_RECORD_TYPE_STRING, SrcmtLogOutputItem.class)
				.setParameter("recordType", recordType)
				.getList(c -> c.toDomain()).stream()
				.sorted(new Comparator<LogOutputItem>() {
					@Override
					public int compare(LogOutputItem o1, LogOutputItem o2) {
						return o1.getItemNo() - o2.getItemNo();
					}
				}).collect(Collectors.toList());
	}

	@Override
	public List<LogOutputItem> getByItemNoAndRecordType(int itemNo, int recordType) {
		return this.queryProxy().query(SELECT_BY_RECORD_TYPE_ITEM_NO_STRING, SrcmtLogOutputItem.class)
				.setParameter("recordType", recordType)
				.setParameter("itemNo", itemNo)
				.getList(c -> c.toDomain());
	}

	@Override
	public List<LogOutputItem> getByItemNosAndRecordType(List<String> itemNos, int recordType) {
		List<LogOutputItem> resultList = new ArrayList<>();
		CollectionUtil.split(itemNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_RECORD_TYPE_ITEM_NO_LIST, SrcmtLogOutputItem.class)
				.setParameter("recordType", recordType)
				.setParameter("itemNos", subList)
				.getList(c -> c.toDomain()));
		});
		return resultList;
	}
	
	
}
