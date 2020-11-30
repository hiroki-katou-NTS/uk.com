package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.DetailOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author hoangnd
 *
 */
@Stateless
public class AsposeAppOverTime {
	public static final String HALF_SIZE_SPACE = " ";
	
	
	public void printAppOverTimeContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		
		Optional<DetailOutput> opDetailOutput = printContentOfApp.getOpDetailOutput();
		if (!opDetailOutput.isPresent()) return;
		DisplayInfoOverTime displayInfoOverTime = opDetailOutput.get().getDisplayInfoOverTime();
		AppOverTime appOverTime = opDetailOutput.get().getAppOverTime();
		// 「申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．基準日に関係しない情報．残業申請設定．申請詳細設定．時刻計算利用区分」= する
		Boolean c1 = displayInfoOverTime.getInfoNoBaseDate().getOverTimeAppSet().getApplicationDetailSetting().getTimeCalUse() == NotUseAtr.USE;
		
		// ※1 = ○　AND
		// 「申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
		Boolean c2 = c1 && displayInfoOverTime.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles();
		
		// ドメインモデル「申請の印刷内容．残業申請の印刷内容．残業申請．申請．事前事後区分」= 事前　AND　
		// 「申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．基準日に関係しない情報．残業休日出勤申請の反映．残業申請．事前．休憩・外出を申請反映する」= する
		
		// ドメインモデル「申請の印刷内容．残業申請の印刷内容．残業申請．申請．事前事後区分」= 事後　AND　
		// 「申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．基準日に関係しない情報．残業休日出勤申請の反映．残業申請．事後．休憩・外出を申請反映する」= する
		Boolean c3_1 = (appOverTime.getApplication().getPrePostAtr() == PrePostAtr.POSTERIOR 
						&& displayInfoOverTime.getInfoNoBaseDate().getOverTimeReflect().getOvertimeWorkAppReflect().getBefore().getBreakLeaveApplication().getBreakReflectAtr() == NotUseAtr.USE
						)
				|| ( appOverTime.getApplication().getPrePostAtr() == PrePostAtr.PREDICT
						&& displayInfoOverTime.getInfoNoBaseDate().getOverTimeReflect().getOvertimeWorkAppReflect().getAfter().getBreakLeaveApplication().getBreakReflectAtr() == NotUseAtr.USE
						);
		// ※1 = ○　OR　※3-1 = ○
		Boolean c3 = c1 || c3_1;
		
		// 「申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．基準日に関係しない情報．残業休日出勤申請の反映．時間外深夜時間を反映する」= する
		Boolean c4 = displayInfoOverTime.getInfoNoBaseDate().getOverTimeReflect().getNightOvertimeReflectAtr() == NotUseAtr.USE;
		
		// 「申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．基準日に関する情報．残業申請で利用する残業枠．フレックス時間表示区分」= true
		Boolean c5 = displayInfoOverTime.getInfoBaseDateOutput().getQuotaOutput().getFlexTimeClf();
		
		// 「残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．NO = 1」 <> empty　AND　残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．乖離理由を選択肢から選ぶ = true
		Boolean c6_1 = !CollectionUtil.isEmpty(displayInfoOverTime.getInfoNoBaseDate().getDivergenceReasonInputMethod())
				? (displayInfoOverTime.getInfoNoBaseDate()
									  .getDivergenceReasonInputMethod()
									  .stream()
									  .filter(x -> x.isDivergenceReasonSelected() && x.getDivergenceTimeNo() == 1)
									  .findFirst().isPresent()
						
						) : false;
		// 「残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．NO = 1」 <> empty　AND　残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．乖離理由を入力する = true		
		Boolean c6_2 = !CollectionUtil.isEmpty(displayInfoOverTime.getInfoNoBaseDate().getDivergenceReasonInputMethod())
				? (displayInfoOverTime.getInfoNoBaseDate()
						  .getDivergenceReasonInputMethod()
						  .stream()
						  .filter(x -> x.isDivergenceReasonInputed() && x.getDivergenceTimeNo() == 1)
						  .findFirst().isPresent()
			
			) : false;
				
		// ※6-1 = ○　OR　※6-2 = ○
		Boolean c6 = c6_1 || c6_2;
		
