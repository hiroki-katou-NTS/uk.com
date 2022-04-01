package nts.uk.ctx.at.shared.infra.repository.dailyattdcal.dailyattendance.timesheet.ouen.support;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.timesheet.ouen.support.SupportWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.support.AccountingOfMoveTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.support.SupportWorkSetting;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.support.KsrmtSupportTask;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaSupportWorkSettingRepository extends JpaRepository implements SupportWorkSettingRepository{

	@Override
	public SupportWorkSetting get(String cid) {
		KsrmtSupportTask entity = queryProxy().query("SELECT o FROM KsrmtSupportTask o WHERE o.cid = :cid", KsrmtSupportTask.class)
				.setParameter("cid", cid)
				.getSingleOrNull();
		if(entity == null) {
			return null;
		}
		SupportWorkSetting rs = toDomain(entity);
		return rs;
	}

	@Override
	public void update(SupportWorkSetting domain) {
		KsrmtSupportTask entity = this.queryProxy().find(domain.getCompanyId().toString(),KsrmtSupportTask.class).orElse(null);
		if(entity != null){
			entity.moveTimeAtr = domain.getAccountingOfMoveTime().value;
			commandProxy().update(entity);
		}
	}

	@Override
	public void insert(SupportWorkSetting domain) {
		KsrmtSupportTask entiti = this.queryProxy().find(domain.getCompanyId().toString(),KsrmtSupportTask.class).orElse(null);
		if(entiti == null){
			KsrmtSupportTask e = KsrmtSupportTask.convert(domain);
			commandProxy().insert(e);
		}
	}

	@Override
	public void delete(SupportWorkSetting domain) {
			commandProxy().remove(KsrmtSupportTask.convert(domain));
	}
	
	private SupportWorkSetting toDomain(KsrmtSupportTask entity) {
		return new SupportWorkSetting(entity.cid, NotUseAtr.USE, AccountingOfMoveTime.of(entity.moveTimeAtr));
	}

}
