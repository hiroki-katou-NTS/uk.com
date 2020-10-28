package nts.uk.ctx.sys.portal.dom.flowmenu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;

public class CreateFlowMenuTest {

	final MenuSettingDto menuDto = new MenuSettingDto("cid", "code", 0, 0, "menuName", "menuCode", 1, 3, 0, 0, 0, 0, 0,
			0);
	final ArrowSettingDto arrowDto = new ArrowSettingDto("cid", "code", 0, 0, "fileName", 0, 0);
	final FileAttachmentSettingDto fileDto = new FileAttachmentSettingDto("cid", "code", 0, 0, "fileId", "content", 0,
			0, 0, 0, 0, 0);
	final ImageSettingDto imageDtoWithFileId = new ImageSettingDto("cid", "code", 0, 0, "fileId", null, 1, 0, 0);
	final ImageSettingDto imageDtoWithFileName = new ImageSettingDto("cid", "code", 0, 0, null, "fileName", 1, 0, 0);
	final LabelSettingDto labelDto = new LabelSettingDto("cid", "code", 0, 0, "content", 0, 0, 0, 0, "textColor",
			"bgColor", 0, 0);
	final LinkSettingDto linkDto = new LinkSettingDto("cid", "code", 0, 0, "content", "url", 0, 0, 0, 0, 0, 0);
	final CreateFlowMenuDto createFlowMenuDto = new CreateFlowMenuDto("cid", "0001", "code", "file-id-123141",
			Arrays.asList(arrowDto), Arrays.asList(fileDto), Arrays.asList(imageDtoWithFileId, imageDtoWithFileName),
			Arrays.asList(labelDto), Arrays.asList(linkDto), Arrays.asList(menuDto));

	final CreateFlowMenu domain0 = new CreateFlowMenu();
	final CreateFlowMenu domainAll = CreateFlowMenu.createFromMemento(createFlowMenuDto);

	@Test
	public void getters() {
		NtsAssert.invokeGetters(domainAll);
		NtsAssert.invokeGetters(domainAll.getFlowMenuLayout().get());
	}

	@Test
	public void setters() {
		domain0.setFlowMenuLayout(Optional.empty());
		domain0.setFlowMenuName(new TopPagePartName("code"));

		assertThat(domain0.getFlowMenuLayout()).as("setter flowMenuLayout").isEqualTo(Optional.empty());
		assertThat(domain0.getFlowMenuName().v()).as("setter flowMenuName").isEqualTo("code");
	}

