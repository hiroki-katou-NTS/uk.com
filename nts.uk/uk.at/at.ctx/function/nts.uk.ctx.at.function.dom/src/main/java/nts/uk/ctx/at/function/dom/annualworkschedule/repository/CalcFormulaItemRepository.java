package nts.uk.ctx.at.function.dom.annualworkschedule.repository;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;

import java.util.List;

/**
* 項目の算出式
*/
public interface CalcFormulaItemRepository
{

    List<CalcFormulaItem> getAllCalcFormulaItem();

    Optional<CalcFormulaItem> getCalcFormulaItemById(String cid);

    void add(CalcFormulaItem domain);

    void update(CalcFormulaItem domain);

    void remove(String cid);

}
