package nts.uk.ctx.at.request.app.find.setting.company.emailset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailContentDto {
    private int division;
    private String emailSubject;
    private String emailText;
}
