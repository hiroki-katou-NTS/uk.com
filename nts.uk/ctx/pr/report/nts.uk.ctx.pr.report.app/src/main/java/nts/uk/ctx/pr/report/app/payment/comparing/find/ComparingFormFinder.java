package nts.uk.ctx.pr.report.app.payment.comparing.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.comparing.CategoryAtr;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormDetailRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.ItemMasterAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComparingFormFinder {
	@Inject
	private ItemMasterAdapter itemMasterPub;
	@Inject
	private ComparingFormDetailRepository formDetailRepository;

	public ComparingFormDto findComparingForm(String formCode) {

		String companyCode = AppContexts.user().companyCode();
		ComparingFormDto comparingFormDto = new ComparingFormDto();

		List<ComparingItemMasterDto> itemMasterTab_0 = this.itemMasterPub
				.findItemMasterByCatergory(CategoryAtr.PAYMENT.value).stream()
				.map(item -> ComparingItemMasterDto.fromDomain(item)).collect(Collectors.toList());
		List<ComparingItemMasterDto> itemMasterTab_1 = this.itemMasterPub
				.findItemMasterByCatergory(CategoryAtr.DEDUCTION.value).stream()
				.map(item -> ComparingItemMasterDto.fromDomain(item)).collect(Collectors.toList());
		List<ComparingItemMasterDto> itemMasterTab_3 = this.itemMasterPub
				.findItemMasterByCatergory(CategoryAtr.ARTICLES.value).stream()
				.map(item -> ComparingItemMasterDto.fromDomain(item)).collect(Collectors.toList());

		List<String> selectForTab_0 = this.formDetailRepository
				.getComparingFormDetailByCategory_Atr(companyCode, formCode, CategoryAtr.PAYMENT.value).stream()
				.map(item -> item.getItemCode().v()).collect(Collectors.toList());
		List<String> selectForTab_1 = this.formDetailRepository
				.getComparingFormDetailByCategory_Atr(companyCode, formCode, CategoryAtr.DEDUCTION.value).stream()
				.map(item -> item.getItemCode().v()).collect(Collectors.toList());
		List<String> selectForTab_3 = this.formDetailRepository
				.getComparingFormDetailByCategory_Atr(companyCode, formCode, CategoryAtr.ARTICLES.value).stream()
				.map(item -> item.getItemCode().v()).collect(Collectors.toList());

		comparingFormDto.setLstItemMasterForTab_0(itemMasterTab_0);
		comparingFormDto.setLstItemMasterForTab_1(itemMasterTab_1);
		comparingFormDto.setLstItemMasterForTab_3(itemMasterTab_3);
		comparingFormDto.setLstSelectForTab_0(selectForTab_0);
		comparingFormDto.setLstSelectForTab_1(selectForTab_1);
		comparingFormDto.setLstSelectForTab_3(selectForTab_3);

		return comparingFormDto;
	}

}
