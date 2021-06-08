package nts.uk.ctx.at.function.app.query.holidayconfirmationtable;


import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ManagermentAtr;

import java.util.List;

/**
 * 代休確認表の表示内容を作成する
 */
public class CreateDisplayContentOfTheCourseQuery {
    public static Object getDisplayContent(GeneralDate referenceDate, List<EmployeeBasicInfoImport> basicInfoImportList,
                                           ManagermentAtr mngAtr, boolean moreSubstituteHolidaysThanHolidays, boolean moreHolidaysThanSubstituteHolidays,
                                           List<AffAtWorkplaceImport> lstAffAtWorkplaceImport, List<WorkplaceInfor> lstWorkplaceInfo) {
        return new Object();
    }
}
