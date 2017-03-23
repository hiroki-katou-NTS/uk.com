package nts.uk.ctx.pr.formula.app.find.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.app.find.formulaeasycondition.EasyFormulaConditionDto;
import nts.uk.ctx.pr.formula.app.find.formulaeasycondition.FormulaEasyConditionDto;
import nts.uk.ctx.pr.formula.app.find.formulaeasydetail.FormulaEasyDetailDto;
import nts.uk.ctx.pr.formula.app.find.formulaeasystandarditem.FormulaEasyStandardItemDto;
import nts.uk.ctx.pr.formula.app.find.formulamanual.FormulaManualDto;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyConditionRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyStandardItemRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaManualRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author Nam-PT activity 8 C screen
 *
 */
@Stateless
public class FormulaSettingFinder {

	@Inject
	FormulaEasyStandardItemRepository formulaEasyStandardItemRepository;

	@Inject
	FormulaEasyConditionRepository formulaEasyConditionRepository;

	@Inject
	FormulaEasyDetailRepository formulaEasyDetailRepository;

	@Inject
	FormulaManualRepository formulaManualRepository;

	public FormulaSettingDto init(String formulaCode, String historyId, int difficultyAtr) {

		LoginUserContext login = AppContexts.user();
		FormulaSettingDto formulaSettingDto = new FormulaSettingDto();

		if (difficultyAtr == 0) {
			// かんたん計算_条件.SEL-1
			Optional<FormulaEasyConditionDto> formulaEasyConditions = formulaEasyConditionRepository
					.find(login.companyCode(), new FormulaCode(formulaCode), historyId).map(f -> {
						FormulaEasyConditionDto formulaEasy = new FormulaEasyConditionDto();
						formulaEasy.setFixFormulaAtr(f.getFixFormulaAtr().value);
						List<EasyFormulaConditionDto> easyFormulaConditionDtos = new ArrayList<>();
						EasyFormulaConditionDto conditionDto = new EasyFormulaConditionDto();
						conditionDto.setEasyFormulaCode(f.getEasyFormulaCode().v());
						conditionDto.setFixMoney(f.getFixMoney().v());
						conditionDto.setReferenceMasterCode(f.getReferenceMasterCode().v());
						easyFormulaConditionDtos.add(conditionDto);
						formulaEasy.setEasyFormulaConditionDto(easyFormulaConditionDtos);
						return formulaEasy;
					});

			List<EasyFormulaFindDto> easyFormula = new ArrayList<>();
			formulaEasyConditions.get().getEasyFormulaConditionDto().stream().map(f -> {
				EasyFormulaFindDto easyFormulaFindDto = new EasyFormulaFindDto();
				easyFormulaFindDto.setEasyFormulaCode(f.getEasyFormulaCode());
				easyFormulaFindDto.setValue(f.getFixMoney());
				easyFormulaFindDto.setRefMasterNo(f.getReferenceMasterCode());
				easyFormulaFindDto.setFixFormulaAtr(formulaEasyConditions.get().getFixFormulaAtr());
				
				List<FormulaEasyDetailDto> formulaEasyDetailDtos = formulaEasyDetailRepository
						.findWithOutPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId).stream()
						.map(formulaEasyDetail -> FormulaEasyDetailDto.fromDomain(formulaEasyDetail)).collect(Collectors.toList());
				
				FormulaEasyFinderDto formulaEasyFinderDto = new FormulaEasyFinderDto();
				formulaEasyDetailDtos.stream().map(formula -> {
					formulaEasyFinderDto.setEasyFormulaCode(formula.getEasyFormulaCode());
					formulaEasyFinderDto.setEasyFormulaName(formula.getEasyFormulaName());
					formulaEasyFinderDto.setBaseFixedAmount(formula.getBaseFixedAmount());
					formulaEasyFinderDto.setBaseAmountDevision(formula.getBaseAmountDevision());
					formulaEasyFinderDto.setBaseFixedAmount(formula.getBaseFixedAmount());
					formulaEasyFinderDto.setBaseValueDevision(formula.getBaseValueDevision());
					formulaEasyFinderDto.setPremiumRate(formula.getPremiumRate());
					formulaEasyFinderDto.setRoundProcessingDevision(formula.getRoundProcessingDevision());
					formulaEasyFinderDto.setCoefficientDivision(formula.getCoefficientDivision());
					formulaEasyFinderDto.setCoefficientFixedValue(formula.getCoefficientFixedValue());
					formulaEasyFinderDto.setAdjustmentDevision(formula.getAdjustmentDevision());
					formulaEasyFinderDto.setTotalRounding(formula.getTotalRounding());
					formulaEasyFinderDto.setMaxLimitValue(formula.getMaxLimitValue());
					formulaEasyFinderDto.setMinLimitValue(formula.getMinLimitValue());					

					// Select reference Code from FormulaEasyStandardItem
					List<FormulaEasyStandardItemDto> formulaEasyStandardItemDtos = formulaEasyStandardItemRepository
							.findAll(login.companyCode(), new FormulaCode(formulaCode), historyId, new EasyFormulaCode(f.getEasyFormulaCode()))
							.stream().map(dto -> FormulaEasyStandardItemDto.fromDomain(dto)).collect(Collectors.toList());
					
					// set reference Code in List<String> referenceItemCodes
					formulaEasyFinderDto.setReferenceItemCodes(formulaEasyStandardItemDtos.stream().map(easyStandard -> {
						return easyStandard.getReferenceItemCode();
					}).collect(Collectors.toList()));
					return formulaEasyFinderDto;
				});
				
				easyFormulaFindDto.setFormulaEasyDetail(formulaEasyFinderDto);
				
				easyFormula.add(easyFormulaFindDto);
				return easyFormula;
			});
			formulaSettingDto.setEasyFormula(easyFormula);

		} else if (difficultyAtr == 1) {
			Optional<FormulaManualDto> formulaManualDto = formulaManualRepository
					.findByPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId)
					.map(f -> FormulaManualDto.fromDomain(f));

			formulaSettingDto.setFormulaContent(formulaManualDto.get().getFormulaContent());
			formulaSettingDto.setReferenceMonthAtr(formulaManualDto.get().getReferenceMonthAtr());
			formulaSettingDto.setRoundAtr(formulaManualDto.get().getRoundAtr());
			formulaSettingDto.setRoundDigit(formulaManualDto.get().getRoundDigit());
		}

		return formulaSettingDto;
	}

}
