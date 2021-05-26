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
import nts.uk.ctx.office.infra.entity.goout.OfidtGoOutInfoSya;
import nts.uk.ctx.office.infra.entity.goout.OfidtGoOutInfoSyaPK;
import nts.uk.shr.com.context.AppContexts;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.社員の外出情報
 */
@Stateless
public class GoOutEmployeeInformationRepositoryImpl extends JpaRepository
		implements GoOutEmployeeInformationRepository {

	// select by List Sids and Date
	private static final String SELECT_BY_SIDS_AND_DATE = "SELECT m FROM OfidtGoOutInfoSya m WHERE m.pk.sid IN :sids AND m.pk.goOutDate = :date";

	private static OfidtGoOutInfoSya toEntity(GoOutEmployeeInformation domain) {
		OfidtGoOutInfoSya entity = new OfidtGoOutInfoSya();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void insert(GoOutEmployeeInformation domain) {
		OfidtGoOutInfoSya entity = GoOutEmployeeInformationRepositoryImpl.toEntity(domain);
		entity.setCid(AppContexts.user().companyId());
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(GoOutEmployeeInformation domain) {
		OfidtGoOutInfoSya entity = GoOutEmployeeInformationRepositoryImpl.toEntity(domain);
		Optional<OfidtGoOutInfoSya> oldEntity = this.queryProxy().find(entity.getPk(),
				OfidtGoOutInfoSya.class);
		oldEntity.ifPresent(updateEntity -> {
			updateEntity.setGoOutTime(entity.getGoOutTime());
			updateEntity.setComebackTime(entity.getComebackTime());
			updateEntity.setGoOutReason(entity.getGoOutReason());
			this.commandProxy().update(updateEntity);
		});
	}

	@Override
	public void delete(GoOutEmployeeInformation domain) {
		OfidtGoOutInfoSya entity = GoOutEmployeeInformationRepositoryImpl.toEntity(domain);
		this.commandProxy().remove(OfidtGoOutInfoSya.class, entity.getPk());
	}

	@Override
	public List<GoOutEmployeeInformation> getByListSidAndDate(List<String> sids, GeneralDate date) {
		List<GoOutEmployeeInformation> list = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subSids -> {
			List<GoOutEmployeeInformation> subList = this.queryProxy()
					.query(SELECT_BY_SIDS_AND_DATE, OfidtGoOutInfoSya.class)
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
				.find(new OfidtGoOutInfoSyaPK(sid, date), OfidtGoOutInfoSya.class)
				.map(GoOutEmployeeInformation::createFromMemento);
	}
}
