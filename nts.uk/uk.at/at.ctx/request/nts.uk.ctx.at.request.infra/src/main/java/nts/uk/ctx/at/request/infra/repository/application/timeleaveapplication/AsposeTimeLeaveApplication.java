package nts.uk.ctx.at.request.infra.repository.application.timeleaveapplication;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AsposeTimeLeaveApplication {
    @Inject
    private TimeLeaveAppReflectRepository timeLeaveAppReflectRepo;

    public void printAppContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
        Cells cells = worksheet.getCells();

        cells.get("B8").setValue(I18NText.getText("KAF012_9"));
        cells.get("B9").setValue(I18NText.getText("KAF012_15"));
        cells.get("B10").setValue(I18NText.getText("KAF012_21"));
        cells.get("B11").setValue(I18NText.getText("KAF012_24"));
        cells.get("B12").setValue(I18NText.getText("KAF012_44"));
        cells.get("B13").setValue(I18NText.getText("KAF012_45"));

        printContentOfApp.getOpPrintContentOfTimeLeave().get().forEach(detail -> {
            StringBuilder content = new StringBuilder();
            Integer totalTime = detail.getTimeDigestApplication().getTimeOff().v()
                    + detail.getTimeDigestApplication().getTimeAnnualLeave().v()
                    + detail.getTimeDigestApplication().getChildTime().v()
                    + detail.getTimeDigestApplication().getNursingTime().v()
                    + detail.getTimeDigestApplication().getOvertime60H().v()
                    + detail.getTimeDigestApplication().getTimeSpecialVacation().v();
            switch (detail.getAppTimeType()) {
                case ATWORK:
                    content.append(detail.getTimeZoneWithWorkNoLst().get(0).getTimeZone().getStartTime().getRawTimeWithFormat());
                    content.append(
                            printContentOfApp.getPrePostAtr() == PrePostAtr.PREDICT
                            ? I18NText.getText("KAF012_11")
                            : I18NText.getText("KAF012_12")
                    );
                    content.append(new TimeWithDayAttr(totalTime).getRawTimeWithFormat());
                    content.append(I18NText.getText("KAF012_41"));
                    cells.get("D8").setValue(content.toString());
                    break;
                case OFFWORK:
                    content.append(detail.getTimeZoneWithWorkNoLst().get(0).getTimeZone().getEndTime().getRawTimeWithFormat());
                    content.append(
                            printContentOfApp.getPrePostAtr() == PrePostAtr.PREDICT
                                    ? I18NText.getText("KAF012_17")
                                    : I18NText.getText("KAF012_18")
                    );
                    content.append(new TimeWithDayAttr(totalTime).getRawTimeWithFormat());
                    content.append(I18NText.getText("KAF012_41"));
                    cells.get("D9").setValue(content.toString());
                    break;
                case ATWORK2:
                    content.append(detail.getTimeZoneWithWorkNoLst().get(0).getTimeZone().getStartTime().getRawTimeWithFormat());
                    content.append(
                            printContentOfApp.getPrePostAtr() == PrePostAtr.PREDICT
                                    ? I18NText.getText("KAF012_11")
                                    : I18NText.getText("KAF012_12")
                    );
                    content.append(new TimeWithDayAttr(totalTime).getRawTimeWithFormat());
                    content.append(I18NText.getText("KAF012_41"));
                    cells.get("D10").setValue(content.toString());
                    break;
                case OFFWORK2:
                    content.append(detail.getTimeZoneWithWorkNoLst().get(0).getTimeZone().getEndTime().getRawTimeWithFormat());
                    content.append(
                            printContentOfApp.getPrePostAtr() == PrePostAtr.PREDICT
                                    ? I18NText.getText("KAF012_17")
                                    : I18NText.getText("KAF012_18")
                    );
                    content.append(new TimeWithDayAttr(totalTime).getRawTimeWithFormat());
                    content.append(I18NText.getText("KAF012_41"));
                    cells.get("D11").setValue(content.toString());
                    break;
                case PRIVATE:
                    detail.getTimeZoneWithWorkNoLst().sort(Comparator.comparing(i -> i.getTimeZone().getStartTime().v()));
                    List<String> privateTimes = detail.getTimeZoneWithWorkNoLst()
                            .stream()
                            .map(i -> i.getTimeZone().getStartTime().getRawTimeWithFormat()
                                    + " ～ "
                                    + i.getTimeZone().getEndTime().getRawTimeWithFormat())
                            .collect(Collectors.toList());
                    content.append(String.join("、", privateTimes));
                    content.append(
                            printContentOfApp.getPrePostAtr() == PrePostAtr.PREDICT
                                    ? I18NText.getText("KAF012_42")
                                    : I18NText.getText("KAF012_43")
                    );
                    content.append(new TimeWithDayAttr(totalTime).getRawTimeWithFormat());
                    content.append(I18NText.getText("KAF012_41"));
                    cells.get("D12").setValue(content.toString());
                    break;
                case UNION:
                    List<String> unionTimes = detail.getTimeZoneWithWorkNoLst()
                            .stream()
                            .map(i -> i.getTimeZone().getStartTime().getRawTimeWithFormat()
                                    + " ～ "
                                    + i.getTimeZone().getEndTime().getRawTimeWithFormat())
                            .collect(Collectors.toList());
                    content.append(String.join("、", unionTimes));
                    content.append(
                            printContentOfApp.getPrePostAtr() == PrePostAtr.PREDICT
                                    ? I18NText.getText("KAF012_42")
                                    : I18NText.getText("KAF012_43")
                    );
                    content.append(new TimeWithDayAttr(totalTime).getRawTimeWithFormat());
                    content.append(I18NText.getText("KAF012_41"));
                    cells.get("D13").setValue(content.toString());
                    break;
                default:
                    break;
            }

            timeLeaveAppReflectRepo.findByCompany(AppContexts.user().companyId()).ifPresent(setting -> {
                if (setting.getDestination().getUnionGoingOut() == NotUseAtr.NOT_USE) {
                    cells.hideRow(12);
                }
                if (setting.getDestination().getPrivateGoingOut() == NotUseAtr.NOT_USE) {
                    cells.hideRow(11);
                }
                if (setting.getDestination().getSecondAfterWork() == NotUseAtr.NOT_USE || !printContentOfApp.isManagementMultipleWorkCycles()) {
                    cells.hideRow(10);
                }
                if (setting.getDestination().getSecondBeforeWork() == NotUseAtr.NOT_USE || !printContentOfApp.isManagementMultipleWorkCycles()) {
                    cells.hideRow(9);
                }
                if (setting.getDestination().getFirstAfterWork() == NotUseAtr.NOT_USE) {
                    cells.hideRow(8);
                }
                if (setting.getDestination().getFirstBeforeWork() == NotUseAtr.NOT_USE) {
                    cells.hideRow(7);
                }
            });
        });

    }
}
