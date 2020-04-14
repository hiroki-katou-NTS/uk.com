package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpHealInsQualifiInfoDto extends PeregDomainDto {
    /**
     * 期間
     */
    @PeregItem("IS00840")
    private String period;

    /**
     * 取得日
     */
    @PeregItem("IS00841")
    private GeneralDate dateOfAcquisition;

    /**
     * 喪失日
     */
    @PeregItem("IS00842")
    private GeneralDate lostDate;

    /**
     * 健康保険番号
     */
    @PeregItem("IS00843")
    private String healInsNumber;

    /**
     * 介護保険番号
     */
    @PeregItem("IS00844")
    private String nurCaseInsNumber;


    public EmpHealInsQualifiInfoDto(String recordId) {
        super(recordId);
    }

    public static EmpHealInsQualifiInfoDto createFromDomain(EmplHealInsurQualifiInfor qualifiInfor, HealInsurNumberInfor numberInfor) {
        EmpHealInsQualifiInfoDto dto = new EmpHealInsQualifiInfoDto(numberInfor.getHistoryId());
        dto.setDateOfAcquisition(qualifiInfor.getMourPeriod().get(0).start());
        dto.setLostDate(qualifiInfor.getMourPeriod().get(0).end());
        dto.setHealInsNumber(numberInfor.getHealInsNumber().map(e -> e.v()).orElse(null));
        dto.setNurCaseInsNumber(numberInfor.getCareInsurNumber().map(e -> e.v()).orElse(null));
        return dto;
    }

    public static EmpHealInsQualifiInfoDto createFromDomain(HealInsurNumberInfor numberInfor, EmpHealthInsurBenefits benefits) {
        EmpHealInsQualifiInfoDto dto = new EmpHealInsQualifiInfoDto(numberInfor.getHistoryId());
        dto.setDateOfAcquisition(benefits.getDatePeriod().start());
        dto.setLostDate(benefits.getDatePeriod().end());
        dto.setHealInsNumber(numberInfor.getHealInsNumber().map(e -> e.v()).orElse(null));
        dto.setNurCaseInsNumber(numberInfor.getCareInsurNumber().map(e -> e.v()).orElse(null));
        return dto;
    }
}
