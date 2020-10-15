package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.HolidayAppType;

/**
 * refactor 4 refactor4
 * 休暇申請種類表示名
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HolidayApplicationTypeDisplayName extends DomainObject {
    /**
     * 表示名
     */
    private ApplicationDisplayName displayName;

    /**
     *休暇申請種類
     */
    private HolidayAppType holidayApplicationType;

    public static HolidayApplicationTypeDisplayName create(int holidayAppType, String displayName) {
        return new HolidayApplicationTypeDisplayName(
                new ApplicationDisplayName(displayName),
                EnumAdaptor.valueOf(holidayAppType, HolidayAppType.class)
        );
    }
}
