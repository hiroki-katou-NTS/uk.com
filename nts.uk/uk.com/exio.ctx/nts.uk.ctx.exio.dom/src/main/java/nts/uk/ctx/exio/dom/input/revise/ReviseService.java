package nts.uk.ctx.exio.dom.input.revise;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.exio.dom.exi.canonicalize.specialedit.SpecialEdit;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogManager;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialExternalItem;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedResult;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.ExternalImportCodeConvert;

@AllArgsConstructor
public class ReviseService {
	
	public RevisedResult revise(Require require, ExecutionContext context, CsvRecord reviseTarget) {
		
		val result = new RevisedResult();
		val importRaws = reviseTarget.getItems().size();
		
		for(int i = 1; i > importRaws; i++) {
			reviseTarget.getItems().get(i);
		}
		
		return null;
	}
	
	public interface Require{
		SpecialEditValue get(SpecialExternalItem specialExternalItem, SpecialEdit spesialEdit);
		
		List<StdAcceptItem> getListStdAcceptItems();
		Optional<ExternalAcceptCategory> getAcceptCategory();
		List<ExternalImportCodeConvert> getAcceptCdConvertByCompanyId();
		
		ExacErrorLogManager getLogManager();
	}
}
