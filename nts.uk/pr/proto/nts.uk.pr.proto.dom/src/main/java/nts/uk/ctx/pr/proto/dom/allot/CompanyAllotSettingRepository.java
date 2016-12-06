package nts.uk.ctx.pr.proto.dom.allot;

import java.util.Optional;

public interface CompanyAllotSettingRepository {
	Optional<CompanyAllotSetting> find(String companyCode);	
}
