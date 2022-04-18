package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.Application;
/**
 * Refactor4
 * @author hoangnd
 *
 */
public interface GoBackDirectlyRepository {
	
	Optional<GoBackDirectly> find(String companyId, String appId);
	
	void add (GoBackDirectly domain);
	
	void update(GoBackDirectly domain);
	
	void remove(GoBackDirectly domain);
	
	void delete(String companyId, String appId);
	
	Optional<GoBackDirectly> find(String companyId, String appId, Application app);
	
}
