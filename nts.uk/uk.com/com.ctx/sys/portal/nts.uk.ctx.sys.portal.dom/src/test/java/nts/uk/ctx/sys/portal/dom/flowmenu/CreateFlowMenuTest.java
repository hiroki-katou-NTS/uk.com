package nts.uk.ctx.sys.portal.dom.flowmenu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;

public class CreateFlowMenuTest {

	final MenuSettingDto menuDto = new MenuSettingDto("cid", "code", 0, 0, "menuName", "menuCode", 1, 3, 0, 0, 0, 0, 0, 0);
	final ArrowSettingDto arrowDto = new ArrowSettingDto("cid", "code", 0, 0, "fileName", 0, 0);
	final FileAttachmentSettingDto fileDto = new FileAttachmentSettingDto("cid", "code", 0, 0, "fileId", "content", 0,
			0, 0, 0, 0, 0);
	final ImageSettingDto imageDto = new ImageSettingDto("cid", "code", 0, 0, "fileId", "fileName", 1, 0, 0);
	final LabelSettingDto labelDto = new LabelSettingDto("cid", "code", 0, 0, "content", 0, 0, 0, 0, "textColor",
			"bgColor", 0, 0);
	final LinkSettingDto linkDto = new LinkSettingDto("cid", "code", 0, 0, "content", "url", 0, 0, 0, 0, 0, 0);
	final CreateFlowMenuDto createFlowMenuDto = new CreateFlowMenuDto("cid", "0001", "code", "file-id-123141",
			Arrays.asList(arrowDto), Arrays.asList(fileDto), Arrays.asList(imageDto), Arrays.asList(labelDto),
			Arrays.asList(linkDto), Arrays.asList(menuDto));

	final CreateFlowMenu domain0 = new CreateFlowMenu();
	final CreateFlowMenu domain1 = new CreateFlowMenu(new TopPagePartCode("0001"), new TopPagePartName("code1"),
			Optional.empty(), "000000000000-0000");
	final CreateFlowMenu domain1_copy = new CreateFlowMenu(new TopPagePartCode("0001"), new TopPagePartName("code1"),
			Optional.empty(), "000000000000-0000");
	final CreateFlowMenu domain2 = new CreateFlowMenu(new TopPagePartCode("0002"), new TopPagePartName("code2"),
			Optional.of(new FlowMenuLayout()), "000000000000-0000");
	final CreateFlowMenu domainAll = CreateFlowMenu.createFromMemento(createFlowMenuDto);

	@Test
	public void getters() {
		NtsAssert.invokeGetters(domain1);
		NtsAssert.invokeGetters(domainAll.getFlowMenuLayout().get());
	}

	@Test
	public void setters() {
		domain0.setFlowMenuLayout(Optional.empty());
		domain0.setFlowMenuName(new TopPagePartName("code"));
		assertEquals(domain0.getFlowMenuLayout(), Optional.empty());
		assertEquals(domain0.getFlowMenuName().v(), "code");
	}

