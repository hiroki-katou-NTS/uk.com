package nts.uk.ctx.at.request.infra.repository.application.gobackdirectly;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.DataWork;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkType;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectly;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectlyPK;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkTime;

/**
 * 
 * @author hoangnd
 *
 */
public class JpaGoBackDirectlyRepository extends JpaRepository implements GoBackDirectlyRepository {
	public static final String FIND_BY_ID = "SELECT * FROM KRQST_APP_GOBACK_DIRECTLY WHERE APP_ID = @appId";

	@Override
	public Optional<GoBackDirectly> find(String appId) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy()).paramString("appId", appId)
				.getSingle(res -> toDomain(res));
	}

	@Override
	public void add(GoBackDirectly domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(GoBackDirectly domain) {
		KrqdtGoBackDirectly krqdtGoBackDirectly = toEntity(domain);
		Optional<KrqdtGoBackDirectly> update = this.queryProxy().find(krqdtGoBackDirectly.krqdtGoBackDirectlyPK, KrqdtGoBackDirectly.class);
		if (!update.isPresent()) return;
		
		update.get().workTypeCD = krqdtGoBackDirectly.workTypeCD;
		update.get().workTimeCD = krqdtGoBackDirectly.workTimeCD;
		update.get().workChangeAtr = krqdtGoBackDirectly.workChangeAtr;
		update.get().goWorkAtr = krqdtGoBackDirectly.goWorkAtr;
		update.get().backHomeAtr = krqdtGoBackDirectly.backHomeAtr;
		this.commandProxy().update(update.get());
			
	}

	@Override
	public void remove(GoBackDirectly domain) {
		this.commandProxy().remove(KrqdtGoBackDirectly.class,
				new KrqdtGoBackDirectlyPK(AppContexts.user().companyId(), domain.getAppID()));

	}

	public GoBackDirectly toDomain(NtsResultRecord res) {
		GoBackDirectly goBackDirectly = new GoBackDirectly(null);
		goBackDirectly.setStraightDistinction(EnumAdaptor.valueOf(res.getInt("GO_WORK_ATR"), EnumConstant.class));
		goBackDirectly.setStraightLine(EnumAdaptor.valueOf(res.getInt("BACK_HOME_ATR"), EnumConstant.class));
		if (Optional.ofNullable(res.getInt("WORK_CHANGE_ATR")).isPresent()) {
			goBackDirectly.setIsChangedWork(
					Optional.of(EnumAdaptor.valueOf(res.getInt("WORK_CHANGE_ATR"), EnumConstant.class)));
		}
		if (Optional.ofNullable(res.getInt("WORK_TYPE_CD")).isPresent()
				|| Optional.ofNullable(res.getInt("WORK_TIME_CD")).isPresent()) {
			DataWork dataWork = new DataWork();
			goBackDirectly.setDataWork(Optional.of(dataWork));
			if (Optional.ofNullable(res.getInt("WORK_TYPE_CD")).isPresent()) {
				dataWork.setWorkType(new InforWorkType(String.valueOf(res.getInt("WORK_TYPE_CD")), null));
			}
			if (Optional.ofNullable(res.getInt("WORK_TYPE_CD")).isPresent()) {
				dataWork.setWorkTime(Optional.of(new InforWorkTime(String.valueOf(res.getInt("WORK_TIME_CD")), null)));
			}
		}

		return goBackDirectly;
	}

	public KrqdtGoBackDirectly toEntity(GoBackDirectly domain) {
		KrqdtGoBackDirectly krqdtGoBackDirectly = new KrqdtGoBackDirectly();
		krqdtGoBackDirectly.krqdtGoBackDirectlyPK = new KrqdtGoBackDirectlyPK(AppContexts.user().companyId(),
				domain.getAppID());
		if (domain.getDataWork().isPresent()) {
			DataWork dataWork = domain.getDataWork().get();
			krqdtGoBackDirectly.workTypeCD = dataWork.getWorkType().getWorkType();
			if (dataWork.getWorkTime().isPresent()) {
				krqdtGoBackDirectly.workTimeCD = dataWork.getWorkTime().get().getWorkTime();
			}
		}

		return krqdtGoBackDirectly;
	}

}
