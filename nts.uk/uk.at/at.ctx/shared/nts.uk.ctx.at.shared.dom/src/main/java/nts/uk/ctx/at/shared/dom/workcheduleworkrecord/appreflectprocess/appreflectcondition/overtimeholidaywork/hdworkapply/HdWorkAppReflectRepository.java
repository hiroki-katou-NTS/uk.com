package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply;

import java.util.Optional;

public interface HdWorkAppReflectRepository {
    Optional<HdWorkAppReflect> findReflectByCompany(String companyId);
}
