package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.i18n.I18NText;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;
import nts.uk.ctx.at.request.dom.application.overtime.service.DetailOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author hoangnd
 *
 */
@Stateless
public class AsposeAppOverTime {
	public static final String HALF_SIZE_SPACE = " ";
	public static final String EMPTY_STRING = "";
	public static final String SPLIT_TIME = "、";
	public static final String TILDLE_STRING = " ～ ";
	private static final String TIME_ZERO = new TimeWithDayAttr(0).getInDayTimeWithFormat();
	
	
	public StringBuilder getContentReason (Optional<DetailOutput> opDetailOutput, int frame, boolean c1, boolean c2) {
		StringBuilder d31 = new StringBuilder("");
		// 申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．選択肢一覧
		// 申請の印刷内容．残業申請の印刷内容．残業申請．申請時間．乖離理由
		// 条件：選択肢一覧．コード = 残業申請．申請時間．乖離理由．理由コード　AND　残業申請．申請時間．乖離理由．乖離時間NO = frame
		Optional<ReasonDivergence> reOptional1 = opDetailOutput.get().getAppOverTime().getApplicationTime().getReasonDissociation().get()
			.stream()
			.filter(x -> x.getDiviationTime() == frame)
			.findFirst();
		Optional<DivergenceReasonInputMethod> diOptional1 = opDetailOutput.get().getDisplayInfoOverTime()
							.getInfoNoBaseDate()
							.getDivergenceReasonInputMethod()
							.stream()
							.filter(x -> x.getDivergenceTimeNo() == frame)
							.findFirst();
		String contentCodeD31 = diOptional1.map(x -> x.getReasons()).orElse(Collections.emptyList())
				.stream()
				.filter(x -> Optional.ofNullable(x.getDivergenceReasonCode()).map(y -> y.v()).orElse("")
						.equals(reOptional1.flatMap(a -> Optional.ofNullable(a.getReasonCode())).map(b -> b.v()).orElse(""))
						)
				.findFirst()
				.flatMap(z -> Optional.ofNullable(z.getReason()))
				.map(a -> a.v())
				.orElse(
						StringUtils.isBlank(reOptional1.flatMap(a -> Optional.ofNullable(a.getReasonCode())).map(b -> b.v()).orElse("")) ? 
								"" 
								: 
								reOptional1.flatMap(a -> Optional.ofNullable(a.getReasonCode())).map(b -> b.v()).orElse("") + " " + I18NText.getText("KAF005_345"));
				
		String contentInputD31 = reOptional1.flatMap(x -> Optional.ofNullable(x.getReason())).map(x -> x.v()).orElse("");
		if (c1) {
			d31.append(contentCodeD31);			
		}
		if (!StringUtils.isBlank(contentCodeD31)) {
			d31.append("\n");			
		}
		if (c2) {
			d31.append(contentInputD31);			
		}
		return d31;
	}
	
	public AsposeAppOverTime.CalRange printAppOverTimeContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		Optional<DetailOutput> opDetailOutput = printContentOfApp.getOpDetailOutput();
		if (!opDetailOutput.isPresent()) return new CalRange() ;
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
		Boolean c3_1 = false;
		if (displayInfoOverTime.getAppDispInfoStartup().getAppDetailScreenInfo().isPresent()) {
			c3_1 = (displayInfoOverTime.getAppDispInfoStartup().getAppDetailScreenInfo().get().getApplication().getPrePostAtr() == PrePostAtr.PREDICT 
					&& displayInfoOverTime.getInfoNoBaseDate().getOverTimeReflect().getOvertimeWorkAppReflect().getBefore().getBreakLeaveApplication().getBreakReflectAtr() == NotUseAtr.USE
					)
					|| ( displayInfoOverTime.getAppDispInfoStartup().getAppDetailScreenInfo().get().getApplication().getPrePostAtr() == PrePostAtr.POSTERIOR
					&& displayInfoOverTime.getInfoNoBaseDate().getOverTimeReflect().getOvertimeWorkAppReflect().getAfter().getBreakLeaveApplication().getBreakReflectAtr() == NotUseAtr.USE
							);			
		}
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
	    
			
		cellB8.setValue(I18NText.getText("KAF005_34"));
		
