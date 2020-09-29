package nts.uk.ctx.at.request.infra.repository.application.gobackdirectly;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.ApplicationStatus;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author anhnm
 *
 */
@Stateless
public class AsposeGoReturnDirectly { 
    private final String HALF_SIZE_SPACE = " ";
    
    public int printContentGoReturn(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
        Cells cells = worksheet.getCells();
        Cell cellB8 = cells.get("B8");
        cellB8.setValue(I18NText.getText("KAF009_32"));
        Cell cellB9 = cells.get("B9");
        cellB9.setValue(I18NText.getText("KAF009_33"));
        Cell cellB10 = cells.get("B10");
        cellB10.setValue(I18NText.getText("KAF009_49"));
        Cell cellB11 = cells.get("B11");
        cellB11.setValue(I18NText.getText("KAF009_50"));
        
        Cell cellD8 = cells.get("D8");
        Cell cellD9 = cells.get("D9");
        Cell cellD10 = cells.get("D10");
        Cell cellD11 = cells.get("D11");
        String valueD8 = "";
        String valueD9 = "";
        String valueD10 = "";
        String valueD11 = "";
        
        // Get print content of KAF009 from printContentOfApp
        Optional<InforGoBackCommonDirectOutput> opInforGoBackCommonDirectOutput = printContentOfApp.getOpInforGoBackCommonDirectOutput();
        if(opInforGoBackCommonDirectOutput.isPresent()) {
            InforGoBackCommonDirectOutput infoGoBackCommonDirectOutput = opInforGoBackCommonDirectOutput.get();
            Optional<GoBackDirectly> gobackDirectly = infoGoBackCommonDirectOutput.getGoBackDirectly();
            
            WorkTypeCode dataWorkTypeCD = null;
            String workTypeCD = "";
            String workTypeName = "";
            String workHourCD = "";
            String workHourName = "";
            if(gobackDirectly.isPresent()) {
                Optional<WorkInformation> dataWork = gobackDirectly.get().getDataWork();
                if(dataWork.isPresent()) {
                    dataWorkTypeCD = dataWork.get().getWorkTypeCode();
                }
                
                // Get List workTime
                List<WorkTimeSetting> workTimeLst = infoGoBackCommonDirectOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst().orElse(null);
                if(workTimeLst != null) {
                    for(WorkTimeSetting wtSetting : workTimeLst) {
                        // Get workHour code + name from each workTime
                        if(wtSetting.getWorktimeCode().equals(dataWork.get().getWorkTimeCode())) {
                            workHourCD = wtSetting.getWorktimeCode().v();
                            workHourName = wtSetting.getWorkTimeDisplayName().getWorkTimeName() == null ? 
                                    workHourName : wtSetting.getWorkTimeDisplayName().getWorkTimeName().v();
                            break;
                        }
                    }
                }
                
                // Get text for value D10
                NotUseAtr straightLineAtr = gobackDirectly.get().getStraightLine();
                if(straightLineAtr.equals(NotUseAtr.USE)) {
                    valueD10 = I18NText.getText("KAF009_47");
                } else {
                    valueD10 = I18NText.getText("KAF009_48");
                }
                // Get text for value D11
                NotUseAtr straightDistinction = gobackDirectly.get().getStraightDistinction();
                if(straightDistinction.equals(NotUseAtr.USE)) {
                    valueD11 = I18NText.getText("KAF009_47");
                } else {
                    valueD11 = I18NText.getText("KAF009_48");
                }
            }
            
            // Get List workType
            List<WorkType> workTypeCodeString = infoGoBackCommonDirectOutput.getLstWorkType();
            if(dataWorkTypeCD != null) {
                for(WorkType wkType : workTypeCodeString) {
                    // get WorkType code + name for each workType 
                    if(wkType.getWorkTypeCode().equals(dataWorkTypeCD)) {
                        workTypeCD = wkType.getWorkTypeCode().v();
                        workTypeName = wkType.getName() == null ? workTypeName : wkType.getName().v();
                        break;
                    }
                }
            }
            
            // Get text for valueD8
            if(workTypeCD.isEmpty()) {
                // Code = empty -> set cell empty
                valueD8 = workTypeCD;
            } else {
                // Name = empty -> set text resource
                if(workTypeName.isEmpty()) {
                    valueD8 = workTypeCD + HALF_SIZE_SPACE + I18NText.getText("KAF009_46");
                } else {
                    valueD8 = workTypeName;
                }
                
               
            }
            
            // Get text for value D9
            if(workTypeCD.isEmpty()) {
                // Code = empty -> set cell empty
                valueD9 = workHourCD;
            } else {
                // Name = empty -> set text resource
                if(workHourName.isEmpty()) {
                    valueD9 = workHourCD + HALF_SIZE_SPACE + I18NText.getText("KAF009_46");
                } else {
                    valueD9 = workHourName;
                }
            }
            
            // Set cell value
            cellD8.setValue(valueD8);
            cellD9.setValue(valueD9);
            cellD10.setValue(valueD10);
            cellD11.setValue(valueD11);
            
            // Delete item A1_1 A1_2 and A2_1 A2_2 by condition
            if(gobackDirectly.isPresent() && gobackDirectly.get().getIsChangedWork().isPresent()) {
                if(!gobackDirectly.get().getIsChangedWork().get().equals(NotUseAtr.USE) 
                        && !infoGoBackCommonDirectOutput.getGoBackReflect().getReflectApplication().equals(ApplicationStatus.DO_REFLECT)) {
                    cells.deleteRow(8);
                    cells.deleteRow(7);
                    return 2;
                }
            }
            
            return 0;
        } else {
            throw new RuntimeException("No content to print");
        }
    }
}
