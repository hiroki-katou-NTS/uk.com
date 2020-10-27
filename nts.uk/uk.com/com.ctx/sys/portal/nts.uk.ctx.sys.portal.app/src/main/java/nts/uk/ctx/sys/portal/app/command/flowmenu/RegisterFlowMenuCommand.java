package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.flowmenu.ArrowSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FileAttachmentSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.ImageSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.LabelSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.LinkSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.MenuSetting;

@Data
public class RegisterFlowMenuCommand implements CreateFlowMenu.MementoGetter {

	/**
	 * フローメニューコード
	 */
	private String flowMenuCode;
	
	/**
	 * フローメニュー名称
	 */
	private String flowMenuName;
	
	/**
	 * 会社ID
	 */
	private String cid;

	@Override
	public String getFileId() {
		// NOT USED
		return null;
	}

	@Override
	public List<MenuSetting> getMenuSettings() {
		// NOT USED
		return null;
	}

	@Override
	public List<ArrowSetting> getArrowSettings() {
		// NOT USED
		return null;
	}

	@Override
	public List<FileAttachmentSetting> getFileAttachmentSettings() {
		// NOT USED
		return null;
	}

	@Override
	public List<ImageSetting> getImageSettings() {
		// NOT USED
		return null;
	}

	@Override
	public List<LabelSetting> getLabelSettings() {
		// NOT USED
		return null;
	}

	@Override
	public List<LinkSetting> getLinkSettings() {
		// NOT USED
		return null;
	}
}
