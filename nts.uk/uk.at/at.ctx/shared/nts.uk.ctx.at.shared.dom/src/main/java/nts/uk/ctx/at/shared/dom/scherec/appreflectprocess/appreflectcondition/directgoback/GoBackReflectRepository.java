package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;

import java.util.Optional;

public interface GoBackReflectRepository {
	Optional<GoBackReflect> findByCompany(String id);

	void add(GoBackReflect domain);

	void update(GoBackReflect domain);

	void remove(GoBackReflect domain);
}
