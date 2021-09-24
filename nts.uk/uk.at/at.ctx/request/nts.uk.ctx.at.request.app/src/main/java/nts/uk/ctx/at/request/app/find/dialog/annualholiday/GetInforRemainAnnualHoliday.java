package nts.uk.ctx.at.request.app.find.dialog.annualholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto.AnnualHolidaysRemainingDto;
import nts.uk.ctx.at.request.dom.application.annualholiday.GetPeriodPrvNextGrantDateAdapter;
import nts.uk.ctx.at.request.dom.application.annualholiday.GrantPeriodDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL020_年休ダイアログ.アルゴリズム.社員の年休残数詳細情報を取得
 * 社員の年休残数詳細情報を取得
 *
 */
@Stateless
public class GetInforRemainAnnualHoliday {
	
	@Inject
	private RemainNumberTempRequireService requireService;
	
	@Inject
	private AnnLeaveRemainNumberAdapter leaveAdapter;
	
	@Inject GetPeriodPrvNextGrantDateAdapter dateAdapter;
	
	public AnnualHolidaysRemainingDto getGetInforRemainAnnualHoliday(GeneralDate baseDate, String sID) {
		val require = requireService.createRequire();
		String cId = AppContexts.user().companyId();
		
		// 10-1.年休の設定を取得する
		AnnualHolidaySetOutput annualHd = AbsenceTenProcess.getSettingForAnnualHoliday(require, cId);
		
		// 年休管理するかないかチェック (取得した年休管理区分をチェック)
		if (annualHd.isYearHolidayManagerFlg()) { 
			// if Trueの場合
			// No.198 基準日時点の年休残数を取得する
			ReNumAnnLeaReferenceDateImport reNumAnnLeave = leaveAdapter.getReferDateAnnualLeaveRemainNumber(sID, baseDate);
			
			// 残数情報を調整
			InforAnnualHolidaysAccHolidayDto accHolidayDto = null;
			
			// 指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
			Optional<GrantPeriodDto> periodGrantDate = dateAdapter.getPeriodYMDGrant(cId, sID, null, null, Optional.empty());
			// getPeriodFromPreviousToNextGrantDate.getPeriodYMDGrant(cid, employeeId, designatedDate, null, null).map(GrantPeriodDto::getPeriod);
			
			// 返す「基準日時点年休残数」をセット
			
			// ・年休・積休残数詳細情報DTO．時間年休の年間上限開始日　＝　取得した期間．開始日
			accHolidayDto.setAnnLimitStart(periodGrantDate.get().getPeriod().start().toString());
			
			// ・年休・積休残数詳細情報DTO．時間年休の年間上限終了日　＝　取得した期間．終了日
			accHolidayDto.setAnnLimitEnd(periodGrantDate.get().getPeriod().end().toString());
			
			// ・年休・積休残数詳細情報DTO．次回付与予定日　＝　取得した期間．開始日
			accHolidayDto.setNextScheDate(periodGrantDate.get().getPeriod().start().toString());

		}
		
		return null;
	}

}
