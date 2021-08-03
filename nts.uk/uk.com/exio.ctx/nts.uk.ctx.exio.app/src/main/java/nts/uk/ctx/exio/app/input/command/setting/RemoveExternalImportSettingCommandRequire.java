package nts.uk.ctx.exio.app.input.command.setting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.uk.ctx.exio.app.input.command.setting.RemoveExternalImportSettingCommandHandler.Require;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RemoveExternalImportSettingCommandRequire {
	
	@Inject
	private ExternalImportSettingRepository externalImportSettingRepo;
	
	public Require create() {
		
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	@RequiredArgsConstructor
	public class RequireImpl implements Require {
		
		@Override
		public void delete(String companyId, ExternalImportCode code) {
			externalImportSettingRepo.delete(companyId, code);
		}
	}
}
