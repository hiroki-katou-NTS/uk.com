package nts.uk.ctx.at.request.app.find.dialog.annualholiday;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 残数情報を調整
 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL020_年休ダイアログ.アルゴリズム.社員の年休残数詳細情報を取得.残数情報を調整
 * @author phongtq
 *
 */
@Stateless
public class AdjustRemainingNumberInformation {
	public InforAnnualHolidaysAccHolidayDto adjustRemainingNumberInformation(ReNumAnnLeaReferenceDateImport reNumAnnLeave) {
		// 年休残数詳細情報DTOを作成
		InforAnnualHolidaysAccHolidayDto accHolidayDto = new InforAnnualHolidaysAccHolidayDto(new ArrayList<>(), new ArrayList<>(), false, "", "", "", false, "", "");
		
		// 年休残数詳細情報DTO．現時点残数をセット - todo QA
		// 年休残数詳細情報DTO．現時点残数　＝　Input．基準日時点年休残数．年休残日数　+　＃KDL020_14　+　Input．基準日時点年休残数．年休残時間　！＝　Empty　：　Input．基準日時点年休残数．年休残時間　：　Empty
		String annualLeaveGrantDay = this.getHoursMinu(reNumAnnLeave.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantDay().intValue());
		String annualLeaveGrantTime = reNumAnnLeave.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantTime() != null ?
				this.getHoursMinu(reNumAnnLeave.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantTime().intValue()) : "";
		accHolidayDto.setCurrentRemainNum(annualLeaveGrantDay + TextResource.localize("KDL020_14") + (annualLeaveGrantTime ));
		
		// 年休残数詳細情報DTO．時間年休の年間上限時間をセット - todo QA
		// 年休残数詳細情報DTO．時間年休の年間上限時間　＝　＃KDL020_65
		// 内容：{0}利用可
		// 　{0}　＝　Input．基準日時点年休残数．年休情報．残数．時間年休(マイナスなし)．時間　+　Input．基準日時点年休残数．年休情報．残数．時間年休(マイナスなし)．時間付与後
		accHolidayDto.setAnnMaxTime(
				TextResource.localize("KDL020_65", ""));
		
		// List＜年休残数詳細＞を作成
		List<AnnualAccumulatedHoliday> lstRemainAnnAccHoliday = new ArrayList<>();
		
		// 取得した基準日時点年休残数．年休付与残数データをチェック - todo
		if (accHolidayDto.getAnnLimitEnd() == null) { // fake tam
			AnnualAccumulatedHoliday remainAnnAccHoliday = new AnnualAccumulatedHoliday("", "", "", "", "");
			
			// 年休残数詳細．付与日をセット
			remainAnnAccHoliday.setGrandDate(TextResource.localize("KDL020_64", "", ""));
			
		} else {
			
		}
		
		return accHolidayDto;
	}
	
	private String getHoursMinu(int time) {
		String minu = String.valueOf(time % 60).length() > 1 ? String.valueOf(time % 60) : 0 + String.valueOf(time % 60);
		String result = String.valueOf(time / 60) + ":" + minu;
		return result;
	}
}