		// 「残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．NO = 2」 <> empty　AND　残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．乖離理由を選択肢から選ぶ = true
		Boolean c7_1 = !CollectionUtil.isEmpty(displayInfoOverTime.getInfoNoBaseDate().getDivergenceReasonInputMethod())
				? (displayInfoOverTime.getInfoNoBaseDate()
						  .getDivergenceReasonInputMethod()
						  .stream()
						  .filter(x -> x.isDivergenceReasonSelected() && x.getDivergenceTimeNo() == 2)
						  .findFirst().isPresent()
			
			) : false;
		
		Boolean c7_2 = !CollectionUtil.isEmpty(displayInfoOverTime.getInfoNoBaseDate().getDivergenceReasonInputMethod())
				? (displayInfoOverTime.getInfoNoBaseDate()
						  .getDivergenceReasonInputMethod()
						  .stream()
						  .filter(x -> x.isDivergenceReasonInputed() && x.getDivergenceTimeNo() == 2)
						  .findFirst().isPresent()
			
			) : false;		
		
		Boolean c7 = c7_1 || c7_2;
		
		
		
		Cells cells = worksheet.getCells();
		Cell cellB8 = cells.get("B8");
		Cell cellB9 = cells.get("B9");
		Cell cellB10 = cells.get("B10");
		Cell cellB11 = cells.get("B11");
		Cell cellB12 = cells.get("B12");
		
		
		Cell cellD8 = cells.get("D8");
		Cell cellD9 = cells.get("D9");
		Cell cellD10 = cells.get("D10");
		Cell cellD11 = cells.get("D11");
		Cell cellD12 = cells.get("D12");
	    
		if (c1) {
			
			cellB8.setValue(I18NText.getText("KAF005_34"));
			
			cellB9.setValue(I18NText.getText("KAF005_35"));
			
			cellB10.setValue(I18NText.getText("KAF005_37"));
			
			cellB11.setValue(I18NText.getText("KAF005_346"));
			
			cellB12.setValue(I18NText.getText("KAF005_40"));
			
			String workType = appOverTime.getWorkInfoOp().map(x -> x.getWorkTypeCode().v()).orElse(null);
			String workTime = appOverTime.getWorkInfoOp().map(x -> x.getWorkTimeCode().v()).orElse(null);
			// 申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．基準日に関する情報．勤務種類リスト
			if (workType != null) {
				String nameWorktype = displayInfoOverTime.getInfoBaseDateOutput().getWorktypes()
					.stream()
					.filter(x -> x.getWorkTypeCode().v() == workType)
					.findFirst().map(x -> x.getName().v()).orElse(null);
				StringBuilder workBuilder = new StringBuilder(workType);
				workBuilder.append(HALF_SIZE_SPACE);
				workBuilder.append(StringUtils.isBlank(nameWorktype) ? I18NText.getText("KAF005_345") : nameWorktype);
				cellD8.setValue(workBuilder);
				
				
			}
			
			
			// 申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．申請表示情報．申請設定（基準日関係あり）．就業時間帯の設定
			if (workTime != null) {
				String nameWorktime = displayInfoOverTime.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst()
						.orElse(Collections.emptyList())	
						.stream()
						.filter(x -> x.getWorktimeCode().v() == workTime)
						.findFirst()
						.map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v())
						.orElse(null);
				StringBuilder workBuilder = new StringBuilder(workTime);
				workBuilder.append(HALF_SIZE_SPACE);
				workBuilder.append(StringUtils.isBlank(nameWorktime) ? I18NText.getText("KAF005_345") : nameWorktime);
				cellD9.setValue(workBuilder);
				
				
			}
			
			appOverTime.getWorkHoursOp().orElse(Collections.emptyList())
				.stream()
				.forEach(x ->  {
					if (x.getWorkNo().v() == 1) {
						
						cellD10.setValue("");
					} else if (x.getWorkNo().v() == 2) {
						cellD11.setValue("");
					}
				});
			appOverTime.getBreakTimeOp().orElse(Collections.emptyList())
				.stream()
				.forEach(x ->  {
					cellD12.setValue("");
				});
			
			
			
			
		}
		
		
		
		
	}
}
