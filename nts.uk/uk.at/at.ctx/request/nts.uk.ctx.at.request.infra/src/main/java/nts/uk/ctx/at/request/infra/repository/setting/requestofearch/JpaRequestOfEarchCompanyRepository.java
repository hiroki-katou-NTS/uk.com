package nts.uk.ctx.at.request.infra.repository.setting.requestofearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.requestofeach.SelectionFlg;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstComAppConfig;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstComAppConfigDetail;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstComAppConfigDetailPK;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaRequestOfEarchCompanyRepository extends JpaRepository implements RequestOfEachCompanyRepository{

	private static final String FIND = "SELECT c "
			+ "FROM KrqstComAppConfig c "
			+ "WHERE c.companyId = :companyId ";
	private static final String FINDDETAIL = "SELECT C "
			+ "FROM KrqstComAppConfigDetail c "
			+ "WHERE c.krqstWpAppConfigDetailPK.companyId = :companyId "
			+ "AND c.krqstWpAppConfigDetailPK.appType = :appType";

	private RequestOfEachCompany toDomain(KrqstComAppConfig entity) {
		return new RequestOfEachCompany(
				entity.krqstComAppConfigDetails.stream().map(x -> toDomainDetail(x)).collect(Collectors.toList()), 
				EnumAdaptor.valueOf(entity.selectOfApproversFlg, SelectionFlg.class));
	}
	/**
	 * get request by company
	 */
	@Override
	public Optional<RequestOfEachCompany> getRequestByCompany(String companyId) {
		Optional<RequestOfEachCompany> data = this.queryProxy()
				.query(FIND, KrqstComAppConfig.class)
				.setParameter("companyId", companyId)
				.getSingle(c -> toDomain(c));
		return data;
	}
	/**
	 * get request detail
	 */
	@Override
	public Optional<RequestAppDetailSetting> getRequestDetail(String companyId, int appType) {
		Optional<RequestAppDetailSetting> data = this.queryProxy()
				.query(FINDDETAIL, KrqstComAppConfigDetail.class)
				.setParameter("companyId", companyId)
				.setParameter("appType", appType)
				.getSingle(c -> toDomainDetail(c));
		return data;
	}
	private RequestAppDetailSetting toDomainDetail(KrqstComAppConfigDetail c) {
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
	/**
	 * convert from Request App Detail Setting domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private  KrqstComAppConfigDetail toEntityDetail(RequestAppDetailSetting domain){
		val entity = new KrqstComAppConfigDetail();
		entity.krqstWpAppConfigDetailPK = new KrqstComAppConfigDetailPK(domain.getCompanyId(), domain.getAppType().value);
		entity.atworkTimeBeginDisFlg = domain.getAtworkTimeBeginDisFlg().value;
		entity.breakInputFieldDisFlg = domain.getBreakInputFieldDisFlg().value;
		entity.breakTimeDisFlg = domain.getBreakTimeDisFlg().value;
		entity.goOutTimeBeginDisFlg = domain.getGoOutTimeBeginDisFlg().value;
		entity.holidayTimeAppCalFlg = domain.getHolidayTimeAppCalFlg().value;
		entity.instructionAtr = domain.getInstructionUseSetting().getInstructionAtr().value;
		entity.instructionMemo = domain.getInstructionUseSetting().getInstructionRemarks().v();
		entity.instructionUseAtr = domain.getInstructionUseSetting().getInstructionUseDivision().value;
		entity.lateOrLeaveAppCancelFlg = domain.getLateOrLeaveAppCancelFlg().value;
		entity.lateOrLeaveAppSettingFlg = domain.getLateOrLeaveAppSettingFlg().value;
		entity.memo = domain.getMemo().v();
		entity.otAppSettingFlg = domain.getOtAppSettingFlg().value;
		entity.prerequisiteForpauseFlg = domain.getPrerequisiteForpauseFlg().value;
		entity.requiredInstructionFlg = domain.getRequiredInstructionFlg().value;
		entity.timeCalUseAtr = domain.getTimeCalUseAtr().value;
		entity.timeEndDispFlg = domain.getTimeEndDispFlg().value;
		entity.timeInputUseAtr = domain.getTimeInputUseAtr().value;
		entity.useAtr = domain.getUserAtr().value;
		return entity;
	}
	/**
	 * convert form Request Of Each Company domain to entity 
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private KrqstComAppConfig toEntity(RequestOfEachCompany domain){
		val entity = new KrqstComAppConfig();
		List<KrqstComAppConfigDetail> list = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		entity.companyId = companyId;
		entity.selectOfApproversFlg = domain.getSelectOfApproversFlg().value;
		if(!domain.getRequestAppDetailSettings().isEmpty()){
			for(RequestAppDetailSetting item: domain.getRequestAppDetailSettings()){
				list.add(toEntityDetail(item));
			}
		}
		entity.krqstComAppConfigDetails = list;
		return entity;
	}
	
	/**
	 * update request of each company
	 * @author yennth
	 */
	@Override
	public void updateRequestOfEachCompany(RequestOfEachCompany request) {
		KrqstComAppConfig entity = toEntity(request);
		Optional<KrqstComAppConfig> oldEntity = this.queryProxy().find(entity.companyId, KrqstComAppConfig.class);
		oldEntity.get().selectOfApproversFlg = entity.selectOfApproversFlg;
		oldEntity.get().krqstComAppConfigDetails = entity.krqstComAppConfigDetails;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert request of each company
	 * @author yennth
	 */
	@Override
	public void insertRequestOfEachCompany(RequestOfEachCompany company) {
		KrqstComAppConfig entity = toEntity(company);
		this.commandProxy().insert(entity);
	}
}
