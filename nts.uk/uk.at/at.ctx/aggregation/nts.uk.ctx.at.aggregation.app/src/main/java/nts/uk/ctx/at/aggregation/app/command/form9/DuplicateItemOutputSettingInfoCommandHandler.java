package nts.uk.ctx.at.aggregation.app.command.form9;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFile;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.system.ServerSystemProperties;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.aggregation.dom.form9.*;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.io.IOUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.*;
import java.util.Optional;

/**
 * 出力項目設定情報を複製する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.App.出力項目設定情報を複製する.出力項目設定情報を複製する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DuplicateItemOutputSettingInfoCommandHandler extends CommandHandler<DuplicateItemOutputSettingInfoCommand> {

    @Inject
    private Form9LayoutRepository form9Repo;

    @Inject
    ApplicationTemporaryFileFactory fileFactory;

    @Inject
    FileStorage fileStorage;

    @Override
    protected void handle(CommandHandlerContext<DuplicateItemOutputSettingInfoCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        if (command == null) return;
        String cid = AppContexts.user().companyId();

        // 1. get(ログイン会社ID、Input.元コード)
        val form9Layout = this.form9Repo.get(cid, new Form9Code(command.getOriginalCode())).orElse(null);

        // 2. 複製する(Require, 様式９の出力レイアウト, 様式９のコード, 様式９の名称, boolean)
        // require, 複製元, Input.先コード, Input.先名称 ,Input.上書きするか
        AtomTask atomTask = CopyForm9LayoutService.copy(
                new CopyForm9LayoutService.Require() {
                    @Override
                    public Optional<StoredFileInfo> getInfo(String fileId) {
                        return fileStorage.getInfo(fileId);
                    }

                    @Override
                    public StoredFileInfo saveFile(String fileName) {
                        InputStream is = this.getClass().getClassLoader().getResourceAsStream("/report/form9/"+ fileName);
                        ApplicationTemporaryFile tempFile = fileFactory.createTempFile();
                        OutputStream os = tempFile.createOutputStream();
                        try {
                            IOUtils.copy(is, os);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        StoredFileInfo fileInfo = fileStorage.store(tempFile.getPath(), fileName, "excelFile");
                        tempFile.dispose();

                        return fileInfo;
                    }

                    @Override
                    public Optional<Form9Layout> getForm9Layout(Form9Code form9Code) {
                        return form9Repo.get(cid, form9Code);
                    }

                    @Override
                    public void insertForm9Layout(Form9Layout layout) {
                        form9Repo.insertLayoutOfUser(cid, layout);
                    }

                    @Override
                    public void deleteForm9Layout(Form9Code form9Code) {
                        form9Repo.deleteLayoutOfUser(cid, form9Code);
                    }
                },
                form9Layout,
                new Form9Code(command.getDestinationCode()),
                new Form9Name(command.getDestinationName()),
                command.isOverwrite()
        );

        // 3. Transaction
        transaction.execute(atomTask);
    }
}
