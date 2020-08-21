package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;

@Getter
@Setter
@NoArgsConstructor
/**
 * 一日分の反映イメージ
 */
public class RefImageEachDay {

    // 勤務作成方法
    private WorkCreateMethod workCreateMethod;

    // 勤務情報
    private WorkInformation workInformation;

    // 年月日
    private GeneralDate date;

    public RefImageEachDay(int workCreateMothod, WorkInformation workInformation, GeneralDate date){
        this.workCreateMethod = EnumAdaptor.valueOf(workCreateMothod, WorkCreateMethod.class);
        this.workInformation = workInformation;
        this.date = date;
    }

}
