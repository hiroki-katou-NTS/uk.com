package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import java.util.List;
import java.util.Optional;

/**
* ローマ字氏名届作成設定
*/
public interface RomajiNameNotiCreSetRepository
{

    List<RomajiNameNotiCreSetting> getAllRomajiNameNotiCreSetting();

    Optional<RomajiNameNotiCreSetting> getRomajiNameNotiCreSettingById(String cid, String userId);

    void add(RomajiNameNotiCreSetting domain);

    void update(RomajiNameNotiCreSetting domain);

    void remove();

}
