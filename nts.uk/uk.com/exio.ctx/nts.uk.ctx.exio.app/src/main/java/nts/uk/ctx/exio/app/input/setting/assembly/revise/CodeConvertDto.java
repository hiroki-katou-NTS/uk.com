package nts.uk.ctx.exio.app.input.setting.assembly.revise;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.CodeConvertDetail;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.ExternalImportCodeConvert;

@Value
public class CodeConvertDto {
	
	boolean importWithoutSetting;
	
	List<Detail> details;
	
	public static CodeConvertDto of(ExternalImportCodeConvert domain) {
		return new CodeConvertDto(
				domain.isImportWithoutSetting(),
				domain.getConvertDetails().stream().map(Detail::of).collect(toList()));
	}

	@Value
	public static class Detail {
		String before;
		String after;
		
		public static Detail of(CodeConvertDetail domain) {
			return new Detail(domain.getBefore().v(), domain.getAfter().v());
		}
	}
}
