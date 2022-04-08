package nts.uk.ctx.at.function.app.find.supportworklist.outputsetting;

import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSettingRepository;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SupportWorkOutputSettingFinder {
    @Inject
    private SupportWorkListOutputSettingRepository repository;

    public List<SupportWorkOutputSettingDto> getAll() {
        return repository.get(AppContexts.user().companyId())
                .stream().map(i -> new SupportWorkOutputSettingDto(i)).collect(Collectors.toList());
    }

    public SupportWorkOutputSettingDto getOne(String code) {
        return repository.get(AppContexts.user().companyId(), new SupportWorkOutputCode(code))
                .map(i -> new SupportWorkOutputSettingDto(i)).orElse(null);
    }
}
