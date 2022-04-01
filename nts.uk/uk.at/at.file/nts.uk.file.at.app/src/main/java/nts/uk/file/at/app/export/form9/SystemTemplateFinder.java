package nts.uk.file.at.app.export.form9;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.gul.web.URLEncode;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Code;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;
import nts.uk.ctx.at.aggregation.dom.form9.Form9LayoutRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.storage.webapi.StreamingOutputFile;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Optional;

@Stateless
public class SystemTemplateFinder {
    @Inject
    private Form9ExcelByFormatExportGenerator exportGenerator;

    @Inject
    private Form9LayoutRepository form9LayoutRepo;

    public Response getSystemTemplateFile(String code) {
        return form9LayoutRepo.get(AppContexts.user().companyId(), new Form9Code(code)).map(layout -> {
            String templateFileName = layout.getFileName(new Form9Layout.Require() {
                @Override
                public Optional<StoredFileInfo> getInfo(String fileId) {
                    return Optional.empty();
                }
                @Override
                public StoredFileInfo saveFile(String fileName) {
                    return null;
                }
            });
            int indexOfLastSlash = templateFileName.lastIndexOf("/");
            if (indexOfLastSlash != -1) {
                templateFileName = templateFileName.substring(indexOfLastSlash + 1);
            }
            String encodedName = URLEncode.encodeAsUtf8(templateFileName);
            String contentDisposition = String.format("attachment; filename=\"%s\"", encodedName);
            InputStream inputStream = exportGenerator.getSystemTemplate(templateFileName);
            return Response.ok()
                    .entity(new StreamingOutputFile(() -> inputStream))
                    .encoding("UTF-8")
                    .header("Content-Disposition", contentDisposition)
                    .build();
        }).orElse(Response.status(404).build());
    }
}
