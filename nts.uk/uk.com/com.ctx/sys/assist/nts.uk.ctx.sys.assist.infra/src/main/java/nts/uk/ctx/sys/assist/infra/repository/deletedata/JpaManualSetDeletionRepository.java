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
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtManualSetDeletion;

@Stateless
public class JpaManualSetDeletionRepository extends JpaRepository implements ManualSetDeletionRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtManualSetDeletion f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.cid =:cid AND  f.sspdtManualSetDeletionPK.delId =:delId ";
	private static final String SELECT_BY_KEY_STRING_STORE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtManualSetDeletionPK.delId =:delId ";
	private static final String SELECT_BY_SYSTEM_TYPE_AND_KEY = SELECT_ALL_QUERY_STRING
				+ " WHERE f.sspdtManualSetDeletionPK.delId IN :delIds ";

	@Override
	public List<ManualSetDeletion> getAllManualSetDeletion() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtManualSetDeletion.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ManualSetDeletion> getManualSetDeletionById(String cid, String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtManualSetDeletion.class).setParameter("cid", cid)
				.setParameter("delId", delId).getSingle(c -> c.toDomain());
	}

	
	@Override
	public Optional<ManualSetDeletion> getManualSetDeletionById(String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING_STORE, SspdtManualSetDeletion.class)
				.setParameter("delId", delId).getSingle(c -> c.toDomain());
	}
	
	@Override
	public void addManualSetting(ManualSetDeletion domain) {
		this.commandProxy().insert(SspdtManualSetDeletion.toEntity(domain));
	}

	@Override
	public List<ManualSetDeletion> getManualSetDeletionsSystemTypeAndId(List<String> delIds) {
		List<ManualSetDeletion> resultList = new ArrayList<>();
		CollectionUtil.split(delIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_SYSTEM_TYPE_AND_KEY, SspdtManualSetDeletion.class)
					.setParameter("delIds", delIds)
					.getList(item -> item.toDomain()));
		});
		return resultList;
	}
	
}
