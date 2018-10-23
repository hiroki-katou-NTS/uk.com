package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

/**
* 資格グループ設定
*/
public interface QualificationGroupSettingRepository
{

    List<QualificationGroupSetting> getAllQualificationlGroupSetting();

    Optional<QualificationGroupSetting> getQualificationlGroupSettingById();

    void add(QualificationGroupSetting domain);

    void update(QualificationGroupSetting domain);

    void remove();

}
