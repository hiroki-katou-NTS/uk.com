package nts.uk.ctx.at.request.infra.repository.setting.requestofearch;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachWorkplace;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.request.dom.setting.requestofeach.SelectionFlg;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstWpAppConfig;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstWpAppConfigDetail;
@Stateless
public class JpaRequestOfEarchWorkplaceRepository extends JpaRepository implements RequestOfEachWorkplaceRepository{

	private static final String FIND = "SELECT c "
			+ "FROM KrqstWpAppConfig c "
			+ "WHERE c.krqstWpAppConfigPK.companyId = :companyId "
			+ "AND c.krqstWpAppConfigPK.workplaceId = :workplaceId";
	
	private static final String FINDDETAIL = "SELECT c "
			+ "FROM KrqstWpAppConfigDetail c "
			+ "WHERE c.krqstWpAppConfigDetailPK.companyId = :companyId "
			+ "AND c.krqstWpAppConfigDetailPK.workplaceId = :workplaceId "
			+ "AND c.krqstWpAppConfigDetailPK.appType = :appType";

	/**
	 * get request of earch workplace by app type
	 */
	@Override
	public Optional<RequestOfEachWorkplace> getRequest(String companyId, String workplaceId) {
		Optional<RequestOfEachWorkplace> data = this.queryProxy()
				.query(FIND, KrqstWpAppConfig.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.getSingle(c -> toDomain(c));
		return data;
	}
	@Override
	public Optional<RequestAppDetailSetting> getRequestDetail(String companyId, String workplaceId, int appType) {
		Optional<RequestAppDetailSetting> data = this.queryProxy()
				.query(FINDDETAIL, KrqstWpAppConfigDetail.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("appType", appType)
				.getSingle(c -> toDomainDetail(c));
		return data;
	}
	
	private RequestOfEachWorkplace toDomain(KrqstWpAppConfig entity) {
		return new RequestOfEachWorkplace(
				entity.krqstWpAppConfigDetails.stream().map(x -> toDomainDetail(x)).collect(Collectors.toList()), 
				entity.krqstWpAppConfigPK.workplaceId, 
				EnumAdaptor.valueOf(entity.selectOfApproversFlg, SelectionFlg.class) );
	}
	
	private RequestAppDetailSetting toDomainDetail(KrqstWpAppConfigDetail c) {
		return RequestAppDetailSetting.createSimpleFromJavaType(c.krqstWpAppConfigDetailPK.companyId, 
				c.krqstWpAppConfigDetailPK.appType,
				c.memo,
				c.useAtr,
				c.prerequisiteForpauseFlg, 
				c.otAppSettingFlg, 
				c.holidayTimeAppCalFlg,
				c.lateOrLeaveAppCancelFlg,
				c.lateOrLeaveAppSettingFlg,
				c.breakInputFieldDisFlg,
				c.breakTimeDisFlg, 
				c.atworkTimeBeginDisFlg,
				c.goOutTimeBeginDisFlg, 
				c.timeCalUseAtr, 
				c.timeInputUseAtr,
				c.requiredInstructionFlg,
				c.instructionAtr,
				c.instructionMemo,
				c.instructionUseAtr,
				c.timeEndDispFlg);
	}

}
