package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

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
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.service.DetailOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author hoangnd
 *
 */
@Stateless
public class AsposeAppOverTime {
	public static final String HALF_SIZE_SPACE = " ";
	
	
	public int printAppOverTimeContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		Stack<Integer> deleteRows = new Stack<>();
		Optional<DetailOutput> opDetailOutput = printContentOfApp.getOpDetailOutput();
		if (!opDetailOutput.isPresent()) return 0 ;
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
	    
		// if (c1) {
			
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
			
		
			
			
		// }
		
		Cell cellD13 = cells.get("D13");
		Cell cellD14 = cells.get("D14");
		Cell cellD15 = cells.get("D15");
		Cell cellD16 = cells.get("D16");
		Cell cellD17 = cells.get("D17");
		Cell cellD18 = cells.get("D18");
		cellD18.setValue(I18NText.getText("KAF005_63"));
		Cell cellF18 = cells.get("F18");
		
		Cell cellH13 = cells.get("H13");
		Cell cellH14 = cells.get("H14");
		Cell cellH15 = cells.get("H15");
		Cell cellH16 = cells.get("H16");
		Cell cellH17 = cells.get("H17");
		Cell cellH18 = cells.get("H18");
		cellH18.setValue(I18NText.getText("KAF005_65"));
		Optional<AttendanceTime> overTimeMidNight = Optional.ofNullable(appOverTime.getApplicationTime()
												.getOverTimeShiftNight()
												.map(OverTimeShiftNight::getOverTimeMidNight)
												.orElse(null));
		if (overTimeMidNight.isPresent()) {
			cellF18.setValue(overTimeMidNight.get().v());			
		}
		Cell cellJ18 = cells.get("J18");
		cellJ18.setValue(appOverTime.getApplicationTime().getFlexOverTime().orElse(null));
		
		
		
		
		Cell cellD19 = cells.get("D19");
		Cell cellD20 = cells.get("D20");
		Cell cellD21 = cells.get("D21");
		Cell cellD22 = cells.get("D22");
		Cell cellD23 = cells.get("D23");
		Cell cellD24 = cells.get("D24");
		cellD24.setValue(I18NText.getText("KAF005_341"));
		
		Cell cellD25 = cells.get("D25");
		cellD25.setValue(I18NText.getText("KAF005_343"));
		
		Cell cellH19 = cells.get("H19");
		Cell cellH20 = cells.get("H20");
		Cell cellH21 = cells.get("H21");
		Cell cellH22 = cells.get("H22");
		Cell cellH23 = cells.get("H23");
		Cell cellH24 = cells.get("H24");
		cellH24.setValue(I18NText.getText("KAF005_342"));
		
		
		Cell cellF13 = cells.get("F13");
		Cell cellF14 = cells.get("F14");
		Cell cellF15 = cells.get("F15");
		Cell cellF16 = cells.get("F16");
		Cell cellF17 = cells.get("F17");
		
		Cell cellF19 = cells.get("F19");
		Cell cellF20 = cells.get("F20");
		Cell cellF21 = cells.get("F21");
		Cell cellF22 = cells.get("F22");
		Cell cellF23 = cells.get("F23");
		
		Cell cellF24 = cells.get("F24");
		Cell cellF25 = cells.get("F25");
		
		
		Cell cellJ19 = cells.get("J19");
		Cell cellJ20 = cells.get("J20");
		Cell cellJ21 = cells.get("J21");
		Cell cellJ22 = cells.get("J22");
		Cell cellJ23 = cells.get("J23");
		
		Cell cellJ24 = cells.get("J24");
		
		
		Cell cellJ13 = cells.get("J13");
		Cell cellJ14 = cells.get("J14");
		Cell cellJ15 = cells.get("J15");
		Cell cellJ16 = cells.get("J16");
		Cell cellJ17 = cells.get("J17");
		if (appOverTime.getApplicationTime().getOverTimeShiftNight().isPresent()) {
			List<HolidayMidNightTime> midNightHolidayTimes = appOverTime.getApplicationTime().getOverTimeShiftNight().get().getMidNightHolidayTimes();
			
			if (!CollectionUtil.isEmpty(midNightHolidayTimes)) {
				midNightHolidayTimes.stream().forEach(x -> {
					Integer time = x.getAttendanceTime().v();
					if (x.getLegalClf() == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork) {
						cellF24.setValue(time);
					} else if (x.getLegalClf() == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork) {
						cellJ24.setValue(time);
					} else if (x.getLegalClf() == StaturoryAtrOfHolidayWork.PublicHolidayWork) {
						cellF25.setValue(time);
					}
				});
			}
		}
		
		List<OvertimeWorkFrame> overTimeQuotaLis = displayInfoOverTime.getInfoBaseDateOutput().getQuotaOutput().getOverTimeQuotaList();
		
