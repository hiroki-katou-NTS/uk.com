package nts.uk.ctx.at.function.infra.repository.supportworklist.outputsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSetting;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSettingRepository;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputCode;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSupportWorkListOutputSettingRepository extends JpaRepository implements SupportWorkListOutputSettingRepository {
    @Override
    public void insert(SupportWorkListOutputSetting domain) {

    }

    @Override
    public void update(SupportWorkListOutputSetting domain) {

    }

    @Override
    public void delete(String companyId, SupportWorkOutputCode code) {

    }

    @Override
    public List<SupportWorkListOutputSetting> get(String companyId) {
        return new ArrayList<>();
    }

    @Override
    public Optional<SupportWorkListOutputSetting> get(String companyId, SupportWorkOutputCode code) {
        return Optional.empty();
    }
}
