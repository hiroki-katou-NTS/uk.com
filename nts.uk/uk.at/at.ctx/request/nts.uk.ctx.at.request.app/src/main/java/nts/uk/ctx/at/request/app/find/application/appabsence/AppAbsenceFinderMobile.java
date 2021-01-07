package nts.uk.ctx.at.request.app.find.application.appabsence;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppForLeaveStartOutputDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeDateParamMobile;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.MaxDaySpecHdDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.MaxHolidayDayParamMobile;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectTypeHolidayParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectWorkOutputDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectWorkTypeHolidayParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.StartMobileParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.VacationCheckOutputDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcessMobile;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppForLeaveStartOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.VacationCheckOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.MaxDaySpecHdOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.SpecialHolidayEventAlgorithm;

@Stateless
public class AppAbsenceFinderMobile {
	public final static String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private AbsenceServiceProcessMobile absenceServiceProcessMobile;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private AppAbsenceFinder appAbsenceFinder;
	
	@Inject
	private SpecialHolidayEventAlgorithm specHdEventAlg;
	
	@Inject
	private AbsenceServiceProcess absenceServiceProcess;
	
	public AppForLeaveStartOutputDto start(StartMobileParam param) {
		
		AppForLeaveStartOutput output = absenceServiceProcessMobile.start(
				param.getMode(),
				param.getCompanyId(),
				Optional.ofNullable(param.getEmployeeIdOp()),
				param.getDatesOp()
						.stream()
						.map(x -> GeneralDate.fromString(x, DATE_FORMAT))
						.collect(Collectors.toList()),
				Optional.ofNullable(param.getAppAbsenceStartInfoOutputOp())
						.flatMap(x -> Optional.of(x.toDomain(param.getCompanyId()))),
				Optional.ofNullable(param.getApplyForLeaveOp()).flatMap(x -> Optional.of(x.toDomain())),
				param.getAppDispInfoStartupOutput().toDomain()
				);
		return AppForLeaveStartOutputDto.fromDomain(output);
	}
	
	/**
	 * Refactor5 申請日を変更する
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS06_休暇申請（スマホ）.A：休暇申請入力画面.アルゴリズム.申請日を変更する
	 * @param param
	 * @return
	 */
	public AppAbsenceStartInfoDto changeAppDate(ChangeDateParamMobile param) {
		// 申請表示情報(基準日関係あり)を取得する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.getAppDispInfoWithDate(
				param.getCompanyId(),
				ApplicationType.OVER_TIME_APPLICATION,
				Optional.ofNullable(param.getDates()).orElse(Collections.emptyList())
													 .stream()
													 .map(x -> GeneralDate.fromString(x, DATE_FORMAT))
													 .collect(Collectors.toList()),
				 param.getAppAbsenceStartInfoDto().getAppDispInfoStartupOutput().toDomain().getAppDispInfoNoDateOutput(),
				true,
				Optional.empty());
		AppAbsenceStartInfoOutput output = param.getAppAbsenceStartInfoDto().toDomain(param.getCompanyId());
		output.getAppDispInfoStartupOutput().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		// 申請日変更時処理
		return appAbsenceFinder.getChangeAppDate(
				param.getCompanyId(),
				param.getAppAbsenceStartInfoDto(),
				Optional.ofNullable(param.getDates()).orElse(Collections.emptyList()),
				ApplicationType.ABSENCE_APPLICATION.value,
				AppDispInfoWithDateDto.fromDomain(appDispInfoWithDateOutput));
	}
	/**
	 * Refactor5 
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS06_休暇申請（スマホ）.A：休暇申請入力画面.ユースケース
	 * @param param
	 * @return
	 */
	public MaxDaySpecHdDto getMaxDaySpecHd(MaxHolidayDayParamMobile param) {
		// 指定する特休枠の上限日数を取得する
		MaxDaySpecHdOutput output = specHdEventAlg.getMaxDaySpecHd(
				param.getCompanyId(),
				param.getSpecHdFrame(),
				param.getSpecHdEvent().toDomain(),
				Optional.ofNullable(param.getRelationCDOp()));
		return MaxDaySpecHdDto.fromDomain(output);
	}
	/**
	 * Refactor5 UKDesign.UniversalK.就業.KAF_申請.KAFS06_休暇申請（スマホ）.A：休暇申請入力画面.ユースケース
	 * @param param
	 * @return
	 */
	public AppAbsenceStartInfoDto selectTypeHoliday(SelectTypeHolidayParam param) {
		// 休暇種類変更時処理
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = absenceServiceProcess.holidayTypeChangeProcess(
				param.getCompanyId(),
				param.getAppAbsenceStartInfoDto().toDomain(param.getCompanyId()),
				Optional.ofNullable(param.getDates()).orElse(Collections.emptyList()),
			    HolidayAppType.ABSENCE);
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}
	/**
	 * Refactor5
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS06_休暇申請（スマホ）.A：休暇申請入力画面.ユースケース
	 * @param param
	 * @return
	 */
	public SelectWorkOutputDto selectWorkType(SelectWorkTypeHolidayParam param) {
		SelectWorkOutputDto output = new SelectWorkOutputDto();
		// 「KDLS02_勤務種類選択（スマホ）」を起動する From UI
		// 勤務種類変更時処理
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = absenceServiceProcess.workTypeChangeProcess(
				param.getCompanyId(),
				param.getDates(),
				param.getAppAbsenceStartInfoOutput().toDomain(param.getCompanyId()),
				EnumAdaptor.valueOf(param.getHolidayAppType(), HolidayAppType.class),
				Optional.ofNullable(param.getWorkTypeCodeAfterOp()).flatMap(x -> Optional.of(x.getWorkTypeCode())));
		output.appAbsenceStartInfoDto = AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
		// 休暇紐付管理をチェックする
		VacationCheckOutput vacationCheckOutput = absenceServiceProcess.checkVacationTyingManage(
				Optional.ofNullable(param.getWorkTypeCodeBeforeOp()).map(x -> x.toDomain()).orElse(null),
				Optional.ofNullable(param.getWorkTypeCodeBeforeOp()).map(x -> x.toDomain()).orElse(null),
				appAbsenceStartInfoOutput.getLeaveComDayOffManas(),
				appAbsenceStartInfoOutput.getPayoutSubofHDManagements());
		output.vacationCheckOutputDto = VacationCheckOutputDto.fromDomain(vacationCheckOutput);
		
		return output;
	}
}
