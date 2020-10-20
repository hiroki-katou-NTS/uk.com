package nts.uk.ctx.sys.auth.dom.personal.contact;

import java.util.Optional;

/**
 * Repository 個人連絡先
 */
public interface PersonalContactRepository {
    /**
     * Add new PersonalContact
     *
     * @param personalContact
     */
    void insert(PersonalContact personalContact);

    /**
     * Update PersonalContact
     *
     * @param personalContact
     */
    void update(PersonalContact personalContact);

    /**
     * Find PersonalContact by personalId
     *
     * @param personalId
     */
    Optional<PersonalContact> getByPersonalId(String personalId);
}
