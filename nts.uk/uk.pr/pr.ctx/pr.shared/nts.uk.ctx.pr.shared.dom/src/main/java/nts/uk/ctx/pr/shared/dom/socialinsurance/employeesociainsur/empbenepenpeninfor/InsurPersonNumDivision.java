package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;
 /**
 * 被保険者整理番号区分
 */
public enum InsurPersonNumDivision {
     DO_NOT_OUPUT(0, "ENUM_doNotOutput_DO_NOT_OUPUT"),
     OUTPUT_HEAL_INSUR_NUM(0, "ENUM_outputhealIsurNum_OUTPUT_HEAL_INSUR_NUM"),
     OUTPUT_THE_WELF_PENNUMBER(0, "ENUM_outputTheWelfPenNumber_OUTPUT_THE_WELF_PENNUMBER"),
     OUTPUT_HEAL_INSUR_UNION(0, "ENUM_outputHealInsurUnion_OUTPUT_HEAL_INSUR_UNION"),
     OUTPUT_THE_FUN_MEMBER(1, "ENUM_outputTheFundMember_OUTPUT_THE_FUN_MEMBER");

     /** The value. */
     public final int value;

     /** The name id. */
     public final String nameId;
     private InsurPersonNumDivision(int value, String nameId)
     {
         this.value = value;
         this.nameId = nameId;
     }
}
