package nts.uk.ctx.bs.employee.app.query.hospitalofficeinfo;

import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.HospitalBusinessOfficeInfoHistory;
import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.HospitalBusinessOfficeInfoHistoryRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Query: 夜勤時間帯履歴一覧を取得する
 */
@Stateless
public class GetAListOfNightShiftHistoryQuery {
    @Inject
    private HospitalBusinessOfficeInfoHistoryRepository officeInfoHistoryRepository;

    public Optional<HospitalBusinessOfficeInfoHistory> getHospitalBusinessOfficeInfo(String workplaceGroupId){

     return officeInfoHistoryRepository.getHospitalBusinessOfficeInfoHistory(workplaceGroupId);
    }
}
