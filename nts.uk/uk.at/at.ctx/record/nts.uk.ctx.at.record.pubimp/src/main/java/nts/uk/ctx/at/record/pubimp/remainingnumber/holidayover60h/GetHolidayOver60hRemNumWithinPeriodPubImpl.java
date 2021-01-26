package nts.uk.ctx.at.record.pubimp.remainingnumber.holidayover60h;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriodImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.record.pub.remainingnumber.holidayover60h.AggrResultOfHolidayOver60hExport;
import nts.uk.ctx.at.record.pub.remainingnumber.holidayover60h.GetHolidayOver60hRemNumWithinPeriodPub;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMng;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class GetHolidayOver60hRemNumWithinPeriodPubImpl implements GetHolidayOver60hRemNumWithinPeriodPub {
    @Inject
    private GetHolidayOver60hRemNumWithinPeriod getHolidayOver60hRemNumWithinPeriod;

    /**
     * [RQ677]期間中の60H超休残数を取得する
     * @param companyId 会社ID
     * @param employeeId 社員ID
     * @param aggrPeriod 集計期間
     * @param mode 実績のみ参照区分
     * @param criteriaDate 基準日
     * @param isOverWriteOpt 上書きフラグ
     * @param forOverWriteList 上書き用の暫定60H超休管理データ
     * @param prevHolidayOver60h 前回の60H超休の集計結果
     * @return 60H超休の集計結果
     */
    @Override
    public AggrResultOfHolidayOver60hExport algorithm(String companyId, String employeeId, DatePeriod aggrPeriod, InterimRemainMngMode mode, GeneralDate criteriaDate, Optional<Boolean> isOverWriteOpt, Optional<List<TmpHolidayOver60hMng>> forOverWriteList, Optional<AggrResultOfHolidayOver60hExport> prevHolidayOver60h) {
        GetHolidayOver60hRemNumWithinPeriod.RequireM1 require60h = new GetHolidayOver60hRemNumWithinPeriodImpl.GetHolidayOver60hRemNumWithinPeriodRequireM1();
        CacheCarrier cacheCarrier1 = new CacheCarrier();
        AggrResultOfHolidayOver60h aggrResultOfHolidayOver60h = this.getHolidayOver60hRemNumWithinPeriod.algorithm(
                require60h,
                cacheCarrier1,
                companyId,
                employeeId,
                aggrPeriod,
                mode,
                criteriaDate,
                isOverWriteOpt,
                forOverWriteList,
                prevHolidayOver60h.map(i -> i.toDomain())
        );
        return AggrResultOfHolidayOver60hExport.fromDomain(aggrResultOfHolidayOver60h);
    }
}
