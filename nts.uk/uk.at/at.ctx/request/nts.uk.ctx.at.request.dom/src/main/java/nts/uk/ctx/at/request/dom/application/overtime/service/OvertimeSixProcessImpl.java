package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;

public class OvertimeSixProcessImpl implements OvertimeSixProcess{

	@Override
	public void checkDisplayColor(List<OverTimeInput> overTimeInputs,
			List<OvertimeInputCaculation> overtimeInputCaculations) {
		for(OverTimeInput overtimeInput : overTimeInputs ){
			for(OvertimeInputCaculation overtimeInputCaculation : overtimeInputCaculations){
				if(overtimeInput.getAttendanceID().value == overtimeInputCaculation.getAttendanceID()){
					if(overtimeInput.getFrameNo() == overtimeInputCaculation.getFrameNo()){
						if(overtimeInput.getStartTime().v() == overtimeInputCaculation.getResultCaculation()){
							if(overtimeInput.getStartTime().v() == 0){
								continue;
							}else if(overtimeInput.getStartTime().v() > 0){
								// 03-01_事前申請超過チェック
								// 06-04_計算実績超過チェック
							}
						}else{
							// in màu
						}
					}
				}
			}
		}
		
	}

}