	@Test
	public void testFlowMenuFull() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "0001");
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);

		// Assert that the newly created domain has the same attributes as the original domain
		assertThat(domain.getCid()).as("testFull cid").isEqualTo(domainAll.getCid());
		assertThat(domain.getFlowMenuCode()).as("testFull flowMenuCode").isEqualTo(domainAll.getFlowMenuCode());
		assertThat(domain.getFlowMenuName()).as("testFull flowMenuName").isEqualTo(domainAll.getFlowMenuName());
		assertThat(domain.getFlowMenuLayout().get().getFileId()).as("testFull fileId")
				.isEqualTo(domainAll.getFlowMenuLayout().get().getFileId());
		assertThat(domain.getFlowMenuLayout().get().getArrowSettings())
				.extracting(x -> x.getFileName().v(), x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.as("testFull arrow").containsExactly(tuple("fileName", 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getFileAttachmentSettings())
				.extracting(x -> x.getFileId(), x -> x.getLinkContent().get(),
						x -> x.getFontSetting().getPosition().getHorizontalPosition().value,
						x -> x.getFontSetting().getPosition().getVerticalPosition().value,
						x -> x.getFontSetting().getSizeAndColor().getBackgroundColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontSize().v(),
						x -> x.getFontSetting().getSizeAndColor().isBold(), x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.as("testFull fileAttachment").containsExactly(
						tuple("fileId", "content", 0, 0, Optional.empty(), Optional.empty(), 0, false, 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getImageSettings())
				.extracting(x -> x.getFileId().isPresent() ? x.getFileId().get() : x.getFileId(),
						x -> x.getFileName().isPresent() ? x.getFileName().get().v() : x.getFileName(),
						x -> x.getIsFixed().value, x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.as("testFull image")
				.containsExactly(
						tuple("fileId", Optional.empty(), 1, 0, 0, 0, 0),
						tuple(Optional.empty(), "fileName", 1, 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getLabelSettings())
				.extracting(x -> x.getLabelContent().get().v(),
						x -> x.getFontSetting().getPosition().getHorizontalPosition().value,
						x -> x.getFontSetting().getPosition().getVerticalPosition().value,
						x -> x.getFontSetting().getSizeAndColor().getBackgroundColor().get().v(),
						x -> x.getFontSetting().getSizeAndColor().getFontColor().get().v(),
						x -> x.getFontSetting().getSizeAndColor().getFontSize().v(),
						x -> x.getFontSetting().getSizeAndColor().isBold(), x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.as("testFull label")
				.containsExactly(tuple("content", 0, 0, "bgColor", "textColor", 0, false, 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getLinkSettings())
				.extracting(x -> x.getLinkContent().get(), x -> x.getUrl().v(),
						x -> x.getFontSetting().getPosition().getHorizontalPosition().value,
						x -> x.getFontSetting().getPosition().getVerticalPosition().value,
						x -> x.getFontSetting().getSizeAndColor().getBackgroundColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontSize().v(),
						x -> x.getFontSetting().getSizeAndColor().isBold(), x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.as("testFull link").containsExactly(
						tuple("content", "url", 0, 0, Optional.empty(), Optional.empty(), 0, false, 0, 0, 0, 0));
		assertThat(domain.getFlowMenuLayout().get().getMenuSettings())
				.extracting(x -> x.getMenuClassification().value, x -> x.getMenuCode().v(), x -> x.getMenuName().v(),
						x -> x.getSystemType().value,
						x -> x.getFontSetting().getPosition().getHorizontalPosition().value,
						x -> x.getFontSetting().getPosition().getVerticalPosition().value,
						x -> x.getFontSetting().getSizeAndColor().getBackgroundColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontColor(),
						x -> x.getFontSetting().getSizeAndColor().getFontSize().v(),
						x -> x.getFontSetting().getSizeAndColor().isBold(), x -> x.getSizeAndPosition().getColumn().v(),
						x -> x.getSizeAndPosition().getHeight().v(), x -> x.getSizeAndPosition().getRow().v(),
						x -> x.getSizeAndPosition().getWidth().v())
				.as("testFull menu").containsExactly(tuple(1, "menuCode", "menuName", 3, 0, 0, Optional.empty(),
						Optional.empty(), 0, false, 0, 0, 0, 0));
	}

	@Test
	public void testFlowMenuEmpty() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		dto.setFlowMenuCode("");
		dto.setFlowMenuName("");
		dto.setCid("cid");
		dto.setContractCode("contracCd");
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);

		assertThat(domain.getCid()).as("testEmpty cid").isEqualTo("cid");
		assertThat(domain.getFlowMenuCode().v()).as("testEmpty flowMenuCode").isEqualTo("");
		assertThat(domain.getFlowMenuName().v()).as("testEmpty flowMenuName").isEqualTo("");
		assertThat(domain.getFlowMenuLayout()).as("testEmpty flowMenuLayout").isEqualTo(Optional.empty());
	}

	@Test
	public void testFileFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		FileAttachmentSettingDto newData = new FileAttachmentSettingDto(null, null, 0, 0, null, null, 0, 0, 1, 1, 0, 0);
		dto.setFileAttachmentSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		SizeAndColor data = dto.getFileAttachmentSettings().get(0).getFontSetting().getSizeAndColor();

		assertThat(data.getBackgroundColor()).as("testFileFont backgroundColor").isEqualTo(Optional.empty());
		assertThat(data.getFontColor()).as("testFileFont fontColor").isEqualTo(Optional.empty());
		assertThat(data.getFontSize().v()).as("testFileFont fontSize").isEqualTo(1);
		assertThat(data.isBold()).as("testFileFont bold").isEqualTo(true);
	}

	@Test
	public void testLabelFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		LabelSettingDto newData = new LabelSettingDto(null, null, 0, 0, null, 0, 0, 1, 1, null, null, 0, 0);
		dto.setLabelSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		SizeAndColor data = dto.getLabelSettings().get(0).getFontSetting().getSizeAndColor();
		
		assertThat(data.getBackgroundColor()).as("testLabelFont backgroundColor").isEqualTo(Optional.empty());
		assertThat(data.getFontColor()).as("testLabelFont fontColor").isEqualTo(Optional.empty());
		assertThat(data.getFontSize().v()).as("testLabelFont fontSize").isEqualTo(1);
		assertThat(data.isBold()).as("testLabelFont bold").isEqualTo(true);
	}

	@Test
	public void testLinkFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		LinkSettingDto newData = new LinkSettingDto(null, null, 0, 0, null, null, 0, 0, 1, 1, 0, 0);
		dto.setLinkSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		SizeAndColor data = dto.getLinkSettings().get(0).getFontSetting().getSizeAndColor();
		
		assertThat(data.getBackgroundColor()).as("testLinkFont backgroundColor").isEqualTo(Optional.empty());
		assertThat(data.getFontColor()).as("testLinkFont fontColor").isEqualTo(Optional.empty());
		assertThat(data.getFontSize().v()).as("testLinkFont fontSize").isEqualTo(1);
		assertThat(data.isBold()).as("testLinkFont bold").isEqualTo(true);
	}

	@Test
	public void testMenuFontEmptyWithBold() {
		CreateFlowMenuDto dto = new CreateFlowMenuDto();
		domainAll.setMemento(dto, "contractCd");
		MenuSettingDto newData = new MenuSettingDto(null, null, 0, 0, null, null, 0, 0, 0, 0, 1, 1, 0, 0);
		dto.setMenuSettings(Arrays.asList(newData));
		CreateFlowMenu domain = CreateFlowMenu.createFromMemento(dto);
		domain.setMemento(dto, "contractCode");
		SizeAndColor data = dto.getMenuSettings().get(0).getFontSetting().getSizeAndColor();
		
		assertThat(data.getBackgroundColor()).as("testMenuFont backgroundColor").isEqualTo(Optional.empty());
		assertThat(data.getFontColor()).as("testMenuFont fontColor").isEqualTo(Optional.empty());
		assertThat(data.getFontSize().v()).as("testMenuFont fontSize").isEqualTo(1);
		assertThat(data.isBold()).as("testMenuFont bold").isEqualTo(true);
	}
}
