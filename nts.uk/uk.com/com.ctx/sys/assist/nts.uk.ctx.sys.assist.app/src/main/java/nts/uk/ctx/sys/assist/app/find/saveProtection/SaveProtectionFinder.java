package nts.uk.ctx.sys.assist.app.find.saveProtection;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.saveProtetion.SaveProtetionRepository;


@Stateless
public class SaveProtectionFinder
{

    @Inject
    private SaveProtetionRepository finder;

    public List<SaveProtectionDto> getAllSaveProtection(){
        return finder.getAllSaveProtection().stream().map(item -> SaveProtectionDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
