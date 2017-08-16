package nts.uk.ctx.at.request.infra.repository.setting.requestofearch;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchCompany;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchCompanyRepository;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstComAppConfig;

public class JpaRequestOfEarchCompanyRepository extends JpaRepository implements RequestOfEarchCompanyRepository{

	private static final String FIND = "SELECT c "
			+ "FROM KrqstComAppConfig c "
			+ "WHERE c.krqstComAppConfigPK.companyId = :companyId "
			+ "AND c.krqstComAppConfigPK.appType = :appType";
	/**
	 * get request of earch company by app type
	 */
	@Override
	public Optional<RequestOfEarchCompany> getRequestByAppType(String companyId, int appType) {
		Optional<RequestOfEarchCompany> data = this.queryProxy()
				.query(FIND, KrqstComAppConfig.class)
				.setParameter("companyId", companyId)
				.setParameter("appType", appType)
				.getSingle(c -> toDomain(c));
		return data;
	}
	private RequestOfEarchCompany toDomain(KrqstComAppConfig entity) {
		return RequestOfEarchCompany.createSimpleFromJavaType(entity.krqstComAppConfigPK.companyId,
				entity.krqstComAppConfigPK.appType,
				entity.memo,
				entity.useAtr,
				entity.prerequisiteForpauseFlg,
				entity.otAppSettingFlg,
				entity.selectOfApproversFlg,
				entity.appUseSettingFlg,
				entity.otBreakInputFieldDisFlg,
				entity.otBreakTimeDisFlg,
				entity.otAtworkTimeBeginDisFlg,
				entity.otGoOutTimeBeginDisFlg,
				entity.otTimeCalUseAtr, 
				entity.otTimeInputUseAtr,
				entity.hwBreakInputFieldDisFlg,
				entity.hwBreakTimeDisFlg,
				entity.hwAtworkTimeBeginDisFlg,
				entity.hwGoOutTimeBeginDisFlg, 
				entity.hwTimeCalUseAtr,
				entity.hwTimeInputUseAtr);
	}

}
