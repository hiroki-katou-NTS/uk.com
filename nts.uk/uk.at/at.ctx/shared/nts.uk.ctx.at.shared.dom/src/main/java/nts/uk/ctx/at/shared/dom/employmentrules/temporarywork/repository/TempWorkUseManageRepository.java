package nts.uk.ctx.at.shared.dom.employmentrules.temporarywork.repository;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.employmentrules.temporarywork.TemporaryWorkUseManage;

public interface TempWorkUseManageRepository {

	Optional<TemporaryWorkUseManage> findByKey(String companyId);
}
