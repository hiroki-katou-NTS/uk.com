package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcResultOfAnyItem;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxRemainingDay;

/** 日別実績の任意項目*/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnyItemValueOfDaily {
	
	private String employeeId;
	
	private GeneralDate ymd;

	/** 任意項目値: 任意項目値 */
	private List<AnyItemValue> items;
	
	
	
    /**
     * 任意項目の計算
     * @return
     */
    public static AnyItemValueOfDaily caluculationAnyItem(String companyId,
    											   		  String employeeId,
    											   		  GeneralDate ymd,
    											   		  List<OptionalItem> optionalItemList,
    											   		  List<Formula> formulaList,
    											   		  List<EmpCondition> empConditionList,
    											   		  Optional<DailyRecordToAttendanceItemConverter> dailyRecordDto,
    											   		  Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt,
    											   		  Optional<AnyItemValueOfDaily> anyItemValueOfDaily) {
    	
               
        //任意項目分ループ
        List<CalcResultOfAnyItem> anyItemList = new ArrayList<>();
        
        for(OptionalItem optionalItem : optionalItemList) {
        	
        	Optional<EmpCondition> empCondition = Optional.empty();
        	List<EmpCondition> findResult = empConditionList.stream().filter(t -> t.getOptItemNo().equals(optionalItem.getOptionalItemNo())).collect(Collectors.toList());
        	if(!findResult.isEmpty()) {
        		empCondition = Optional.of(findResult.get(0));
        	}
        	
        	//利用条件の判定
        	if(optionalItem.checkTermsOfUse(empCondition,bsEmploymentHistOpt)) {
        		List<Formula> test = formulaList.stream().filter(t -> t.getOptionalItemNo().equals(optionalItem.getOptionalItemNo())).collect(Collectors.toList());
        		//計算処理
                anyItemList.add(optionalItem.caluculationFormula(companyId, optionalItem, test, dailyRecordDto, Optional.empty()));
        	}
        }
        
        AnyItemValueOfDaily result = new AnyItemValueOfDaily(employeeId,ymd,new ArrayList<>());
        
        for(CalcResultOfAnyItem calcResultOfAnyItem:anyItemList) {
        	int itemNo = calcResultOfAnyItem.getOptionalItemNo().v();
        	result.getItems().add(new AnyItemValue(new AnyItemNo(itemNo),
					  							   calcResultOfAnyItem.getCount().map(v -> new AnyItemTimes(BigDecimal.valueOf(v.doubleValue()))),
					  							   calcResultOfAnyItem.getMoney().map(v -> new AnyItemAmount(v.intValue())),
					  							   calcResultOfAnyItem.getTime().map(v -> new AnyItemTime(v.intValue()))));
    	}

        return result;
    }

    public Optional<AnyItemValue> getNo(int no) {
    	return items.stream().filter(i -> i.getItemNo().v() == no).findFirst();
    }
}
