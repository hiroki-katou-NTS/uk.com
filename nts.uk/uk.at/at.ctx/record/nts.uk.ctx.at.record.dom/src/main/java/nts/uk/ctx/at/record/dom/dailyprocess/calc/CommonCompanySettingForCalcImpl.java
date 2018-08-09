package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPUnitUseSettingRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.ot.zerotime.ZeroTimeRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 計算に必要なパラメータを取得する
 * @author keisuke_hoshina
 *
 */
@Stateless
public class CommonCompanySettingForCalcImpl implements CommonCompanySettingForCalc{

	//休暇加算設定
	@Inject
	private HolidayAddtionRepository holidayAddtionRepository;
	//総拘束時間
	@Inject
	private SpecificWorkRuleRepository specificWorkRuleRepository;
	//会社ごとの代休設定
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	//乖離
	@Inject 
	private DivergenceTimeRepository divergenceTimeRepository;
	//エラーアラーム設定
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	
	@Inject
	private BPUnitUseSettingRepository bPUnitUseSettingRepository; 
	
	@Inject
	private ZeroTimeRepository zeroTimeRepository;
	
	@Override
	public ManagePerCompanySet getCompanySetting() {
		
		String companyId = AppContexts.user().companyId();
		
		return new ManagePerCompanySet(holidayAddtionRepository.findByCompanyId(companyId),
									  holidayAddtionRepository.findByCId(companyId),
									  specificWorkRuleRepository.findCalcMethodByCid(companyId),
									  compensLeaveComSetRepository.find(companyId),
									  divergenceTimeRepository.getAllDivTime(companyId),
									  errorAlarmWorkRecordRepository.getAllErAlCompanyAndUseAtr(companyId, true),
									  bPUnitUseSettingRepository.getSetting(companyId),
									  zeroTimeRepository.findByCId(companyId));
	}

}
