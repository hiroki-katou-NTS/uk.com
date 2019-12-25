package nts.uk.ctx.hr.develop.infra.repository.setting.datedisplay;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySetting;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingRepository;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingValue;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateSettingClass;
import nts.uk.ctx.hr.develop.infra.entity.setting.datedisplay.JdsmtDateDisplaySetting;

/**
 * @author anhdt
 *
 */
@Stateless
public class JpaDateDisplaySettingRepository extends JpaRepository implements DateDisplaySettingRepository {
	
	private static final String SEL = "SELECT dst FROM JdsmtDateDisplaySetting dst ";
	private static final String SEL_BY_COMPANY_ID = SEL + "WHERE dst.cid = :companyId ";
	private static final String SEL_BY_COMPANY_ID_PROGARM_ID = SEL + "WHERE dst.cid = :companyId AND dst.programId = :programId ";

	@Override
	public List<DateDisplaySetting> getSettingByCompanyId(String companyId) {
		return this.queryProxy()
			.query(SEL_BY_COMPANY_ID, JdsmtDateDisplaySetting.class)
			.setParameter("companyId", companyId)
			.getList(s -> toDomain(s));
	}

	@Override
	public List<DateDisplaySetting> getSettingByCompanyIdAndProgramId(String programId, String companyId) {
		return this.queryProxy()
				.query(SEL_BY_COMPANY_ID_PROGARM_ID, JdsmtDateDisplaySetting.class)
				.setParameter("companyId", companyId)
				.setParameter("programId", programId)
				.getList(s -> toDomain(s));
	}
	
    @Override
    public void add(String companyId, List<DateDisplaySetting> domains) {
        // this.commandProxy().insert(new JdsmtDateDisplaySetting(domain));
        for (DateDisplaySetting domain : domains) {
            this.commandProxy().insert(new JdsmtDateDisplaySetting(companyId, domain));
        }
    }
	
    @Override
    public void update(String companyId, List<DateDisplaySetting> domains) {
        for (DateDisplaySetting domain : domains) {
            this.commandProxy().update(new JdsmtDateDisplaySetting(companyId, domain));
        }
    }
	
	private DateDisplaySetting toDomain(JdsmtDateDisplaySetting entity) {
		
		DateDisplaySettingValue start = DateDisplaySettingValue.builder()
				.settingClass(EnumAdaptor.valueOf(entity.startDateSetClass, DateSettingClass.class))
				.settingDate(entity.startDateSetDate)
				.settingMonth(entity.startDateSetMonth)
				.settingNum(entity.startDateSetNum)
				.build();
		
		DateDisplaySettingValue end = null;
		if(entity.endDateSetClass != null) {
			end = DateDisplaySettingValue.builder()
					.settingClass(EnumAdaptor.valueOf(entity.endDateSetClass, DateSettingClass.class))
					.settingDate(entity.endDateSetDate)
					.settingMonth(entity.endDateSetMonth)
					.settingNum(entity.endDateSetNum)
					.build();
		}
		
		return  DateDisplaySetting.builder()
				.companyId(entity.cid)
				.programId(entity.programId)
				.startDateSetting(start)
				.endDateSetting(Optional.ofNullable(end))
				.build();	
	}

	
}
