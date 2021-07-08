/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.password.changelog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLog;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLogPK;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLogPK_;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLog_;
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;

/**
 * The Class JpaPasswordChangeLogRepository.
 */
@Stateless
public class JpaPasswordChangeLogRepository extends JpaRepository implements PasswordChangeLogRepository {
	
	private static final String FIND_BY_USERID = "SELECT c FROM SacdtPasswordChangeLog c"
			+ " WHERE c.sacdtPasswordChangeLogPK.userId = :userId"
			+ " ORDER By c.sacdtPasswordChangeLogPK.modifiedDatetime DESC";
	
	/**
	 * get list パスワード変更ログ
	 * @param ユーザID userId
	 * @return
	 * @author hoatt
	 */
	@Override
	public List<PasswordChangeLog> getListPwChangeLog(String userId) {
		return this.queryProxy().query(FIND_BY_USERID, SacdtPasswordChangeLog.class)
				.setParameter("userId", userId)
				.getList(c -> new PasswordChangeLog(c.getSacdtPasswordChangeLogPK().getLogId(), 
						c.getSacdtPasswordChangeLogPK().getUserId(),
						c.getSacdtPasswordChangeLogPK().getModifiedDatetime(),
						new HashPassword(c.getPassword())));
	}
}
