package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;
/**
 * 資格情報
 */
public interface QualificationInformationRepository {

    List<QualificationInformation> getQualificationGroupSettingByCompanyID();

    Optional<QualificationInformation> getQualificationGroupSettingById(String qualificationCode);

    void add(QualificationInformation domain);

    void update(QualificationInformation domain);

    void remove(QualificationInformation domain);
}
