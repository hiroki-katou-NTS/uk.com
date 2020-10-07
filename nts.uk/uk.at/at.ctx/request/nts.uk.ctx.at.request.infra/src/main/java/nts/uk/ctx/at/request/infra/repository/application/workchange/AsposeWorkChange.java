package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author anhnm
 *
 */
@Stateless
public class AsposeWorkChange {
    private final String HALF_SIZE_SPACE = " ";
	
	public int printWorkChangeContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
	    int deleteCnt = 0;
	    Cells cells = worksheet.getCells();
	    Cell cellB8 = cells.get("B8");
        cellB8.setValue(I18NText.getText("KAF007_32"));
        Cell cellB9 = cells.get("B9");
        cellB9.setValue(I18NText.getText("KAF007_33"));
        Cell cellB10 = cells.get("B10");
        cellB10.setValue(I18NText.getText("KAF007_34"));
        Cell cellB11 = cells.get("B11");
        cellB11.setValue(I18NText.getText("KAF007_35"));
        Cell cellB12 = cells.get("B12");
        cellB12.setValue(I18NText.getText("KAF007_80"));
        Cell cellB13 = cells.get("B13");
        cellB13.setValue(I18NText.getText("KAF007_81"));
        
        Cell cellD8 = cells.get("D8");
        Cell cellD9 = cells.get("D9");
        Cell cellD10 = cells.get("D10");
        Cell cellD11 = cells.get("D11");
        Cell cellD12 = cells.get("D12");
        Cell cellD13 = cells.get("D13");
        String valueD8 = "";
        String valueD9 = "";
        String valueD10 = "";
        String valueD11 = "";
        String valueD12 = "";
        String valueD13 = "";
        
        Optional<PrintContentOfWorkChange> printContentWorkChange = printContentOfApp.getOpPrintContentOfWorkChange();
        if(printContentWorkChange.isPresent()) {
            AppWorkChangeDispInfo appDisp = printContentWorkChange.get().getAppWorkChangeDispInfo();
            AppWorkChange appWorkChange = printContentWorkChange.get().getAppWorkChange();
            
            String workTypeCD = "";
            String workTypeName = "";
            String workHourCD = "";
            String workHourName = "";
            
            Optional<WorkTypeCode> dataWorkTypeCD = appWorkChange.getOpWorkTypeCD();
            Optional<WorkTimeCode> dataWorkTimeCD = appWorkChange.getOpWorkTimeCD();
            
            // Get value workTypeCD + workTypeName
            List<WorkType> workTypeLst = appDisp.getWorkTypeLst();
            if(dataWorkTypeCD.isPresent()) {
                workTypeCD = dataWorkTypeCD.get().v();
                for(WorkType workType : workTypeLst) {
                    if(workType.getWorkTypeCode().equals(dataWorkTypeCD.get())) {
                        workTypeCD = workType.getWorkTypeCode().v();
                        workTypeName = workType.getName().v();
                        break;
                    }
                }                
            }
            
            // Get value workTimeCD + workTimeName
            List<WorkTimeSetting> workTimeLst = appDisp.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpWorkTimeLst().orElse(null);
            if(dataWorkTimeCD.isPresent()) {
                workHourCD = dataWorkTimeCD.get().v();
                for(WorkTimeSetting workTime : workTimeLst) {
                    if(workTime.getWorktimeCode().equals(dataWorkTimeCD.get())) {
                        workHourCD = workTime.getWorktimeCode().v();
                        workHourName = workTime.getWorkTimeDisplayName().getWorkTimeName().v();
                        break;
                    }
                }
            }
            
            // Set value D8
            if(workTypeName != "") {
                valueD8 = workTypeName;
            } else {
                valueD8 = workTypeCD + HALF_SIZE_SPACE + I18NText.getText("KAF007_79");
            }
            
            // Set value D9
            if(workHourCD == "") {
                valueD9 = I18NText.getText("KAF007_84");
            } else {
                if(workHourName != "") {
                    valueD9 = workHourName;
                } else {
                    valueD9 = workHourCD + HALF_SIZE_SPACE + I18NText.getText("KAF007_79");
                }
            }
            
            // Get time1 + time 2
            List<TimeZoneWithWorkNo> listTimeZone = appWorkChange.getTimeZoneWithWorkNoLst();
            List<TimeZoneWithWorkNo> timezZone1 = listTimeZone.stream().filter(item -> item.getWorkNo().v().equals(1)).collect(Collectors.toList());
            List<TimeZoneWithWorkNo> timezZone2 = listTimeZone.stream().filter(item -> item.getWorkNo().v().equals(2)).collect(Collectors.toList());
            if(appDisp.getReflectWorkChangeApp().getWhetherReflectAttendance().equals(NotUseAtr.USE)) {
                if(timezZone1.size() > 0) {
                    valueD10 = timezZone1.get(0).getTimeZone().getStartTime().getInDayTimeWithFormat()
                            + HALF_SIZE_SPACE + "～" + HALF_SIZE_SPACE
                            + timezZone1.get(0).getTimeZone().getEndTime().getInDayTimeWithFormat();
                } else {
                    // Not happen
                }
                
                if(appDisp.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles()) {
                    if(timezZone2.size() > 0) {
                        valueD11 = timezZone2.get(0).getTimeZone().getStartTime().getInDayTimeWithFormat()
                                + HALF_SIZE_SPACE + "～" + HALF_SIZE_SPACE
                                + timezZone2.get(0).getTimeZone().getEndTime().getInDayTimeWithFormat();
                    }
                }
            }
            
            // Set value D12
            if(appWorkChange.getStraightGo().equals(NotUseAtr.USE)) {
                valueD12 = I18NText.getText("KAF007_82");
            } else {
                valueD12 = I18NText.getText("KAF007_83");
            }
            
            // Set value D13
            if(appWorkChange.getStraightBack().equals(NotUseAtr.USE)) {
                valueD13 = I18NText.getText("KAF007_82");
            } else {
                valueD13 = I18NText.getText("KAF007_83");
            }
            
            if(valueD10 == "") {
                cells.deleteRow(9);
                deleteCnt++;
            }
            if(!appDisp.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles()) {
                cells.deleteRow(10);
                deleteCnt++;
            }
            
            cellD8.setValue(valueD8);
            cellD9.setValue(valueD9);
            cellD10.setValue(valueD10);
            cellD11.setValue(valueD11);
            cellD12.setValue(valueD12);
            cellD13.setValue(valueD13);
        } else {
            throw new RuntimeException("No content to print");
        }
        return deleteCnt;
	}
}
