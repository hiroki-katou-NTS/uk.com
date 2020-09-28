package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback;

import java.util.Optional;

public interface GoBackReflectRepository {
	Optional<GoBackReflect> findByCompany(String id);

	void add(GoBackReflect domain);

	void update(GoBackReflect domain);

	void remove(GoBackReflect domain);
}
