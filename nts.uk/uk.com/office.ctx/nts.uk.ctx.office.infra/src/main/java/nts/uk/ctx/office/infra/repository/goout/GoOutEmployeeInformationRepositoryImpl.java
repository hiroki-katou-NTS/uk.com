package nts.uk.ctx.office.infra.repository.goout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;
import nts.uk.ctx.office.infra.entity.goout.GoOutEmployeeInformationEntity;
import nts.uk.ctx.office.infra.entity.goout.GoOutEmployeeInformationEntityPK;
import nts.uk.shr.com.context.AppContexts;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.社員の外出情報
 */
@Stateless
public class GoOutEmployeeInformationRepositoryImpl extends JpaRepository
		implements GoOutEmployeeInformationRepository {

	// select by List Sids and Date
	private static final String SELECT_BY_SIDS_AND_DATE = "SELECT m FROM GoOutEmployeeInformationEntity m WHERE m.pk.sid IN :sids AND m.pk.goOutDate = :date";

	private static GoOutEmployeeInformationEntity toEntity(GoOutEmployeeInformation domain) {
		GoOutEmployeeInformationEntity entity = new GoOutEmployeeInformationEntity();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void insert(GoOutEmployeeInformation domain) {
		GoOutEmployeeInformationEntity entity = GoOutEmployeeInformationRepositoryImpl.toEntity(domain);
		entity.setVersion(0);
		entity.setContractCd(AppContexts.user().contractCode());
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(GoOutEmployeeInformation domain) {
		GoOutEmployeeInformationEntity entity = GoOutEmployeeInformationRepositoryImpl.toEntity(domain);
		Optional<GoOutEmployeeInformationEntity> oldEntity = this.queryProxy().find(entity.getPk(),
				GoOutEmployeeInformationEntity.class);
		oldEntity.ifPresent(updateEntity -> {
			updateEntity.setVersion(updateEntity.getVersion() + 1);
			updateEntity.setGoOutTime(entity.getGoOutTime());
			updateEntity.setComebackTime(entity.getComebackTime());
			updateEntity.setGoOutReason(entity.getGoOutReason());
			this.commandProxy().update(updateEntity);
		});
	}

	@Override
	public void delete(GoOutEmployeeInformation domain) {
		GoOutEmployeeInformationEntity entity = GoOutEmployeeInformationRepositoryImpl.toEntity(domain);
		this.commandProxy().remove(GoOutEmployeeInformationEntity.class, entity.getPk());
	}

	@Override
	public List<GoOutEmployeeInformation> getByListSidAndDate(List<String> sids, GeneralDate date) {
		List<GoOutEmployeeInformation> list = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subSids -> {
			List<GoOutEmployeeInformation> subList = this.queryProxy()
					.query(SELECT_BY_SIDS_AND_DATE, GoOutEmployeeInformationEntity.class)
					.setParameter("sids", subSids)
					.setParameter("date", date)
					.getList(GoOutEmployeeInformation::createFromMemento);
			list.addAll(subList);
		});
		return list;
	}

	@Override
	public Optional<GoOutEmployeeInformation> getBySidAndDate(String sid, GeneralDate date) {
		return this.queryProxy()
				.find(new GoOutEmployeeInformationEntityPK(sid, date), GoOutEmployeeInformationEntity.class)
				.map(GoOutEmployeeInformation::createFromMemento);
	}
}
