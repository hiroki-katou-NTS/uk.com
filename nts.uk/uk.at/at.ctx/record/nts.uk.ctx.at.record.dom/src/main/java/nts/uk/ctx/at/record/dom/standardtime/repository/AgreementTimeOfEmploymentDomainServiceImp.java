package nts.uk.ctx.at.record.dom.standardtime.repository;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Employment36HoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class AgreementTimeOfEmploymentDomainServiceImp implements AgreementTimeOfEmploymentDomainService {

	@Inject
	private Employment36HoursRepository agreementTimeOfEmploymentRepostitory;

	@Override
	public List<String> add(BasicAgreementSetting basicAgreementSetting,
			AgreementTimeOfEmployment agreementTimeOfEmployment) {
		List<String> errors = this.checkError(basicAgreementSetting, agreementTimeOfEmployment);
		if (errors.isEmpty()) {
			this.agreementTimeOfEmploymentRepostitory.insert(agreementTimeOfEmployment);
		}
		return errors;
	}

	@Override
	public void remove(String companyId, String employmentCategoryCode, int laborSystemtAtr, String basicSettingId) {

		Optional<AgreementTimeOfEmployment> timeOfEmployment =  agreementTimeOfEmploymentRepostitory.getByCidAndCd(companyId,employmentCategoryCode, EnumAdaptor.valueOf(laborSystemtAtr,LaborSystemtAtr.class));
		if(timeOfEmployment.isPresent()){
			//2: delete
			agreementTimeOfEmploymentRepostitory.delete(timeOfEmployment.get());
		}
	}

	@Override
	public List<String> update(BasicAgreementSetting basicAgreementSetting, AgreementTimeOfEmployment agreementTimeOfEmployment) {

		List<String> errors = this.checkError(basicAgreementSetting, agreementTimeOfEmployment);
		if (errors.isEmpty()) {
			this.agreementTimeOfEmploymentRepostitory.update(agreementTimeOfEmployment);
		}
		return errors;
	}
	
	/**
	 * 登録時チェック処理 (Xử lý check khi ấn đăng ký)
	 * 
	 * @param basicAgreementSetting
	 * @return
	 */
	private List<String> checkError(BasicAgreementSetting basicAgreementSetting, AgreementTimeOfEmployment agreementTimeOfEmployment){
		List<String> result = new ArrayList<>();
		// アラーム時間、エラー時間、限度時間のチェックをする
		if (checkLimitTimeAndErrorTime(basicAgreementSetting)) {
			/**
			 * パラメータ parameters {0}：#KMK008_66 {1}：#KMK008_68
			 */
			String rowNamePeriod = getRowNamePeriodForLimitTime(basicAgreementSetting);
			result.add("Msg_59,"+rowNamePeriod+",KMK008_66,KMK008_68");
		}

		if (checkAlarmTimeAndErrorTime(basicAgreementSetting)) {
			/**
			 * パラメータ parameters {0}：#KMK008_67 {1}：#KMK008_66
			 * 
			 */
			String rowNamePeriod = getRowNamePeriodForAlarmTime(basicAgreementSetting);
			result.add("Msg_59,"+rowNamePeriod+",KMK008_67,KMK008_66");
		}
		
		// 上限規制とエラー時間のチェックをする
		if(checkUpperLimitAndErrorTime(basicAgreementSetting, agreementTimeOfEmployment)){
			result.add("Msg_1488,KMK008_96,KMK008_42,KMK008_120");
		}
		
		return result;
	}
	
	private boolean checkUpperLimitAndErrorTime(BasicAgreementSetting basicAgreementSetting,
			AgreementTimeOfEmployment agreementTimeOfEmployment) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		if (agreementTimeOfEmployment.getUpperAgreementSetting().getUpperMonth().v().intValue() > 0 && basicAgreementSetting.getErrorOneMonth()
//				.valueAsMinutes() > agreementTimeOfEmployment.getUpperAgreementSetting().getUpperMonth().valueAsMinutes()) {
//			return true;
//		}
		return false;
	}

	private boolean checkLimitTimeAndErrorTime(BasicAgreementSetting setting) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		if ((setting.getErrorWeek().v().compareTo(setting.getLimitWeek().v()) > 0 && setting.getLimitWeek().v()!=0)
//				|| (setting.getErrorTwoWeeks().v().compareTo(setting.getLimitTwoWeeks().v()) > 0 && setting.getLimitTwoWeeks().v()!=0)
//				|| (setting.getErrorFourWeeks().v().compareTo(setting.getLimitFourWeeks().v()) > 0 && setting.getLimitFourWeeks().v()!=0)
//				|| (setting.getErrorOneMonth().v().compareTo(setting.getLimitOneMonth().v()) > 0 && setting.getLimitOneMonth().v()!=0 )
//				|| (setting.getErrorTwoMonths().v().compareTo(setting.getLimitTwoMonths().v()) > 0 && setting.getLimitTwoMonths().v()!=0)
//				|| (setting.getErrorThreeMonths().v().compareTo(setting.getLimitThreeMonths().v()) > 0 && setting.getLimitThreeMonths().v()!=0)
//				|| (setting.getErrorOneYear().v().compareTo(setting.getLimitOneYear().v()) > 0) && setting.getLimitOneYear().v()!=0) {
//			return true;
//		}
		return false;
	}

	private boolean checkAlarmTimeAndErrorTime(BasicAgreementSetting setting) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		if (setting.getAlarmWeek().v().compareTo(setting.getErrorWeek().v()) > 0
//				|| setting.getAlarmTwoWeeks().v().compareTo(setting.getErrorTwoWeeks().v()) > 0
//				|| setting.getAlarmFourWeeks().v().compareTo(setting.getErrorFourWeeks().v()) > 0
//				|| setting.getAlarmOneMonth().v().compareTo(setting.getErrorOneMonth().v()) > 0
//				|| setting.getAlarmTwoMonths().v().compareTo(setting.getErrorTwoMonths().v()) > 0
//				|| setting.getAlarmThreeMonths().v().compareTo(setting.getErrorThreeMonths().v()) > 0
//				|| setting.getAlarmOneYear().v().compareTo(setting.getErrorOneYear().v()) > 0) {
//			return true;
//		}
		return false;
	}
	
	private String getRowNamePeriodForLimitTime(BasicAgreementSetting setting) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		if (setting.getErrorWeek().v().compareTo(setting.getLimitWeek().v()) > 0 && setting.getLimitWeek().v()!=0) {
//			return "KMK008_22";
//		}
//		if (setting.getErrorTwoWeeks().v().compareTo(setting.getLimitTwoWeeks().v()) > 0 && setting.getLimitTwoWeeks().v()!=0) {
//			return "KMK008_23";
//		}
//		if (setting.getErrorFourWeeks().v().compareTo(setting.getLimitFourWeeks().v()) > 0 && setting.getLimitFourWeeks().v()!=0) {
//			return "KMK008_24";
//		}
//		if (setting.getErrorOneMonth().v().compareTo(setting.getLimitOneMonth().v()) > 0 && setting.getLimitOneMonth().v()!=0 ) {
//			return "KMK008_25";
//		}
//		if (setting.getErrorTwoMonths().v().compareTo(setting.getLimitTwoMonths().v()) > 0 && setting.getLimitTwoMonths().v()!=0) {
//			return "KMK008_26";
//		}
//		if (setting.getErrorThreeMonths().v().compareTo(setting.getLimitThreeMonths().v()) > 0 && setting.getLimitThreeMonths().v()!=0) {
//			return "KMK008_27";
//		}
//		if (setting.getErrorOneYear().v().compareTo(setting.getLimitOneYear().v()) > 0 && setting.getLimitOneYear().v()!=0) {
//			return "KMK008_28";
//		}
		return "KMK008_22";
	}

	private String getRowNamePeriodForAlarmTime(BasicAgreementSetting setting) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		if (setting.getAlarmWeek().v().compareTo(setting.getErrorWeek().v()) > 0) {
//			return "KMK008_22";
//		}
//		if (setting.getAlarmTwoWeeks().v().compareTo(setting.getErrorTwoWeeks().v()) > 0) {
//			return "KMK008_23";
//		}
//		if (setting.getAlarmFourWeeks().v().compareTo(setting.getErrorFourWeeks().v()) > 0) {
//			return "KMK008_24";
//		}
//		if (setting.getAlarmOneMonth().v().compareTo(setting.getErrorOneMonth().v()) > 0) {
//			return "KMK008_25";
//		}
//		if (setting.getAlarmTwoMonths().v().compareTo(setting.getErrorTwoMonths().v()) > 0) {
//			return "KMK008_26";
//		}
//		if (setting.getAlarmThreeMonths().v().compareTo(setting.getErrorThreeMonths().v()) > 0) {
//			return "KMK008_27";
//		}
//		if (setting.getAlarmOneYear().v().compareTo(setting.getErrorOneYear().v()) > 0) {
//			return "KMK008_28";
//		}
		return "KMK008_22";
	}
}
