package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.info;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveDataInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfoRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcmtCareHDInfo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaLeaveForCareInfoRepo extends JpaRepository implements LeaveForCareInfoRepository {
	
	private final static String SELECT_CARE_INFO_DATA_BY_SID = String.join(" ",
			"SELECT i.SID AS ISID, i.USE_ATR AS IUSE_ATR, i.UPPER_LIM_SET_ART AS IUPPER_LIM_SET_ART, i.MAX_DAY_THIS_FISCAL_YEAR AS IMAX_DAY_THIS_FISCAL_YEAR, i.MAX_DAY_NEXT_FISCAL_YEAR as IMAX_DAY_NEXT_FISCAL_YEAR,",
			"d.SID AS DSID, d.USED_DAYS AS DUSED_DAYS,",
			"ci.SID AS CISID, ci.USE_ATR AS CIUSE_ATR, i.UPPER_LIM_SET_ART AS CIUPPER_LIM_SET_ART, i.MAX_DAY_THIS_FISCAL_YEAR AS CIMAX_DAY_THIS_FISCAL_YEAR, i.MAX_DAY_NEXT_FISCAL_YEAR as CIMAX_DAY_NEXT_FISCAL_YEAR,",
			"cd.SID AS CDSID, cd.USED_DAYS as CDUSED_DAYS",
			"FROM [OOTSUKATEST].[dbo].[KRCMT_CARE_HD_INFO] i",
			"LEFT JOIN [OOTSUKATEST].[dbo].[KRCMT_CARE_HD_DATA] d",
			"ON i.SID = d.SID AND i.CID = '{cid}'",
			"LEFT JOIN [OOTSUKATEST].[dbo].[KRCMT_CHILD_CARE_HD_INFO] ci",
			"ON ci.SID = i.SID AND ci.CID = '{cid}'",
			"LEFT JOIN [OOTSUKATEST].[dbo].[KRCMT_CHILD_CARE_HD_DATA] cd",
			"ON cd.SID = i.SID AND cd.CID = '{cid}'",
			"WHERE i.SID = '{sid}'"); 

	@Override
	public Optional<LeaveForCareInfo> getCareByEmpId(String empId) {
		Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(empId, KrcmtCareHDInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtCareHDInfo entity = entityOpt.get();
			return Optional.of(LeaveForCareInfo.createCareLeaveInfo(entity.getSId(), entity.getUseAtr(),
					entity.getUpperLimSetAtr(), entity.getMaxDayThisFiscalYear(), entity.getMaxDayNextFiscalYear()));
		}
		return Optional.empty();
	}

	@Override
	public void add(LeaveForCareInfo obj, String cId) {
		KrcmtCareHDInfo entity = new KrcmtCareHDInfo(obj.getSId(), cId, obj.isUseClassification() ? 1 : 0,
				obj.getUpperlimitSetting().value,
				obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null,
				obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(LeaveForCareInfo obj, String cId) {
		Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtCareHDInfo entity = entityOpt.get();
			entity.setCId(cId);
			entity.setUseAtr(obj.isUseClassification() ? 1 : 0);
			entity.setUpperLimSetAtr(obj.getUpperlimitSetting().value);
			entity.setMaxDayNextFiscalYear(
					obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
			entity.setMaxDayThisFiscalYear(
					obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null);
			this.commandProxy().update(entity);
		}
	}

	@Override
	public Optional<CareLeaveDataInfo> getCareInfoDataBysId(String empId) {
		String sqlString = SELECT_CARE_INFO_DATA_BY_SID
				.replaceAll("\\{sid\\}", empId)
				.replaceAll("\\{cid\\}", AppContexts.user().companyId());
		
		@SuppressWarnings("unchecked")
		List<Object[]> queryResult =  this.getEntityManager().createNativeQuery(sqlString).getResultList();
		
		if(queryResult != null && queryResult.get(0) != null) {
			Object[] record = queryResult.get(0);
			
			if(record[0] != null && record[5] != null && record[7] != null && record[12] != null) {
				LeaveForCareInfo leaveForCareInfo = LeaveForCareInfo
						.createCareLeaveInfo(record[0].toString(), 
								Integer.parseInt(record[1].toString()), 
								Integer.parseInt(record[2].toString()),
								Double.parseDouble(record[3].toString()),
								Double.parseDouble(record[4].toString()));
				
				LeaveForCareData leaveForCareData = LeaveForCareData.getCareHDRemaining(record[5].toString(), Double.parseDouble(record[6].toString()));
				
				ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo =  ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(record[7].toString(),  
						Integer.parseInt(record[7].toString()), 
						Integer.parseInt(record[8].toString()),
						Double.parseDouble(record[10].toString()),
						Double.parseDouble(record[11].toString()));
				
				ChildCareLeaveRemainingData childCareLeaveRemainingData = ChildCareLeaveRemainingData.getChildCareHDRemaining(record[12].toString(), Double.parseDouble(record[13].toString()));
				
				return Optional.ofNullable(new CareLeaveDataInfo(leaveForCareInfo, leaveForCareData, childCareLeaveRemainingInfo , childCareLeaveRemainingData));
			}
		}
		
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}
