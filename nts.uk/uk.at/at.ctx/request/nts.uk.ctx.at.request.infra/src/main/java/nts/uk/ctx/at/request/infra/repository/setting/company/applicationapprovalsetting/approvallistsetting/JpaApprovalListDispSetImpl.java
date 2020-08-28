package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.approvallistsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.WeekNumberDays;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.approvallistsetting.KrqmtApproval;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApprovalListDispSetImpl extends JpaRepository implements ApprovalListDispSetRepository {

	@Override
	public Optional<ApprovalListDisplaySetting> findByCID(String companyID) {
		String sql = "select * from KRQMT_APPROVAL where CID = @companyID";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.getSingle(rec -> {
						return new ApprovalListDisplaySetting(
								rec.getString("CID"), 
								EnumAdaptor.valueOf(rec.getInt("REASON_DISP_ATR"), DisplayAtr.class), 
								EnumAdaptor.valueOf(rec.getInt("PRE_EXCESS_ATR"), DisplayAtr.class), 
								EnumAdaptor.valueOf(rec.getInt("ATD_EXCESS_ATR"), DisplayAtr.class), 
								new WeekNumberDays(rec.getInt("WARNING_DAYS")), 
								EnumAdaptor.valueOf(rec.getInt("WKP_DISP_ATR"), NotUseAtr.class));
				});
	}

	@Override
	public void save(ApprovalListDisplaySetting domain) {
		Optional<KrqmtApproval> optEntity = this.queryProxy().find(domain.getCompanyID(), KrqmtApproval.class);
		if (optEntity.isPresent()) {
			KrqmtApproval entity = optEntity.get();
			entity.setAppReasonDisAtr(domain.getAppReasonDisAtr().value);
			entity.setAdvanceExcessMessDisAtr(domain.getAdvanceExcessMessDisAtr().value);
			entity.setActualExcessMessDisAtr(domain.getActualExcessMessDisAtr().value);
			entity.setWarningDateDisAtr(domain.getWarningDateDisAtr().v());
			entity.setDisplayWorkPlaceName(domain.getDisplayWorkPlaceName().value);
			this.commandProxy().update(entity);
		} else {
			KrqmtApproval entity = new KrqmtApproval(
					domain.getCompanyID(),
					domain.getAppReasonDisAtr().value,
					domain.getAdvanceExcessMessDisAtr().value,
					domain.getActualExcessMessDisAtr().value,
					domain.getWarningDateDisAtr().v(),
					domain.getDisplayWorkPlaceName().value
			);
			this.commandProxy().insert(entity);
		}
	}

}
