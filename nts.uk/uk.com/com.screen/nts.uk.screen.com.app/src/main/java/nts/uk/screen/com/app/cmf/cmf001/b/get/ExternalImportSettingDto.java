package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingMapping;
import nts.uk.shr.com.context.AppContexts;

@Value
public class ExternalImportSettingDto {

	/** 会社ID */
	private String companyId;

	/** 受入設定コード */
	private String code;

	/** 受入設定名称 */
	private String name;

	/** 受入ドメインID */
	private int domain;

	/** 受入モード */
	private int mode;

	/** CSVの項目名取得行 */
	private int itemNameRow;

	/** CSVの取込開始行 */
	private int importStartRow;

	/** レイアウト項目リスト */
	private List<Integer> itemNoList;

	public void merge(ExternalImportSetting domain) {

		domain.setName(new ExternalImportName(name));
		domain.setImportingMode(ImportingMode.valueOf(mode));
		domain.getAssembly().setCsvFileInfo(toCsvFileInfo());

		if(this.domain == domain.getExternalImportDomainId().value) {

		}else {
			domain.changeDomain(ImportingDomainId.valueOf(this.domain), itemNoList);
		}


	}

	public static ExternalImportSettingDto fromDomain(ExternalImportSetting domain) {

		return new ExternalImportSettingDto(
				domain.getCompanyId(),
				domain.getCode().toString(),
				domain.getName().toString(),
				domain.getExternalImportDomainId().value,
				domain.getImportingMode().value,
				domain.getAssembly().getCsvFileInfo().getItemNameRowNumber().hashCode(),
				domain.getAssembly().getCsvFileInfo().getImportStartRowNumber().hashCode(),
				domain.getAssembly().getMapping().getMappings().stream()
				.map(m -> m.getItemNo())
				.collect(Collectors.toList()));
	}

	public ExternalImportSetting toDomain() {
		return new ExternalImportSetting(
				AppContexts.user().companyId(),
				new ExternalImportCode(code),
				new ExternalImportName(name),
				ImportingDomainId.valueOf(domain),
				ImportingMode.valueOf(mode),
				ExternalImportAssemblyMethod.create(
						toCsvFileInfo(),
						itemNoList));
	}

	private ExternalImportCsvFileInfo toCsvFileInfo() {
		return new ExternalImportCsvFileInfo(
				new ExternalImportRowNumber(itemNameRow),
				new ExternalImportRowNumber(importStartRow));
	}

}
