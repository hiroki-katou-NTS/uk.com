package nts.uk.ctx.sys.assist.infra.repository.deletedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletionRepository;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtDeletionManual;

@Stateless
public class JpaManualSetDeletionRepository extends JpaRepository implements ManualSetDeletionRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtDeletionManual f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.cid =:cid AND  f.sspdtDeletionManualPK.delId =:delId ";
	private static final String SELECT_BY_KEY_STRING_STORE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtDeletionManualPK.delId =:delId ";
	private static final String SELECT_BY_SYSTEM_TYPE_AND_KEY = SELECT_ALL_QUERY_STRING
				+ " WHERE f.sspdtDeletionManualPK.delId IN :delIds ";

	@Override
	public List<ManualSetDeletion> getAllManualSetDeletion() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtDeletionManual.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ManualSetDeletion> getManualSetDeletionById(String cid, String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtDeletionManual.class).setParameter("cid", cid)
				.setParameter("delId", delId).getSingle(c -> c.toDomain());
	}

	
	@Override
	public Optional<ManualSetDeletion> getManualSetDeletionById(String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING_STORE, SspdtDeletionManual.class)
				.setParameter("delId", delId).getSingle(c -> c.toDomain());
	}
	
	@Override
	public void addManualSetting(ManualSetDeletion domain) {
		this.commandProxy().insert(SspdtDeletionManual.toEntity(domain));
	}

	@Override
	public List<ManualSetDeletion> getManualSetDeletionsSystemTypeAndId(List<String> delIds) {
		List<ManualSetDeletion> resultList = new ArrayList<>();
		CollectionUtil.split(delIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_SYSTEM_TYPE_AND_KEY, SspdtDeletionManual.class)
					.setParameter("delIds", delIds)
					.getList(item -> item.toDomain()));
		});
		return resultList;
	}
	
}
