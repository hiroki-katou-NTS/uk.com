package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;

public class CreateFlowMenuTest {

	final MenuSettingDto menuDto = new MenuSettingDto("", "", 0, 0, "", "", 0, 0, 0, 0, 0, 0, "", "", 0, 0);
	final ArrowSettingDto arrowDto = new ArrowSettingDto("", "", 0, 0, "", 0, 0);
	final FileAttachmentSettingDto fileDto = new FileAttachmentSettingDto("", "", 0, 0, "", "", 0, 0, 0, 0, "", "", 0,
			0);
	final ImageSettingDto imageDto = new ImageSettingDto("", "", 0, 0, "", "", 0, 0, 0, 0, "", "", 0, 0);
	final LabelSettingDto labelDto = new LabelSettingDto("", "", 0, 0, "", 0, 0, 0, 0, "", "", 0, 0);
	final LinkSettingDto linkDto = new LinkSettingDto("", "", 0, 0, "", "", 0, 0, 0, 0, "", "", 0, 0);

	final CreateFlowMenu domain0 = new CreateFlowMenu();
	final CreateFlowMenu domain1 = new CreateFlowMenu(new TopPagePartCode("0001"), new TopPagePartName("code1"),
			Optional.empty(), "000000000000-0000");
	final CreateFlowMenu domain1_copy = new CreateFlowMenu(new TopPagePartCode("0001"), new TopPagePartName("code1"),
			Optional.empty(), "000000000000-0000");
	final CreateFlowMenu domain2 = new CreateFlowMenu(new TopPagePartCode("0002"), new TopPagePartName("code2"),
			Optional.of(new FlowMenuLayout()), "000000000000-0000");
	final CreateFlowMenu domainAll = new CreateFlowMenu(new TopPagePartCode("0001"), new TopPagePartName("code"),
			Optional.of(new FlowMenuLayout("file-id-123141", Arrays.asList(MenuSetting.createFromMemento(menuDto)),
					Arrays.asList(LabelSetting.createFromMemento(labelDto)),
					Arrays.asList(LinkSetting.createFromMemento(linkDto)),
					Arrays.asList(FileAttachmentSetting.createFromMemento(fileDto)),
					Arrays.asList(ImageSetting.createFromMemento(imageDto)),
					Arrays.asList(ArrowSetting.createFromMemento(arrowDto)))),
			"00001");

	@Test
	public void getters() {
		NtsAssert.invokeGetters(domain1);
		NtsAssert.invokeGetters(domainAll.getFlowMenuLayout().get());
		NtsAssert.invokeGetters(MenuSetting.createFromMemento(menuDto));
		NtsAssert.invokeGetters(ArrowSetting.createFromMemento(arrowDto));
		NtsAssert.invokeGetters(FileAttachmentSetting.createFromMemento(fileDto));
		NtsAssert.invokeGetters(ImageSetting.createFromMemento(imageDto));
		NtsAssert.invokeGetters(LabelSetting.createFromMemento(labelDto));
		NtsAssert.invokeGetters(LinkSetting.createFromMemento(linkDto));
	}

	@Test
	public void setters() {
		domain0.setCid(null);
		domain0.setFlowMenuCode(null);
		domain0.setFlowMenuLayout(Optional.empty());
		domain0.setFlowMenuName(null);
		domain0.setVersion(0);
	}

	@Test
	public void testFlowMenuFull() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "0001");
		CreateFlowMenu.createFromMemento(dto);
	}

	@Test
	public void testFlowMenuEmpty() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		dto.setCid("cid");
		dto.setContractCode("contracCd");
		CreateFlowMenu.createFromMemento(dto);
	}
	
	@Test
	public void testFileFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		FileAttachmentSettingDto newData = new FileAttachmentSettingDto("", "", 0, 0, "", "", 0, 0, 0, 1, null, null, 0, 0);
		dto.setFileAttachmentSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
	}

	@Test
	public void testImageFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		ImageSettingDto newData = new ImageSettingDto("", "", 0, 0, "", "", 0, 0, 0, 1, null, null, 0, 0);
		dto.setImageSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
	}
	
	@Test
	public void testLabelFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		LabelSettingDto newData = new LabelSettingDto("", "", 0, 0, null, 0, 0, 0, 1, null, null, 0, 0);
		dto.setLabelSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
	}
	
	@Test
	public void testLinkFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		LinkSettingDto newData = new LinkSettingDto("", "", 0, 0, "", "", 0, 0, 0, 1, null, null, 0, 0);
		dto.setLinkSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
	}

	@Test
	public void testMenuFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		MenuSettingDto newData = new MenuSettingDto("", "", 0, 0, "", "", 0, 0, 0, 0, 0, 1, null, null, 0, 0);
		dto.setMenuSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
	}
}
