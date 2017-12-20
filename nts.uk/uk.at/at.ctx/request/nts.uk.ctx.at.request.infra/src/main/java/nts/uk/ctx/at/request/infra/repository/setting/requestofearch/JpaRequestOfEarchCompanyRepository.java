package nts.uk.ctx.at.request.infra.repository.setting.requestofearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.requestofeach.SelectionFlg;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstComAppConfig;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstComAppConfigDetail;
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

}
