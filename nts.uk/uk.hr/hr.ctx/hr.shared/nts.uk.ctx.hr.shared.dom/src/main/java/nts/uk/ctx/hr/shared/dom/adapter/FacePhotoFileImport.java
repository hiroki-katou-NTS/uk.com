package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FacePhotoFileImport {
	String thumbnailFileID;

	String facePhotoFileID;
}
