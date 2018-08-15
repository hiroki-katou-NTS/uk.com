package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqstAppWorkChangeSet;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqstAppWorkChangeSetPk;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaAppWorkChangeSetRepository extends JpaRepository implements IAppWorkChangeSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrqstAppWorkChangeSet f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
														+ " WHERE f.appWorkChangeSetPk.cid =:companyID";

	@Override
	public List<AppWorkChangeSet> getAllAppWorkChangeSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrqstAppWorkChangeSet.class)
				.getList(item -> toDomain(item));
	}

	@Override
	public Optional<AppWorkChangeSet> findWorkChangeSetByID(String cid) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KrqstAppWorkChangeSet.class).setParameter("companyID", cid)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public void add(AppWorkChangeSet domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(AppWorkChangeSet domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(AppWorkChangeSet domain) {
		this.commandProxy().remove(toEntity(domain));
	}

	@Override
	public void remove(String key) {
		this.commandProxy().remove(KrqstAppWorkChangeSetPk.class, new KrqstAppWorkChangeSetPk(key));
	}

	private static AppWorkChangeSet toDomain(KrqstAppWorkChangeSet entity) {
		return AppWorkChangeSet.createFromJavaType(entity.appWorkChangeSetPk.cid, entity.excludeHoliday,
				entity.workChangeTimeAtr, entity.displayResultAtr, entity.initDisplayWorktime, entity.commentContent1,
				entity.commentFontWeight1, entity.commentFontColor1, entity.commentContent2, entity.commentFontWeight2,
				entity.commentFontColor2);
	}

	private KrqstAppWorkChangeSet toEntity(AppWorkChangeSet domain) {
		return new KrqstAppWorkChangeSet(new KrqstAppWorkChangeSetPk(domain.getCid()), domain.getExcludeHoliday(),
				domain.getWorkChangeTimeAtr().value, domain.getDisplayResultAtr(), domain.getInitDisplayWorktime().value,
				domain.getCommentContent1() == null ? null : domain.getCommentContent1().v(), 
				domain.getCommentFontWeight1().value,
				domain.getCommentFontColor1().v(), 
				domain.getCommentContent2() == null ? null : domain.getCommentContent2().v(),
				domain.getCommentFontWeight2().value, 
				domain.getCommentFontColor2().v());
	}

}
