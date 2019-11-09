package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.*;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InsLossDataExport {

    private String empCd;

    private String officeCd;

    private Integer other;

    private String otherReason;

    private Integer caInsurance;

    private Integer numRecoved;

    private Integer cause;

    private String percentOrMore;

    private String remarksOther;

    private String remarksAndOtherContent;

    private Integer remunMonthlyAmountKind;

    private Integer remunMonthlyAmount;

    private Integer totalMonthyRemun;

    private String livingAbroad;

    private String resonOther;

    private String resonAndOtherContent;

    private String shortTimeWorkes;

    private String shortStay;

    private String depenAppoint;

    private String qualifiDistin;

    private int continReemAfterRetirement;

    private int isMoreEmp;

    private String basicPenNumber;

    private String personName;

    private String personNameKana;

    private String oldName;

    private String birthDay;

    private String endDate;

    private String healInsNumber;

    private int prefectureNo;

    private String officeNumber1;

    private String officeNumber2;

    private String officeNumber;

    private String unionOfficeNumber;

    private String portCd;

    private String add1;

    private String add2;

    private String companyName;

    private String repName;

    private String phoneNumber;

    private String oldNameKana;

    private String welfOfficeNumber1;

    private String welfOfficeNumber2;

    private String welfOfficeNumber;

    private String welfPenNumber;

    private String healInsUnionNumber;

    private String memberNumber;

    private String healInsInherenPr;

    private String endDate2;

    private Integer other2;

    private String otherReason2;

    private Integer caInsurance2;

    private Integer numRecoved2;

    private Integer cause2;

    private Integer insPerCls;

    private int underSeventy;

    private String empId;

    public static PersonExport getPersonInfor(List<EmployeeInfoEx> employeeInfoList, List<PersonExport> personList, String empId){
        PersonExport person = new PersonExport();
        Optional<EmployeeInfoEx> employeeInfoEx = employeeInfoList.stream().filter(item -> item.getEmployeeId().equals(empId)).findFirst();
        if(employeeInfoEx.isPresent()) {
            Optional<PersonExport> personEx = personList.stream().filter(item -> item.getPersonId().equals(employeeInfoEx.get().getPId())).findFirst();
            if (personEx.isPresent()){
                person.setGender(personEx.get().getGender());
                person.setPersonId(personEx.get().getPersonId());
                person.setPersonNameGroup(personEx.get().getPersonNameGroup());
                person.setBirthDate(personEx.get().getBirthDate());
            }
        }
        return person;
    }

}
