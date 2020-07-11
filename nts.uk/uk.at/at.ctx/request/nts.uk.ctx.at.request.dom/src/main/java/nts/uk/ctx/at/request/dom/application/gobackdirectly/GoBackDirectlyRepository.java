package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.Optional;

public interface GoBackDirectlyRepository {
	Optional<GoBackDirectly> find(String appId);
	void add (GoBackDirectly domain);
	void update(GoBackDirectly domain);
	void remove(GoBackDirectly domain);
}
