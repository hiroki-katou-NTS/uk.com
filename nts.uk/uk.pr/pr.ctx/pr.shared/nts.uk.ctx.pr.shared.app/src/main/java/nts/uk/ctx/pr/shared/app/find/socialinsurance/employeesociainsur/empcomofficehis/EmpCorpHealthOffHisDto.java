package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
@NoArgsConstructor
public class EmpCorpHealthOffHisDto extends PeregDomainDto {

    /**
     * 開始日
     */
    @PeregItem("IS00788")
    private GeneralDate startDate;

    /**
     * 終了日
     */
    @PeregItem("IS00789")
    private GeneralDate endDate;

    /**
     *社会保険事業所コード
     */
    @PeregItem("IS00790")
    private String socialInsurOfficeCode;

    public EmpCorpHealthOffHisDto(String recordId, GeneralDate startDate, GeneralDate endDate, String socialInsurOfficeCode){
        super(recordId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.socialInsurOfficeCode = socialInsurOfficeCode;
    }

    public static EmpCorpHealthOffHisDto createFromDomain(EmpCorpHealthOffHis domain, AffOfficeInformation domainInfo){
        return new EmpCorpHealthOffHisDto(
                domainInfo.getHistoryId(),
                domain.getPeriod().get(0).start(),
                domain.getPeriod().get(0).end(),
                domainInfo.getSocialInsurOfficeCode().v()
        );
    }
}
