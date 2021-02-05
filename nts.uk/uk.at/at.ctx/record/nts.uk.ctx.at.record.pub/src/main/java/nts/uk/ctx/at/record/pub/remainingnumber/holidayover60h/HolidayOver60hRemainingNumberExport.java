package nts.uk.ctx.at.record.pub.remainingnumber.holidayover60h;

import lombok.*;
import nts.uk.ctx.at.record.dom.monthly.vacation.holidayover60h.HolidayOver60h;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolidayOver60hRemainingNumberExport {

    /** 60H超休（マイナスあり）. 使用時間 */
    private AnnualLeaveUsedTime usedTimeWithMinus;

    /** 60H超休（マイナスあり）. 残時間 */
    private AnnualLeaveRemainingTime remainingTimeWithMinus;

    /** 60H超休（マイナスなし）. 使用時間 */
    private AnnualLeaveUsedTime usedTimeNoMinus;

    /** 60H超休（マイナスなし）. 残時間 */
    private AnnualLeaveRemainingTime remainingTimeNoMinus;
    /**
     * 繰越時間
     */
    private AnnualLeaveRemainingTime carryForwardTimes;
    /**
     * 未消化数
     */
    private AnnualLeaveRemainingTime holidayOver60hUndigestNumber;

    public HolidayOver60hRemainingNumber toDomain() {
        HolidayOver60hRemainingNumber domain = new HolidayOver60hRemainingNumber();

        HolidayOver60h hdOver60WithMinus = new HolidayOver60h();
        hdOver60WithMinus.setRemainingTime(remainingTimeWithMinus);
        hdOver60WithMinus.setUsedTime(usedTimeWithMinus);
        domain.setHolidayOver60hWithMinus(hdOver60WithMinus);

        HolidayOver60h hdOver60NoMinus = new HolidayOver60h();
        hdOver60NoMinus.setRemainingTime(remainingTimeNoMinus);
        hdOver60NoMinus.setUsedTime(usedTimeNoMinus);
        domain.setHolidayOver60hNoMinus(hdOver60NoMinus);

        domain.setCarryForwardTimes(carryForwardTimes);
        domain.setHolidayOver60hUndigestNumber(holidayOver60hUndigestNumber);

        return domain;
    }

    public static HolidayOver60hRemainingNumberExport fromDomain(HolidayOver60hRemainingNumber domain) {
        return new HolidayOver60hRemainingNumberExport(
                domain.getHolidayOver60hWithMinus().getUsedTime(),
                domain.getHolidayOver60hWithMinus().getRemainingTime(),
                domain.getHolidayOver60hNoMinus().getUsedTime(),
                domain.getHolidayOver60hNoMinus().getRemainingTime(),
                domain.getCarryForwardTimes(),
                domain.getHolidayOver60hUndigestNumber()
        );
    }
}