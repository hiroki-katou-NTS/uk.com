package nts.uk.ctx.at.record.pub.remainingnumber.holidayover60h;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hError;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggrResultOfHolidayOver60hExport {
    /** 使用回数 */
    private UsedTimes usedTimes;
    /** 60H超休エラー情報 */
    private List<Integer> holidayOver60hErrors;
    /** 60H超休情報（期間終了日時点） */
    private HolidayOver60hInfoExport asOfPeriodEnd;
    /** 60H超休情報（期間終了日の翌日開始時点） */
    private HolidayOver60hInfoExport asOfStartNextDayOfPeriodEnd;
    /** 60H超休情報（消滅） */
    private HolidayOver60hInfoExport lapsed;

    public AggrResultOfHolidayOver60h toDomain() {
        return AggrResultOfHolidayOver60h.of(
//                usedTimes,
        		asOfPeriodEnd == null ? Finally.empty() : Finally.of(asOfPeriodEnd.toDomain()),
                asOfStartNextDayOfPeriodEnd == null ? Finally.empty() : Finally.of(asOfStartNextDayOfPeriodEnd.toDomain()),
                Optional.ofNullable(lapsed == null ? null : lapsed.toDomain()),
                holidayOver60hErrors.stream().map(i -> EnumAdaptor.valueOf(i, HolidayOver60hError.class)).collect(Collectors.toList())
        );
    }

    public static AggrResultOfHolidayOver60hExport fromDomain(AggrResultOfHolidayOver60h domain) {
        return new AggrResultOfHolidayOver60hExport(
                new UsedTimes(0),
                domain.getHolidayOver60hErrors().stream().map(i -> i.value).collect(Collectors.toList()),
                convert(domain.getAsOfPeriodEnd()),
                convert(domain.getAsOfStartNextDayOfPeriodEnd()),
                domain.getLapsed().map(c -> HolidayOver60hInfoExport.fromDomain(c)).orElse(null)
        );
    }

	private static HolidayOver60hInfoExport convert(Finally<HolidayOver60hInfo> domain){
		
		return !domain.isPresent() ? null : 
			HolidayOver60hInfoExport.fromDomain(domain.get());
	}
}