		cellB9.setValue(I18NText.getText("KAF005_35"));
		
		cellB10.setValue(I18NText.getText("KAF005_37"));
		
		cellB11.setValue(I18NText.getText("KAF005_346"));
		
		cellB12.setValue(I18NText.getText("KAF005_40"));
		
		String workType = appOverTime.getWorkInfoOp().flatMap(x -> Optional.ofNullable(x.getWorkTypeCode())).map(x -> x.v()).orElse(null);
		String workTime = appOverTime.getWorkInfoOp().flatMap(x -> Optional.ofNullable(x.getWorkTimeCode())).map(x -> x.v()).orElse(null);
		// 申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．基準日に関する情報．勤務種類リスト
		if (workType != null) {
			String nameWorktype = displayInfoOverTime.getInfoBaseDateOutput().getWorktypes()
				.stream()
				.filter(x -> x.getWorkTypeCode().v().equals(workType))
				.findFirst().map(x -> x.getName().v()).orElse(null);
			StringBuilder workBuilder = new StringBuilder("");
			workBuilder.append(StringUtils.isBlank(nameWorktype) ? workType + HALF_SIZE_SPACE + I18NText.getText("KAF005_345") : nameWorktype);
			cellD8.setValue(workBuilder);
			
			
		}
		
		
		// 申請の印刷内容．残業申請の印刷内容．残業申請の表示情報．申請表示情報．申請設定（基準日関係あり）．就業時間帯の設定
		if (workTime != null) {
			String nameWorktime = displayInfoOverTime.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst()
					.orElse(Collections.emptyList())	
					.stream()
					.filter(x -> x.getWorktimeCode().v().equals(workTime))
					.findFirst()
					.map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v())
					.orElse(null);
			StringBuilder workBuilder = new StringBuilder("");
			workBuilder.append(StringUtils.isBlank(nameWorktime) ? workTime + HALF_SIZE_SPACE + I18NText.getText("KAF005_345") : nameWorktime);
			cellD9.setValue(workBuilder);
			
			
		}
		StringBuilder contentD10 = new StringBuilder(EMPTY_STRING);
		StringBuilder contentD11 = new StringBuilder(EMPTY_STRING);
		appOverTime.getWorkHoursOp().orElse(Collections.emptyList())
			.stream()
			.forEach(x ->  {
				if (x.getWorkNo().v() == 1) {
					contentD10.append(x.getTimeZone().getStartTime().getFullText());
					contentD10.append(TILDLE_STRING);
					contentD10.append(x.getTimeZone().getEndTime().getFullText());					
				} else {
					contentD11.append(x.getTimeZone().getStartTime().getFullText());
					contentD11.append(TILDLE_STRING);
					contentD11.append(x.getTimeZone().getEndTime().getFullText());					
				}
			});
		cellD10.setValue(contentD10);
		cellD11.setValue(contentD11);
		StringBuilder contentD12 = new StringBuilder(EMPTY_STRING);
		appOverTime.getBreakTimeOp().orElse(Collections.emptyList())
			.stream()
			.forEach(x ->  {
				if (x.getWorkNo().v() != 1) {
					contentD12.append(SPLIT_TIME);
				}
				contentD12.append(x.getTimeZone().getStartTime().getFullText());
				contentD12.append(TILDLE_STRING);
				contentD12.append(x.getTimeZone().getEndTime().getFullText());
			});
		cellD12.setValue(contentD12);	
		
			
			
		// }
		
		Cell cellD13 = cells.get("D13");
		Cell cellD14 = cells.get("D14");
		Cell cellD15 = cells.get("D15");
		Cell cellD16 = cells.get("D16");
		Cell cellD17 = cells.get("D17");
		Cell cellD18 = cells.get("D18");
		
		Cell cellH13 = cells.get("H13");
		Cell cellH14 = cells.get("H14");
		Cell cellH15 = cells.get("H15");
		Cell cellH16 = cells.get("H16");
		Cell cellH17 = cells.get("H17");
		Cell cellH18 = cells.get("H18");
		
		
		
		
		
		
		Cell cellD19 = cells.get("D19");
		Cell cellD20 = cells.get("D20");
		Cell cellD21 = cells.get("D21");
		Cell cellD22 = cells.get("D22");
		Cell cellD23 = cells.get("D23");
		Cell cellD24 = cells.get("D24");
		
		Cell cellD25 = cells.get("D25");
		
		Cell cellH19 = cells.get("H19");
		Cell cellH20 = cells.get("H20");
		Cell cellH21 = cells.get("H21");
		Cell cellH22 = cells.get("H22");
		Cell cellH23 = cells.get("H23");
		Cell cellH24 = cells.get("H24");
		
		
		
		Cell cellF13 = cells.get("F13");
		Cell cellF14 = cells.get("F14");
		Cell cellF15 = cells.get("F15");
		Cell cellF16 = cells.get("F16");
		Cell cellF17 = cells.get("F17");
		Cell cellF18 = cells.get("F18");
		
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
		Cell cellJ18 = cells.get("J18");
		
		List<OvertimeWorkFrame> overTimeQuotaLis = displayInfoOverTime.getInfoBaseDateOutput().getQuotaOutput().getOverTimeQuotaList();
		
		List<OverTimeObject> overTimeList = overTimeQuotaLis.stream()
						.map(x -> new OverTimeObject(x.getOvertimeWorkFrName().v(), 0, x.getOvertimeWorkFrNo().v().intValue()))
						.collect(Collectors.toList());
		
		Optional<AttendanceTime> overTimeMidNight = Optional.ofNullable(appOverTime.getApplicationTime()
												.getOverTimeShiftNight()
												.map(OverTimeShiftNight::getOverTimeMidNight)
												.orElse(null));
		if (overTimeMidNight.isPresent()) {
			if (overTimeMidNight.get().v() > 0) {
				overTimeList.add(new OverTimeObject(I18NText.getText("KAF005_63"), overTimeMidNight.get().v(), 11));			
			}
		}
		
		if (appOverTime.getApplicationTime().getFlexOverTime().isPresent()) {
			if (appOverTime.getApplicationTime().getFlexOverTime().get().v() > 0) {
				overTimeList.add(new OverTimeObject(I18NText.getText("KAF005_65"), appOverTime.getApplicationTime().getFlexOverTime().get().v(), 12));	
			}
		}
		List<OverTimeObject> holidayList = displayInfoOverTime.getWorkdayoffFrames()
														.stream()
														.map(x -> new OverTimeObject(x.getWorkdayoffFrName().v(), 0, x.getWorkdayoffFrNo().v().intValue()))
														.collect(Collectors.toList());
		
		if (appOverTime.getApplicationTime().getOverTimeShiftNight().isPresent()) {
			List<HolidayMidNightTime> midNightHolidayTimes = appOverTime.getApplicationTime().getOverTimeShiftNight().get().getMidNightHolidayTimes();
			if (CollectionUtil.isEmpty(midNightHolidayTimes)) {
				midNightHolidayTimes = new ArrayList<HolidayMidNightTime>();
			}
			holidayList.addAll(
					midNightHolidayTimes.stream().map(x -> {
									if (x.getAttendanceTime() != null) {
										if (x.getAttendanceTime().v() > 0) {
				//							String time = new TimeWithDayAttr(x.getAttendanceTime().v()).getInDayTimeWithFormat();
											if (x.getLegalClf() == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork) {
												return new OverTimeObject(I18NText.getText("KAF005_341"),x.getAttendanceTime().v(), 11 );
											} else if (x.getLegalClf() == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork) {
												return new OverTimeObject(I18NText.getText("KAF005_342"), x.getAttendanceTime().v(), 12);
											} else if (x.getLegalClf() == StaturoryAtrOfHolidayWork.PublicHolidayWork) {
												return new OverTimeObject(I18NText.getText("KAF005_343"), x.getAttendanceTime().v(), 14);
											}													
										}
									}
									return null;
								}).filter(x -> x != null)
								.collect(Collectors.toList())
			);
			
		}
		
		final List<OverTimeObject> overTimeListTemp = overTimeList;
		final List<OverTimeObject> holidayListTemp = holidayList;
		appOverTime.getApplicationTime().getApplicationTime()
				  						.stream()
				  						.forEach(x -> {
				  							if (x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME) {
				  								Optional<OverTimeObject> overTimeObjectOp =  overTimeListTemp.stream()
				  										.filter(a -> a.getFrame() == x.getFrameNo().v() && a.getFrame() > 0 && a.getFrame() < 11).findFirst();
				  								if (overTimeObjectOp.isPresent()) {
				  									if (x.getApplicationTime().v() > 0) {
				  										overTimeObjectOp.get().setTime(x.getApplicationTime().v());
				  									}
				  								}
				  							} else if (x.getAttendanceType() == AttendanceType_Update.BREAKTIME) {
				  								Optional<OverTimeObject> overTimeObjectOp =  holidayListTemp.stream()
				  										.filter(a -> a.getFrame() == x.getFrameNo().v() && a.getFrame() > 0 && a.getFrame() < 11).findFirst();
				  								if (overTimeObjectOp.isPresent()) {
				  									if (x.getApplicationTime().v() > 0) {
				  										overTimeObjectOp.get().setTime(x.getApplicationTime().v());
				  									}				  									
				  								}
				  							}
				  							
				  						});
		
		if (!CollectionUtil.isEmpty(holidayList)) {
			holidayList = holidayList.stream().filter(x -> x.getTime() > 0).collect(Collectors.toList());
			final List<OverTimeObject> holidayListTemp2 = holidayList;
			IntStream.range(0, holidayList.size())
			         .forEach(x -> {
			        	 OverTimeObject object = holidayListTemp2.get(x);
			        	 String time = new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat();
			        	 if (x == 0) {
			        		 cellD19.setValue(object.getName());
			        		 cellF19.setValue(time);
			        	 } else if (x == 1) {
			        		 cellH19.setValue(object.getName());
			        		 cellJ19.setValue(time);
			        	 } else if (x == 2) {
			        		 cellD20.setValue(object.getName());
			        		 cellF20.setValue(time);
			        	 } else if (x == 3) {
			        		 cellH20.setValue(object.getName());
			        		 cellJ20.setValue(time);
			        	 }  else if (x == 4) {
			        		 cellD21.setValue(object.getName());
			        		 cellF21.setValue(time);
			        	 } else if (x == 5) {
			        		 cellH21.setValue(object.getName());
			        		 cellJ21.setValue(time);
			        	 }  else if (x == 6) {
			        		 cellD22.setValue(object.getName());
			        		 cellF22.setValue(time);
			        	 } else if (x == 7) {
			        		 cellH22.setValue(object.getName());
			        		 cellJ22.setValue(time);
			        	 }  else if (x == 8) {
			        		 cellD23.setValue(object.getName());
			        		 cellF23.setValue(time);
			        	 } else if (x == 9) {
			        		 cellH23.setValue(object.getName());
			        		 cellJ23.setValue(time);
			        	 } else if (x == 10) {
			        		 cellD24.setValue(object.getName());
			        		 cellF24.setValue(time);
			        	 } else if (x == 11) {
			        		 cellH24.setValue(object.getName());
			        		 cellJ24.setValue(time);
			        	 }  else if (x == 12) {
			        		 cellD25.setValue(object.getName());
			        		 cellF25.setValue(time);
			        	 }
			        	 
			         });
		}
		
		
		if (!CollectionUtil.isEmpty(overTimeList)) {
			overTimeList = overTimeList.stream().filter(x -> x.getTime() > 0).collect(Collectors.toList());
			final List<OverTimeObject> overTimeListTemp2 = overTimeList;
			IntStream.range(0, overTimeList.size())
			         .forEach(x -> {
			        	 OverTimeObject object = overTimeListTemp2.get(x);
			        	 if (x == 0) {
			        		 cellD13.setValue(object.getName());
			        		 cellF13.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 } else if (x == 1) {
			        		 cellH13.setValue(object.getName());
			        		 cellJ13.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 } else if (x == 2) {
			        		 cellD14.setValue(object.getName());
			        		 cellF14.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 } else if (x == 3) {
			        		 cellH14.setValue(object.getName());
			        		 cellJ14.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 }  else if (x == 4) {
			        		 cellD15.setValue(object.getName());
			        		 cellF15.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 } else if (x == 5) {
			        		 cellH15.setValue(object.getName());
			        		 cellJ15.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 }  else if (x == 6) {
			        		 cellD16.setValue(object.getName());
			        		 cellF16.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 } else if (x == 7) {
			        		 cellH16.setValue(object.getName());
			        		 cellJ16.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 }  else if (x == 8) {
			        		 cellD17.setValue(object.getName());
			        		 cellF17.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 } else if (x == 9) {
			        		 cellH17.setValue(object.getName());
			        		 cellJ17.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 } else if (x == 10) {
			        		 cellD18.setValue(object.getName());
			        		 cellF18.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 } else if (x == 11) {
			        		 cellH18.setValue(object.getName());
			        		 cellJ18.setValue(new TimeWithDayAttr(object.getTime()).getInDayTimeWithFormat());
			        	 }
			        	 
			         });
		}
		

		
		Cell cellB30 = cells.get("B30");
		Cell cellD30 = cells.get("D30");
		Cell cellB33 = cells.get("B33");
		Cell cellD33 = cells.get("D33");
		
		String b30 = opDetailOutput.get().getDisplayInfoOverTime().getInfoNoBaseDate().getDivergenceTimeRoot()
					.stream()
					.filter(x -> x.getDivergenceTimeNo() == 1)
					.map(x -> x.getDivTimeName().v())
					.findFirst()
					.orElse(EMPTY_STRING);
		String b33 = opDetailOutput.get().getDisplayInfoOverTime().getInfoNoBaseDate().getDivergenceTimeRoot()
					.stream()
					.filter(x -> x.getDivergenceTimeNo() == 2)
					.map(x -> x.getDivTimeName().v())
					.findFirst()
					.orElse(EMPTY_STRING);
		cellB30.setValue(I18NText.getText("KAF005_93", b30));
		cellB33.setValue(I18NText.getText("KAF005_93", b33));
		if (appOverTime.getApplicationTime().getReasonDissociation().isPresent()) {
			StringBuilder d31 = this.getContentReason(opDetailOutput, 1, c6_1, c6_2);
			cellD30.setValue(d31);
			StringBuilder d34 = this.getContentReason(opDetailOutput, 2, c7_1, c7_2);
			cellD33.setValue(d34);
		}
		
		Cell cellB13 = cells.get("B13");
		Cell cellB19 = cells.get("B19");
		
		cellB13.setValue(I18NText.getText("KAF005_50"));
		cellB19.setValue(I18NText.getText("KAF005_70"));
		AsposeAppOverTime.CalRange result = new CalRange();
		if (!c7) {
			cells.deleteRows(31, 3);
			result.setStartReasonLabel(result.getStartReasonLabel() + 3);
		}
		if (!c6) {
			result.setStartReasonLabel(result.getStartReasonLabel() + 3);
			cells.deleteRows(29, 3);
		}

		if (!CollectionUtil.isEmpty(holidayList)) {
			int size = holidayList.size();
			int deleteRows = (size / 2) + (size % 2);
			result.setStartReasonCommon(result.getStartReasonCommon() + 7 - deleteRows);
			cells.deleteRows(18 + deleteRows, 7 - deleteRows);
		} else {
			result.setStartReasonCommon(result.getStartReasonCommon() + 7);
			cells.deleteRows(18, 7);
		}
		if (!CollectionUtil.isEmpty(overTimeList)) {
			int size = overTimeList.size();
			int deleteRows = (size / 2) + (size % 2);
			result.setStartReasonCommon(result.getStartReasonCommon() + 6 - deleteRows);
			cells.deleteRows(12 + deleteRows, 6 - deleteRows);
		} else {
			result.setStartReasonCommon(result.getStartReasonCommon() + 6);
			cells.deleteRows(12, 6);
		}
		if (!c1) {
			result.setStartReasonCommon(result.getStartReasonCommon() + 1);
			cells.deleteRow(11);
		}
		
		if (!c2 
				// || StringUtils.isBlank(contentD11)
				) {
			result.setStartReasonCommon(result.getStartReasonCommon() + 1);
			cells.deleteRow(10);
		}
		if (!c1) {
			result.setStartReasonCommon(result.getStartReasonCommon() + 3);
			cells.deleteRows(7, 3);
		}
		return result;
		
		
	}
	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	public class CalRange {
		private int startReasonCommon = 0;
		private int startReasonLabel = 0;
		
	}
}
