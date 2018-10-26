package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformation;

/**
 * 資格情報
 */
@NoArgsConstructor
@Data
public class QualificationInformationCommand {

    /**
     * 会社ID
     */
    private String companyID;

    /**
     * 資格コード
     */
    private String qualificationCode;

    /**
     * 資格名称
     */
    private String qualificationName;

    public QualificationInformation fromCommandToDomain() {
        return new QualificationInformation(companyID, qualificationCode, qualificationName);
    }
}

