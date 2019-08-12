package nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset;

import lombok.*;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaValue;

import java.util.Optional;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SocialInsurNotiCreateSetDto {
    public Optional<SalGenParaValue> salGenParaValue;
    public Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet;
}
