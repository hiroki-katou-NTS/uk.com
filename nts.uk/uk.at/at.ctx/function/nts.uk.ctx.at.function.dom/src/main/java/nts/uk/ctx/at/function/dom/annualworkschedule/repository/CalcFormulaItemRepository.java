package nts.uk.ctx.at.function.dom.annualworkschedule.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;

/**
* 項目の算出式
*/
public interface CalcFormulaItemRepository
{

    List<CalcFormulaItem> getAllCalcFormulaItem();

    Optional<CalcFormulaItem> getCalcFormulaItemById(String cid, String setOutCd, String itemOutCd, int attendanceItemId);

    void add(CalcFormulaItem domain);

    void update(CalcFormulaItem domain);

    void remove(String cid, String setOutCd, String itemOutCd, int attendanceItemId);

}
