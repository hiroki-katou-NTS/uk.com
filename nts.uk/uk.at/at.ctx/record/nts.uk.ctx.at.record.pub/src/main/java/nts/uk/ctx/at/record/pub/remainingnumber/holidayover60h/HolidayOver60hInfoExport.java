package nts.uk.ctx.at.record.pub.remainingnumber.holidayover60h;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hInfo;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HolidayOver60hInfoExport {
    /** 年月日 */
    private GeneralDate ymd;

    /** 残数 */
    private HolidayOver60hRemainingNumberExport remainingNumber;

    /** 付与残数データ */
    private List<HolidayOver60hGrantRemainingExport> grantRemainingList;

    public HolidayOver60hInfo toDomain() {
        HolidayOver60hInfo domain = new HolidayOver60hInfo();
        domain.setYmd(ymd);
        domain.setRemainingNumber(remainingNumber.toDomain());
        List<HolidayOver60hGrantRemaining> grantRemainingList = this.grantRemainingList.stream().map(i -> {
            HolidayOver60hGrantRemaining tmp = new HolidayOver60hGrantRemaining();
            tmp.setAnnLeavID(i.getAnnLeavID());
            tmp.setCid(i.getCid());
            tmp.setEmployeeId(i.getEmployeeId());
            tmp.setGrantDate(i.getGrantDate());
            tmp.setDeadline(i.getDeadline());
            tmp.setExpirationStatus(i.getExpirationStatus());
            tmp.setRegisterType(i.getRegisterType());
            tmp.setDetails(i.getDetails());
            tmp.setDummyAtr(i.isDummyAtr());
            return tmp;
        }).collect(Collectors.toList());

        domain.getGrantRemainingList().addAll(grantRemainingList);
        return domain;
    }

    public static HolidayOver60hInfoExport fromDomain(HolidayOver60hInfo domain) {
        return new HolidayOver60hInfoExport(
                domain.getYmd(),
                HolidayOver60hRemainingNumberExport.fromDomain(domain.getRemainingNumber()),
                domain.getGrantRemainingList().stream().map(HolidayOver60hGrantRemainingExport::fromDomain).collect(Collectors.toList())
        );
    }
}
