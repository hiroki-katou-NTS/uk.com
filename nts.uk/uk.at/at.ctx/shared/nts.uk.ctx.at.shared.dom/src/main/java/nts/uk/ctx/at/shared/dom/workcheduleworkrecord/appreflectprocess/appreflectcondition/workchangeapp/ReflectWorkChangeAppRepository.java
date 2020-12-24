package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp;

import java.util.Optional;

public interface ReflectWorkChangeAppRepository {

	Optional<ReflectWorkChangeApp> findByCompanyIdReflect(String companyId);
}
