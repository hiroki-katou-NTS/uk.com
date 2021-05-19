package nts.uk.ctx.health.infra.api.repository.emoji.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiState;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiStateRepository;
import nts.uk.ctx.health.infra.api.entity.emoji.employee.HhldtMoodSya;
import nts.uk.ctx.health.infra.api.entity.emoji.employee.HhldtMoodSyaPK;
import nts.uk.shr.com.context.AppContexts;

/*
 * Repository Implements UKDesign.ドメインモデル.NittsuSystem.UniversalK.NittsuSystem.Common (NtsCommons).ヘルスライフ.感情状態管理.感情状態管理.社員の感情状態
 * 社員の感情状態Repository
 */

@Stateless
public class EmployeeEmojiStateRepositoryImpl extends JpaRepository implements EmployeeEmojiStateRepository {

	// select by List Sids and Date
	private static final String SELECT_BY_SIDS_AND_DATE = "SELECT m FROM HhldtMoodSya m WHERE m.pk.sid IN :sids AND m.pk.date = :date";

	// select by List Sids and Period
	private static final String SELECT_BY_SIDS_AND_PERIOD = "SELECT m FROM HhldtMoodSya m WHERE m.pk.sid IN :sids AND m.pk.date >= :start AND m.pk.date <= :end";

	private static HhldtMoodSya toEntity(EmployeeEmojiState domain) {
		HhldtMoodSya entity = new HhldtMoodSya();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void insert(EmployeeEmojiState domain) {
		HhldtMoodSya entity = EmployeeEmojiStateRepositoryImpl.toEntity(domain);
		entity.setVersion(0);
		entity.setCid(AppContexts.user().companyId());
		entity.setContractCd(AppContexts.user().contractCode());
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(EmployeeEmojiState domain) {
		HhldtMoodSya entity = EmployeeEmojiStateRepositoryImpl.toEntity(domain);
		Optional<HhldtMoodSya> oldEntity = this.queryProxy().find(entity.getPk(),
				HhldtMoodSya.class);
		oldEntity.ifPresent(updateEntity -> {
			updateEntity.setEmojiType(entity.getEmojiType());
			this.commandProxy().update(updateEntity);
		});
	}

	@Override
	public Optional<EmployeeEmojiState> getBySidAndDate(String sid, GeneralDate date) {
		return this.queryProxy()
				.find(new HhldtMoodSyaPK(sid, date), HhldtMoodSya.class)
				.map(EmployeeEmojiState::createFromMemento);
	}

	@Override
	public List<EmployeeEmojiState> getByListSidAndDate(List<String> sids, GeneralDate date) {
		List<EmployeeEmojiState> list = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subSids -> {
			List<EmployeeEmojiState> subList = this.queryProxy()
					.query(SELECT_BY_SIDS_AND_DATE, HhldtMoodSya.class)
					.setParameter("sids", subSids)
					.setParameter("date", date)
					.getList(EmployeeEmojiState::createFromMemento);
			list.addAll(subList);
		});
		return list;
	}

	@Override
	public List<EmployeeEmojiState> getByListSidAndPeriod(List<String> sids, DatePeriod period) {
		List<EmployeeEmojiState> list = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subSids -> {
			List<EmployeeEmojiState> subList = this.queryProxy()
					.query(SELECT_BY_SIDS_AND_PERIOD, HhldtMoodSya.class)
					.setParameter("sids", subSids)
					.setParameter("start", period.start())
					.setParameter("end", period.end())
					.getList(EmployeeEmojiState::createFromMemento);
			list.addAll(subList);
		});
		return list;
	}
}
