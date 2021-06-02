package nts.uk.ctx.at.function.dom.adapter.standardmenu;

import java.util.List;

public interface StandardMenuAdaptor {
	// [No.675]メニューの表示名を取得する
		/**
		 * 
		 * @param companyId 会社ID
		 * @param  List<StandardMenuNameQuery> query
		 * @return List<StandardMenuNameExport>
		 */
		public List<StandardMenuNameImport> getMenuDisplayName(String companyId, List<StandardMenuNameQueryImport> query);

		List<StandardMenuNameImport> getMenus(String companyId, int system);
}
