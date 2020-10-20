package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.List;
import java.util.Optional;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.infra.entity.flex.KshstFlexWorkSetting;
import nts.uk.ctx.at.shared.infra.entity.flex.KshstFlexWorkSettingPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstElapseYears;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstElapseYearsPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantDateTbl;
import nts.uk.shr.com.context.AppContexts;

/**
 * 特別休暇付与経過年数テーブル
 * @author masaaki_jinno
 *
 */
public class JpaElapseYearRepository extends JpaRepository implements ElapseYearRepository{

//	private final static String SELECT_ELAPSE_BY_GDCD_QUERY 
//			= "SELECT e "
//			+ "FROM KshstElapseYears e "
//			+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode ";
//	
//	private final static String DELETE_All_ELAPSE = "DELETE FROM KshstElapseYears e "
//			+ "WHERE e.pk.companyId =:companyId AND e.pk.specialHolidayCode = :specialHolidayCode ";
	
	/**
	 * Create Elapse Year Domain From Entity
	 * @param c
	 * @return
	 */
	private ElapseYear createDomainFromEntity(KshstElapseYears e) {
		String companyId = AppContexts.user().companyId();
		int specialHolidayCode = e.pk.specialHolidayCode;
		boolean fixedAssign = e.fixedAssign == 1;
		int years = e.cycleYears;
		int months = e.cycleMonths;
		
		return ElapseYear.createFromJavaType(
			companyId, specialHolidayCode, fixedAssign, months, years);
	}

	/**
	 * Convert domain to entity
	 * @param domain
	 * @return
	 */
	private KshstElapseYears toEntity(ElapseYear domain) {
		KshstElapseYearsPK pk 
			= new KshstElapseYearsPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v());
		int years = 0;
		int months = 0;
		if ( domain.getGrantCycleAfterTbl().isPresent() ){
			years = domain.getGrantCycleAfterTbl().get().getElapseYearMonth().getYear();
			months = domain.getGrantCycleAfterTbl().get().getElapseYearMonth().getMonth();
		}
		int fixedAssign = 0;
		if (domain.isFixedAssign()){
			fixedAssign = 1;
		}
		return new KshstElapseYears(pk, fixedAssign, years, months);
	}
	
	/**
	 * 取得
	 */
	@Override
	public Optional<ElapseYear> findByCode(CompanyId companyId, SpecialHolidayCode specialHolidayCode) {
		Optional<ElapseYear> e = this.queryProxy()
			.find(new KshstElapseYearsPK(companyId.v(), specialHolidayCode.v().intValue()), KshstElapseYears.class)
			.map(x -> this.createDomainFromEntity(x));
		return e;
	}

	/**
	 * 削除
	 */
	@Override
	public void delete(String companyId, int specialHolidayCode) {
		this.commandProxy().remove(
				KshstElapseYears.class,
				new KshstElapseYearsPK(companyId, specialHolidayCode));
	}
	
	/**
	 * 更新
	 * @param elapseYear
	 */
	private void update(ElapseYear elapseYear) {
		this.delete(elapseYear.getCompanyId(), elapseYear.getSpecialHolidayCode().v());
		
		KshstElapseYears kshstElapseYears = this.toEntity(elapseYear);
		this.commandProxy().insert(kshstElapseYears);
		
	}
	
}

