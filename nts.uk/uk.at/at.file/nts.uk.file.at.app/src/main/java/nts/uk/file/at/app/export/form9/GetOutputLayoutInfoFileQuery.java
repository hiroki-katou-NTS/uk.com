package nts.uk.file.at.app.export.form9;

import lombok.val;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Code;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;
import nts.uk.ctx.at.aggregation.dom.form9.Form9LayoutRepository;
import nts.uk.file.at.app.export.form9.dto.Form9LayoutFileNameDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 出力レイアウト情報を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.App.出力レイアウト情報を取得する.出力レイアウト情報を取得する
 */
@Stateless
public class GetOutputLayoutInfoFileQuery {
    @Inject
    private Form9LayoutRepository form9LayoutRepo;

    @Inject
    private FileStorage fileStorage;

    @Inject
    private StoredFileInfoRepository storedFileInfoRepo;

    public Form9LayoutFileNameDto get(String cid, String code) {
        val form9Layout = form9LayoutRepo.get(cid, new Form9Code(code)).orElse(null);
        if (form9Layout == null) return null;

        String fileName = form9Layout.getFileName(new Form9Layout.Require() {
            @Override
            public Optional<StoredFileInfo> getInfo(String fileId) {
                return fileStorage.getInfo(fileId);
            }

            @Override
            public StoredFileInfo saveFile(String fileName) {
                return null;
            }
        });

        return new Form9LayoutFileNameDto(form9Layout, fileName);
    }
}
