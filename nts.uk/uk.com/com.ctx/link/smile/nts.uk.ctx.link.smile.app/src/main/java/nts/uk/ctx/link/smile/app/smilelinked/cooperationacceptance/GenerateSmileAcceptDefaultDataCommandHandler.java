package nts.uk.ctx.link.smile.app.smilelinked.cooperationacceptance;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItemRepository;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceItem;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;
import nts.uk.shr.com.company.CompanyId;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * SMILEの外部受入の初期データを生成する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GenerateSmileAcceptDefaultDataCommandHandler extends CommandHandler<GenerateSmileAcceptDefaultDataCommand> {

    @Inject
    private TransactionService transaction;

    @Override
    protected void handle(CommandHandlerContext<GenerateSmileAcceptDefaultDataCommand> context) {

        val command = context.getCommand();

        Require require = EmbedStopwatch.embed(new RequireImpl());

        AtomTask task = command.getSmileCooperationAcceptanceItem().generateDefaultData(
                require,
                AppContexts.user().companyId(),
                command.getExternalImportCode());

        transaction.execute(task);
    }

    public interface Require extends
            SmileCooperationAcceptanceItem.RequireGenerateDefaultData {
    }

    @Inject
    private SmileCooperationAcceptanceSettingRepository smileAcceptSettingRepo;

    @Inject
    private ExternalImportSettingRepository externalImportSettingRepo;

    @Inject
    private FileStorage fileStorage;
    
    @Inject
    private ReviseItemRepository reviseItemRepo;

    private class RequireImpl implements Require {

        @Override
        public SmileCooperationAcceptanceSetting getSmileCooperationAcceptanceSetting(String companyId, SmileCooperationAcceptanceItem item) {
            return smileAcceptSettingRepo.get(CompanyId.getContractCodeOf(companyId), companyId).stream()
                    .filter(s -> s.getCooperationAcceptance() == item)
                    .findFirst()
                    .get();
        }

        @Override
        public void save(SmileCooperationAcceptanceSetting smileSetting) {
            smileAcceptSettingRepo.update(smileSetting);
        }

        @Override
        public boolean existsExternalImportSetting(String companyId, ExternalImportCode code) {
            return externalImportSettingRepo.exist(companyId, code);
        }

        @Override
        public Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode code) {
            return externalImportSettingRepo.get(Optional.empty(), companyId, code);
        }

        @Override
        public void addSetting(ExternalImportSetting setting) {
            externalImportSettingRepo.insert(setting);
        }
        
        @Override
        public String storeFileThenGetFileId(InputStream fileInputStream, String fileName, String fileType) {
            val info = fileStorage.store(fileInputStream, fileName, fileType);
            return info.getId();
        }

		@Override
		public List<ReviseItem> getReviseItems(String companyId, ExternalImportCode code) {
			return reviseItemRepo.get(companyId, code);
		}

		@Override
		public void addReviseItems(List<ReviseItem> reviseItems) {
			reviseItemRepo.persist(reviseItems);
		}
    }
}
