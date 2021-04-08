package nts.uk.ctx.office.infra.repository.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.office.dom.comment.EmployeeCommentInformation;
import nts.uk.ctx.office.dom.comment.EmployeeCommentInformationRepository;
import nts.uk.ctx.office.infra.entity.comment.OfiDtCommentSya;
import nts.uk.shr.com.context.AppContexts;

/*
 * Repository Implements UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.社員のコメント情報
 */
@Stateless
public class EmployeeCommentInformationRepositoryImpl extends JpaRepository
		implements EmployeeCommentInformationRepository {

	// select by List Sids and Date
	private static final String SELECT_BY_SIDS_AND_DATE = "SELECT m FROM OfiDtCommentSya m WHERE m.pk.sid IN :sids AND m.pk.date = :date";

	// select by Sid and Date
	private static final String SELECT_BY_SID_AND_DATE = "SELECT m FROM OfiDtCommentSya m WHERE m.pk.sid = :sid AND m.pk.date = :date";

	// select TOP 1 by Sid
	private static final String SELECT_TOP_1_BY_SID = "SELECT m FROM OfiDtCommentSya m WHERE m.pk.sid = :sid ORDER BY m.pk.date DESC";

	private static OfiDtCommentSya toEntity(EmployeeCommentInformation domain) {
		OfiDtCommentSya entity = new OfiDtCommentSya();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void insert(EmployeeCommentInformation domain) {
		OfiDtCommentSya entity = EmployeeCommentInformationRepositoryImpl.toEntity(domain);
		entity.setVersion(0);
		entity.setContractCd(AppContexts.user().contractCode());
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(EmployeeCommentInformation domain) {
		OfiDtCommentSya entity = EmployeeCommentInformationRepositoryImpl.toEntity(domain);
		Optional<OfiDtCommentSya> oldEntity = this.queryProxy().find(entity.getPk(),
				OfiDtCommentSya.class);
		oldEntity.ifPresent(updateEntity -> {
			updateEntity.setComment(entity.getComment());
			this.commandProxy().update(updateEntity);
		});
	}

	@Override
	public void delete(EmployeeCommentInformation domain) {
		OfiDtCommentSya entity = EmployeeCommentInformationRepositoryImpl.toEntity(domain);
		this.commandProxy().remove(OfiDtCommentSya.class, entity.getPk());
	}

	@Override
	public Map<String, EmployeeCommentInformation> getByListSidAndDate(List<String> sids, GeneralDate date) {
		Map<String, EmployeeCommentInformation> map = new HashMap<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subSids -> {
			List<EmployeeCommentInformation> list = this.queryProxy()
					.query(SELECT_BY_SIDS_AND_DATE, OfiDtCommentSya.class)
					.setParameter("sids", subSids).setParameter("date", date)
					.getList(EmployeeCommentInformation::createFromMemento);
			Map<String, EmployeeCommentInformation> subMap = list.stream()
					.collect(Collectors.toMap(EmployeeCommentInformation::getSid, domain -> domain));
			map.putAll(subMap);
		});
		return map;
	}

	@Override
	public Optional<EmployeeCommentInformation> getBySidAndDate(String sid, GeneralDate date) {
		return this.queryProxy().query(SELECT_BY_SID_AND_DATE, OfiDtCommentSya.class)
				.setParameter("sid", sid).setParameter("date", date)
				.getSingle(EmployeeCommentInformation::createFromMemento);
	}

	@Override
	public Optional<EmployeeCommentInformation> getTop1BySid(String sid) {
		return this.queryProxy()
				.query(SELECT_TOP_1_BY_SID, OfiDtCommentSya.class)
				.setParameter("sid", sid)
				.getList(EmployeeCommentInformation::createFromMemento)
				.stream().findFirst();
	}
}
