package nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.DomainObject;

/**
 *UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.画像設定
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class ImageSetting extends DomainObject {

	/**
	 * 画像ファイルID
	 */
	private Optional<String> fileId;
	
	/**
	 * 画像ファイル名
	 */
	private Optional<FileName> fileName;
	
	/**
	 * 既定区分
	 */
	private FixedClassification isFixed;
	
	/**
	 * サイズと位置
	 */
	private SizeAndPosition sizeAndPosition;
	
	/**
	 * 比率
	 */
	private double ratio;
}
