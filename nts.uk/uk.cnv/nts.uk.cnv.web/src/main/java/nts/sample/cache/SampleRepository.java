package nts.sample.cache;

import java.util.List;
import java.util.Optional;

public interface SampleRepository {

	Optional<SampleDomain> find(String codes);
	List<SampleDomain> find(List<String> codes);
}