	@Test
	public void testFlowMenuFull() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "0001");
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		assertEquals(domain.getCid(), domainAll.getCid());
		assertEquals(domain.getFlowMenuCode().v(), domainAll.getFlowMenuCode().v());
		assertEquals(domain.getFlowMenuName().v(), domainAll.getFlowMenuName().v());
		assertEquals(domain.getFlowMenuLayout().get().getFileId(), domainAll.getFlowMenuLayout().get().getFileId());
		assertThat(domain.getFlowMenuLayout().get().getArrowSettings()).extracting(x -> x.getFileName().v(),
				x -> x.getSizeAndPosition().getColumn().v(), x -> x.getSizeAndPosition().getHeight().v(),
				x -> x.getSizeAndPosition().getRow().v(), x -> x.getSizeAndPosition().getWidth().v())
				.containsExactly(tuple("fileName", 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getFileAttachmentSettings())
				.extracting(x -> x.getFileId(), 
						x -> x.getLinkContent().get(),
						x -> x.getFontSetting().getPosition().getHorizontalPosition().value,
						x -> x.getFontSetting().getPosition().getVerticalPosition().value,
						x -> x.getFontSetting().getSizeAndColor().getBackgroundColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontSize().v(),
						x -> x.getFontSetting().getSizeAndColor().isBold(), 
						x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), 
						x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.containsExactly(tuple("fileId", "content", 0, 0, Optional.empty(), Optional.empty(), 0, false, 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getImageSettings())
				.extracting(x -> x.getFileId().get(),
						x -> x.getFileName().get().v(),
						x -> x.getIsFixed().value,
						x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), 
						x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.containsExactly(tuple("fileId", "fileName", 1, 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getLabelSettings())
				.extracting(x -> x.getLabelContent().get().v(),
						x -> x.getFontSetting().getPosition().getHorizontalPosition().value,
						x -> x.getFontSetting().getPosition().getVerticalPosition().value,
						x -> x.getFontSetting().getSizeAndColor().getBackgroundColor().get().v(),
						x -> x.getFontSetting().getSizeAndColor().getFontColor().get().v(),
						x -> x.getFontSetting().getSizeAndColor().getFontSize().v(),
						x -> x.getFontSetting().getSizeAndColor().isBold(), 
						x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), 
						x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.containsExactly(tuple("content", 0, 0, "bgColor", "textColor", 0, false, 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getLinkSettings())
				.extracting(x -> x.getLinkContent().get(),
						x -> x.getUrl().v(),
						x -> x.getFontSetting().getPosition().getHorizontalPosition().value,
						x -> x.getFontSetting().getPosition().getVerticalPosition().value,
						x -> x.getFontSetting().getSizeAndColor().getBackgroundColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontSize().v(),
						x -> x.getFontSetting().getSizeAndColor().isBold(), 
						x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), 
						x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.containsExactly(tuple("content", "url", 0, 0, Optional.empty(), Optional.empty(), 0, false, 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getMenuSettings())
				.extracting(x -> x.getMenuClassification().value,
						x -> x.getMenuCode().v(),
						x -> x.getMenuName().v(),
						x -> x.getSystemType().value,
						x -> x.getFontSetting().getPosition().getHorizontalPosition().value,
						x -> x.getFontSetting().getPosition().getVerticalPosition().value,
						x -> x.getFontSetting().getSizeAndColor().getBackgroundColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontSize().v(),
						x -> x.getFontSetting().getSizeAndColor().isBold(), 
						x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), 
						x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.containsExactly(tuple(1, "menuCode", "menuName", 3, 0, 0, Optional.empty(), Optional.empty(), 0, false, 0, 0, 0, 0));
	}

	@Test
	public void testFlowMenuEmpty() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		dto.setCid("cid");
		dto.setContractCode("contracCd");
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		assertEquals(domain.getCid(), "cid");
		assertEquals(domain.getFlowMenuCode().v(), "");
		assertEquals(domain.getFlowMenuName().v(), "");
		assertEquals(domain.getFlowMenuLayout(), Optional.empty());
	}

	@Test
	public void testFileFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		FileAttachmentSettingDto newData = new FileAttachmentSettingDto(null, null, 0, 0, null, null, 0, 0, 0, 1, 0,
				0);
		dto.setFileAttachmentSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		SizeAndColor data = dto.getFileAttachmentSettings().get(0).getFontSetting().getSizeAndColor();
		assertEquals(data.getBackgroundColor(), Optional.empty());
		assertEquals(data.getFontColor(), Optional.empty());
		assertEquals(data.isBold(), true);
	}

	@Test
	public void testImageFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		ImageSettingDto newData = new ImageSettingDto(null, null, 0, 0, null, null, 0, 0, 0);
		dto.setImageSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		assertEquals(dto.getImageSettings().get(0).getFileName(), Optional.empty());
	}

	@Test
	public void testLabelFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		LabelSettingDto newData = new LabelSettingDto(null, null, 0, 0, null, 0, 0, 0, 1, null, null, 0, 0);
		dto.setLabelSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		SizeAndColor data = dto.getLabelSettings().get(0).getFontSetting().getSizeAndColor();
		assertEquals(data.getBackgroundColor(), Optional.empty());
		assertEquals(data.getFontColor(), Optional.empty());
		assertEquals(data.isBold(), true);
	}

	@Test
	public void testLinkFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		LinkSettingDto newData = new LinkSettingDto(null, null, 0, 0, null, null, 0, 0, 0, 1, 0, 0);
		dto.setLinkSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		SizeAndColor data = dto.getLinkSettings().get(0).getFontSetting().getSizeAndColor();
		assertEquals(data.getBackgroundColor(), Optional.empty());
		assertEquals(data.getFontColor(), Optional.empty());
		assertEquals(data.isBold(), true);
	}

	@Test
	public void testMenuFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		MenuSettingDto newData = new MenuSettingDto(null, null, 0, 0, null, null, 0, 0, 0, 0, 0, 1, 0, 0);
		dto.setMenuSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		SizeAndColor data = dto.getMenuSettings().get(0).getFontSetting().getSizeAndColor();
		assertEquals(data.getBackgroundColor(), Optional.empty());
		assertEquals(data.getFontColor(), Optional.empty());
		assertEquals(data.isBold(), true);
	}
}