		overTimeQuotaLis.stream()
						.forEach(x -> {
							String nameWorkFrame = x.getOvertimeWorkFrName().v();
							if (x.getOvertimeWorkFrNo().v().intValue() == 1) {
								cellD13.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 2) {
								cellH13.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 3) {
								cellD14.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 4) {
								cellH14.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 5) {
								cellD15.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 6) {
								cellH15.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 7) {
								cellD16.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 8) {
								cellH16.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 9) {
								cellD17.setValue(nameWorkFrame);
							} else if (x.getOvertimeWorkFrNo().v().intValue() == 10) {
								cellH17.setValue(nameWorkFrame);
							} 
						});
		
		
		
		
		appOverTime.getApplicationTime().getApplicationTime()
				  						.stream()
				  						.forEach(x -> {
				  							if (x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME) {
				  								Integer time = x.getApplicationTime().v();
					  							if (x.getFrameNo().v() == 1) {
					  								cellF13.setValue(time);
					  							} else if (x.getFrameNo().v() == 2) {
					  								cellJ13.setValue(time);
					  							} else if (x.getFrameNo().v() == 3) {
					  								cellF14.setValue(time);
					  							} else if (x.getFrameNo().v() == 4) {
					  								cellJ14.setValue(time);
					  							} else if (x.getFrameNo().v() == 5) {
					  								cellF15.setValue(time);
					  							} else if (x.getFrameNo().v() == 6) {
					  								cellJ15.setValue(time);
					  							} else if (x.getFrameNo().v() == 7) {
					  								cellF16.setValue(time);
					  							} else if (x.getFrameNo().v() == 8) {
					  								cellJ16.setValue(time);
					  							} else if (x.getFrameNo().v() == 9) {
					  								cellF17.setValue(time);
					  							} else if (x.getFrameNo().v() == 10) {
					  								cellJ17.setValue(time);
					  							}
				  							} else if (x.getAttendanceType() == AttendanceType_Update.BREAKTIME) {
				  								Integer time = x.getApplicationTime().v();
					  							if (x.getFrameNo().v() == 1) {
					  								cellF19.setValue(time);
					  							} else if (x.getFrameNo().v() == 2) {
					  								cellJ19.setValue(time);
					  							} else if (x.getFrameNo().v() == 3) {
					  								cellF20.setValue(time);
					  							} else if (x.getFrameNo().v() == 4) {
					  								cellJ20.setValue(time);
					  							} else if (x.getFrameNo().v() == 5) {
					  								cellF21.setValue(time);
					  							} else if (x.getFrameNo().v() == 6) {
					  								cellJ21.setValue(time);
					  							} else if (x.getFrameNo().v() == 7) {
					  								cellF22.setValue(time);
					  							} else if (x.getFrameNo().v() == 8) {
					  								cellJ22.setValue(time);
					  							} else if (x.getFrameNo().v() == 9) {
					  								cellF23.setValue(time);
					  							} else if (x.getFrameNo().v() == 10) {
					  								cellJ23.setValue(time);
					  							}
				  							}
				  							
				  						});
		
		// 
		displayInfoOverTime.getWorkdayoffFrames()
						   .stream()
						   .forEach(x -> {
							   
							   String workdayoffFrName = x.getWorkdayoffFrName().v();
							   
							   if (x.getWorkdayoffFrNo().v().intValue() == 1) {
								   cellD19.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 2) {
								   cellH19.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 3) {
								   cellD20.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 4) {
								   cellH20.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 5) {
								   cellD21.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 6) {
								   cellH21.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 7) {
								   cellD22.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 8) {
								   cellH22.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 9) {
								   cellD23.setValue(workdayoffFrName);
							   } else if (x.getWorkdayoffFrNo().v().intValue() == 10) {
								   cellH23.setValue(workdayoffFrName);
							   }
							   
						   });
		
		Cell cellB31 = cells.get("B31");
		Cell cellD31 = cells.get("D31");
		Cell cellB32 = cells.get("B32");
		Cell cellD32 = cells.get("D32");
		
		if (!c1) {
//			deleteRows.push(8);
//			deleteRows.push(9);
//			deleteRows.push(10);
		}
		if (!c2) {
//			deleteRows.push(11);
		}
		if (!c3) {
//			deleteRows.push(12);			
		}
		if (!c4 && !c5) {
//			deleteRows.push(18);
		}
		if (!c6 && !c6_1 && !c6_2) {
//			deleteRows.push(31);
		}
		if (!c7 && !c7_1 && !c7_2) {
//			deleteRows.push(34);
		}
//		cells.deleteRows(34, 2);
		cells.deleteRows(31, 5);
		cells.deleteRows(28, 3);
		return 0;
		
		
	}
}
