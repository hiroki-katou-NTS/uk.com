package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.logging.log4j.util.Strings;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeApp;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeZoneApp;
import nts.uk.ctx.at.request.dom.application.stamp.EngraveAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampApp;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppOther;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.com.time.TimeZone;

@Stateless
public class AsposeAppStamp {

	private final String EMPTY = "";
	private final String HALF_WIDTH_SPACE = " ";

	/**
	 * @param worksheet
	 * @param printContentOfApp
	 */
	public void printAppStampContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		// check type print
		StampRequestMode type = printContentOfApp.getOpAppStampOutput().get().getAppDispInfoStartupOutput()
				.getAppDetailScreenInfo().get().getApplication().getOpStampRequestMode().get();
		Optional<AppStampOutput> appStampOutputOp = printContentOfApp.getOpAppStampOutput();
		Cells cells = worksheet.getCells();

		if (type == StampRequestMode.STAMP_ONLINE_RECORD) {
			Optional<AppRecordImage> appRecordImageOp = appStampOutputOp.get().getAppRecordImage();
			AppRecordImage appRecordImage = appRecordImageOp.get();
			Cell cellB8 = cells.get("B8");
			cellB8.setValue(I18NText.getText("KAF002_77"));
			Cell cellB9 = cells.get("B9");
			cellB9.setValue(I18NText.getText("KAF007_79"));

			// set content right side

			Cell cellD8 = cells.get("D8");
			cellD8.setValue(appRecordImage.getAppStampCombinationAtr().equals(EngraveAtr.GO_OUT)
					? (appRecordImage.getAppStampCombinationAtr().name + "（"
							+ appRecordImage.getAppStampGoOutAtr().get().nameId + "）")
					: (appRecordImage.getAppStampCombinationAtr().name));
			Cell cellD9 = cells.get("D9");
			cellD9.setValue(new TimeWithDayAttr(appRecordImage.getAttendanceTime().v()).getInDayTimeWithFormat());
		} else {
			String workHour = EMPTY, workHour2 = EMPTY, temporaryTime = EMPTY, temporaryTime2 = EMPTY,
					temporaryTime3 = EMPTY, outTime = EMPTY, outTime2 = EMPTY, outTime3 = EMPTY, outTime4 = EMPTY, outTime5 = EMPTY, outTime6 = EMPTY, outTime7 = EMPTY, outTime8 = EMPTY, outTime9 = EMPTY, outTime10 = EMPTY,
					breakTime = EMPTY, breakTime2 = EMPTY, breakTime3 = EMPTY, breakTime4 = EMPTY, breakTime5 = EMPTY, breakTime6 = EMPTY, breakTime7 = EMPTY, breakTime8 = EMPTY, breakTime9 = EMPTY, breakTime10 = EMPTY,
					childCareTime = EMPTY, childCareTime2 = EMPTY, nursingTime = EMPTY, nursingTime2 = EMPTY;
			int deleteCnt = 0;

			/*
			 * Set value for time items
			 */
			if (appStampOutputOp.get().getAppStampReflectOptional().isPresent()) {
				List<TimeStampApp> listTimeStampApp = appStampOutputOp.get().getAppStampOptional().get()
						.getListTimeStampApp();
				List<TimeStampAppOther> listTimeStampAppOther = appStampOutputOp.get().getAppStampOptional().get()
						.getListTimeStampAppOther();
				List<DestinationTimeApp> listDesTimeApp =  appStampOutputOp.get().getAppStampOptional().get().getListDestinationTimeApp();
				List<DestinationTimeZoneApp> listDestTimeZoneApp = appStampOutputOp.get().getAppStampOptional().get().getListDestinationTimeZoneApp();

				for (TimeStampApp timeStampApp : listTimeStampApp) {
					DestinationTimeApp destTimeApp = timeStampApp.getDestinationTimeApp();

					if (appStampOutputOp.get().getAppStampReflectOptional().get().getAttendence()
							.equals(NotUseAtr.USE)) {
						/*
						 * workHour1
						 */
						// Start time workHour1
						if (destTimeApp.getEngraveFrameNo() == 1 && destTimeApp.getTimeStampAppEnum().value == 0
								&& destTimeApp.getStartEndClassification().value == 0) {
							workHour = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time workHour1
						if (destTimeApp.getEngraveFrameNo() == 1 && destTimeApp.getTimeStampAppEnum().value == 0
								&& destTimeApp.getStartEndClassification().value == 1) {
							workHour += timeStampApp.getTimeOfDay().getFullText();
						}

						if (appStampOutputOp.get().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
								.isManagementMultipleWorkCycles()) {
							/*
							 * workHour2
							 */
							// Start time workHour2
							if (destTimeApp.getEngraveFrameNo() == 2 && destTimeApp.getTimeStampAppEnum().value == 0
									&& destTimeApp.getStartEndClassification().value == 0) {
								workHour2 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
										+ HALF_WIDTH_SPACE;
							}
							// End time workHour2
							if (destTimeApp.getEngraveFrameNo() == 2 && destTimeApp.getTimeStampAppEnum().value == 0
									&& destTimeApp.getStartEndClassification().value == 1) {
								workHour2 += timeStampApp.getTimeOfDay().getFullText();
							}
						}
					}

					if (appStampOutputOp.get().getAppStampReflectOptional().get().getTemporaryAttendence()
							.equals(NotUseAtr.USE)) {
						/*
						 * temporaryTime
						 */
						// Start time temporaryTime
						if (destTimeApp.getEngraveFrameNo() == 1 && destTimeApp.getTimeStampAppEnum().value == 1
								&& destTimeApp.getStartEndClassification().value == 0) {
							temporaryTime = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time temporaryTime
						if (destTimeApp.getEngraveFrameNo() == 1 && destTimeApp.getTimeStampAppEnum().value == 1
								&& destTimeApp.getStartEndClassification().value == 1) {
							temporaryTime += timeStampApp.getTimeOfDay().getFullText();
						}
						/*
						 * temporaryTime2
						 */
						// Start time temporaryTime2
						if (destTimeApp.getEngraveFrameNo() == 2 && destTimeApp.getTimeStampAppEnum().value == 1
								&& destTimeApp.getStartEndClassification().value == 0) {
							temporaryTime2 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time temporaryTime2
						if (destTimeApp.getEngraveFrameNo() == 2 && destTimeApp.getTimeStampAppEnum().value == 1
								&& destTimeApp.getStartEndClassification().value == 1) {
							temporaryTime2 += timeStampApp.getTimeOfDay().getFullText();
						}
						/*
						 * temporaryTime3
						 */
						// Start time temporaryTime3
						if (destTimeApp.getEngraveFrameNo() == 3 && destTimeApp.getTimeStampAppEnum().value == 1
								&& destTimeApp.getStartEndClassification().value == 0) {
							temporaryTime3 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time temporaryTime3
						if (destTimeApp.getEngraveFrameNo() == 3 && destTimeApp.getTimeStampAppEnum().value == 1
								&& destTimeApp.getStartEndClassification().value == 1) {
							temporaryTime3 += timeStampApp.getTimeOfDay().getFullText();
						}
					}

					if (appStampOutputOp.get().getAppStampReflectOptional().get().getOutingHourse()
							.equals(NotUseAtr.USE)) {
						/*
						 * outTime
						 */
						// Start time outTime
						if (destTimeApp.getEngraveFrameNo() == 1 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 1 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime2
						 */
						// Start time outTime2
						if (destTimeApp.getEngraveFrameNo() == 2 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime2 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime2
						if (destTimeApp.getEngraveFrameNo() == 2 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime2 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime2 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime3
						 */
						// Start time outTime3
						if (destTimeApp.getEngraveFrameNo() == 3 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime3 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 3 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime3 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime3 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime4
						 */
						// Start time outTime4
						if (destTimeApp.getEngraveFrameNo() == 4 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime4 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 4 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime4 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime4 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime5
						 */
						// Start time outTime5
						if (destTimeApp.getEngraveFrameNo() == 5 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime5 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 5 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime5 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime5 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime6
						 */
						// Start time outTime6
						if (destTimeApp.getEngraveFrameNo() == 6 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime6 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 6 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime6 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime6 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime7
						 */
						// Start time outTime7
						if (destTimeApp.getEngraveFrameNo() == 7 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime7 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 7 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime7 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime7 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime8
						 */
						// Start time outTime3
						if (destTimeApp.getEngraveFrameNo() == 8 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime8 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 8 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime8 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime8 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime9
						 */
						// Start time outTime9
						if (destTimeApp.getEngraveFrameNo() == 9 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime9 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 9 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime9 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime9 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
						/*
						 * outTime10
						 */
						// Start time outTime
						if (destTimeApp.getEngraveFrameNo() == 10 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 0) {
							outTime10 = timeStampApp.getTimeOfDay().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
						}
						// End time outTime
						if (destTimeApp.getEngraveFrameNo() == 10 && destTimeApp.getTimeStampAppEnum().value == 2
								&& destTimeApp.getStartEndClassification().value == 1) {
							outTime10 += timeStampApp.getTimeOfDay().getFullText();
							if(timeStampApp.getAppStampGoOutAtr().isPresent()) {
								outTime10 += HALF_WIDTH_SPACE + "（" + timeStampApp.getAppStampGoOutAtr().get().nameId + "）";
							}
						}
					}
				}

				for(DestinationTimeApp destinationTimeApp : listDesTimeApp) {
					if(destinationTimeApp.getTimeStampAppEnum().value == 0 && destinationTimeApp.getEngraveFrameNo() == 1) {
						workHour = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 0 && destinationTimeApp.getEngraveFrameNo() == 2) {
						workHour2 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 1 && destinationTimeApp.getEngraveFrameNo() == 1) {
						temporaryTime = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 1 && destinationTimeApp.getEngraveFrameNo() == 2) {
						temporaryTime2 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 1 && destinationTimeApp.getEngraveFrameNo() == 3) {
						temporaryTime3 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 1) {
						outTime = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 2) {
						outTime2 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 3) {
						outTime3 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 4) {
						outTime4 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 5) {
						outTime5 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 6) {
						outTime6 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 7) {
						outTime7 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 8) {
						outTime8 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 9) {
						outTime9 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeApp.getTimeStampAppEnum().value == 2 && destinationTimeApp.getEngraveFrameNo() == 10) {
						outTime10 = I18NText.getText("KAF002_80");
					}
				}

				for (TimeStampAppOther timeStampAppOther : listTimeStampAppOther) {
					DestinationTimeZoneApp destTimeZone = timeStampAppOther.getDestinationTimeZoneApp();
					TimeZone timeZone = timeStampAppOther.getTimeZone();

					if (appStampOutputOp.get().getAppStampReflectOptional().get().getBreakTime()
							.equals(NotUseAtr.USE)) {
						/*
						 * breakTime
						 */
						if (destTimeZone.getEngraveFrameNo() == 1
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime2
						 */
						if (destTimeZone.getEngraveFrameNo() == 2
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime2 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime2 += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime3
						 */
						if (destTimeZone.getEngraveFrameNo() == 3
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime3 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime3 += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime4
						 */
						if (destTimeZone.getEngraveFrameNo() == 4
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime4 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime4 += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime5
						 */
						if (destTimeZone.getEngraveFrameNo() == 5
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime5 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime5 += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime6
						 */
						if (destTimeZone.getEngraveFrameNo() == 6
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime6 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime6 += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime7
						 */
						if (destTimeZone.getEngraveFrameNo() == 7
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime7 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime7 += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime8
						 */
						if (destTimeZone.getEngraveFrameNo() == 8
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime8 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime8 += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime9
						 */
						if (destTimeZone.getEngraveFrameNo() == 9
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime9 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime9 += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * breakTime10
						 */
						if (destTimeZone.getEngraveFrameNo() == 10
								& destTimeZone.getTimeZoneStampClassification().value == 2) {
							breakTime10 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								breakTime10 += timeZone.getEndTime().getFullText();
							}
						}
					}

					if (appStampOutputOp.get().getAppStampReflectOptional().get().getParentHours()
							.equals(NotUseAtr.USE)) {
						/*
						 * childCareTime
						 */
						if (destTimeZone.getEngraveFrameNo() == 1
								& destTimeZone.getTimeZoneStampClassification().value == 0) {
							childCareTime = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								childCareTime += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * childCareTime2
						 */
						if (destTimeZone.getEngraveFrameNo() == 2
								& destTimeZone.getTimeZoneStampClassification().value == 0) {
							childCareTime2 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								childCareTime2 += timeZone.getEndTime().getFullText();
							}
						}
					}

					if (appStampOutputOp.get().getAppStampReflectOptional().get().getParentHours()
							.equals(NotUseAtr.USE)) {
						/*
						 * nursingTime
						 */
						if (destTimeZone.getEngraveFrameNo() == 1
								& destTimeZone.getTimeZoneStampClassification().value == 1) {
							nursingTime = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								nursingTime += timeZone.getEndTime().getFullText();
							}
						}
						/*
						 * nursingTime2
						 */
						if (destTimeZone.getEngraveFrameNo() == 2
								& destTimeZone.getTimeZoneStampClassification().value == 1) {
							nursingTime2 = timeZone.getStartTime().getFullText() + HALF_WIDTH_SPACE + "～"
									+ HALF_WIDTH_SPACE;
							if (timeZone.getEndTime() != null) {
								nursingTime2 += timeZone.getEndTime().getFullText();
							}
						}
					}
				}

				for(DestinationTimeZoneApp destinationTimeZoneApp : listDestTimeZoneApp) {
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 1) {
						breakTime = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 2) {
						breakTime2 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 3) {
						breakTime3 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 4) {
						breakTime4 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 5) {
						breakTime5 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 6) {
						breakTime6 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 7) {
						breakTime7 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 8) {
						breakTime8 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 8) {
						breakTime9 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 2 && destinationTimeZoneApp.getEngraveFrameNo() == 10) {
						breakTime10 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 0 && destinationTimeZoneApp.getEngraveFrameNo() == 1) {
						childCareTime = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 0 && destinationTimeZoneApp.getEngraveFrameNo() == 2) {
						childCareTime2 = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 1 && destinationTimeZoneApp.getEngraveFrameNo() == 1) {
						nursingTime = I18NText.getText("KAF002_80");
					}
					if(destinationTimeZoneApp.getTimeZoneStampClassification().value == 1 && destinationTimeZoneApp.getEngraveFrameNo() == 2) {
						nursingTime2 = I18NText.getText("KAF002_80");
					}

				}
			}

			// Copy cells
			try {
				for (int i = 8; i <= 35; i++) {
					cells.copyRow(cells, 7, i);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			// set value to cell
			Cell cellB8 = cells.get("B8");
			Cell cellD8 = cells.get("D8");
			Cell cellB9 = cells.get("B9");
			Cell cellD9 = cells.get("D9");
			Cell cellB10 = cells.get("B10");
			Cell cellD10 = cells.get("D10");
			Cell cellB11 = cells.get("B11");
			Cell cellD11 = cells.get("D11");
			Cell cellB12 = cells.get("B12");
			Cell cellD12 = cells.get("D12");
			Cell cellB13 = cells.get("B13");
			Cell cellD13 = cells.get("D13");
			Cell cellB14 = cells.get("B14");
			Cell cellD14 = cells.get("D14");
			Cell cellB15 = cells.get("B15");
			Cell cellD15 = cells.get("D15");
			Cell cellB16 = cells.get("B16");
			Cell cellD16 = cells.get("D16");
			Cell cellB17 = cells.get("B17");
			Cell cellD17 = cells.get("D17");
			Cell cellB18 = cells.get("B18");
			Cell cellD18 = cells.get("D18");
			Cell cellB19 = cells.get("B19");
			Cell cellD19 = cells.get("D19");
			Cell cellB20 = cells.get("B20");
			Cell cellD20 = cells.get("D20");
			Cell cellB21 = cells.get("B21");
			Cell cellD21 = cells.get("D21");
			Cell cellB22 = cells.get("B22");
			Cell cellD22 = cells.get("D22");
			Cell cellB23 = cells.get("B23");
			Cell cellD23 = cells.get("D23");
			Cell cellB24 = cells.get("B24");
			Cell cellD24 = cells.get("D24");
			Cell cellB25 = cells.get("B25");
			Cell cellD25 = cells.get("D25");
			Cell cellB26 = cells.get("B26");
			Cell cellD26 = cells.get("D26");
			Cell cellB27 = cells.get("B27");
			Cell cellD27 = cells.get("D27");
			Cell cellB28 = cells.get("B28");
			Cell cellD28 = cells.get("D28");
			Cell cellB29 = cells.get("B29");
			Cell cellD29 = cells.get("D29");
			Cell cellB30 = cells.get("B30");
			Cell cellD30 = cells.get("D30");
			Cell cellB31 = cells.get("B31");
			Cell cellD31 = cells.get("D31");
			Cell cellB32 = cells.get("B32");
			Cell cellD32 = cells.get("D32");
			Cell cellB33 = cells.get("B33");
			Cell cellD33 = cells.get("D33");
			Cell cellB34 = cells.get("B34");
			Cell cellD34 = cells.get("D34");
			Cell cellB35 = cells.get("B35");
			Cell cellD35 = cells.get("D35");
			Cell cellB36 = cells.get("B36");
			Cell cellD36 = cells.get("D36");

			if (!nursingTime2.equals(EMPTY)) {
				cellB36.setValue(I18NText.getText("KAF002_69", "2"));
				cellD36.setValue(nursingTime2);
			} else {
				cells.deleteRow(35);
				deleteCnt++;
			}

			if (!nursingTime.equals(EMPTY)) {
				cellB35.setValue(I18NText.getText("KAF002_69", "1"));
				cellD35.setValue(nursingTime);
			} else {
				cells.deleteRow(34);
				deleteCnt++;
			}

			if (!childCareTime2.equals(EMPTY)) {
				cellB34.setValue(I18NText.getText("KAF002_68", "2"));
				cellD34.setValue(childCareTime2);
			} else {
				cells.deleteRow(33);
				deleteCnt++;
			}

			if (!childCareTime.equals(EMPTY)) {
				cellB33.setValue(I18NText.getText("KAF002_68", "1"));
				cellD33.setValue(childCareTime);
			} else {
				cells.deleteRow(32);
				deleteCnt++;
			}

			if (!breakTime10.equals(EMPTY)) {
				cellB32.setValue(I18NText.getText("KAF002_75", "10"));
				cellD32.setValue(breakTime10);
			} else {
				cells.deleteRow(31);
				deleteCnt++;
			}

			// Add fix

			if (!breakTime9.equals(EMPTY)) {
				cellB31.setValue(I18NText.getText("KAF002_75", "9"));
				cellD31.setValue(breakTime9);
			} else {
				cells.deleteRow(30);
				deleteCnt++;
			}

			if (!breakTime8.equals(EMPTY)) {
				cellB30.setValue(I18NText.getText("KAF002_75", "8"));
				cellD30.setValue(breakTime8);
			} else {
				cells.deleteRow(29);
				deleteCnt++;
			}

			if (!breakTime7.equals(EMPTY)) {
				cellB29.setValue(I18NText.getText("KAF002_75", "7"));
				cellD29.setValue(breakTime7);
			} else {
				cells.deleteRow(28);
				deleteCnt++;
			}

			if (!breakTime6.equals(EMPTY)) {
				cellB28.setValue(I18NText.getText("KAF002_75", "6"));
				cellD28.setValue(breakTime6);
			} else {
				cells.deleteRow(27);
				deleteCnt++;
			}

			if (!breakTime5.equals(EMPTY)) {
				cellB27.setValue(I18NText.getText("KAF002_75", "5"));
				cellD27.setValue(breakTime5);
			} else {
				cells.deleteRow(26);
				deleteCnt++;
			}

			if (!breakTime4.equals(EMPTY)) {
				cellB26.setValue(I18NText.getText("KAF002_75", "4"));
				cellD26.setValue(breakTime4);
			} else {
				cells.deleteRow(25);
				deleteCnt++;
			}

			// Add fix

			if (!breakTime3.equals(EMPTY)) {
				cellB25.setValue(I18NText.getText("KAF002_75", "3"));
				cellD25.setValue(breakTime3);
			} else {
				cells.deleteRow(24);
				deleteCnt++;
			}

			if (!breakTime2.equals(EMPTY)) {
				cellB24.setValue(I18NText.getText("KAF002_75", "2"));
				cellD24.setValue(breakTime2);
			} else {
				cells.deleteRow(23);
				deleteCnt++;
			}

			if (!breakTime.equals(EMPTY)) {
				cellB23.setValue(I18NText.getText("KAF002_75", "1"));
				cellD23.setValue(breakTime);
			} else {
				cells.deleteRow(22);
				deleteCnt++;
			}

			if (!outTime10.equals(EMPTY)) {
				cellB22.setValue(I18NText.getText("KAF002_67", "10"));
				cellD22.setValue(outTime10);
			} else {
				cells.deleteRow(21);
				deleteCnt++;
			}

			// Add fix
			if (!outTime9.equals(EMPTY)) {
				cellB21.setValue(I18NText.getText("KAF002_67", "9"));
				cellD21.setValue(outTime9);
			} else {
				cells.deleteRow(20);
				deleteCnt++;
			}

			if (!outTime8.equals(EMPTY)) {
				cellB20.setValue(I18NText.getText("KAF002_67", "8"));
				cellD20.setValue(outTime8);
			} else {
				cells.deleteRow(19);
				deleteCnt++;
			}

			if (!outTime7.equals(EMPTY)) {
				cellB19.setValue(I18NText.getText("KAF002_67", "7"));
				cellD19.setValue(outTime7);
			} else {
				cells.deleteRow(18);
				deleteCnt++;
			}

			if (!outTime6.equals(EMPTY)) {
				cellB18.setValue(I18NText.getText("KAF002_67", "6"));
				cellD18.setValue(outTime6);
			} else {
				cells.deleteRow(17);
				deleteCnt++;
			}

			if (!outTime5.equals(EMPTY)) {
				cellB17.setValue(I18NText.getText("KAF002_67", "5"));
				cellD17.setValue(outTime5);
			} else {
				cells.deleteRow(16);
				deleteCnt++;
			}

			if (!outTime4.equals(EMPTY)) {
				cellB16.setValue(I18NText.getText("KAF002_67", "4"));
				cellD16.setValue(outTime4);
			} else {
				cells.deleteRow(15);
				deleteCnt++;
			}

			// Add fix

			if (!outTime3.equals(EMPTY)) {
				cellB15.setValue(I18NText.getText("KAF002_67", "3"));
				cellD15.setValue(outTime3);
			} else {
				cells.deleteRow(14);
				deleteCnt++;
			}

			if (!outTime2.equals(EMPTY)) {
				cellB14.setValue(I18NText.getText("KAF002_67", "2"));
				cellD14.setValue(outTime2);
			} else {
				cells.deleteRow(13);
				deleteCnt++;
			}

			if (!outTime.equals(EMPTY)) {
				cellB13.setValue(I18NText.getText("KAF002_67", "1"));
				cellD13.setValue(outTime);
			} else {
				cells.deleteRow(12);
				deleteCnt++;
			}

			if (!temporaryTime3.equals(EMPTY)) {
				cellB12.setValue(I18NText.getText("KAF002_66", "3"));
				cellD12.setValue(temporaryTime3);
			} else {
				cells.deleteRow(11);
				deleteCnt++;
			}

			if (!temporaryTime2.equals(EMPTY)) {
				cellB11.setValue(I18NText.getText("KAF002_66", "2"));
				cellD11.setValue(temporaryTime2);
			} else {
				cells.deleteRow(10);
				deleteCnt++;
			}

			if (!temporaryTime.equals(EMPTY)) {
				cellB10.setValue(I18NText.getText("KAF002_66", "1"));
				cellD10.setValue(temporaryTime);
			} else {
				cells.deleteRow(9);
				deleteCnt++;
			}

			if (!workHour2.equals(EMPTY)) {
				cellB9.setValue(I18NText.getText("KAF002_65", "2"));
				cellD9.setValue(workHour2);
			} else {
				cells.deleteRow(8);
				deleteCnt++;
			}

			if (!workHour.equals(EMPTY)) {
				cellB8.setValue(I18NText.getText("KAF002_65", "1"));
				cellD8.setValue(workHour);
			} else {
				cells.deleteRow(7);
				deleteCnt++;
			}

			// set style for bottom

			cells.setRowHeightPixel(38 - deleteCnt - 2, 5);
			cells.setRowHeightPixel(38 - deleteCnt - 1, 130);
			cells.setRowHeightPixel(38 - deleteCnt, 5);
			cells.setRowHeightPixel(38 - deleteCnt + 1, 5);
			cells.setRowHeightPixel(38 - deleteCnt + 2, 130);
			cells.setRowHeightPixel(38 - deleteCnt + 3, 5);

			// before reason
			Cell bReason = worksheet.getCells().get("B" + (38 - deleteCnt - 1));
			Cell cReason = worksheet.getCells().get("C" + (38 - deleteCnt - 1));
			Cell dReason = worksheet.getCells().get("D" + (38 - deleteCnt - 1));
			Cell eReason = worksheet.getCells().get("E" + (38 - deleteCnt - 1));
			Cell fReason = worksheet.getCells().get("F" + (38 - deleteCnt - 1));
			Cell gReason = worksheet.getCells().get("G" + (38 - deleteCnt - 1));
			Cell hReason = worksheet.getCells().get("H" + (38 - deleteCnt - 1));
			Cell iReason = worksheet.getCells().get("I" + (38 - deleteCnt - 1));
			Cell jReason = worksheet.getCells().get("J" + (38 - deleteCnt - 1));
			Cell kReason = worksheet.getCells().get("K" + (38 - deleteCnt - 1));

			Style bReasonStyle = bReason.getStyle();
			Style cReasonStyle = cReason.getStyle();
			Style dReasonStyle = dReason.getStyle();
			Style eReasonStyle = eReason.getStyle();
			Style fReasonStyle = fReason.getStyle();
			Style gReasonStyle = gReason.getStyle();
			Style hReasonStyle = hReason.getStyle();
			Style iReasonStyle = iReason.getStyle();
			Style jReasonStyle = jReason.getStyle();
			Style kReasonStyle = kReason.getStyle();

			bReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			bReasonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
			cReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			cReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
			dReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			dReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			eReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			eReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			eReasonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			fReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			fReasonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			fReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			gReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			gReasonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			gReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			hReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			hReasonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			hReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			iReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			iReasonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			iReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			jReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			jReasonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			jReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			kReasonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
			kReasonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			kReasonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);

			bReason.setStyle(bReasonStyle);
			cReason.setStyle(cReasonStyle);
			dReason.setStyle(dReasonStyle);
			eReason.setStyle(eReasonStyle);
			fReason.setStyle(fReasonStyle);
			gReason.setStyle(gReasonStyle);
			hReason.setStyle(hReasonStyle);
			iReason.setStyle(iReasonStyle);
			jReason.setStyle(jReasonStyle);
			kReason.setStyle(kReasonStyle);

			// reason
			Cell bReasonLabel = cells.get("B" + (38 - deleteCnt));
			Cell cReasonLabel = cells.get("C" + (38 - deleteCnt));
			Cell dReasonLabel = cells.get("D" + (38 - deleteCnt));
			Cell kReasonLabel = cells.get("K" + (38 - deleteCnt));

			Style bReasonLabelStyle = bReasonLabel.getStyle();
			Style cReasonLabelStyle = cReasonLabel.getStyle();
			Style dReasonLabelStyle = dReasonLabel.getStyle();
			Style kReasonLabelStyle = kReasonLabel.getStyle();

			bReasonLabelStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER)
					.setLineStyle(CellBorderType.THIN);
			cReasonLabelStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER)
					.setLineStyle(CellBorderType.THIN);
			dReasonLabelStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER)
					.setLineStyle(CellBorderType.THIN);
			kReasonLabelStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER)
					.setLineStyle(CellBorderType.THIN);

			bReasonLabelStyle.setHorizontalAlignment(TextAlignmentType.LEFT);
			bReasonLabelStyle.setVerticalAlignment(TextAlignmentType.TOP);
			bReasonLabelStyle.setIndentLevel(1);
			dReasonLabelStyle.setHorizontalAlignment(TextAlignmentType.LEFT);
			dReasonLabelStyle.setVerticalAlignment(TextAlignmentType.TOP);
			dReasonLabelStyle.getFont().setSize(9);
			dReasonLabelStyle.getFont().setName("源ノ角ゴシック JP Normal");
			dReasonLabelStyle.setIndentLevel(1);

			dReasonLabelStyle.setTextWrapped(true);

			bReasonLabel.setStyle(bReasonLabelStyle);
			cReasonLabel.setStyle(cReasonLabelStyle);
			dReasonLabel.setStyle(dReasonLabelStyle);
			kReasonLabel.setStyle(kReasonLabelStyle);

			cells.merge(38 - deleteCnt - 1, 1, 1, 2);
			cells.merge(38 - deleteCnt - 1, 3, 1, 8);

			// under reason
			Cell bReasonUnder = worksheet.getCells().get("B" + (38 - deleteCnt + 1));
			Cell cReasonUnder = worksheet.getCells().get("C" + (38 - deleteCnt + 1));
			Cell dReasonUnder = worksheet.getCells().get("D" + (38 - deleteCnt + 1));
			Cell eReasonUnder = worksheet.getCells().get("E" + (38 - deleteCnt + 1));
			Cell fReasonUnder = worksheet.getCells().get("F" + (38 - deleteCnt + 1));
			Cell gReasonUnder = worksheet.getCells().get("G" + (38 - deleteCnt + 1));
			Cell hReasonUnder = worksheet.getCells().get("H" + (38 - deleteCnt + 1));
			Cell iReasonUnder = worksheet.getCells().get("I" + (38 - deleteCnt + 1));
			Cell jReasonUnder = worksheet.getCells().get("J" + (38 - deleteCnt + 1));
			Cell kReasonUnder = worksheet.getCells().get("K" + (38 - deleteCnt + 1));

			Style bReasonUnderStyle = bReasonUnder.getStyle();
			Style cReasonUnderStyle = cReasonUnder.getStyle();
			Style dReasonUnderStyle = dReasonUnder.getStyle();
			Style eReasonUnderStyle = eReasonUnder.getStyle();
			Style fReasonUnderStyle = fReasonUnder.getStyle();
			Style gReasonUnderStyle = gReasonUnder.getStyle();
			Style hReasonUnderStyle = hReasonUnder.getStyle();
			Style iReasonUnderStyle = iReasonUnder.getStyle();
			Style jReasonUnderStyle = jReasonUnder.getStyle();
			Style kReasonUnderStyle = kReasonUnder.getStyle();

			bReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
			bReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			cReasonUnderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
			cReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			dReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			dReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
			eReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			eReasonUnderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			eReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			fReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			fReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			fReasonUnderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			gReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			gReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			gReasonUnderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			hReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			hReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			hReasonUnderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			iReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			iReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			iReasonUnderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			jReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			jReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			jReasonUnderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			kReasonUnderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER)
					.setLineStyle(CellBorderType.THIN);
			kReasonUnderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			kReasonUnderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);

			bReasonUnder.setStyle(bReasonUnderStyle);
			cReasonUnder.setStyle(cReasonUnderStyle);
			dReasonUnder.setStyle(dReasonUnderStyle);
			eReasonUnder.setStyle(eReasonUnderStyle);
			fReasonUnder.setStyle(fReasonUnderStyle);
			gReasonUnder.setStyle(gReasonUnderStyle);
			hReasonUnder.setStyle(hReasonUnderStyle);
			iReasonUnder.setStyle(iReasonUnderStyle);
			jReasonUnder.setStyle(jReasonUnderStyle);
			kReasonUnder.setStyle(kReasonUnderStyle);

			// before remark
			Cell bRemark = worksheet.getCells().get("B" + (38 - deleteCnt + 2));
			Cell cRemark = worksheet.getCells().get("C" + (38 - deleteCnt + 2));
			Cell dRemark = worksheet.getCells().get("D" + (38 - deleteCnt + 2));
			Cell kRemark = worksheet.getCells().get("K" + (38 - deleteCnt + 2));

			Style bRemarkStyle = bRemark.getStyle();
			Style cRemarkStyle = cRemark.getStyle();
			Style dRemarkStyle = dRemark.getStyle();
			Style kRemarkStyle = kRemark.getStyle();

			bRemarkStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
			bRemarkStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
			cRemarkStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
			cRemarkStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
			dRemarkStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
			kRemarkStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);

			bRemark.setStyle(bRemarkStyle);
			cRemark.setStyle(cRemarkStyle);
			dRemark.setStyle(dRemarkStyle);
			kRemark.setStyle(kRemarkStyle);

			// remark
			Cell bRemarkLabel = cells.get("B" + (38 - deleteCnt + 3));
			Cell cRemarkLabel = cells.get("C" + (38 - deleteCnt + 3));
			Cell dRemarkLabel = cells.get("D" + (38 - deleteCnt + 3));
			Cell kRemarkLabel = cells.get("K" + (38 - deleteCnt + 3));

			bRemarkLabel.setStyle(bReasonLabelStyle);
			cRemarkLabel.setStyle(cReasonLabelStyle);
			dRemarkLabel.setStyle(dReasonLabelStyle);
			kRemarkLabel.setStyle(kReasonLabelStyle);

			cells.merge(38 - deleteCnt + 2, 1, 1, 2);
			cells.merge(38 - deleteCnt + 2, 3, 1, 8);

			// under remark
			Cell bRemarkUnder = worksheet.getCells().get("B" + (38 - deleteCnt + 4));
			Cell cRemarkUnder = worksheet.getCells().get("C" + (38 - deleteCnt + 4));
			Cell dRemarkUnder = worksheet.getCells().get("D" + (38 - deleteCnt + 4));
			Cell eRemarkUnder = worksheet.getCells().get("E" + (38 - deleteCnt + 4));
			Cell fRemarkUnder = worksheet.getCells().get("F" + (38 - deleteCnt + 4));
			Cell gRemarkUnder = worksheet.getCells().get("G" + (38 - deleteCnt + 4));
			Cell hRemarkUnder = worksheet.getCells().get("H" + (38 - deleteCnt + 4));
			Cell iRemarkUnder = worksheet.getCells().get("I" + (38 - deleteCnt + 4));
			Cell jRemarkUnder = worksheet.getCells().get("J" + (38 - deleteCnt + 4));
			Cell kRemarkUnder = worksheet.getCells().get("K" + (38 - deleteCnt + 4));

			bRemarkUnder.setStyle(bReasonUnderStyle);
			cRemarkUnder.setStyle(cReasonUnderStyle);
			dRemarkUnder.setStyle(dReasonUnderStyle);
			eRemarkUnder.setStyle(eReasonUnderStyle);
			fRemarkUnder.setStyle(fReasonUnderStyle);
			gRemarkUnder.setStyle(gReasonUnderStyle);
			hRemarkUnder.setStyle(hReasonUnderStyle);
			iRemarkUnder.setStyle(iReasonUnderStyle);
			jRemarkUnder.setStyle(jReasonUnderStyle);
			kRemarkUnder.setStyle(kReasonUnderStyle);

			// print bottom
			Cell reasonLabel = worksheet.getCells().get("B" + (38 - deleteCnt));
			Cell remarkLabel = worksheet.getCells().get("B" + (41 - deleteCnt));
			Cell reasonContent = worksheet.getCells().get("D" + (38 - deleteCnt));

			this.printBottomKAF002(reasonLabel, remarkLabel, reasonContent, printContentOfApp);
		}
	}

	public void copyCells(Worksheet worksheet) {
		Cells cells = worksheet.getCells();

		try {
			cells.copyRow(cells, 6, 7);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void printBottomKAF002(Cell reasonLabel, Cell remarkLabel, Cell reasonContent,
			PrintContentOfApp printContentOfApp) {
		reasonLabel.setValue(I18NText.getText("KAF000_52"));
		remarkLabel.setValue(I18NText.getText("KAF000_59"));
		String appReasonStandard = Strings.EMPTY;
		if (printContentOfApp.getAppReasonStandard() != null) {
			appReasonStandard = printContentOfApp.getAppReasonStandard().getReasonTypeItemLst().stream().findFirst()
					.map(x -> x.getReasonForFixedForm().v()).orElse(null);
		}
		String appReason = Strings.EMPTY;
		if (printContentOfApp.getOpAppReason() != null) {
			appReason = printContentOfApp.getOpAppReason().v();
		}
		reasonContent.setValue(appReasonStandard + "\n" + appReason);
	}
}
