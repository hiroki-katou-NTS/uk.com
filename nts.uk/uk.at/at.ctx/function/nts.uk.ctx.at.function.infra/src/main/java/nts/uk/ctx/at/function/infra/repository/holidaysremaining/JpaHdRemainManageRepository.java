package nts.uk.ctx.at.function.infra.repository.holidaysremaining;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaHdRemainManageRepository extends JpaRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnmtHdRemainManage f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.hdRemainManagePk.cid =:cid AND  f.hdRemainManagePk.cd =:cd ";

}
