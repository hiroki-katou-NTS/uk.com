package nts.uk.shr.infra.file.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.ws.WebService;
import nts.arc.system.ServerSystemProperties;
import nts.gul.text.IdentifierUtil;

@Path("image/editor")
@Produces("application/json")
public class ImageEditorWebService extends WebService{
	
	@Inject
	private FileStorage fileStorage;

	@POST
	@Path("/cropimage")
	public StoredFileInfo cropImage(ImageCropQuery query) {
		try {
			String string64 = query.getFile().substring(query.getFile().indexOf(",")+1);
			InputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(string64));
			BufferedImage bfi = ImageIO.read(is);
			int width = getWidth(query, bfi.getWidth()), height = getHeight(query, bfi.getHeight());
			
			if(query.isCrop() && isCroppable(width, height)){
				bfi = bfi.getSubimage(query.getX(), query.getY(), width, height);
			}
			
			File file = new File(ServerSystemProperties.fileStoragePath() + query.getFileName());

			ImageIO.write(bfi, query.getFormat(), file);

			StoredFileInfo fileInfo = this.fileStorage.store(IdentifierUtil.randomUniqueId(),
					file.toPath(), query.getFileName(), query.getStereoType());
			file.delete();
			return fileInfo;
		} catch (IOException e) {
			throw new RuntimeException("File is not a image.");
		}
	} 
	
	private boolean isCroppable(int width, int height){
		return width > 0 && height > 0;
	}
	
	private int getWidth(ImageCropQuery query, int width){
		return Math.min(query.getWidth(), width - query.getX());
	}
	
	private int getHeight(ImageCropQuery query, int height){
		return Math.min(query.getHeight(), height - query.getY());
	}
}
