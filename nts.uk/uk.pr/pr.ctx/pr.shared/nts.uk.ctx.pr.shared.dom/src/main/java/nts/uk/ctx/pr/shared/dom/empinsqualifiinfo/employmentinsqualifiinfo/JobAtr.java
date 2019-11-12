package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;
import nts.arc.i18n.I18NText;

/**
 * 雇用職種区分
 */
public enum JobAtr {
    MANAGER(0, I18NText.getText("")),
    TECHNICAL(1, I18NText.getText("")),
    ADMINISTRATIVE(2, I18NText.getText("")),
    SALES(3, I18NText.getText("")),
    SERVICE(4, I18NText.getText("")),
    SECURITY(5, I18NText.getText("")),
    AGRICULTURE_FORESTRY_FISHERY(6, I18NText.getText("")),
    PRODUCTION(7, I18NText.getText("")),
    TRANSPORT_MACHINE_OPERATION(8, I18NText.getText("")),
    CONSTRUCTION_MINING(9, I18NText.getText("")),
    TRANSPORTATION_CLEANING_PACKAGING(10, I18NText.getText(""));

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
