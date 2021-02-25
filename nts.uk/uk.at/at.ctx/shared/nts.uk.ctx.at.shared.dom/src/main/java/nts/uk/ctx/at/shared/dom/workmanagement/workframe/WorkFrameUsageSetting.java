package nts.uk.ctx.at.shared.dom.workmanagement.workframe;


import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AggregateRoot : 作業枠利用設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業枠.作業枠利用設定
 */
public class WorkFrameUsageSetting extends AggregateRoot{
    // 枠設定 : 作業枠設定
    private List<WorkFrameSetting> frameSettingList;
    private static final int MAX_NO = 5;

    public WorkFrameUsageSetting(List<WorkFrameSetting> frameSettingList ){

        this.frameSettingList = frameSettingList;
        //[	inv-1] case @枠設定.作業枠NO　は重複してはいけない
        List<Integer> taskFrameNo = frameSettingList.stream().map(e->e.getTaskFrameNo().v()).collect(Collectors.toList());
        List<Integer> dps = taskFrameNo.stream().distinct()
                .filter(entry -> Collections.frequency(taskFrameNo, entry) > 1).collect(Collectors.toList());
        if(!dps.isEmpty()){
            throw new BusinessException("Msg_2064");
        }
        // inv-2] 	case 上位枠の「@枠設定.利用区分 = しない」の場合、「@枠設定.利用区分 = する」に設定できない
        Optional<WorkFrameSetting> itemNo05 = frameSettingList.stream().filter(x->x.getTaskFrameNo().v() == 5).findFirst();
        Optional<WorkFrameSetting> itemNo04 = frameSettingList.stream().filter(x->x.getTaskFrameNo().v() == 4).findFirst();
        Optional<WorkFrameSetting> itemNo03 = frameSettingList.stream().filter(x->x.getTaskFrameNo().v() == 3).findFirst();
        Optional<WorkFrameSetting> itemNo02 = frameSettingList.stream().filter(x->x.getTaskFrameNo().v() == 2).findFirst();
        Optional<WorkFrameSetting> itemNo01 = frameSettingList.stream().filter(x->x.getTaskFrameNo().v() == 1).findFirst();

        itemNo05.ifPresent(i->{
            itemNo04.ifPresent(j->{
                if(i.getUseAtr() == UseAtr.USE && j.getUseAtr() == UseAtr.NOTUSE){
                    throw new BusinessException("Msg_2063",j.getWorkFrameName().v(),i.getWorkFrameName().v());
                }
            });
        });

        itemNo04.ifPresent(i->{
            itemNo03.ifPresent(j->{
                if(i.getUseAtr() == UseAtr.USE && j.getUseAtr() == UseAtr.NOTUSE){
                    throw new BusinessException("Msg_2063",j.getWorkFrameName().v(),i.getWorkFrameName().v());
                }
            });
        });

        itemNo03.ifPresent(i->{
            itemNo02.ifPresent(j->{
                if(i.getUseAtr() == UseAtr.USE && j.getUseAtr() == UseAtr.NOTUSE){
                    throw new BusinessException("Msg_2063",j.getWorkFrameName().v(),i.getWorkFrameName().v());
                }
            });
        });

        itemNo02.ifPresent(i->{
            itemNo01.ifPresent(j->{
                if(i.getUseAtr() == UseAtr.USE && j.getUseAtr() == UseAtr.NOTUSE){
                    throw new BusinessException("Msg_2063",j.getWorkFrameName().v(),i.getWorkFrameName().v());
                }
            });
        });

        Comparator<WorkFrameSetting> compareByTaskFrameNo = Comparator.comparing(o -> o.getTaskFrameNo().v());
        frameSettingList.sort(compareByTaskFrameNo);

    }

}
