package nts.uk.ctx.at.function.app.find.attendancetype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AttendanceTypeFinder {
    @Inject
    private AttendanceTypeRepository attendanceTypeRepository;
    /**
     * get attendance infor by screen use atr
     * @param screenUseAtr
     * @return
     */
    public List<AttendanceTypeDto> getItemByScreenUseAtr(int screenUseAtr){
        String companyID = AppContexts.user().companyId();
        return this.attendanceTypeRepository.getItemByScreenUseAtr(companyID, screenUseAtr).stream()
            .map(x -> new AttendanceTypeDto(x.getCompanyId(), x.getAttendanceItemId(), x.getAttendanceItemType().value))
            .collect(Collectors.toList());
    }

}
