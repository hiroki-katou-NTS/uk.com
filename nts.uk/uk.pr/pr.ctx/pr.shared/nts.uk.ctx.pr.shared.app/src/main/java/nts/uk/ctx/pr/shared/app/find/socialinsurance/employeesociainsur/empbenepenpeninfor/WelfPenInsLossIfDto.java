package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.NumInsCards;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.ReasonsForLossPensionIns;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIf;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.RemarkForQuaCompany;

import java.util.Optional;
@Value
@Data
@AllArgsConstructor
public class WelfPenInsLossIfDto {
    /**
     * 社員ID
     */
    private String empId;

    /**
     * その他
     */
    private int other;

    /**
     * その他理由
     */
    private String otherReason;

    /**
     * 保険証回収添付枚数
     */
    private Integer caInsuarace;

    /**
     * 保険証回収返不能枚数
     */
    private Integer numRecoved;

    /**
     * 資格喪失原因
     */
    private Integer cause;


    public static WelfPenInsLossIfDto fromDomain(WelfPenInsLossIf domain){
       return new WelfPenInsLossIfDto(
               domain.getEmpId(),
               domain.getOther(),
               domain.getOtherReason().map(i->i.v()).orElse(null),
               domain.getCaInsuarace().map(i->i.v()).orElse(null),
               domain.getNumRecoved().map(i->i.v()).orElse(null),
               domain.getCause().get().value
       );


    }
}
