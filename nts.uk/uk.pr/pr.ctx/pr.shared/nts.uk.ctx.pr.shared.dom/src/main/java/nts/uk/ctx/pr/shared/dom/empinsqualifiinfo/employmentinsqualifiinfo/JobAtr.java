package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;
import nts.arc.i18n.I18NText;

/**
 * 雇用職種区分
 */
public enum JobAtr {
    MANAGER(0, I18NText.getText("Enum_JobAtr_MANAGER")),
    TECHNICAL(1, I18NText.getText("Enum_JobAtr_TECHNICAL")),
    ADMINISTRATIVE(2, I18NText.getText("Enum_JobAtr_ADMINISTRATIVE")),
    SALES(3, I18NText.getText("Enum_JobAtr_SALES")),
    SERVICE(4, I18NText.getText("Enum_JobAtr_SERVICE")),
    SECURITY(5, I18NText.getText("Enum_JobAtr_SECURITY")),
    AGRICULTURE_FORESTRY_FISHERY(6, I18NText.getText("Enum_JobAtr_AGRICULTURE_FORESTRY_FISHERY")),
    PRODUCTION(7, I18NText.getText("Enum_JobAtr_PRODUCTION")),
    TRANSPORT_MACHINE_OPERATION(8, I18NText.getText("Enum_JobAtr_TRANSPORT_MACHINE_OPERATION")),
    CONSTRUCTION_MINING(9, I18NText.getText("Enum_JobAtr_CONSTRUCTION_MINING")),
    TRANSPORTATION_CLEANING_PACKAGING(10, I18NText.getText("Enum_JobAtr_TRANSPORTATION_CLEANING_PACKAGING"));

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    JobAtr(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
