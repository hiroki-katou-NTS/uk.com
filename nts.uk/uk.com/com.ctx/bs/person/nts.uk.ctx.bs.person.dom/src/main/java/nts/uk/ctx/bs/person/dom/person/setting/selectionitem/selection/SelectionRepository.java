package nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection;

public interface SelectionRepository {

	void add(Selection selection);

	void update(Selection selection);

	void remove(String selectionId);
}
