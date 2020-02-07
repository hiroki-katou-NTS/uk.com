package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class RomajiNameNotiCreSetFinder {

    @Inject
    private RomajiNameNotiCreSetRepository finder;

    public RomajiNameNotiCreSetDto getRomajiNameNotiCreSettingById(){
        Optional<RomajiNameNotiCreSetting> romajiNameNotiCreSetting  = finder.getRomajiNameNotiCreSettingById();
        if(romajiNameNotiCreSetting.isPresent()){
            return RomajiNameNotiCreSetDto.fromDomain(romajiNameNotiCreSetting.get());
        } else {
            return null;
        }
    }


}
