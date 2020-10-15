package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet_Old;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqstAppWorkChangeSet_Old;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqstAppWorkChangeSetPk_Old;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaAppWorkChangeSetRepository_Old extends JpaRepository implements IAppWorkChangeSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrqmtAppWorkChangeSet f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
														+ " WHERE f.appWorkChangeSetPk.cid =:companyID";

	@Override
	public List<AppWorkChangeSet_Old> getAllAppWorkChangeSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrqstAppWorkChangeSet_Old.class)
				.getList(item -> toDomain(item));
	}

	@Override
	public Optional<AppWorkChangeSet_Old> findWorkChangeSetByID(String cid) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KrqstAppWorkChangeSet_Old.class).setParameter("companyID", cid)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public void add(AppWorkChangeSet_Old domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(AppWorkChangeSet_Old domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(AppWorkChangeSet_Old domain) {
		this.commandProxy().remove(toEntity(domain));
	}

	@Override
	public void remove(String key) {
		this.commandProxy().remove(KrqstAppWorkChangeSetPk_Old.class, new KrqstAppWorkChangeSetPk_Old(key));
	}

	private static AppWorkChangeSet_Old toDomain(KrqstAppWorkChangeSet_Old entity) {
		return AppWorkChangeSet_Old.createFromJavaType(entity.appWorkChangeSetPk.cid, entity.excludeHoliday,
				entity.workChangeTimeAtr, entity.displayResultAtr, entity.initDisplayWorktime, entity.commentContent1,
				entity.commentFontWeight1, entity.commentFontColor1, entity.commentContent2, entity.commentFontWeight2,
				entity.commentFontColor2);
	}

	private KrqstAppWorkChangeSet_Old toEntity(AppWorkChangeSet_Old domain) {
		return new KrqstAppWorkChangeSet_Old(new KrqstAppWorkChangeSetPk_Old(domain.getCid()), domain.getExcludeHoliday(),
				domain.getWorkChangeTimeAtr().value, domain.getDisplayResultAtr(), domain.getInitDisplayWorktime().value,
				domain.getCommentContent1() == null ? null : domain.getCommentContent1().v(), 
				domain.getCommentFontWeight1().value,
				domain.getCommentFontColor1().v(), 
				domain.getCommentContent2() == null ? null : domain.getCommentContent2().v(),
				domain.getCommentFontWeight2().value, 
				domain.getCommentFontColor2().v());
	}

}
