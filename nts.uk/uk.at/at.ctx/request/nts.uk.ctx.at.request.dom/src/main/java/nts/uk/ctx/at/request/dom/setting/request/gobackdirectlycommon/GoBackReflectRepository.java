package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon;

import java.util.Optional;

public interface GoBackReflectRepository {
	public Optional<GoBackReflect> findByCompany(String id);
}
