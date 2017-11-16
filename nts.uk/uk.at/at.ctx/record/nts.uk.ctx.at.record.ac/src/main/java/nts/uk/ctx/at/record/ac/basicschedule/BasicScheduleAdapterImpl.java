package nts.uk.ctx.at.record.ac.basicschedule;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.WorkScheduleSidDto;

@Stateless
public class BasicScheduleAdapterImpl implements BasicScheduleAdapter{

	@Override
	public List<BasicScheduleSidDto> findAllBasicSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkScheduleSidDto> findAllWorkSchedule() {
		// TODO Auto-generated method stub
		return null;
	}



}
